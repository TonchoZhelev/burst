package me.TonchoZhelev.obj;

import java.awt.Graphics;
import java.awt.Rectangle;

import me.TonchoZhelev.libs.Images;

public class bwall extends globvar{
	
	int width = 32;
	int height = 32;

	public bwall(int x1,int y1){
		this.x1=x1;
		this.y1=y1;
		
		if(Math.random()>0.5){
			img=Images.altwall;
		}else img = Images.wall;
		
		rect=new Rectangle(x1,y1,width,height);
	}
	
	@Override
	void sprite_anim() {
		
	}

	@Override
	public void update() {
		
	}
	
	@Override
	protected void colision_detection() {
		// TODO Auto-generated method stub
		
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
