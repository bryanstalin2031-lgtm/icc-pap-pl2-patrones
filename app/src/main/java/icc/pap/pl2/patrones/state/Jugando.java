package icc.pap.pl2.patrones.state;

import icc.pap.pl2.patrones.juego.Partida;
import icc.pap.pl2.patrones.juego.ResultadoOperacion;
import icc.pap.pl2.patrones.models.Coordenada;
import icc.pap.pl2.patrones.models.Direccion;

/** Movimiento activo, consumo de comida y detección de colisiones. */
public class Jugando extends EstadoActivo {

    @Override
    public ResultadoOperacion iniciar(Partida partida) {
        return ResultadoOperacion.rechazo("La partida ya está en juego.");
    }

    @Override
    public ResultadoOperacion pausar(Partida partida) {
        partida.cambiarEstado(new Pausado());
        return ResultadoOperacion.ok("Partida en pausa.");
    }

    @Override
    public ResultadoOperacion reanudar(Partida partida) {
        return ResultadoOperacion.rechazo("La partida no está en pausa.");
    }

    @Override
    public ResultadoOperacion avanzar(Partida partida) {
        partida.avanzarEfectos();
        Coordenada destino = partida.serpiente().siguienteCabeza();
        boolean come = partida.hayComidaEn(destino);

        if (partida.colisiona(destino, come)) {
            partida.cambiarEstado(new Terminado());
            String causa = partida.fueraDelTablero(destino) ? "el borde" : "su propio cuerpo";
            return ResultadoOperacion.ok("Fin de la partida: la serpiente chocó con " + causa
                    + ". Puntaje final: " + partida.puntaje() + ".");
        }

        partida.serpiente().mover(come);
        if (!come) {
            return ResultadoOperacion.ok();
        }

        partida.consumirComida();
        if (partida.tableroCompleto()) {
            partida.cambiarEstado(new Terminado());
            return ResultadoOperacion.ok("¡Tablero completo! Puntaje final: " + partida.puntaje() + ".");
        }
        partida.colocarAutomatica();
        return ResultadoOperacion.ok("Comida consumida. Puntaje: " + partida.puntaje() + ".");
    }

    @Override
    public ResultadoOperacion cambiarDireccion(Partida partida, Direccion direccion) {
        if (partida.serpiente().cambiarDireccion(direccion)) {
            return ResultadoOperacion.ok();
        }
        return ResultadoOperacion.rechazo("Giro de 180° no permitido.");
    }

    @Override
    public String nombre() {
        return "JUGANDO";
    }

    @Override
    public boolean enMovimiento() {
        return true;
    }
}
