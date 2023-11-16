package me.TonchoZhelev.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.concurrent.CopyOnWriteArrayList;

import me.Toncho.Zhelev.input.MouseInput;
import me.TonchoZhelev.libs.Images;
import me.TonchoZhelev.libs.Reference;
import me.TonchoZhelev.main.MainFrame;

public class Menus {
	
	public Rectangle play,quit,resume;
	
	public CopyOnWriteArrayList<Rectangle> levelbuts = new CopyOnWriteArrayList<Rectangle>(); 
	
	public Menus(){
		
		int fillerY = 400;
		
		play = new Rectangle(Reference.centerX-100,fillerY , 200 , 50);
		quit = new Rectangle(Reference.centerX-100,fillerY + 60 , 200 , 50);
		
		resume = new Rectangle(Reference.centerX-100,fillerY , 200 , 50);	
			
	}
	
	public void loadlevels(){
		
		int levelY=10;
		int levelX=10;
		
		for(int i=0 ; i<Images.level_imgs.size() ; i++){

			Rectangle rect = new Rectangle(levelX,levelY , 200 , 50);
			levelbuts.add(rect);
			levelX += 210;
			
			if(levelX>MainFrame.width-200){
				levelX=10;
				levelY+=60;
			}
			
		}
	}
	
	public void drawButton(Graphics g,Rectangle rect,String text,int offsetX){
		Font mFont = new Font("Arial",Font.BOLD,40);
		g.setFont(mFont);
		
		if(MouseInput.MOUSE.intersects(rect)) g.setColor(Color.decode("#B22222"));
		else g.setColor(Color.decode("#8B4513"));

		g.drawRect(rect.x, rect.y, rect.width, rect.height);
		g.drawString(text, rect.x + offsetX + 10, rect.y + 40);
	}
	
	public void renderMainMenu(Graphics g){
		
		g.drawImage(Images.splash, MainFrame.width/2 - 300, 0, 600, 547, null);
		drawButton(g,play,"Play",45);
		drawButton(g,quit,"Quit",45);
		
	}
	
	public void renderPauseMenu(Graphics g){
		
		drawButton(g,resume,"Resume",15);
		drawButton(g,quit,"Menu",45);
		
	}
	
	public void renderLevelMenu(Graphics g){
		
		for(Rectangle rect : levelbuts){	
			
			drawButton(g,rect,"Level "+levelbuts.indexOf(rect),45);
			
		}
		
		drawButton(g,quit,"Menu",45);
		
	}
	
	public void renderDEADMenu(Graphics g){
		
		Font mFont = new Font("Arial",Font.BOLD,50);
		g.setFont(mFont);
		g.setColor(Color.RED);
		g.drawString("DEAD", MainFrame.width/2 - 80, MainFrame.height/2);
		drawButton(g,quit,"Menu",45);
		
	}
	
	public void renderWINMenu(Graphics g){
		
		Font mFont = new Font("Arial",Font.BOLD,50);
		g.setFont(mFont);
		g.setColor(Color.GREEN);
		g.drawString("YOU WIN", MainFrame.width/2 - 100, MainFrame.height/2);
		drawButton(g,quit,"Menu",45);
		
	}

}
