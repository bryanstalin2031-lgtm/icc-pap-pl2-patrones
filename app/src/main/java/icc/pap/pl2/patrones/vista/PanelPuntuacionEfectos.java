package icc.pap.pl2.patrones.vista;

import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/** Muestra puntaje, efectos activos con sus ticks restantes e intervalo de desplazamiento. */
public class PanelPuntuacionEfectos extends JPanel {

    private final JLabel puntaje = new JLabel("0");
    private final JLabel efectos = new JLabel();
    private final JLabel intervalo = new JLabel();

    public PanelPuntuacionEfectos() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        setPreferredSize(new Dimension(190, 0));
        puntaje.setFont(puntaje.getFont().deriveFont(Font.BOLD, 28f));
        add(new JLabel("Puntuación"));
        add(puntaje);
        add(javax.swing.Box.createVerticalStrut(16));
        add(new JLabel("Efectos activos"));
        add(efectos);
        add(javax.swing.Box.createVerticalStrut(16));
        add(new JLabel("Intervalo"));
        add(intervalo);
    }

    public void actualizar(int puntos, List<String> efectosActivos, int intervaloMs) {
        puntaje.setText(String.valueOf(puntos));
        efectos.setText(efectosActivos.isEmpty()
                ? "Ninguno"
                : "<html>" + String.join("<br>", efectosActivos) + "</html>");
        intervalo.setText(intervaloMs + " ms");
    }
}