package icc.pap.pl2.patrones.factoryMethod;

import icc.pap.pl2.patrones.models.Coordenada;

public class FabricaComidaNormal extends FabricaComida {

    @Override
    protected Comida crearComida(Coordenada posicion) {
        return new ComidaNormal(posicion);
    }

}
