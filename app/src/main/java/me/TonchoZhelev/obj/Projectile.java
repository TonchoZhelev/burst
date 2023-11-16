package me.TonchoZhelev.obj;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import me.TonchoZhelev.enums.Projectile_type;
import me.TonchoZhelev.levels.LoadedLevel;
import me.TonchoZhelev.libs.Images;
import me.TonchoZhelev.libs.Sounds;
import me.TonchoZhelev.main.MainFrame;
import me.TonchoZhelev.sfx.SoundPlayer;

public class Projectile extends globvar {

	//speed mod
	private int sm=10;
	private double tx,ty;
	
	public boolean dead=false;
	
	int a=0;
	
	private SoundPlayer splayer;
	
	private Projectile_type type;
	
	public Projectile(int x1,int y1,double tx,double ty,Projectile_type type){
		
		this.x1=x1;
		this.y1=y1;
		
		this.tx=tx;
		this.ty=ty;
		
		this.type=type;
		
		if(type == Projectile_type.tomato_shuriken){
			img = Images.shuriken;
		}else if(type == Projectile_type.corn){			
			img = Images.corn;
		}
		
		width = img.getWidth(null);
		height = img.getHeight(null);
		
		rect = new Rectangle(x1,y1,width,height);
		splayer = new SoundPlayer();
		dircalc();
		
	}
	
	void sprite_anim() {
		
	}
	
	private void dircalc(){

		double distX =tx - x1-(width/2-1);
		double distY =ty - y1-(height/2-1);
		double angleRadians = Math.atan2(distY, distX);
		dir = Math.toDegrees(angleRadians);
		
	}
	
	private void move(){
		
			x0=x1;
			y0=y1;
		
			x1+= Math.cos(Math.toRadians(dir))*sm;
			y1+= Math.sin(Math.toRadians(dir))*sm;
			
			rect.x=(int) x1;
			rect.y=(int) y1;

	}
	
	protected void colision_detection(){
		
		if(x1 < -10 || x1 > MainFrame.width+10 || y1 < -10 || y1 > MainFrame.height+10){
			dead=true;
		}
		
		for(globvar wall : LoadedLevel.walls){
			if(rect.intersects(wall.rect)){
				dead=true;
			}
		}
		
		if(dead){
			splayer.playClip(Sounds.shuriken1_hit, 1);
		}
		
	}

	public void update() {
		
		move();
		colision_detection();
		
	}

	@Override
	public void render(Graphics g) {
		
		if(type == Projectile_type.tomato_shuriken){
			a+=3;
			Graphics2D g2d = (Graphics2D)g;
		    g2d.rotate(Math.toRadians(a), x1 + width/2-1, y1 + height/2-1);
			g.drawImage(img, (int)x1, (int)y1,width,height, null);
		    g2d.rotate(-Math.toRadians(a),x1 + width/2-1, y1 + height/2-1);
		    if(a>=360)a=0;
		}else if (type == Projectile_type.corn){
			Graphics2D g2d = (Graphics2D)g;
		    g2d.rotate(Math.toRadians(dir)-45, x1 + width/2-1, y1 + height/2-1);
			g.drawImage(img, (int)x1, (int)y1,width,height, null);
		    g2d.rotate(-Math.toRadians(dir)+45,x1 + width/2-1, y1 + height/2-1);
		}
		
	}

	public double get_y1(){
		return y1;
	}
	
	public double get_x1(){
		return x1;
	}
	
}
