package icc.pap.pl2.patrones.models;


public enum Direccion {
    ARRIBA(0, -1), ABAJO(0, 1), IZQUIERDA(-1, 0), DERECHA(1, 0);

    private final int dx;
    private final int dy;
    private Direccion opuesta;

    Direccion(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // Las constantes ya existen aquí, por eso se enlazan en el bloque static
    static {
        ARRIBA.opuesta = ABAJO;
        ABAJO.opuesta = ARRIBA;
        IZQUIERDA.opuesta = DERECHA;
        DERECHA.opuesta = IZQUIERDA;
    }

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }

    public boolean esOpuesta(Direccion otra) {
        return opuesta == otra;
    }
}
