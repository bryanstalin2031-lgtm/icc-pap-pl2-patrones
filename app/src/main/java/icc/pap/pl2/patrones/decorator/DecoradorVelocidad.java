package icc.pap.pl2.patrones.decorator;

import icc.pap.pl2.patrones.models.TipoComida;

public class DecoradorVelocidad extends DecoradorReglas {

    public static final String NOMBRE = "Velocidad";
    public static final int DURACION_TICKS = 40;
    public static final int INTERVALO_MINIMO_MS = 50;

    public DecoradorVelocidad(ReglasJuego envuelto) {
        super(envuelto, DURACION_TICKS);
    }

    /** Reduce a la mitad el intervalo que entregan las reglas envueltas. */
    @Override
    public int intervaloMs() {
        return Math.max(INTERVALO_MINIMO_MS, envuelto.intervaloMs() / 2);
    }

    @Override
    protected String nombreEfecto() {
        return NOMBRE;
    }

    @Override
    protected TipoComida tipoComida() {
        return TipoComida.VELOCIDAD;
    }
}