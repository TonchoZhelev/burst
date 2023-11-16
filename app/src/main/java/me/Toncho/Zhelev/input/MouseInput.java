package me.Toncho.Zhelev.input;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import me.TonchoZhelev.enums.GameState;
import me.TonchoZhelev.levels.LevelLoader;
import me.TonchoZhelev.main.MainFrame;

public class MouseInput extends MouseAdapter {

	public static int MOUSE_X,MOUSE_Y;
	public static boolean left_click,right_click;
	public static Rectangle MOUSE = new Rectangle(1,1,1,1);
	
	public void mousePressed(MouseEvent e) {
		int mouse = e.getButton();
		
		if (mouse == MouseEvent.BUTTON1){
			
			left_click=true;
			
			switch(MainFrame.state){
			case GAME:
				break;
			case MENU:
				
				//Play
				if(MOUSE.intersects(MainFrame.getInstance().menu.play)){
					
					MainFrame.state = GameState.LEVELMENU;
					
				}
				
				//Quit
				if(MOUSE.intersects(MainFrame.getInstance().menu.quit)){
					MainFrame.getInstance().stop();
				}
				
				break;
			case PAUSE:
				
				//Play
				if(MOUSE.intersects(MainFrame.getInstance().menu.resume)){
					MainFrame.state = GameState.GAME;
					KeyInput.pause = false;
				}
				
				//Quit
				if(MOUSE.intersects(MainFrame.getInstance().menu.quit)){
					MainFrame.state = GameState.MENU;
					KeyInput.pause = false;
				}
				
				break;
			case LEVELMENU:
				
				if(MOUSE.intersects(MainFrame.getInstance().menu.quit)){
					MainFrame.state = GameState.MENU;
					KeyInput.pause = false;
				}
				
				for(Rectangle recta : MainFrame.getInstance().menu.levelbuts){
					
					if(MOUSE.intersects(recta)){
						
						LevelLoader.loadLevel(MainFrame.getInstance().menu.levelbuts.indexOf(recta));
						MainFrame.state=GameState.GAME;
						
					}
				}
				
				break;
			case DEAD:
				//Quit
				if(MOUSE.intersects(MainFrame.getInstance().menu.quit)){
					MainFrame.state = GameState.MENU;
					KeyInput.pause = false;
				}
				
				break;
			case WIN:
				//Quit
				if(MOUSE.intersects(MainFrame.getInstance().menu.quit)){
					MainFrame.state = GameState.MENU;
					KeyInput.pause = false;
				}
				
				break;
			default:
				break;
			}
		}
		
		if (mouse == MouseEvent.BUTTON3){
			
			right_click=true;
		}
		
	};
	
	public void mouseReleased(MouseEvent e) {
		int mouse = e.getButton();
		
		if (mouse == MouseEvent.BUTTON1){
			
			left_click=false;
		}
		
		if (mouse == MouseEvent.BUTTON3){
			
			right_click=false;
		}
		
	};	
	
	public void mouseMoved(MouseEvent e){
		
		MOUSE_X=e.getX();
		MOUSE_Y=e.getY();
		MOUSE.setLocation(MOUSE_X, MOUSE_Y);
	}
	
	public void mouseDragged(MouseEvent e){
		
		MOUSE_X=e.getX();
		MOUSE_Y=e.getY();
		MOUSE.setLocation(MOUSE_X, MOUSE_Y);
	}
	
}
