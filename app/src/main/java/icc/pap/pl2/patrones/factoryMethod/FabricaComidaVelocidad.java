package icc.pap.pl2.patrones.factoryMethod;

import icc.pap.pl2.patrones.models.Coordenada;

public class FabricaComidaVelocidad extends FabricaComida{

    @Override
    protected Comida crearComida(Coordenada posicion) {
        return new ComidaVelocidad(posicion);
    }
    
}
