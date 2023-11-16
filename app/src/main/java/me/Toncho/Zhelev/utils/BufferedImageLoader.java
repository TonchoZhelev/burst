package me.Toncho.Zhelev.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import me.TonchoZhelev.libs.Reference;

public class BufferedImageLoader {
	
	private BufferedImage image;
	
	public BufferedImage LoadImage(String imagePath) throws IOException{
		
		InputStream input = getClass().getResourceAsStream(Reference.SPRITE_LOCATION + imagePath);
		
		image = ImageIO.read(input);
		return image;
	}

}
