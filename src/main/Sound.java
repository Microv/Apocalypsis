package main;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	/**
	 * Inizializza ed avvia un effetto sonoro
	 * @param path Percorso su disco dell'effetto sonoro
	 */
	public static void playSound(String path){
		try {
			
			URL url = Sound.class.getResource(path);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			
			//File soundFile = new File(path);
			//AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		
			Clip clip = AudioSystem.getClip();
			
			clip.open(audioIn);
			
			clip.start();
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Inizializza ed avvia un effetto sonoro
	 * facendolo riprodurre a ripetizione
	 * @param path Percorso su disco dell'effetto sonoro
	 */
	public static void loopSound(String path){
		
		try{
			File soundFile = new File(path);
			
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		
			Clip clip = AudioSystem.getClip();
			
			clip.open(audioIn);
			
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Crea un clip audio da riprodurre
	 * @param path Percorso su disco dell'effetto sonoro
	 */
	public static Clip getClip(String path){
		
		try {
			
			
			
			URL url = Sound.class.getResource(path);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
			
			//File soundFile = new File(path);
			
			//AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		
			Clip clip = AudioSystem.getClip();
			
			clip.open(audioIn);
			
			return clip;
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
}