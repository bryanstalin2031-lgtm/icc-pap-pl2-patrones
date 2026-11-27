package icc.pap.pl2.patrones.vista;

import javax.swing.JComponent;

/** Logo, título y la insignia con el estado actual de la partida. */
public class PanelCabecera extends JComponent {

    private String estado = "PREPARADO";

    public PanelCabecera() {
        setPreferredSize(new Dimension(100, 62));
    }

    public void actualizar(String nombreEstado) {
        this.estado = nombreEstado;
        repaint();
    }

    private Color colorEstado() {
        return switch (estado) {
            case "JUGANDO" -> Tema.ACENTO;
            case "PAUSADO" -> new Color(0xF0B429);
            case "TERMINADO" -> new Color(0xE0455B);
            default -> new Color(0x5AA9FF);
        };
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        Tema.suavizar(g2);

        g2.setPaint(new GradientPaint(0, 0, new Color(0x5FD0AA), 44, 44, new Color(0x3FA88C)));
        g2.fillRoundRect(0, 4, 44, 44, 14, 14);
        Tema.icono(g2, TipoComida.VELOCIDAD, 22, 26, 13, Tema.FONDO);

        g2.setFont(Tema.negrita(22f));
        FontMetrics fm = g2.getFontMetrics();
        g2.setColor(Tema.TEXTO);
        g2.drawString("Snake ", 58, 26);
        g2.setColor(Tema.ACENTO);
        g2.drawString("Lab", 58 + fm.stringWidth("Snake "), 26);
        g2.setFont(Tema.fuente(12f));
        g2.setColor(Tema.TEXTO_SUAVE);
        g2.drawString("Minijuego con State, Decorator y Factory Method", 58, 44);

        Color color = colorEstado();
        g2.setFont(Tema.negrita(12f));
        String texto = "Estado: " + estado;
        int ancho = g2.getFontMetrics().stringWidth(texto) + 44;
        int x = getWidth() - ancho;
        g2.setColor(Tema.mezclar(Tema.FONDO, color, 0.14));
        g2.fillRoundRect(x, 8, ancho, 34, 34, 34);
        g2.setColor(Tema.mezclar(Tema.FONDO, color, 0.55));
        g2.drawRoundRect(x, 8, ancho, 34, 34, 34);
        g2.setColor(color);
        g2.fillOval(x + 15, 21, 8, 8);
        g2.drawString(texto, x + 31, 29);

        g2.setColor(Tema.BORDE);
        g2.drawLine(0, getHeight() - 2, getWidth(), getHeight() - 2);
        g2.dispose();
    }
}