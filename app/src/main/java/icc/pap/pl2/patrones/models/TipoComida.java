package icc.pap.pl2.patrones.models;


public enum TipoComida {
    NORMAL("Normal"),
    PUNTUACION_DOBLE("Puntuación doble"),
    VELOCIDAD("Velocidad");

    private final String etiqueta;

    TipoComida(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
