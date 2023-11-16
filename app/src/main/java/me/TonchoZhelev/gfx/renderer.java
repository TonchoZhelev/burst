package me.TonchoZhelev.gfx;

import java.awt.Color;
import java.awt.Graphics;

import me.TonchoZhelev.levels.LoadedLevel;
import me.TonchoZhelev.libs.Images;
import me.TonchoZhelev.main.MainFrame;
import me.TonchoZhelev.obj.MobCoock;
import me.TonchoZhelev.obj.Powerup;
import me.TonchoZhelev.obj.bwall;

public class renderer {
	
	public void renderBackground(Graphics g){
		
		switch(MainFrame.state){
		
		case GAME:	
			
			g.drawImage(Images.background, 0, 0, MainFrame.width , MainFrame.height , null);
			
			break;
		case MENU:
			
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, MainFrame.width, MainFrame.height);
			
			break;

		case PAUSE:
			
			g.drawImage(Images.background, 0, 0, MainFrame.width , MainFrame.height , null);
		
			break;
		case LEVELMENU:
			
			break;
		default:
			break;
		}
		
	}
	
	public void renderForeground(Graphics g){
		
		switch(MainFrame.state){
		case GAME:
			
			//walls
			for(bwall wall : LoadedLevel.walls){
				wall.render(g);
			}
			
			//powerups
			for(Powerup power : LoadedLevel.powerups){
				power.render(g);
			}
			
			//mobs
			for(MobCoock mob : LoadedLevel.mobcoocks){
				mob.render(g);
			}
			
			MainFrame.getInstance().player.render(g);
			
			
			break;
		case MENU:			
			
			MainFrame.getInstance().menu.renderMainMenu(g);
			
			break;

		case PAUSE:
			
			MainFrame.getInstance().player.render(g);
			
			//walls
			for(bwall wall : LoadedLevel.walls){
				wall.render(g);
			}
			
			//powerups
			for(Powerup power : LoadedLevel.powerups){
				power.render(g);
			}
			
		
			break;
		case LEVELMENU:
			
			MainFrame.getInstance().menu.renderLevelMenu(g);
			
			break;
		case DEAD:
			
			g.setColor(Color.BLACK);
			g.drawRect(-1, -1, MainFrame.width, MainFrame.height);
			
			MainFrame.getInstance().menu.renderDEADMenu(g);
			
			break;
		case WIN:
			
			g.setColor(Color.BLACK);
			g.drawRect(-1, -1, MainFrame.width, MainFrame.height);
			
			MainFrame.getInstance().menu.renderWINMenu(g);
			
			break;
		default:
			break;
		}
		
	}
	
	public void renderHUD(Graphics g){
		
		switch(MainFrame.state){
		case GAME:
			
			MainFrame.getInstance().player.renderHUD(g);
			
			break;
		case MENU:			
			
			
			break;

		case PAUSE:
			
			MainFrame.getInstance().menu.renderPauseMenu(g);
		
			break;
			
		case LEVELMENU:
			
			break;
		default:
			break;
		}
		
	}
	
	
	
}
