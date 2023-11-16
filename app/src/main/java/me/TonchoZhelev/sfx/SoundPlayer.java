package me.TonchoZhelev.sfx;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import me.TonchoZhelev.enums.GameState;
import me.TonchoZhelev.main.MainFrame;

public class SoundPlayer {
	
	public void playClip( Clip clip , float volume)
	{	
		if(MainFrame.state == GameState.GAME){
			FloatControl gainControl = 
					(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(volume);
		
				if( clip.isRunning() ){
						clip.stop();
				}
	    
				clip.setFramePosition( 0 );
				clip.start();
		}
	}

}
