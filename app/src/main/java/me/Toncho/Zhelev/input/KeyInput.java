package me.Toncho.Zhelev.input;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	public static boolean up,down,left,right,shoot,pause;
	
	public void keyPressed(KeyEvent e){
		
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W){
			up=true;
		}else if(key == KeyEvent.VK_S){
			down=true;
		}
		
		if(key == KeyEvent.VK_A){
			left=true;
		} else if(key == KeyEvent.VK_D){
			right=true;
		}
		
		if(key == KeyEvent.VK_Z){
			shoot = true;
		}
		
		if(key == KeyEvent.VK_P){
			
			if(pause == false){
				pause = true;				
			} else pause = false;
			
		}
		
	}
	
	public void keyReleased(KeyEvent e){
		
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W){
			up=false;
		}else if(key == KeyEvent.VK_S){
			down=false;
		}
		
		if(key == KeyEvent.VK_A){
			left=false;
		} else if(key == KeyEvent.VK_D){
			right=false;
		}
		
		if(key == KeyEvent.VK_Z){
			shoot = false;
		}
		
	}
	
}
