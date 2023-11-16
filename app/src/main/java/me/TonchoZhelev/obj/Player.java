package me.TonchoZhelev.obj;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.Timer;

import me.Toncho.Zhelev.input.KeyInput;
import me.Toncho.Zhelev.input.MouseInput;
import me.TonchoZhelev.enums.GameState;
import me.TonchoZhelev.enums.PowerupType;
import me.TonchoZhelev.enums.Projectile_type;
import me.TonchoZhelev.levels.LevelLoader;
import me.TonchoZhelev.levels.LoadedLevel;
import me.TonchoZhelev.libs.Images;
import me.TonchoZhelev.libs.Sounds;
import me.TonchoZhelev.main.MainFrame;
import me.TonchoZhelev.sfx.SoundPlayer;

public class Player extends globvar{
	
	private boolean imgdir;
	
	//timer limits the bullets per second
	private Timer shoot_timer;
	
	//timer responsible for animation when taking damage
	private Timer TDT;
	//boolean responsible for the change of image and an int that keeps the number of times the img has been changed
	private boolean chimg=false;
	private int imgchcic=0;
	
	private int shoot_milis=300;
	
	private int charge=50;
	private int maxcharge=100;
	
	//permission to shoot
	private boolean shp=true;
	
	//special shoot limiter
	private boolean ssl=false;
	
	private int score=0;
	
	private int sprite_index=0;
	
	private SoundPlayer splayer;
	private SoundPlayer walking_sound_player;
	
	public CopyOnWriteArrayList<Projectile> tomatoes = new CopyOnWriteArrayList<Projectile>();
	
	public boolean dead;
	
	Image img = Images.pig_wallking.get(sprite_index);
	
	private boolean open_mouth=false;
	private Timer open_mouth_timer;
	
	private Timer wallking_animation_timer;
	
	public Player(int x1,int y1){
		this.x1=x1;
		this.y1=y1;
		
		speed = 1.1;
		
		health = 100;
		maxhealth = 100;
		
		width=64;
		height=69;
		
		rect = new Rectangle(x1,y1,width,height);
		
		splayer = new SoundPlayer();
		walking_sound_player = new SoundPlayer();
		
		shoot_timer = new Timer(shoot_milis,new Timer_Listener("shoot_timer"));
		TDT = new Timer(500,new Timer_Listener("TDT"));
		open_mouth_timer = new Timer(300,new Timer_Listener("OMT"));
		wallking_animation_timer = new Timer(200,new Timer_Listener("WAT"));
		
		
	}
	
	private class Timer_Listener implements ActionListener{
		private String timer_name;
		
		public Timer_Listener(String timer_name){
			this.timer_name=timer_name;
		}

		public void actionPerformed(ActionEvent e) {
			
			if(timer_name.equals("shoot_timer")){
				ST();
			}else if(timer_name.equals("TDT")){
				TDT();
			}else if(timer_name.equals("OMT")){
				OMT();
			}else if(timer_name.equals("WAT")){
				WAT();
			}
			
		}
		
		private void WAT(){
			sprite_index++;
			
			walking_sound_player.playClip(Sounds.footstep, - 30.0f);
			
			if(sprite_index>Images.pig_wallking.size()-2)sprite_index=0;
			wallking_animation_timer.stop();
		}
		
		private void OMT(){
			open_mouth=false;
			open_mouth_timer.stop();
		}
		
		private void ST(){
			shp = true;
		}
		
		private void TDT(){
			if(imgchcic<5){
				if(chimg){
					chimg=false;
				}else chimg=true;
				imgchcic++;
			}else {
				chimg=false;
				imgchcic=0;
				TDT.stop();
			}
		}
		
	}

	private void move() {
		
		x0=x1;
		y0=y1;
			
			if(KeyInput.up){
				
				y1 -= speed;
				
			}else if(KeyInput.down){
				
				y1 += speed;
				
			}
			
			if(KeyInput.left){
				
				x1 -= speed;
				
			}else if(KeyInput.right){
				
				x1 += speed;
					
			}
			
			rect.x=(int) x1;
			rect.y=(int) y1;
		
	}
	
	private void shoot(){
		
		if(MouseInput.left_click && shp && charge>=maxcharge/50){
			
			open_mouth=true;
			open_mouth_timer.start();
			
			Projectile tomato = new Projectile((int)x1+width/2-10,(int)y1+height/2-10,MouseInput.MOUSE_X,MouseInput.MOUSE_Y,Projectile_type.tomato_shuriken);
			tomatoes.add(tomato);
			splayer.playClip(Sounds.shuriken1_throw , 1);
			shp=false;
			shoot_timer.start();
			
			charge-=maxcharge/50;
			
		}else if(!MouseInput.left_click){
			shoot_timer.stop();
			shp=true;
			
		}
		
		if(MouseInput.right_click && charge>=50 && !ssl){
			
			open_mouth=true;
			open_mouth_timer.start();
			
			for(int i=0;i<100;i++){
				
				//x = r * cos( theta ) + x1 // y = r * sin( theta )+ y1 // theta is the angle at which we want to shoot at , x1 and y1 are the centre coordinates
				Projectile tomato = new Projectile((int)x1+width/2-10,(int)y1+height/2-10,(MainFrame.width * Math.cos( i*360/100 )+x1),(MainFrame.width * Math.sin( i*360/100 )+y1), Projectile_type.tomato_shuriken);
				tomatoes.add(tomato);
				
				splayer.playClip(Sounds.shuriken1_throw , 1);
			
			}
			
			charge-=50;
			
			ssl=true;
		}
		
		if(!MouseInput.right_click){
			ssl=false;
		}
		
	}
	
