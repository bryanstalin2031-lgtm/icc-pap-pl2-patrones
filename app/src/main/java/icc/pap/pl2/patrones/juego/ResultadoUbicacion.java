package icc.pap.pl2.patrones.juego;

import icc.pap.pl2.patrones.models.Coordenada;

public record ResultadoUbicacion(boolean valido, Coordenada coordenada, String mensaje) {

    public static ResultadoUbicacion ok(Coordenada coordenada) {
        return new ResultadoUbicacion(true, coordenada, "");
    }

    public static ResultadoUbicacion rechazo(String mensaje) {
        return new ResultadoUbicacion(false, null, mensaje);
    }

}
