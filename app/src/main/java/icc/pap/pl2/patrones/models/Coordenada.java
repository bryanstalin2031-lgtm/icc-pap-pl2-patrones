package icc.pap.pl2.patrones.models;

public record Coordenada(int x, int y) {
    public Coordenada mover(Direccion direccion) {
        return new Coordenada(x + direccion.dx(), y + direccion.dy());
    }

}
