package viewmodel;

import modelo.*;
import java.util.List;
import java.util.Observable;

public class JuegoViewModel extends Observable {
    private JuegoModelo modelo;
    private String mensajeResultado;
    private boolean turnoJugador2;
    private Jugada jugadaJugador1Temp;
    private boolean partidaEnCurso;
    private long tiempoInicio;
    private int tiempoLimite;  // en segundos
    
    public JuegoViewModel() {
        modelo = new JuegoModelo();
        mensajeResultado = "¡Bienvenido a Jan Ken Po!";
        turnoJugador2 = false;
        partidaEnCurso = false;
        tiempoLimite = 60;  // 5 segundos por defecto
    }
    
    public void setModoMultijugador(boolean multijugador) {
        modelo.setModoMultijugador(multijugador);
        mensajeResultado = multijugador ? "Modo multijugador activado" : "Modo contra IA activado";
        notificarCambios();
    }
    
    public boolean esModoMultijugador() {
        return modelo.esModoMultijugador();
    }
    
    public void setDificultadIA(boolean dificil) {
        modelo.setDificultadIA(dificil);
        mensajeResultado = dificil ? "Dificultad: Difícil" : "Dificultad: Fácil";
        notificarCambios();
    }
    
    public boolean getDificultadIA() {
        return modelo.getDificultadIA();
    }
    
    public void setTiempoLimite(int segundos) {
        this.tiempoLimite = segundos;
    }
    
    public int getTiempoLimite() {
        return tiempoLimite;
    }
    
    public boolean esTurnoJugador2() {
        return turnoJugador2;
    }
    
    public void iniciarPartida() {
        partidaEnCurso = true;
        tiempoInicio = System.currentTimeMillis();
        notificarCambios();
    }
    
    public boolean partidaEnCurso() {
        return partidaEnCurso;
    }
    
    public long getTiempoRestante() {
        if (!partidaEnCurso) return 0;
        long tiempoTranscurrido = (System.currentTimeMillis() - tiempoInicio) / 1000;
        long restante = tiempoLimite - tiempoTranscurrido;
        return restante > 0 ? restante : 0;
    }
    
    public boolean tiempoAgotado() {
        return getTiempoRestante() == 0 && partidaEnCurso;
    }
    
    public void seleccionarJugada(Jugada jugada) {
        if (modelo.esModoMultijugador()) {
            if (!turnoJugador2) {
                jugadaJugador1Temp = jugada;
                turnoJugador2 = true;
                mensajeResultado = "Jugador 2, elige tu jugada";
            } else {
                Resultado resultado = modelo.jugar(jugadaJugador1Temp, jugada);
                actualizarMensajeResultado(resultado, jugadaJugador1Temp, jugada);
                turnoJugador2 = false;
                partidaEnCurso = false;
            }
        } else {
            Resultado resultado = modelo.jugarContraIA(jugada);
            Partida ultimaPartida = modelo.getUltimaPartida();
            actualizarMensajeResultado(resultado, ultimaPartida.getJugador1(), ultimaPartida.getJugador2());
            partidaEnCurso = false;
        }
        notificarCambios();
    }
    
    private void actualizarMensajeResultado(Resultado resultado, Jugada jugada1, Jugada jugada2) {
        String mensaje = "Jugador 1: " + jugada1 + " | ";
        mensaje += modelo.esModoMultijugador() ? "Jugador 2: " : "IA: ";
        mensaje += jugada2 + " | ";
        
        switch (resultado) {
            case JUGADOR1_GANA:
                mensaje += "¡Jugador 1 gana!";
                break;
            case JUGADOR2_GANA:
                mensaje += modelo.esModoMultijugador() ? "¡Jugador 2 gana!" : "¡La IA gana!";
                break;
            case EMPATE:
                mensaje += "¡Empate!";
                break;
        }
        
        mensajeResultado = mensaje;
    }
    
    public void reiniciarJuego() {
        modelo.reiniciarPuntuacion();
        mensajeResultado = "Juego reiniciado";
        turnoJugador2 = false;
        partidaEnCurso = false;
        notificarCambios();
    }
    
    public String getMensajeResultado() {
        return mensajeResultado;
    }
    
    public int getVictoriasJugador1() {
        return modelo.getVictoriasJugador1();
    }
    
    public int getVictoriasJugador2() {
        return modelo.getVictoriasJugador2();
    }
    
    public int getEmpates() {
        return modelo.getEmpates();
    }
    
    public List<Partida> getHistorialPartidas() {
        return modelo.getHistorialPartidas();
    }
    
    private void notificarCambios() {
        setChanged();
        notifyObservers();
    }
}