package icc.pap.pl2.patrones.factoryMethod;

import icc.pap.pl2.patrones.decorator.ReglasJuego;
import icc.pap.pl2.patrones.models.Coordenada;
import icc.pap.pl2.patrones.models.TipoComida;

public class ComidaNormal extends Comida{

    protected ComidaNormal(Coordenada posicion) {
        super(posicion);
    }

    @Override
    public TipoComida tipo() {
        return TipoComida.NORMAL;
    }

   
    @Override
    public ReglasJuego aplicarEfecto(ReglasJuego reglas) {
       return reglas;
    }
    
}
