package modelo;

import java.util.Date;

public class Partida {
    private Jugada jugador1;
    private Jugada jugador2;
    private Resultado resultado;
    private Date fecha;
    
    public Partida(Jugada jugador1, Jugada jugador2) {
        this.jugador1 = jugador1;
        this.jugador2 = jugador2;
        this.fecha = new Date();
        calcularResultado();
    }
    
    private void calcularResultado() {
        if (jugador1 == jugador2) {
            resultado = Resultado.EMPATE;
        } else if (jugador1.ganaA(jugador2)) {
            resultado = Resultado.JUGADOR1_GANA;
        } else {
            resultado = Resultado.JUGADOR2_GANA;
        }
    }
    
    public Jugada getJugador1() {
        return jugador1;
    }
    
    public Jugada getJugador2() {
        return jugador2;
    }
    
    public Resultado getResultado() {
        return resultado;
    }
    
    public Date getFecha() {
        return fecha;
    }
    
    @Override
    public String toString() {
        return "Jugador 1: " + jugador1 + " - Jugador 2: " + jugador2 + " - Resultado: " + resultado;
    }
}