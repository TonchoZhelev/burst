package me.TonchoZhelev.main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import me.Toncho.Zhelev.input.KeyInput;
import me.Toncho.Zhelev.input.MouseInput;
import me.Toncho.Zhelev.utils.ResourceLoader;
import me.TonchoZhelev.enums.GameState;
import me.TonchoZhelev.gfx.renderer;
import me.TonchoZhelev.levels.LevelLoader;
import me.TonchoZhelev.levels.LoadedLevel;
import me.TonchoZhelev.libs.Reference;
import me.TonchoZhelev.obj.MobCoock;
import me.TonchoZhelev.obj.Player;
import me.TonchoZhelev.screens.Menus;

public class MainFrame extends Canvas implements Runnable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String title = "FARM BURST";
	
	public static final int width = 800;
	public static final int height = width/4*3;
	
	private boolean running = false;
	private Thread thread;
	
	public static MainFrame mFrame = new MainFrame();
	
	private renderer gfx;
	
	public Menus menu;
	public static GameState state = GameState.MENU;
	
	public Player player;
	
	public static MainFrame getInstance(){
		return mFrame;
	}
	
	public void init(){
		
		System.out.println(Reference.RESOURCE_LOCATION);
		
		ResourceLoader.loadImages();
		ResourceLoader.loadSounds();
		
		menu = new Menus();
		
		gfx = new renderer();
		
		player=new Player(300,300);
		
		MouseInput mouse = new MouseInput();
		KeyInput keys = new KeyInput();
		
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
		this.addKeyListener(keys);
		
		
		LevelLoader.loadLevel_imgs();
		menu.loadlevels();
		
	}
	
	public void tick(){
	
		if(state==GameState.GAME){
			
			player.update();
		
		}
		
		for(MobCoock mob : LoadedLevel.mobcoocks){
			mob.update();
		}
			
		if(KeyInput.pause && state == GameState.GAME){
			state = GameState.PAUSE;
		} else if (state==GameState.PAUSE && !KeyInput.pause)state = GameState.GAME;
		
	}
	
	public void render(){
		
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs==null){
			createBufferStrategy(2);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		//////////////////////////////////
		
		gfx.renderBackground(g);
		gfx.renderForeground(g);
		gfx.renderHUD(g);
		
		g.dispose();
		bs.show();
		bs.dispose();
	}

	@Override
	public void run() {
		
		init();
		
		long lastTime=System.nanoTime();
		final double numTicks=60.0;
		double n=1000000000/numTicks;
		double delta=0;
		double frames=0;
		double ticks=0;
		long timer=System.currentTimeMillis();
		long currentTime;
		
		while(running){
			currentTime= System.nanoTime();
			delta+=(currentTime-lastTime)/n;
			lastTime=currentTime;
			
			if(delta >= 1){
				tick();
				ticks ++;
				delta --;
			}
			
			render();
			
			frames++;
		
			if(System.currentTimeMillis()-timer>1000){
				
				timer+=1000;
				System.out.println("Ticks: "+ticks+" frames: "+frames);
				
				ticks=0;
				frames=0;
			}
			
		}
		stop();
	}

	public static void main(String[] args) {
		
		Image icon = Toolkit.getDefaultToolkit().getImage(Reference.SPRITE_LOCATION+"icon.png");
		
		JFrame frame = new JFrame();
		
		frame.add(mFrame);
		
		frame.setTitle(title);
		
		frame.setIconImage(icon);
		
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
		mFrame.start();
		
	}
	
	private synchronized void start(){
		if(running){
			return;
		}else {
			running=true;
			thread = new Thread(this);
			thread.start();
		}
		
	}
	
	public synchronized void stop(){
		if(!running){
			return;
		}else {
			
			running=false;
			
			System.exit(0);
			
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
