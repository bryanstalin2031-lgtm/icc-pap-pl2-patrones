package icc.pap.pl2.patrones.state;

import icc.pap.pl2.patrones.juego.Partida;
import icc.pap.pl2.patrones.juego.ResultadoOperacion;
import icc.pap.pl2.patrones.models.Direccion;

/** Partida inicializada, sin movimiento hasta presionar Iniciar. */
public class Preparado extends EstadoActivo {

    @Override
    public ResultadoOperacion iniciar(Partida partida) {
        partida.cambiarEstado(new Jugando());
        return ResultadoOperacion.ok("Partida iniciada.");
    }

    @Override
    public ResultadoOperacion pausar(Partida partida) {
        return ResultadoOperacion.rechazo("No se puede pausar: la partida aún no ha iniciado.");
    }

    @Override
    public ResultadoOperacion reanudar(Partida partida) {
        return ResultadoOperacion.rechazo("No se puede reanudar: la partida no está en pausa.");
    }

    @Override
    public ResultadoOperacion avanzar(Partida partida) {
        return ResultadoOperacion.rechazo("La partida está preparada: presione Iniciar.");
    }

    @Override
    public ResultadoOperacion cambiarDireccion(Partida partida, Direccion direccion) {
        return ResultadoOperacion.rechazo("Presione Iniciar antes de mover la serpiente.");
    }

    @Override
    public String nombre() {
        return "PREPARADO";
    }
}

