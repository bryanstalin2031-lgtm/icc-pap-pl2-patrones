package icc.pap.pl2.patrones.juego;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import icc.pap.pl2.patrones.models.Coordenada;
import icc.pap.pl2.patrones.models.Serpiente;
import icc.pap.pl2.patrones.models.Tablero;
import icc.pap.pl2.patrones.models.TipoComida;

public class UbicadorComida {

    public static final int PROBABILIDAD_DOBLE = 15;
    public static final int PROBABILIDAD_VELOCIDAD = 15;

    private final Random azar;

    public UbicadorComida() {
        this(new Random());
    }

    public UbicadorComida(Random azar) {
        this.azar = azar;
    }

    public ResultadoUbicacion validarManual(String xTexto, String yTexto, Tablero tablero, Serpiente serpiente) {
        if (xTexto == null || yTexto == null || xTexto.isBlank() || yTexto.isBlank()) {
            return ResultadoUbicacion.rechazo("Ingrese las coordenadas X e Y.");
        }
        int x;
        int y;
        try {
            x = Integer.parseInt(xTexto.trim());
            y = Integer.parseInt(yTexto.trim());
        } catch (NumberFormatException e) {
            return ResultadoUbicacion.rechazo("X e Y deben ser números enteros.");
        }
        Coordenada c = new Coordenada(x, y);
        if (!tablero.estaDentro(c)) {
            return ResultadoUbicacion.rechazo("La coordenada (" + x + ", " + y + ") está fuera del tablero (X: 0-"
                    + (tablero.ancho() - 1) + ", Y: 0-" + (tablero.alto() - 1) + ").");
        }
        if (serpiente.ocupa(c)) {
            return ResultadoUbicacion.rechazo("La casilla (" + x + ", " + y + ") está ocupada por la serpiente.");
        }
        return ResultadoUbicacion.ok(c);
    }

    public ResultadoUbicacion elegirAleatoria(Tablero tablero, Serpiente serpiente) {
        List<Coordenada> libres = new ArrayList<>();
        for (int y = 0; y < tablero.alto(); y++) {
            for (int x = 0; x < tablero.ancho(); x++) {
                Coordenada c = new Coordenada(x, y);
                if (!serpiente.ocupa(c)) {
                    libres.add(c);
                }
            }
        }
        if (libres.isEmpty()) {
            return ResultadoUbicacion.rechazo("No hay casillas libres.");
        }
        return ResultadoUbicacion.ok(libres.get(azar.nextInt(libres.size())));
    }

    public TipoComida elegirTipoAleatorio() {
        int tirada = azar.nextInt(100);
        if (tirada < PROBABILIDAD_DOBLE) {
            return TipoComida.PUNTUACION_DOBLE;
        }
        if (tirada < PROBABILIDAD_DOBLE + PROBABILIDAD_VELOCIDAD) {
            return TipoComida.VELOCIDAD;
        }
        return TipoComida.NORMAL;
    }
}