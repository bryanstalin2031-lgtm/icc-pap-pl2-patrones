package icc.pap.pl2.patrones.vista;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Ellipse2D;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.JPanel;

import icc.pap.pl2.patrones.factoryMethod.Comida;
import icc.pap.pl2.patrones.juego.Partida;
import icc.pap.pl2.patrones.models.Coordenada;
import icc.pap.pl2.patrones.models.Direccion;
import icc.pap.pl2.patrones.models.Tablero;
import icc.pap.pl2.patrones.models.TipoComida;

public class PanelTablero extends JPanel {

    private static final int CELDA = 40;
    private static final int PAD = 10;
    private static final int EJE_X = 18;
    private static final int EJE_Y = 16;
    private static final int LEYENDA = 34;

    private final Partida partida;
    private Consumer<Coordenada> alSeleccionar = c -> {
    };
    private Coordenada resaltada;

    public PanelTablero(Partida partida) {
        this.partida = partida;
        Tablero t = partida.tablero();
        setOpaque(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setPreferredSize(
                new Dimension(origenX() + t.ancho() * CELDA + PAD, origenY() + t.alto() * CELDA + PAD + LEYENDA));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Coordenada c = casillaEn(e.getX(), e.getY());
                if (c != null) {
                    alSeleccionar.accept(c);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resaltada = null;
                repaint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                resaltada = casillaEn(e.getX(), e.getY());
                repaint();
            }
        });
    }

    public void setAlSeleccionar(Consumer<Coordenada> accion) {
        this.alSeleccionar = accion;
    }

    private int origenX() {
        return PAD + EJE_X;
    }

    private int origenY() {
        return PAD + EJE_Y;
    }

    private Coordenada casillaEn(int px, int py) {
        Coordenada c = new Coordenada(Math.floorDiv(px - origenX(), CELDA), Math.floorDiv(py - origenY(), CELDA));
        return partida.tablero().estaDentro(c) ? c : null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        Tema.suavizar(g2);
        Tablero t = partida.tablero();
        int ox = origenX();
        int oy = origenY();
        int ancho = t.ancho() * CELDA;
        int alto = t.alto() * CELDA;

        g2.setColor(new Color(0x0F1830));
        g2.fillRoundRect(0, 0, ox + ancho + PAD, oy + alto + PAD, 26, 26);
        g2.setColor(new Color(0x24325A));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 0, ox + ancho + PAD - 1, oy + alto + PAD - 1, 26, 26);

        g2.setFont(Tema.fuente(11f));
        FontMetrics fm = g2.getFontMetrics();
        g2.setColor(new Color(0x6F7FA3));
        for (int x = 0; x < t.ancho(); x++) {
            String s = String.valueOf(x);
            g2.drawString(s, ox + x * CELDA + (CELDA - fm.stringWidth(s)) / 2, oy - 5);
        }
        for (int y = 0; y < t.alto(); y++) {
            String s = String.valueOf(y);
            g2.drawString(s, ox - 6 - fm.stringWidth(s), oy + y * CELDA + (CELDA + fm.getAscent()) / 2 - 2);
        }

        g2.setColor(Tema.TABLERO);
        g2.fillRoundRect(ox, oy, ancho, alto, 14, 14);
        g2.setColor(Tema.LINEAS);
        g2.setStroke(new BasicStroke(1f));
        for (int i = 1; i < t.ancho(); i++) {
            g2.drawLine(ox + i * CELDA, oy + 4, ox + i * CELDA, oy + alto - 4);
        }
        for (int j = 1; j < t.alto(); j++) {
            g2.drawLine(ox + 4, oy + j * CELDA, ox + ancho - 4, oy + j * CELDA);
        }
        if (resaltada != null) {
            g2.setColor(new Color(42, 107, 94, 55));
            g2.fillRoundRect(ox + resaltada.x() * CELDA + 2, oy + resaltada.y() * CELDA + 2, CELDA - 4, CELDA - 4, 8,
                    8);
        }

        Comida comida = partida.comidaActual();
        if (comida != null) {
            dibujarComida(g2, comida, ox, oy);
        }
        dibujarSerpiente(g2, ox, oy);
        dibujarEstadoSobreTablero(g2, ox, oy, ancho, alto);
        dibujarLeyenda(g2, oy + alto + PAD + 22);
        g2.dispose();
    }

    private void dibujarComida(Graphics2D g2, Comida comida, int ox, int oy) {
        Coordenada p = comida.posicion();
        double cx = ox + p.x() * CELDA + CELDA / 2.0;
        double cy = oy + p.y() * CELDA + CELDA / 2.0;
        g2.setColor(new Color(0, 0, 0, 40));
        g2.fill(new Ellipse2D.Double(cx - 13, cy - 11, 28, 28));
        g2.setColor(Tema.colorDe(comida.tipo()));
        g2.fill(new Ellipse2D.Double(cx - 14, cy - 14, 28, 28));
        if (comida.tipo() == TipoComida.NORMAL) {
            g2.setColor(new Color(255, 255, 255, 90));
            g2.fill(new Ellipse2D.Double(cx - 8, cy - 9, 7, 5));
        } else {
            Tema.icono(g2, comida.tipo(), cx, cy, 8, Tema.FONDO);
        }
    }

    private void dibujarSerpiente(Graphics2D g2, int ox, int oy) {
        List<Coordenada> segmentos = partida.serpiente().segmentos();
        for (int i = segmentos.size() - 1; i >= 0; i--) {
            Coordenada s = segmentos.get(i);
            g2.setColor(i == 0 ? Tema.CABEZA : Tema.CUERPO);
            g2.fillRoundRect(ox + s.x() * CELDA + 3, oy + s.y() * CELDA + 3, CELDA - 6, CELDA - 6, 10, 10);
        }
        Coordenada cabeza = segmentos.get(0);
        Direccion d = partida.serpiente().direccion();
        double cx = ox + cabeza.x() * CELDA + CELDA / 2.0;
        double cy = oy + cabeza.y() * CELDA + CELDA / 2.0;
        g2.setColor(Color.WHITE);
        for (int lado = -1; lado <= 1; lado += 2) {
            double ex = cx + d.dx() * 7 + (-d.dy()) * lado * 7;
            double ey = cy + d.dy() * 7 + d.dx() * lado * 7;
            g2.fill(new Ellipse2D.Double(ex - 2.5, ey - 2.5, 5, 5));
        }
    }

    private void dibujarEstadoSobreTablero(Graphics2D g2, int ox, int oy, int ancho, int alto) {
        String estado = partida.nombreEstado();
        if (!estado.equals("PAUSADO") && !estado.equals("TERMINADO")) {
            return;
        }
        g2.setColor(new Color(11, 18, 32, 150));
        g2.fillRoundRect(ox, oy, ancho, alto, 14, 14);
        g2.setFont(Tema.negrita(28f));
        g2.setColor(Tema.TEXTO);
        String texto = estado.equals("PAUSADO") ? "PAUSA" : "FIN DE LA PARTIDA";
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(texto, ox + (ancho - fm.stringWidth(texto)) / 2, oy + alto / 2 + fm.getAscent() / 3);
    }

    private void dibujarLeyenda(Graphics2D g2, int y) {
        g2.setFont(Tema.fuente(12f));
        FontMetrics fm = g2.getFontMetrics();
        int x = 8;
        x = leyendaCuadro(g2, fm, x, y, Tema.CABEZA, "Cabeza");
        x = leyendaCuadro(g2, fm, x, y, Tema.CUERPO, "Cuerpo");
        for (TipoComida tipo : TipoComida.values()) {
            Tema.icono(g2, tipo, x + 5, y - 4, 6, Tema.colorDe(tipo));
            g2.setColor(Tema.TEXTO_SUAVE);
            g2.drawString(tipo.toString(), x + 16, y);
            x += 16 + fm.stringWidth(tipo.toString()) + 16;
        }
    }

    private int leyendaCuadro(Graphics2D g2, FontMetrics fm, int x, int y, Color color, String texto) {
        g2.setColor(color);
        g2.fillRoundRect(x, y - 9, 11, 11, 3, 3);
        g2.setColor(Tema.TEXTO_SUAVE);
        g2.drawString(texto, x + 16, y);
        return x + 16 + fm.stringWidth(texto) + 16;
    }
}