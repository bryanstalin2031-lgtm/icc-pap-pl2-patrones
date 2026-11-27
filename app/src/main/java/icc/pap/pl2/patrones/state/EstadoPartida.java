package icc.pap.pl2.patrones.state;

import icc.pap.pl2.patrones.juego.Partida;
import icc.pap.pl2.patrones.juego.ResultadoOperacion;
import icc.pap.pl2.patrones.models.Direccion;
import icc.pap.pl2.patrones.models.TipoComida;

/** Contrato del patrón State: cada estado decide qué operaciones permite y a qué estado pasa. */
public interface EstadoPartida {

    ResultadoOperacion iniciar(Partida partida);

    ResultadoOperacion pausar(Partida partida);

    ResultadoOperacion reanudar(Partida partida);

    ResultadoOperacion reiniciar(Partida partida);

    ResultadoOperacion avanzar(Partida partida);

    ResultadoOperacion cambiarDireccion(Partida partida, Direccion direccion);

    ResultadoOperacion ubicarManual(Partida partida, String x, String y, TipoComida tipo);

    ResultadoOperacion ubicarAleatoria(Partida partida, TipoComida tipo);

    String nombre();

    /** Indica si el temporizador de la ventana debe estar corriendo. */
    boolean enMovimiento();
}
