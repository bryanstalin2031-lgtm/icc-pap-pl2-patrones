package icc.pap.pl2.patrones.factoryMethod;

import icc.pap.pl2.patrones.decorator.ReglasJuego;
import icc.pap.pl2.patrones.models.Coordenada;
import icc.pap.pl2.patrones.models.TipoComida;

public abstract class Comida {
    public static final int PUNTOS_BASE = 10;

    protected final Coordenada posicion;

    protected Comida(Coordenada posicion) {
        this.posicion = posicion;
    }

    public Coordenada posicion() {
        return posicion;
    }

    public int puntosBase() {
        return PUNTOS_BASE;
    }

    public abstract TipoComida tipo();

    public abstract ReglasJuego aplicarEfecto(ReglasJuego reglas);

}
