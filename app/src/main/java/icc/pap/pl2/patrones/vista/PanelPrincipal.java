package icc.pap.pl2.patrones.vista;

import java.util.Timer;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import icc.pap.pl2.patrones.juego.Partida;
import icc.pap.pl2.patrones.juego.ResultadoOperacion;
import icc.pap.pl2.patrones.models.Direccion;

/** Arma la pantalla, captura acciones, las envía a Partida y refleja los resultados. */
public class PanelPrincipal extends JPanel {

    private final Partida partida;
    private final Timer temporizador;
    private final PanelCabecera cabecera = new PanelCabecera();
    private final PanelTablero panelTablero;
    private final PanelEstadisticas estadisticas = new PanelEstadisticas();
    private final PanelEfectos efectos = new PanelEfectos();
    private final PanelPartida panelPartida;
    private final PanelGenerarComida panelComida;
    private final PanelRedondeado banner = new PanelRedondeado(14);
    private final JLabel textoBanner = new JLabel(" ");

    public PanelPrincipal(Partida partida) {
        super(new BorderLayout(14, 12));
        this.partida = partida;
        setBackground(Tema.FONDO);
        setBorder(BorderFactory.createEmptyBorder(16, 18, 18, 18));

        panelTablero = new PanelTablero(partida);
        panelPartida = new PanelPartida(partida, r -> refrescar(r, true));
        panelComida = new PanelGenerarComida(partida, r -> refrescar(r, true));
        panelTablero.setAlSeleccionar(c -> {
            panelComida.setCoordenadas(c.x(), c.y());
            panelTablero.requestFocusInWindow();
        });
        temporizador = new Timer(partida.intervaloMs(), e -> refrescar(partida.tick(), false));

        banner.setLayout(new BorderLayout());
        banner.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        textoBanner.setFont(Tema.fuente(13f));
        banner.add(textoBanner, BorderLayout.CENTER);

        JPanel izquierda = new JPanel(new BorderLayout(0, 10));
        izquierda.setOpaque(false);
        izquierda.add(panelTablero, BorderLayout.NORTH);
        izquierda.add(banner, BorderLayout.SOUTH);

        JPanel derecha = new JPanel();
        derecha.setOpaque(false);
        derecha.setLayout(new BoxLayout(derecha, BoxLayout.Y_AXIS));
        derecha.setPreferredSize(new Dimension(450, 0));
        derecha.add(fijar(estadisticas));
        derecha.add(Box.createVerticalStrut(10));
        derecha.add(fijar(efectos));
        derecha.add(Box.createVerticalStrut(10));
        derecha.add(fijar(panelPartida));
        derecha.add(Box.createVerticalStrut(10));
        derecha.add(fijar(panelComida));
        derecha.add(Box.createVerticalStrut(10));
        derecha.add(fijar(tarjetaAyuda()));

        add(cabecera, BorderLayout.NORTH);
        add(izquierda, BorderLayout.CENTER);
        add(derecha, BorderLayout.EAST);

        configurarTeclas();
        refrescar(ResultadoOperacion.ok("Presione Iniciar para comenzar."), false);
    }

    private static JComponent fijar(JComponent c) {
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, c.getPreferredSize().height));
        return c;
    }

    private PanelRedondeado tarjetaAyuda() {
        PanelRedondeado t = new PanelRedondeado();
        t.setLayout(new BoxLayout(t, BoxLayout.Y_AXIS));
        t.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        String[] lineas = {
            "Mover: flechas del teclado.",
            "Tablero " + partida.tablero().ancho() + " x " + partida.tablero().alto()
                    + ", origen (0, 0) arriba a la izquierda.",
            "Clic en una casilla del tablero: rellena X e Y."};
        for (String linea : lineas) {
            JLabel l = new JLabel(linea);
            l.setFont(Tema.fuente(12f));
            l.setForeground(Tema.TEXTO_SUAVE);
            l.setAlignmentX(Component.LEFT_ALIGNMENT);
            t.add(l);
        }
        return t;
    }

    private void configurarTeclas() {
        asignarTecla(KeyEvent.VK_UP, Direccion.ARRIBA);
        asignarTecla(KeyEvent.VK_DOWN, Direccion.ABAJO);
        asignarTecla(KeyEvent.VK_LEFT, Direccion.IZQUIERDA);
        asignarTecla(KeyEvent.VK_RIGHT, Direccion.DERECHA);
    }

    private void asignarTecla(int tecla, Direccion direccion) {
        String clave = "girar" + direccion.name();
        getInputMap(WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(tecla, 0), clave);
        getActionMap().put(clave, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refrescar(partida.girar(direccion), false);
            }
        });
    }

    private void refrescar(ResultadoOperacion resultado, boolean tomarFoco) {
        mostrarMensaje(resultado);
        cabecera.actualizar(partida.nombreEstado());
        panelPartida.actualizar(partida.nombreEstado());
        estadisticas.actualizar(partida.puntaje(), partida.record(), partida.intervaloMs());
        efectos.actualizar(partida.efectosActivos());
        panelTablero.repaint();
        sincronizarTemporizador();
        if (tomarFoco) {
            panelTablero.requestFocusInWindow();
        }
    }

    private void mostrarMensaje(ResultadoOperacion resultado) {
        if (resultado.mensaje().isEmpty()) {
            return;
        }
        textoBanner.setText(resultado.mensaje());
        if (resultado.exito()) {
            banner.setColores(Tema.OK_FONDO, Tema.OK_BORDE);
            textoBanner.setForeground(Tema.OK_TEXTO);
        } else {
            banner.setColores(Tema.ERROR_FONDO, Tema.ERROR_BORDE);
            textoBanner.setForeground(Tema.ERROR_TEXTO);
        }
    }

    private void sincronizarTemporizador() {
        if (partida.enMovimiento()) {
            temporizador.setDelay(partida.intervaloMs());
            if (!temporizador.isRunning()) {
                temporizador.start();
            }
        } else {
            temporizador.stop();
        }
    }
}