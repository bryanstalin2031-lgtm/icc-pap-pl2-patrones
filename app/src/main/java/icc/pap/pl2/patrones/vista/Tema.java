package icc.pap.pl2.patrones.vista;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;

import icc.pap.pl2.patrones.models.TipoComida;
public final class Tema {

    public static final Color FONDO = new Color(0x0B1220);
    public static final Color TARJETA = new Color(0x121A2E);
    public static final Color BORDE = new Color(0x1F2B47);
    public static final Color TEXTO = new Color(0xE6EDF7);
    public static final Color TEXTO_SUAVE = new Color(0x8B9AB8);
    public static final Color ACENTO = new Color(0x4FC3A1);
    public static final Color BOTON = new Color(0x1A2440);
    public static final Color BOTON_BORDE = new Color(0x2B3A62);

    public static final Color OK_FONDO = new Color(0x0F2A22);
    public static final Color OK_BORDE = new Color(0x1E5A47);
    public static final Color OK_TEXTO = new Color(0x9BE3C6);
    public static final Color ERROR_FONDO = new Color(0x3A0F1C);
    public static final Color ERROR_BORDE = new Color(0x6B1D33);
    public static final Color ERROR_TEXTO = new Color(0xFFC2CD);

    public static final Color TABLERO = new Color(0xE9E1D3);
    public static final Color LINEAS = new Color(0xD8CEB9);
    public static final Color CUERPO = new Color(0x3F9C8A);
    public static final Color CABEZA = new Color(0x2A6B5E);

    private Tema() {
    }

    public static Font fuente(float tamano) {
        return new Font(Font.SANS_SERIF, Font.PLAIN, 1).deriveFont(tamano);
    }

    public static Font negrita(float tamano) {
        return new Font(Font.SANS_SERIF, Font.BOLD, 1).deriveFont(tamano);
    }

    public static Color colorDe(TipoComida tipo) {
        return switch (tipo) {
            case NORMAL -> new Color(0xE0455B);
            case PUNTUACION_DOBLE -> new Color(0xF0B429);
            case VELOCIDAD -> new Color(0x3BB4E5);
        };
    }

    public static Color mezclar(Color a, Color b, double t) {
        return new Color(
                (int) Math.round(a.getRed() + (b.getRed() - a.getRed()) * t),
                (int) Math.round(a.getGreen() + (b.getGreen() - a.getGreen()) * t),
                (int) Math.round(a.getBlue() + (b.getBlue() - a.getBlue()) * t));
    }

    public static void suavizar(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setStroke(new BasicStroke(1f));
    }

    public static void icono(Graphics2D g, TipoComida tipo, double cx, double cy, double r, Color color) {
        g.setColor(color);
        switch (tipo) {
            case NORMAL -> g.fill(new Ellipse2D.Double(cx - r, cy - r, 2 * r, 2 * r));
            case PUNTUACION_DOBLE -> g.fill(estrella(cx, cy, r));
            case VELOCIDAD -> g.fill(rayo(cx, cy, r));
        }
    }

    private static Path2D estrella(double cx, double cy, double r) {
        Path2D.Double figura = new Path2D.Double();
        for (int i = 0; i < 10; i++) {
            double radio = i % 2 == 0 ? r : r * 0.45;
            double angulo = -Math.PI / 2 + i * Math.PI / 5;
            double x = cx + radio * Math.cos(angulo);
            double y = cy + radio * Math.sin(angulo);
            if (i == 0) {
                figura.moveTo(x, y);
            } else {
                figura.lineTo(x, y);
            }
        }
        figura.closePath();
        return figura;
    }

    private static Path2D rayo(double cx, double cy, double r) {
        double[][] puntos = {{0.15, -1}, {-0.65, 0.1}, {-0.05, 0.1}, {-0.2, 1}, {0.65, -0.15}, {0.05, -0.15}};
        Path2D.Double figura = new Path2D.Double();
        for (int i = 0; i < puntos.length; i++) {
            double x = cx + puntos[i][0] * r;
            double y = cy + puntos[i][1] * r;
            if (i == 0) {
                figura.moveTo(x, y);
            } else {
                figura.lineTo(x, y);
            }
        }
        figura.closePath();
        return figura;
    }
}