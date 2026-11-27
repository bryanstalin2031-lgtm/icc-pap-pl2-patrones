package icc.pap.pl2.patrones.state;

import icc.pap.pl2.patrones.juego.Partida;
import icc.pap.pl2.patrones.juego.ResultadoOperacion;
import icc.pap.pl2.patrones.models.Direccion;
import icc.pap.pl2.patrones.models.TipoComida;

/** Se bloquean los desplazamientos; solo Reiniciar permite recuperar la partida. */
public class Terminado implements EstadoPartida {

    private static final String BLOQUEADO = "La partida terminó: use Reiniciar.";

    @Override
    public ResultadoOperacion iniciar(Partida partida) {
        return ResultadoOperacion.rechazo(BLOQUEADO);
    }

    @Override
    public ResultadoOperacion pausar(Partida partida) {
        return ResultadoOperacion.rechazo(BLOQUEADO);
    }

    @Override
    public ResultadoOperacion reanudar(Partida partida) {
        return ResultadoOperacion.rechazo(BLOQUEADO);
    }

    @Override
    public ResultadoOperacion reiniciar(Partida partida) {
        partida.resetear();
        return ResultadoOperacion.ok("Partida reiniciada.");
    }

    @Override
    public ResultadoOperacion avanzar(Partida partida) {
        return ResultadoOperacion.rechazo(BLOQUEADO);
    }

    @Override
    public ResultadoOperacion cambiarDireccion(Partida partida, Direccion direccion) {
        return ResultadoOperacion.rechazo(BLOQUEADO);
    }

    @Override
    public ResultadoOperacion ubicarManual(Partida partida, String x, String y, TipoComida tipo) {
        return ResultadoOperacion.rechazo(BLOQUEADO);
    }

    @Override
    public ResultadoOperacion ubicarAleatoria(Partida partida, TipoComida tipo) {
        return ResultadoOperacion.rechazo(BLOQUEADO);
    }

    @Override
    public String nombre() {
        return "TERMINADO";
    }

    @Override
    public boolean enMovimiento() {
        return false;
    }
}
