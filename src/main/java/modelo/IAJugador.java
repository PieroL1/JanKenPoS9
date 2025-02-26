package modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IAJugador {
    private boolean dificultadDificil;
    private List<Jugada> historialJugadasRival;
    
    public IAJugador() {
        dificultadDificil = false;
        historialJugadasRival = new ArrayList<>();
    }
    
    public void setDificultad(boolean dificil) {
        this.dificultadDificil = dificil;
    }
    
    public boolean getDificultad() {
        return dificultadDificil;
    }
    
    public Jugada obtenerJugada(Jugada ultimaJugadaRival) {
        if (ultimaJugadaRival != null) {
            historialJugadasRival.add(ultimaJugadaRival);
        }
        
        if (!dificultadDificil || historialJugadasRival.size() < 3) {
            return Jugada.getJugadaAleatoria();
        } else {
            // Estrategia: anticipar basándose en los patrones del jugador
            return estrategiaBasica();
        }
    }
    
    private Jugada estrategiaBasica() {
        // Intentar detectar patrones básicos o más frecuentes
        Map<Jugada, Integer> frecuencia = new HashMap<>();
        for (Jugada j : Jugada.values()) {
            frecuencia.put(j, 0);
        }
        
        // Contar frecuencia de jugadas
        for (Jugada j : historialJugadasRival) {
            frecuencia.put(j, frecuencia.get(j) + 1);
        }
        
        // Encontrar la jugada más frecuente
        Jugada jugadaMasFrecuente = Jugada.PIEDRA;
        int maxFrec = 0;
        
        for (Map.Entry<Jugada, Integer> entry : frecuencia.entrySet()) {
            if (entry.getValue() > maxFrec) {
                maxFrec = entry.getValue();
                jugadaMasFrecuente = entry.getKey();
            }
        }
        
        // Contrarrestar la jugada más frecuente
        if (jugadaMasFrecuente == Jugada.PIEDRA) return Jugada.PAPEL;
        if (jugadaMasFrecuente == Jugada.PAPEL) return Jugada.TIJERAS;
        return Jugada.PIEDRA;
    }
}