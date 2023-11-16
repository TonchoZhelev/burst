package me.TonchoZhelev.levels;

import java.util.concurrent.CopyOnWriteArrayList;

import me.TonchoZhelev.obj.MobCoock;
import me.TonchoZhelev.obj.Powerup;
import me.TonchoZhelev.obj.bwall;

public class LoadedLevel {
	
	public static int index = 0;
	public static CopyOnWriteArrayList<bwall> walls = new CopyOnWriteArrayList<bwall>();
	public static CopyOnWriteArrayList<Powerup> powerups = new CopyOnWriteArrayList<Powerup>();
	public static CopyOnWriteArrayList<MobCoock> mobcoocks = new CopyOnWriteArrayList<MobCoock>();

}
