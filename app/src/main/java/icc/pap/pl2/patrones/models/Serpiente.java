package icc.pap.pl2.patrones.models;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/** La cabeza es el primer segmento de la lista. */
public class Serpiente {

    private final Deque<Coordenada> segmentos = new ArrayDeque<>();
    private Direccion direccion;          // la usada en el último movimiento
    private Direccion direccionPendiente; // la que se usará en el próximo movimiento

    public Serpiente(Coordenada cabeza, Direccion direccion, int longitud) {
        this.direccion = direccion;
        this.direccionPendiente = direccion;
        for (int i = 0; i < longitud; i++) {
            segmentos.addLast(new Coordenada(
                    cabeza.x() - i * direccion.dx(),
                    cabeza.y() - i * direccion.dy()));
        }
    }

    public Direccion direccion() {
        return direccion;
    }

    public Coordenada cabeza() {
        return segmentos.peekFirst();
    }

    public Coordenada siguienteCabeza() {
        return cabeza().mover(direccionPendiente);
    }

    /** Rechaza el giro de 180° respecto al último movimiento realizado. */
    public boolean cambiarDireccion(Direccion nueva) {
        if (nueva.esOpuesta(direccion)) {
            return false;
        }
        direccionPendiente = nueva;
        return true;
    }

    public void mover(boolean crecer) {
        direccion = direccionPendiente;
        segmentos.addFirst(cabeza().mover(direccion));
        if (!crecer) {
            segmentos.removeLast();
        }
    }

    public boolean ocupa(Coordenada c) {
        return segmentos.contains(c);
    }

    /** Si no crece, la cola se desplaza y su casilla queda libre: no cuenta como choque. */
    public boolean chocaConCuerpo(Coordenada destino, boolean crecer) {
        int revisar = crecer ? segmentos.size() : segmentos.size() - 1;
        int i = 0;
        for (Coordenada segmento : segmentos) {
            if (i++ >= revisar) {
                break;
            }
            if (segmento.equals(destino)) {
                return true;
            }
        }
        return false;
    }

    public int longitud() {
        return segmentos.size();
    }

    public List<Coordenada> segmentos() {
        return List.copyOf(segmentos);
    }
}
