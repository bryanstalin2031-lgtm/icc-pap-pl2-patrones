package icc.pap.pl2.patrones.decorator;

import java.util.List;

/** Contrato común del componente base y de los decoradores (patrón Decorator). */
public interface ReglasJuego {

    int calcularPuntos(int puntosBase);

    int intervaloMs();

    /** Descuenta un tick a la duración de los efectos (solo se llama mientras se juega). */
    void avanzarTick();

    /** Devuelve la cadena de reglas sin los decoradores cuya duración terminó. */
    ReglasJuego limpiarExpirados();

    /** Devuelve la cadena sin el efecto indicado (para renovar un efecto repetido). */
    ReglasJuego sinEfecto(String nombreEfecto);

    List<EfectoActivo> efectosActivos();
}