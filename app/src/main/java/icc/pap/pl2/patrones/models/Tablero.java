package icc.pap.pl2.patrones.models;


/** Cuadrícula de tamaño fijo: 14 columnas (x: 0-13) por 11 filas (y: 0-10). */
public class Tablero {

    public static final int ANCHO_POR_DEFECTO = 14;
    public static final int ALTO_POR_DEFECTO = 11;

    private final int ancho;
    private final int alto;

    public Tablero() {
        this(ANCHO_POR_DEFECTO, ALTO_POR_DEFECTO);
    }

    public Tablero(int ancho, int alto) {
        if (ancho < 3 || alto < 1) {
            throw new IllegalArgumentException("El tablero debe medir al menos 3 x 1");
        }
        this.ancho = ancho;
        this.alto = alto;
    }

    public int ancho() {
        return ancho;
    }

    public int alto() {
        return alto;
    }

    public int totalCasillas() {
        return ancho * alto;
    }

    public boolean estaDentro(Coordenada c) {
        return c.x() >= 0 && c.x() < ancho && c.y() >= 0 && c.y() < alto;
    }
}
