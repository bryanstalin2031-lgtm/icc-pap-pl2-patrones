package icc.pap.pl2.patrones.state;

import icc.pap.pl2.patrones.juego.Partida;
import icc.pap.pl2.patrones.juego.ResultadoOperacion;
import icc.pap.pl2.patrones.models.Direccion;

/** Conserva la posición; la lógica de desplazamiento y la duración de los efectos no avanzan. */
public class Pausado extends EstadoActivo {

    @Override
    public ResultadoOperacion iniciar(Partida partida) {
        return ResultadoOperacion.rechazo("La partida está en pausa: use Reanudar.");
    }

    @Override
    public ResultadoOperacion pausar(Partida partida) {
        return ResultadoOperacion.rechazo("La partida ya está en pausa.");
    }

    @Override
    public ResultadoOperacion reanudar(Partida partida) {
        partida.cambiarEstado(new Jugando());
        return ResultadoOperacion.ok("Partida reanudada.");
    }

    @Override
    public ResultadoOperacion avanzar(Partida partida) {
        return ResultadoOperacion.rechazo("La partida está en pausa.");
    }

    @Override
    public ResultadoOperacion cambiarDireccion(Partida partida, Direccion direccion) {
        return ResultadoOperacion.rechazo("Reanude la partida para mover la serpiente.");
    }

    @Override
    public String nombre() {
        return "PAUSADO";
    }
}