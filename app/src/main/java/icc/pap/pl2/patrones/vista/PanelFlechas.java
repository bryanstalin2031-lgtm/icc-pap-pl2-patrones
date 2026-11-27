package icc.pap.pl2.patrones.vista;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;

import javax.swing.JComponent;

import icc.pap.pl2.patrones.models.Direccion;

public class PanelFlechas extends JComponent {

    private static final int TECLA = 32;
    private static final int ESPACIO = 6;

    private Direccion actual = Direccion.DERECHA;

    public PanelFlechas() {
        setOpaque(false);
        setPreferredSize(new Dimension(3 * TECLA + 2 * ESPACIO, 2 * TECLA + ESPACIO));
    }

    public void actualizar(Direccion direccion) {
        this.actual = direccion;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        Tema.suavizar(g2);
        int paso = TECLA + ESPACIO;
        tecla(g2, paso, 0, Direccion.ARRIBA);
        tecla(g2, 0, paso, Direccion.IZQUIERDA);
        tecla(g2, paso, paso, Direccion.ABAJO);
        tecla(g2, 2 * paso, paso, Direccion.DERECHA);
        g2.dispose();
    }

    private void tecla(Graphics2D g2, int x, int y, Direccion direccion) {
        boolean activa = direccion == actual;
        g2.setColor(activa ? new Color(0x10282A) : Tema.BOTON);
        g2.fillRoundRect(x, y, TECLA, TECLA, 10, 10);
        g2.setColor(activa ? Tema.ACENTO : Tema.BOTON_BORDE);
        g2.drawRoundRect(x, y, TECLA, TECLA, 10, 10);
        flecha(g2, x + TECLA / 2.0, y + TECLA / 2.0, direccion, activa ? Tema.ACENTO : Tema.TEXTO);
    }

    private void flecha(Graphics2D g2, double cx, double cy, Direccion d, Color color) {
        double fx = d.dx();
        double fy = d.dy();
        double px = -fy;
        double py = fx;
        Path2D.Double triangulo = new Path2D.Double();
        triangulo.moveTo(cx + fx * 7, cy + fy * 7);
        triangulo.lineTo(cx - fx * 5 + px * 6, cy - fy * 5 + py * 6);
        triangulo.lineTo(cx - fx * 5 - px * 6, cy - fy * 5 - py * 6);
        triangulo.closePath();
        g2.setColor(color);
        g2.fill(triangulo);
    }
}