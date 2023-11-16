package me.TonchoZhelev.obj;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.Timer;

import me.TonchoZhelev.enums.Projectile_type;
import me.TonchoZhelev.levels.LoadedLevel;
import me.TonchoZhelev.libs.Images;
import me.TonchoZhelev.libs.Sounds;
import me.TonchoZhelev.main.MainFrame;
import me.TonchoZhelev.sfx.SoundPlayer;

public class MobCoock extends globvar {
	
	private boolean imgdir;
	
	//timer limits the bullets per second
	private Timer shoot_timer;
	
	//timer responsible for animation when taking damage
	private Timer TDT;
	//boolean responsible for the change of image and an int that keeps the number of times the img has been changed
	private boolean chimg=false;
	private int imgchcic=0;
	
	//permission to shoot
	private boolean shp=true;
	
	private int walk_sprite_index=0;
	private int shoot_sprite_index=0;
	
	private SoundPlayer splayer;
	private SoundPlayer walking_sound_player;
	
	public CopyOnWriteArrayList<Projectile> corns = new CopyOnWriteArrayList<Projectile>();
	
	Image img = Images.mobCoock_wallking.get(walk_sprite_index);
	
	private Timer wallking_animation_timer;
	private Timer shooting_animation_timer;
	
	//bool for changeing from shooting mode to walking mode
	private boolean chshootimg=false;
	
	private Timer changedir;
	private boolean chdir=true;
	
	private boolean see_player=false;
	private double player_knownX;
	private double player_knownY;
	
	private double player_width;
	private double player_height;
	
