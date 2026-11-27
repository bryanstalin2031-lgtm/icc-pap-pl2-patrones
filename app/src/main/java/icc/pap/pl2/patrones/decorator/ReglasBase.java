package icc.pap.pl2.patrones.decorator;

import java.util.List;

/** Componente base: sin efectos. */
public class ReglasBase implements ReglasJuego {

    public static final int INTERVALO_INICIAL_MS = 200;

    @Override
    public int calcularPuntos(int puntosBase) {
        return puntosBase;
    }

    @Override
    public int intervaloMs() {
        return INTERVALO_INICIAL_MS;
    }

    @Override
    public void avanzarTick() {
    }

    @Override
    public ReglasJuego limpiarExpirados() {
        return this;
    }

    @Override
    public ReglasJuego sinEfecto(String nombreEfecto) {
        return this;
    }

    @Override
    public List<EfectoActivo> efectosActivos() {
        return List.of();
    }
}
