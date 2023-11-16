package me.Toncho.Zhelev.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import me.TonchoZhelev.libs.Reference;

public class SoundLoader {
	
	public Clip loadSound( String filename ) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
	    Clip in = null;
	    
	    InputStream input = getClass().getResourceAsStream(Reference.SOUND_LOCATION + filename);
	    
	    InputStream bufferedIn = new BufferedInputStream(input);
	    
	    AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn);
	    in = AudioSystem.getClip();
	    in.open( audioIn );

	    return in;
	}

}
