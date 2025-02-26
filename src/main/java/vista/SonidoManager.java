package vista;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.BufferedInputStream;
import java.io.InputStream;

public class SonidoManager {
    public enum TipoSonido {
        SELECCION, VICTORIA, DERROTA, EMPATE
    }

    public static void reproducirSonido(TipoSonido tipo) {
        String ruta = "";

        switch (tipo) {
            case SELECCION:
                ruta = "/sonidos/seleccion.mp3";
                break;
            case VICTORIA:
                ruta = "/sonidos/victoria.mp3";
                break;
            case DERROTA:
                ruta = "/sonidos/derrota.mp3";
                break;
            case EMPATE:
                ruta = "/sonidos/empate.mp3";
                break;
        }

        try {
            InputStream is = SonidoManager.class.getResourceAsStream(ruta);
            if (is == null) {
                System.out.println("No se pudo encontrar el archivo de sonido: " + ruta);
                return;
            }

            BufferedInputStream bis = new BufferedInputStream(is);
            AdvancedPlayer player = new AdvancedPlayer(bis);
            new Thread(() -> {
                try {
                    player.play();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
