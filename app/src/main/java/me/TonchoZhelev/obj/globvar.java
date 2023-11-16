package me.TonchoZhelev.obj;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class globvar {
	
	Image img;
	
	protected double dir;
	protected int height;
	protected int width;	
	protected double x1;
	protected double y1;
	protected double x0;
	protected double y0;

	protected double speedX=0;
	protected double speedY=0;
	protected double speed;
	protected int health;
	protected int maxhealth;
	
	protected Rectangle rect;

	abstract void sprite_anim();
	public abstract void update();
	abstract protected void colision_detection();
	public abstract void render(Graphics g);
	
	abstract public double get_y1();
	
	abstract public double get_x1();
	
}
