package me.TonchoZhelev.obj;

import java.awt.Graphics;
import java.awt.Rectangle;

import me.TonchoZhelev.enums.PowerupType;
import me.TonchoZhelev.levels.LoadedLevel;
import me.TonchoZhelev.libs.Images;

public class Powerup extends globvar{
	
	public PowerupType type;
	
	public Powerup(int x1,int y1,PowerupType type){
		
		this.x1=x1;
		this.y1=y1;
		
		this.type=type;
		
		if(type == PowerupType.tomato){
			width=32;
			height=31;
			
			img = Images.tomato;
		}
		
		if(type == PowerupType.flower){
			width=32;
			height=36;
			
			img = Images.flower;
		}
		
		if(type == PowerupType.spec_flower){
			width=32;
			height=36;
			
			img = Images.spec_flower;
		}
		
		rect=new Rectangle(x1,y1,width,height);
		
	}

	@Override
	void sprite_anim() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		
		rect.x=(int)x1;
		rect.y=(int)y1;
	}
	
	@Override
	protected void colision_detection() {
		
		
	}
	
	public void pickup(){
		
		LoadedLevel.powerups.remove(this);
		
	}
	
	@Override
	public void render(Graphics g) {
		
			g.drawImage(img, (int)x1, (int)y1,width,height, null);
		
	}
	
	public double get_y1(){
		return y1;
	}
	
	public double get_x1(){
		return x1;
	}


}
