package icc.pap.pl2.patrones.factoryMethod;

import icc.pap.pl2.patrones.decorator.DecoradorPuntuacionDoble;
import icc.pap.pl2.patrones.decorator.ReglasJuego;
import icc.pap.pl2.patrones.models.Coordenada;
import icc.pap.pl2.patrones.models.TipoComida;

public class ComidaDoble extends Comida {

    protected ComidaDoble(Coordenada posicion) {
        super(posicion);
    }

    @Override
    public TipoComida tipo() {
        return TipoComida.PUNTUACION_DOBLE;
    }

    @Override
    public ReglasJuego aplicarEfecto(ReglasJuego reglas) {
        return new DecoradorPuntuacionDoble(reglas.sinEfecto(DecoradorPuntuacionDoble.NOMBRE));

    }

}
