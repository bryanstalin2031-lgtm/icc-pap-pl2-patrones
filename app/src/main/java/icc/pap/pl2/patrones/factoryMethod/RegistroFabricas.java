package icc.pap.pl2.patrones.factoryMethod;

import java.util.EnumMap;
import java.util.Map;

import icc.pap.pl2.patrones.models.TipoComida;

public class RegistroFabricas {
    private final Map<TipoComida, FabricaComida> fabricas = new EnumMap<>(TipoComida.class);

    public RegistroFabricas(){
        fabricas.put(TipoComida.NORMAL, new FabricaComidaNormal());
        fabricas.put(TipoComida.PUNTUACION_DOBLE, new FabricaComidaDoble());
        fabricas.put(TipoComida.VELOCIDAD, new FabricaComidaVelocidad());
    }

    public FabricaComida obtener(TipoComida tipo){
        return fabricas.get(tipo);
    }
}