	protected void take_damage(int damage){
		
		health-=damage;
		splayer.playClip(Sounds.hit,1);
		chimg=true;
		imgchcic=0;
		TDT.start();
		
	}
	
	@Override
	protected void colision_detection() {
		
		for(bwall wall: LoadedLevel.walls){
			
			if(wall.rect.intersects(x1+5, y1+60, width-5, height-60)){
				x1=x0;
				y1=y0;
			}
			
		}
		
		for(Powerup power: LoadedLevel.powerups){
			
			if(power.rect.intersects(x1+5, y1+60, width-5, height-60)){
				
				splayer.playClip(Sounds.powerup,1);
				
				open_mouth=true;
				open_mouth_timer.start();
				
				if(power.type.equals(PowerupType.tomato)){
					power.pickup();
					
					if(charge<maxcharge){
						charge+=maxcharge/10;
					}
					
					if(charge>maxcharge){
						charge=maxcharge;
					}
					
				}
				
				if(power.type.equals(PowerupType.flower)){
					power.pickup();
					
					score++;
					
					if(health<maxhealth){
						health+=maxhealth/20;
					} else health = maxhealth;
				}
				
				if(power.type.equals(PowerupType.spec_flower)){
					
					if(LoadedLevel.index+1 < Images.level_imgs.size()){
						LevelLoader.loadLevel(LoadedLevel.index+1);
					}else MainFrame.state = GameState.WIN;
						
				}
				
			}
			
		}
		
		for(MobCoock mob : LoadedLevel.mobcoocks){
			
			/*if(mob.rect.intersects(x1+5, y1+60, width-5, height-60)){
				x1=x0;
				y1=y0;
			}*/
			
			for(Projectile corn : mob.corns){
				if(rect.intersects(corn.rect)){		
					take_damage(maxhealth/10);;
					corn.dead=true;
				}
			}
		}
	}

	@Override
	public void update() {
		
		move();
		colision_detection();
		shoot();
		sprite_anim();
		
	
		
		for(Projectile tomato : tomatoes){
			if(tomato.dead){
				tomatoes.remove(tomato);
			}
			tomato.update();
		}
		
		if(health < 1){
			MainFrame.state = GameState.DEAD;
		}
		
	}
	
	@Override
	protected void sprite_anim() {		
		
		if(!open_mouth){
			img = Images.pig_wallking.get(sprite_index);
		}else img = Images.pig_wallking_openmouth.get(sprite_index);
		
		if(x0!=x1 || y0!=y1){
			wallking_animation_timer.start();
		}
		else {
			wallking_animation_timer.stop();
			sprite_index=0;
		}
		
		if(x0>x1)imgdir=true;
		else if(x1>x0)imgdir=false;
		
		if(sprite_index>4)sprite_index=0;
		
	}

	@Override
	public void render(Graphics g) {
		
		Graphics2D a = (Graphics2D) g;
		
		if(chimg){
			 a.setComposite(AlphaComposite.SrcOver.derive(0.3f));
		}
		
		if(imgdir){
			a.drawImage(img , (int)x1, (int)y1,width,height, null);
		}else a.drawImage(img , (int)x1 + img.getWidth(null), (int)y1,-width,height, null);
		
		a.setComposite(AlphaComposite.SrcOver);
		
		for(Projectile tomato : tomatoes){
			tomato.render(g);
		}
	}
	
	public void reset(int x1,int y1){
		this.x1=x1;
		this.y1=y1;
		
		this.health = maxhealth;
		this.charge = maxcharge/2;
		this.score = 0;
	}
	
	public void relocate(int x1,int y1){
		this.x1=x1;
		this.y1=y1;
	}
	
	public Point get_location(){
		Point a = new Point((int)x1,(int)y1);
		return a;
	}
	
	public void renderHUD(Graphics g) {

		
		
		g.setColor(Color.green);
		g.fillRect(45, 14 , health * 93/maxhealth - 10, Images.HealthBar.getHeight()-20);
		g.fillOval(health * 93/maxhealth + 20, 12, 23, 23);
		
		g.drawImage(Images.HealthBar, 10 , 0 ,Images.HealthBar.getWidth(),Images.HealthBar.getHeight(),null);
		
		
		g.setColor(Color.RED);
		g.fillRect(39, 64 , charge * 93/maxcharge - 10, Images.BurstBar.getHeight()-20);
		g.fillOval(charge * 93/maxcharge + 20, 63, 24, 15);
		
		g.drawImage(Images.BurstBar, 10 , 50 ,Images.BurstBar.getWidth(),Images.BurstBar.getHeight(),null);
		
		g.setColor(Color.decode("#66ff99"));
		Font mfont = new Font("Ariel",Font.BOLD,20);
		g.setFont(mfont);
		g.drawString("Score: " + score*10, MainFrame.width - 150, 30);
		
	}

	
	public double get_y1(){
		return y1;
	}
	
	public double get_x1(){
		return x1;
	}
}
