package me.Toncho.Zhelev.utils;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import me.TonchoZhelev.libs.Images;
import me.TonchoZhelev.libs.Sounds;

public class ResourceLoader {

	private static BufferedImageLoader imageLoader = new BufferedImageLoader();

	private static SoundLoader soundloader = new SoundLoader();

	public static void loadImages() {

		try {

			for (int i = 1; i <= 5; i++) {
				Images.pig_wallking.add(imageLoader.LoadImage("BURST_PIG64X69Frame0" + i + ".png"));

				Images.pig_wallking_openmouth.add(imageLoader.LoadImage("BURST_PIG64X69_Mouth_FR_0" + i + ".png"));

				Images.mobCoock_wallking.add(imageLoader.LoadImage("MobCoock_frame_0" + i + ".png"));

				Images.mobCoock_shooting.add(imageLoader.LoadImage("MobCoockShoot_FR_0" + i + ".png"));

			}

			Images.splash = imageLoader.LoadImage("BURST_SPLASH_PS_scaled.png");

			Images.shuriken = imageLoader.LoadImage("Tomato_Shuriken64X74.png");
			Images.tomato = imageLoader.LoadImage("Tomato32X31.png");
			Images.flower = imageLoader.LoadImage("Flower32X36.png");

			Images.spec_flower = imageLoader.LoadImage("Flower32X36_special.png");

			Images.corn = imageLoader.LoadImage("CornAmmo32X34.png");

			Images.wall = imageLoader.LoadImage("Tales64X63.png");
			Images.altwall = imageLoader.LoadImage("StoneWall_Grass64X65.png");

			Images.HealthBar = imageLoader.LoadImage("HEALTH_BAR128X105.png");
			Images.BurstBar = imageLoader.LoadImage("BURST_BAR128X105 .png");

			Images.background = imageLoader.LoadImage("BURST_BACKGROUND.png");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void loadSounds() {

		try {

			Sounds.shuriken1_throw = soundloader.loadSound("shuriken1_throw.wav");
			Sounds.shuriken1_hit = soundloader.loadSound("shuriken1_hit.wav");

			Sounds.hit = soundloader.loadSound("hit.wav");

			Sounds.powerup = soundloader.loadSound("powerup.wav");

			Sounds.enemy_hit = soundloader.loadSound("enemy_hit.wav");

			Sounds.footstep = soundloader.loadSound("footstep2.wav");
			Sounds.footstep1 = soundloader.loadSound("footstep21.wav");

		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			e.printStackTrace();
		}

	}

}
