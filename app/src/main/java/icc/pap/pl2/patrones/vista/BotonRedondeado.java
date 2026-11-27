package icc.pap.pl2.patrones.vista;

import java.awt.*;
import javax.swing.JButton;

import icc.pap.pl2.patrones.models.TipoComida;

public class BotonRedondeado extends JButton {

    public enum Estilo {
        NORMAL, PRIMARIO
    }

    private final Estilo estilo;
    private TipoComida icono;
    private boolean seleccionado;

    public BotonRedondeado(String texto, Estilo estilo) {
        super(texto);
        this.estilo = estilo;
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setFocusable(false);
        setOpaque(false);
        setRolloverEnabled(true);
        setFont(Tema.negrita(13f));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public void setIcono(TipoComida tipo) {
        this.icono = tipo;
        repaint();
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
        repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        FontMetrics fm = getFontMetrics(getFont());
        return new Dimension(fm.stringWidth(getText()) + 28 + (icono != null ? 22 : 0), 38);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        Tema.suavizar(g2);
        boolean primario = estilo == Estilo.PRIMARIO && !seleccionado;
        Color fondo = primario ? Tema.ACENTO : Tema.BOTON;
        Color borde = primario ? Tema.ACENTO : Tema.BOTON_BORDE;
        if (seleccionado) {
            fondo = new Color(0x10282A);
            borde = Tema.ACENTO;
        }
        if (getModel().isRollover()) {
            fondo = Tema.mezclar(fondo, Color.WHITE, 0.10);
        }
        if (getModel().isPressed()) {
            fondo = Tema.mezclar(fondo, Color.BLACK, 0.20);
        }
        g2.setColor(fondo);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
        g2.setColor(borde);
        g2.setStroke(new BasicStroke(seleccionado ? 1.8f : 1f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);

        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int anchoIcono = icono != null ? 22 : 0;
        int x = (getWidth() - fm.stringWidth(getText()) - anchoIcono) / 2;
        if (icono != null) {
            Tema.icono(g2, icono, x + 8, getHeight() / 2.0, 7, Tema.colorDe(icono));
        }
        g2.setColor(primario ? Tema.FONDO : Tema.TEXTO);
        g2.drawString(getText(), x + anchoIcono, (getHeight() - fm.getHeight()) / 2 + fm.getAscent());
        g2.dispose();
    }
}
