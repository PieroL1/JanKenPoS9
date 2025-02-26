package modelo;

public enum Jugada {
    PIEDRA, PAPEL, TIJERAS;
    
    public static Jugada getJugadaAleatoria() {
        int random = (int) (Math.random() * 3);
        return values()[random];
    }
    
    public boolean ganaA(Jugada otra) {
        if (this == PIEDRA && otra == TIJERAS) return true;
        if (this == PAPEL && otra == PIEDRA) return true;
        if (this == TIJERAS && otra == PAPEL) return true;
        return false;
    }
}
