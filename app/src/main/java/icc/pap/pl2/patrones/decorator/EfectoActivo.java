package icc.pap.pl2.patrones.decorator;

import icc.pap.pl2.patrones.models.TipoComida;

/** Datos de un efecto vigente: la vista los usa para mostrar nombre, ticks restantes y barra. */
public record EfectoActivo(TipoComida tipo, String nombre,
     int ticksRestantes, int duracionTicks) {
}
