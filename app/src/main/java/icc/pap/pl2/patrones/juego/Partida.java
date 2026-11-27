package icc.pap.pl2.patrones.juego;

import java.util.List;

import icc.pap.pl2.patrones.decorator.ReglasBase;
import icc.pap.pl2.patrones.decorator.ReglasJuego;
import icc.pap.pl2.patrones.factoryMethod.Comida;
import icc.pap.pl2.patrones.factoryMethod.RegistroFabricas;
import icc.pap.pl2.patrones.models.Coordenada;
import icc.pap.pl2.patrones.models.Direccion;
import icc.pap.pl2.patrones.models.Serpiente;
import icc.pap.pl2.patrones.models.Tablero;
import icc.pap.pl2.patrones.models.TipoComida;
import icc.pap.pl2.patrones.state.EstadoPartida;
import icc.pap.pl2.patrones.state.Preparado;

public class Partida {

    private static final int LONGITUD_INICIAL = 3;

    private final Tablero tablero;
    private final UbicadorComida ubicador;
    private final RegistroFabricas fabricas = new RegistroFabricas();

    private Serpiente serpiente;
    private Comida comidaActual;
    private ReglasJuego reglas;
    private EstadoPartida estado;
    private int puntaje;

    public Partida() {
        this(new Tablero(), new UbicadorComida());

    }

    public Partida(Tablero tablero, UbicadorComida ubicador) {
        this.tablero = tablero;
        this.ubicador = ubicador;
        resetar();
    }

    public ResultadoOperacion iniciar() {
        return estado.iniciar(this);
    }

    public ResultadoOperacion pausar() {
        return estado.pausar(this);
    }

    public ResultadoOperacion renaudar() {
        return estado.reanudar(this);
    }

    public ResultadoOperacion reiniciar() {
        return estado.reiniciar();
    }

    public ResultadoOperacion tick() {
        return estado.avanzar();
    }

    public ResultadoOperacion girar(Direccion direccion) {
        return estado.cambiarDireccion(this, direccion);
    }

    public ResultadoOperacion ubicarManual(String x, String y, TipoComida tipo) {
        return estado.ubicarManual(this, x, y, tipo);
    }

    public ResultadoOperacion ubicarAletoria(TipoComida tipo) {
        return estado.ubicarAleatoria(this, tipo);
    }

    public void cambiarEstado(EstadoPartida nuevo) {
        this.estado = nuevo;
    }

    public void resetar() {
        int xCabeza = Math.min(4, tablero.ancho() - 1);
        serpiente = new Serpiente(new Coordenada(xCabeza, tablero.alto() / 2), Direccion.DERECHA, LONGITUD_INICIAL);
        reglas = new ReglasBase();
        puntaje = 0;
        comidaActual = null;
        estado = new Preparado();
        if (!tableroCompleto()) {
            colocarAletoria(TipoComida.NORMAL);
        }

    }

    public ResultadoOperacion colocarManual(String x, String y, TipoComida tipo) {
        ResultadoOperacion u = ubicador.validartManual(x, y, tablero, serpiente);
        return u.valido() ? fijarComida(u.coordenada(), tipo) : ResultadoOperacion.rechazo(u.mensaje());
    }

    public ResultadoOperacion colocarAletoria(TipoComida tipo) {
        ResultadoOperacion u = ubicador.elegirAletoria(tablero, serpiente);
        return u.valido() ? fijarComida(u.coordenada(), tipo) : ResultadoOperacion.rechazo(u.mensaje());

    }

    private ResultadoOperacion fijarComida(Coordenada c, TipoComida tipo) {
        comidaActual = fabricas.obtener(tipo).generar(c);
        return ResultadoOperacion.ok("Comida " + tipo + "ubicada en(" + c.x() + ", " + c.y() + ").");
    }

    public boolean tableroCompleto() {
        return serpiente.longitud() >= tablero.totalCasillas();
    }

    public boolean fueraDelTablero(Coordenada c) {
        return !tablero.estaDentro(c);
    }

    public boolean hayComidaEn(Coordenada c) {
        return comidaActual != null && comidaActual.posicion().equals(c);
    }

    public void consumirComida() {
        reglas = comidaActual.aplicarEfecto(reglas);
        puntaje += reglas.calcularPuntos(comidaActual.puntosBase());
        comidaActual = null;
    }

    public void avanzarEfectos() {
        reglas.avanzarTick();
        reglas = reglas.limpiarExpirados();
    }

    public Tablero tablero() {
        return tablero;
    }

    public Serpiente serpiente() {
        return serpiente;
    }

    public Comida comidaActual() {
        return comidaActual;
    }

    public int puntaje() {
        return puntaje;
    }

    public int intervaloMs() {
        return reglas.intervaloMs();
    }

    public List<String> efectosActivos() {
        return reglas.efectosActivos();
    }

    public String nombreEstado() {
        return estado.nombre();
    }

    public boolean enMovimiento() {
        return estado.enMovimiento();
    }

}
