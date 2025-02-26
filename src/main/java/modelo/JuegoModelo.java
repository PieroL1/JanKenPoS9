package modelo;

import java.util.ArrayList;
import java.util.List;

public class JuegoModelo {
    private boolean modoMultijugador;
    private IAJugador ia;
    private List<Partida> historialPartidas;
    private int victoriasJugador1;
    private int victoriasJugador2;
    private int empates;
    private Jugada ultimaJugadaJugador1;
    
    public JuegoModelo() {
        modoMultijugador = false;
        ia = new IAJugador();
        historialPartidas = new ArrayList<>();
        reiniciarPuntuacion();
    }
    
    public void setModoMultijugador(boolean modoMultijugador) {
        this.modoMultijugador = modoMultijugador;
    }
    
    public boolean esModoMultijugador() {
        return modoMultijugador;
    }
    
    public void setDificultadIA(boolean dificil) {
        ia.setDificultad(dificil);
    }
    
    public boolean getDificultadIA() {
        return ia.getDificultad();
    }
    
    public Resultado jugar(Jugada jugada1, Jugada jugada2) {
        Partida partida = new Partida(jugada1, jugada2);
        historialPartidas.add(partida);
        actualizarPuntuacion(partida.getResultado());
        ultimaJugadaJugador1 = jugada1;
        return partida.getResultado();
    }
    
    public Resultado jugarContraIA(Jugada jugada) {
        Jugada jugadaIA = ia.obtenerJugada(ultimaJugadaJugador1);
        return jugar(jugada, jugadaIA);
    }
    
    private void actualizarPuntuacion(Resultado resultado) {
        switch (resultado) {
            case JUGADOR1_GANA:
                victoriasJugador1++;
                break;
            case JUGADOR2_GANA:
                victoriasJugador2++;
                break;
            case EMPATE:
                empates++;
                break;
        }
    }
    
    public void reiniciarPuntuacion() {
        victoriasJugador1 = 0;
        victoriasJugador2 = 0;
        empates = 0;
        historialPartidas.clear();
        ultimaJugadaJugador1 = null;
    }
    
    public int getVictoriasJugador1() {
        return victoriasJugador1;
    }
    
    public int getVictoriasJugador2() {
        return victoriasJugador2;
    }
    
    public int getEmpates() {
        return empates;
    }
    
    public List<Partida> getHistorialPartidas() {
        return new ArrayList<>(historialPartidas);
    }
    
    public Partida getUltimaPartida() {
        if (historialPartidas.isEmpty()) {
            return null;
        }
        return historialPartidas.get(historialPartidas.size() - 1);
    }
}