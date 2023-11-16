package me.TonchoZhelev.levels;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import me.Toncho.Zhelev.utils.BufferedImageLoader;
import me.TonchoZhelev.enums.PowerupType;
import me.TonchoZhelev.libs.Images;
import me.TonchoZhelev.libs.Reference;
import me.TonchoZhelev.main.MainFrame;
import me.TonchoZhelev.obj.MobCoock;
import me.TonchoZhelev.obj.Powerup;
import me.TonchoZhelev.obj.bwall;

public class LevelLoader {

	static int levelindex = 0;

	BufferedImageLoader imageLoader = new BufferedImageLoader();

	public static BufferedImage Load_img(String imagePath) throws IOException {
		BufferedImage image;

		InputStream input = LevelLoader.class.getResourceAsStream(Reference.LEVEL_LOCATION + imagePath);

		image = ImageIO.read(input);
		return image;
	}

	public static void loadLevel_imgs() {

		try {

			Images.level_imgs.add(Load_img("level1.png"));
			Images.level_imgs.add(Load_img("level2.png"));
			Images.level_imgs.add(Load_img("level3.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void loadLevel(int index) {

		LoadedLevel.powerups.clear();
		LoadedLevel.walls.clear();
		LoadedLevel.mobcoocks.clear();

		levelindex = index;

		// OBJECT MAP
		int PlayerColor = Images.level_imgs.get(index).getRGB(0, 0);
		int WallColor = Images.level_imgs.get(index).getRGB(0, 1);
		int TomatoPowerupColor = Images.level_imgs.get(index).getRGB(0, 2);
		int FlowerPowerupColor = Images.level_imgs.get(index).getRGB(0, 3);
		int MobCoockColor = Images.level_imgs.get(index).getRGB(0, 4);

		int ExitFlower = Images.level_imgs.get(index).getRGB(0, 5);

		int cc = 0;

		for (int yi = 0; yi < Images.level_imgs.get(index).getHeight(); yi++) {
			for (int xi = 1; xi < Images.level_imgs.get(index).getWidth(); xi++) {

				cc = Images.level_imgs.get(index).getRGB(xi, yi);

				if (cc != -1) {
					if (cc == PlayerColor)
						MainFrame.getInstance().player.reset(xi - 3, yi - 2);
					else if (cc == WallColor)
						loadwall(xi - 3, yi - 2);
					else if (cc == TomatoPowerupColor)
						loadpowerup(xi - 3, yi - 2, PowerupType.tomato);
					else if (cc == FlowerPowerupColor)
						loadpowerup(xi - 3, yi - 2, PowerupType.flower);
					else if (cc == MobCoockColor)
						loadenemie(xi - 3, yi - 2, "mobcoock");
					else if (cc == ExitFlower) {
						loadpowerup(xi - 3, yi - 2, PowerupType.spec_flower);
						System.out.println("spec flower");
					}

				}
			}
		}

		LoadedLevel.index = levelindex;

	}

	private static void loadwall(int x, int y) {

		bwall wall = new bwall(x, y);
		LoadedLevel.walls.add(wall);

	}

	private static void loadenemie(int x, int y, String type) {

		if (type.equals("mobcoock")) {
			MobCoock mob = new MobCoock(x, y);
			LoadedLevel.mobcoocks.add(mob);
		}

	}

	private static void loadpowerup(int x, int y, PowerupType type) {

		Powerup power1 = new Powerup(x, y, type);
		LoadedLevel.powerups.add(power1);

	}

}
