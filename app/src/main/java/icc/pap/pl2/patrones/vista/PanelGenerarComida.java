package icc.pap.pl2.patrones.vista;

import java.awt.*;
import java.util.*;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.border.LineBorder;

import icc.pap.pl2.patrones.juego.Partida;
import icc.pap.pl2.patrones.juego.ResultadoOperacion;
import icc.pap.pl2.patrones.models.TipoComida;

public class PanelGenerarComida extends PanelRedondeado {

    private final JTextField campoX = campo();
    private final JTextField campoY = campo();
    private final Map<TipoComida, BotonRedondeado> botonesTipo = new EnumMap<>(TipoComida.class);
    private TipoComida seleccionado = TipoComida.NORMAL;

    public PanelGenerarComida(Partida partida, Consumer<ResultadoOperacion> alEjecutar) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(12, 14, 14, 14));

        JLabel titulo = etiqueta("GENERADOR DE ALIMENTOS", Tema.negrita(12f), Tema.TEXTO);

        JPanel tipos = fila(new GridLayout(1, 3, 8, 0));
        for (TipoComida tipo : TipoComida.values()) {
            BotonRedondeado b = new BotonRedondeado(tipo.toString(), BotonRedondeado.Estilo.NORMAL);
            b.setIcono(tipo);
            b.setFont(Tema.negrita(12f));
            b.addActionListener(e -> seleccionar(tipo));
            botonesTipo.put(tipo, b);
            tipos.add(b);
        }
        seleccionar(TipoComida.NORMAL);
        fijarAlto(tipos);

        JPanel coordenadas = fila(new GridLayout(1, 2, 8, 0));
        coordenadas.add(columna("Coordenada X (0-" + (partida.tablero().ancho() - 1) + ")", campoX));
        coordenadas.add(columna("Coordenada Y (0-" + (partida.tablero().alto() - 1) + ")", campoY));
        fijarAlto(coordenadas);

        JPanel acciones = fila(new GridLayout(1, 2, 8, 0));
        BotonRedondeado ubicar = new BotonRedondeado("Ubicar comida", BotonRedondeado.Estilo.PRIMARIO);
        ubicar.addActionListener(e -> alEjecutar.accept(
                partida.ubicarManual(campoX.getText(), campoY.getText(), seleccionado)));
        BotonRedondeado aleatoria = new BotonRedondeado("Generar aleatoria", BotonRedondeado.Estilo.NORMAL);
        aleatoria.addActionListener(e -> alEjecutar.accept(partida.ubicarAleatoria(seleccionado)));
        acciones.add(ubicar);
        acciones.add(aleatoria);
        fijarAlto(acciones);

        add(titulo);
        add(Box.createVerticalStrut(10));
        add(tipos);
        add(Box.createVerticalStrut(10));
        add(coordenadas);
        add(Box.createVerticalStrut(10));
        add(acciones);
    }

    public void setCoordenadas(int x, int y) {
        campoX.setText(String.valueOf(x));
        campoY.setText(String.valueOf(y));
    }

    private void seleccionar(TipoComida tipo) {
        seleccionado = tipo;
        botonesTipo.forEach((t, b) -> b.setSeleccionado(t == tipo));
    }

    private static JLabel etiqueta(String texto, java.awt.Font fuente, java.awt.Color color) {
        JLabel l = new JLabel(texto);
        l.setFont(fuente);
        l.setForeground(color);
        l.setAlignmentX(Component.LEFT_ALIGNMENT);
        return l;
    }

    private static JPanel fila(GridLayout layout) {
        JPanel p = new JPanel(layout);
        p.setOpaque(false);
        p.setAlignmentX(Component.LEFT_ALIGNMENT);
        return p;
    }

    private static void fijarAlto(JComponent c) {
        c.setMaximumSize(new Dimension(Integer.MAX_VALUE, c.getPreferredSize().height));
    }

    private static JPanel columna(String titulo, JTextField campo) {
        JPanel p = new JPanel();
        p.setOpaque(false);
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(etiqueta(titulo, Tema.fuente(11f), Tema.TEXTO_SUAVE));
        p.add(Box.createVerticalStrut(4));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        p.add(campo);
        return p;
    }

    private static JTextField campo() {
        JTextField c = new JTextField();
        c.setBackground(Tema.FONDO);
        c.setForeground(Tema.TEXTO);
        c.setCaretColor(Tema.TEXTO);
        c.setFont(Tema.fuente(13f));
        c.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(Tema.BOTON_BORDE, 1, true), BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        return c;
    }
}
