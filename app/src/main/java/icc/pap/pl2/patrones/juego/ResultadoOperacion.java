package icc.pap.pl2.patrones.juego;

public record ResultadoOperacion(boolean exito, String mensaje) {

    public static ResultadoOperacion ok() {
        return new ResultadoOperacion(true, "");
    }

    public static ResultadoOperacion ok(String mensaje) {
        return new ResultadoOperacion(true, mensaje);
    }

    public static ResultadoOperacion rechazo(String mensaje) {
        return new ResultadoOperacion(false, mensaje);
    }
}
