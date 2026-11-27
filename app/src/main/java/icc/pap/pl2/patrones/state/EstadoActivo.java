package icc.pap.pl2.patrones.state;

import icc.pap.pl2.patrones.juego.Partida;
import icc.pap.pl2.patrones.juego.ResultadoOperacion;
import icc.pap.pl2.patrones.models.TipoComida;

/** Base de Preparado, Jugando y Pausado: comparten reinicio y ubicación de comida. */
public abstract class EstadoActivo implements EstadoPartida {

    @Override
    public ResultadoOperacion reiniciar(Partida partida) {
        partida.resetear();
        return ResultadoOperacion.ok("Partida reiniciada.");
    }

    @Override
    public ResultadoOperacion ubicarManual(Partida partida, String x, String y, TipoComida tipo) {
        if (partida.tableroCompleto()) {
            return resolverTableroCompleto(partida);
        }
        return partida.colocarManual(x, y, tipo);
    }

    @Override
    public ResultadoOperacion ubicarAleatoria(Partida partida, TipoComida tipo) {
        if (partida.tableroCompleto()) {
            return resolverTableroCompleto(partida);
        }
        return partida.colocarAleatoria(tipo);
    }

    // Sin casillas libres no hay dónde ubicar comida: se informa y la partida termina
    private ResultadoOperacion resolverTableroCompleto(Partida partida) {
        partida.cambiarEstado(new Terminado());
        return ResultadoOperacion.ok("Tablero completo: no hay casillas libres. Fin de la partida.");
    }

    @Override
    public boolean enMovimiento() {
        return false;
    }
}