	public MobCoock(int x1, int y1){
		
		this.x1 = x1;
		this.y1 = y1;
		
		x0=x1;
		y0=y1;
		
		speed = 1;
		
		dir = Math.random()*360;
		
		img = Images.mobCoock_wallking.get(0);
		width =	92;//60X91
		height = 107;
		
		health=100;
		maxhealth=100;
		
		player_width = MainFrame.getInstance().player.width;
		player_height = MainFrame.getInstance().player.height;
		
		rect = new Rectangle(x1+20,y1+10,width-40,height-20);
		
		splayer = new SoundPlayer();
		walking_sound_player = new SoundPlayer();
		
		shoot_timer = new Timer(500,new Timer_Listener("shoot_timer"));
		TDT = new Timer(500,new Timer_Listener("TDT"));
		wallking_animation_timer = new Timer(200,new Timer_Listener("WAT"));
		
		shooting_animation_timer = new Timer(100,new Timer_Listener("SAT"));
		
		changedir = new Timer(200,new Timer_Listener("CD"));
		
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
			}else if(timer_name.equals("WAT")){
				WAT();
			}else if(timer_name.equals("CD")){
				CD();
			}else if(timer_name.equals("SAT")){
				SAT();
			}
			
		}
		
		private void SAT(){
			shoot_sprite_index++;	
			
			if(shoot_sprite_index>Images.mobCoock_shooting.size()-2)shoot_sprite_index=0;

		}
		
		private void CD(){
			chdir = true;
			changedir.stop();
		}
		
		private void WAT(){
			walk_sprite_index++;
			
			walking_sound_player.playClip(Sounds.footstep , -30.0f);
			
			if(walk_sprite_index>Images.mobCoock_wallking.size()-2)walk_sprite_index=0;
			wallking_animation_timer.stop();
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

	protected void take_damage(int damage){
		
		health-=damage;
		splayer.playClip(Sounds.hit,1);
		chimg=true;
		imgchcic=0;
		TDT.start();
		
	}
	
	protected void Change_direction(){
		
		if(chdir){			
			dir=Math.random()*360;		
			changedir.start();
			chdir=false;
		}
		
	}
	
	private void see_player(){
		
		see_player=true;
		
		double pl_lockX = MainFrame.getInstance().player.get_location().getX()+player_width/2-1;
		double pl_lockY = MainFrame.getInstance().player.get_location().getY()+player_height/2-1;
		
		Line2D conline = new Line2D.Double(x1+width/2-1, y1+height/2-1, pl_lockX, pl_lockY);
		
		for(bwall wall : LoadedLevel.walls){
			
			if(conline.intersects(wall.rect)){
				
				see_player=false;
				
			}
			
		}
		
		if(see_player){
			player_knownX = pl_lockX;
			player_knownY = pl_lockY;
		}
		
		//System.out.println(see_player);
		
	}

	private void shoot(){
		
		if(shp){
			
			Projectile corn = new Projectile((int)x1+width/2-10,(int)y1+height/2-10,player_knownX,player_knownY,Projectile_type.corn);
			corns.add(corn);
			splayer.playClip(Sounds.shuriken1_throw, 1);
			shp=false;
			shoot_timer.start();
			
			
		}
		
	}

	
	private void move() {
		
		x0=x1;
		y0=y1;
		
		if(shp){
			
			x1+= Math.cos(Math.toRadians(dir))*speed;
			y1+= Math.sin(Math.toRadians(dir))*speed;
			
		}
			
		see_player();
		
		if(see_player){
			
			if(get_dist(MainFrame.getInstance().player.rect)<400){
				
				if(!shooting_animation_timer.isRunning()){
					chshootimg=true;
					shooting_animation_timer.start();
				}
				
				shoot();
			}
		} else {
			shp=true;
			chshootimg=false;
			shoot_sprite_index=0;
			shoot_timer.stop();
		}

		if(get_dist(MainFrame.getInstance().player.rect)>400){
			chshootimg=false;
			shooting_animation_timer.stop();
		}
		
		
		rect.x=(int) x1+20;
		rect.y=(int) y1+4;
		
	}
	
	@Override
	void sprite_anim() {
		
		if(!chshootimg){
			img = Images.mobCoock_wallking.get(walk_sprite_index);
		} else img = Images.mobCoock_shooting.get(shoot_sprite_index);
			
		if(x0!=x1 || y0!=y1){
			wallking_animation_timer.start();
		}else {
			wallking_animation_timer.stop();
			walk_sprite_index=0;
		}
		
		if(x0>x1)imgdir=true;
		else if(x1>x0)imgdir=false;
		
		if(see_player && get_dist(MainFrame.getInstance().player.rect)<400){
			
			if(player_knownX < x1)imgdir=true;
			else if(player_knownX > x1)imgdir=false;
			
		}
		
	}

	@Override
	public void update() {
		
		colision_detection();
		sprite_anim();
		move();
		
		if(health<1){
			LoadedLevel.mobcoocks.remove(this);
		}
		
		for(Projectile corn : corns){		
			if(corn.dead){
				corns.remove(corn);
			}
			corn.update();
		}
		
	}

	private double get_dist(Rectangle rect2){
		double dist;
		
		double x2,x3,y2,y3;
		
		x2=x1+width/2-1;
		y2=y1+height/2-1;
		
		x3=rect2.x+rect2.width/2-1;
		y3=rect2.y+rect2.height/2-1;
		
		dist = Math.sqrt((x2-x3)*(x2-x3) + (y2-y3)*(y2-y3));
		
		return dist;
	}
	
	@Override
	protected void colision_detection() {
		for(bwall wall: LoadedLevel.walls){
			
			if(get_dist(wall.rect)<60 || rect.intersects(wall.rect)){
				x1=x0;
				y1=y0;
				
				Change_direction();
			}
			
		}
		
		for(Projectile tomato : MainFrame.getInstance().player.tomatoes){
			if(rect.intersects(tomato.rect)){		
				take_damage(maxhealth/10);
				tomato.dead=true;
				
				Change_direction();
			}
		}
		
		for(MobCoock mob : LoadedLevel.mobcoocks){
			if(mob.rect.intersects(x1+5, y1+60, width-5, height-60) && !mob.equals(this)){
				
				Change_direction();
			}
		}
		
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
		
		for(Projectile corn : corns){
			corn.render(g);
		}
		
	}
	
	public double get_y1(){
		return y1;
	}
	
	public double get_x1(){
		return x1;
	}

}
