package icc.pap.pl2.patrones.decorator;

import java.util.ArrayList;
import java.util.List;

import icc.pap.pl2.patrones.models.TipoComida;

/** Decorador abstracto: comparte el contrato y delega en el componente envuelto. */
public abstract class DecoradorReglas implements ReglasJuego {

    protected ReglasJuego envuelto;
    protected int ticksRestantes;
    protected final int duracionTicks;

    protected DecoradorReglas(ReglasJuego envuelto, int duracionTicks) {
        if (envuelto == null) {
            throw new IllegalArgumentException("El decorador necesita reglas que envolver");
        }
        this.envuelto = envuelto;
        this.ticksRestantes = duracionTicks;
        this.duracionTicks = duracionTicks;
    }

    protected abstract String nombreEfecto();

    protected abstract TipoComida tipoComida();

    @Override
    public int calcularPuntos(int puntosBase) {
        return envuelto.calcularPuntos(puntosBase);
    }

    @Override
    public int intervaloMs() {
        return envuelto.intervaloMs();
    }

    @Override
    public void avanzarTick() {
        envuelto.avanzarTick();
        if (ticksRestantes > 0) {
            ticksRestantes--;
        }
    }

    @Override
    public ReglasJuego limpiarExpirados() {
        envuelto = envuelto.limpiarExpirados();
        return ticksRestantes <= 0 ? envuelto : this;
    }

    @Override
    public ReglasJuego sinEfecto(String nombreEfecto) {
        envuelto = envuelto.sinEfecto(nombreEfecto);
        return nombreEfecto.equals(nombreEfecto()) ? envuelto : this;
    }

    @Override
    public List<EfectoActivo> efectosActivos() {
        List<EfectoActivo> efectos = new ArrayList<>(envuelto.efectosActivos());
        efectos.add(new EfectoActivo(tipoComida(), nombreEfecto(), ticksRestantes, duracionTicks));
        return efectos;
    }
}

