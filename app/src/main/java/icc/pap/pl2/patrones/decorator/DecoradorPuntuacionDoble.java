package icc.pap.pl2.patrones.decorator;

import icc.pap.pl2.patrones.models.TipoComida;

public class DecoradorPuntuacionDoble extends DecoradorReglas {

    public static final String NOMBRE = "Puntuación x2";
    public static final int DURACION_TICKS = 50;
    public static final int MULTIPLICADOR = 2;

    public DecoradorPuntuacionDoble(ReglasJuego envuelto) {
        super(envuelto, DURACION_TICKS);
    }

    @Override
    public int calcularPuntos(int puntosBase) {
        return envuelto.calcularPuntos(puntosBase) * MULTIPLICADOR;
    }

    @Override
    protected String nombreEfecto() {
        return NOMBRE;
    }

    @Override
    protected TipoComida tipoComida() {
        return TipoComida.PUNTUACION_DOBLE;
    }
}