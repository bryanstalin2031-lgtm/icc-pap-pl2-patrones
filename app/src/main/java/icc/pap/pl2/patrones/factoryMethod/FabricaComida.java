package icc.pap.pl2.patrones.factoryMethod;

import icc.pap.pl2.patrones.models.Coordenada;

public abstract class FabricaComida {

    protected abstract Comida crearComida(Coordenada posicion);

    public final Comida generar(Coordenada posicion){
        if(posicion == null){
            throw new IllegalArgumentException("La posición de la comida no puede ser nula");
        }
        return  crearComida(posicion);
    }

}
