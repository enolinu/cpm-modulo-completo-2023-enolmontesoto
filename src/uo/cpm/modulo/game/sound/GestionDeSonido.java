package uo.cpm.modulo.game.sound;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class GestionDeSonido {
	
	// Método que reroduce el sonido localizado en la ruta que se pasa por parámetro.
	
	public static void reproducirSonido(String ruta) {
		try {
			File fichero = new File(ruta);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fichero);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
