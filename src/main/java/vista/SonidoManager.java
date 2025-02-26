package vista;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SonidoManager {
    public enum TipoSonido {
        SELECCION, VICTORIA, DERROTA, EMPATE
    }
    
    public static void reproducirSonido(TipoSonido tipo) {
        String ruta = "";
        
        switch (tipo) {
            case SELECCION:
                ruta = "/sonidos/seleccion.wav";
                break;
            case VICTORIA:
                ruta = "/sonidos/victoria.wav";
                break;
            case DERROTA:
                ruta = "/sonidos/derrota.wav";
                break;
            case EMPATE:
                ruta = "/sonidos/empate.wav";
                break;
        }
        
        try {
            // Nota: En una implementación real, necesitarías tener archivos de sonido
            // en el classpath del proyecto en la carpeta /sonidos/
            URL url = SonidoManager.class.getResource(ruta);
            if (url == null) {
                System.out.println("No se pudo encontrar el archivo de sonido: " + ruta);
                return;
            }
            
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}