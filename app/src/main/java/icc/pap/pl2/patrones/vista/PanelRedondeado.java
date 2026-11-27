package icc.pap.pl2.patrones.vista;

import java.awt.*;
import javax.swing.JPanel;

public class PanelRedondeado extends JPanel {

    private final int arco;
    private Color relleno = Tema.TARJETA;
    private Color borde = Tema.BORDE;

    public PanelRedondeado() {
        this(18);
    }

    public PanelRedondeado(int arco) {
        this.arco = arco;
        setOpaque(false);
    }

    public void setColores(Color relleno, Color borde) {
        this.relleno = relleno;
        this.borde = borde;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        Tema.suavizar(g2);
        g2.setColor(relleno);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arco, arco);
        g2.setColor(borde);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arco, arco);
        g2.dispose();
        super.paintComponent(g);
    }
}
