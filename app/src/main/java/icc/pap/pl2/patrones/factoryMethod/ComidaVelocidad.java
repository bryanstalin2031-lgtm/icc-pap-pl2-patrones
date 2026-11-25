package icc.pap.pl2.patrones.factoryMethod;

import icc.pap.pl2.patrones.decorator.DecoradorVelocidad;
import icc.pap.pl2.patrones.decorator.ReglasJuego;
import icc.pap.pl2.patrones.models.Coordenada;
import icc.pap.pl2.patrones.models.TipoComida;

public class ComidaVelocidad extends Comida {

    protected ComidaVelocidad(Coordenada posicion) {
        super(posicion);
    }

    @Override
    public TipoComida tipo() {
        return TipoComida.VELOCIDAD;
    }

    @Override
    public ReglasJuego aplicarEfecto(ReglasJuego reglas) {
        return new DecoradorVelocidad(reglas.sinEfecto(DecoradorVelocidad.NOMBRE));
 
    }
    
}
