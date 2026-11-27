package icc.pap.pl2.patrones.vista;

import javax.swing.JLabel;
import javax.swing.JPanel;

/** Tarjetas de Puntos, Récord y Ritmo (según el intervalo de desplazamiento). */
public class PanelEstadisticas extends JPanel {

    private final JLabel puntos = valor(26f);
    private final JLabel record = valor(26f);
    private final JLabel ritmo = valor(19f);

    public PanelEstadisticas() {
        setOpaque(false);
        setLayout(new GridLayout(1, 3, 10, 0));
        add(tarjeta("PUNTOS", puntos));
        add(tarjeta("RÉCORD", record));
        add(tarjeta("RITMO", ritmo));
    }

    private static JLabel valor(float tamano) {
        JLabel l = new JLabel("0");
        l.setFont(Tema.negrita(tamano));
        l.setForeground(Tema.TEXTO);
        return l;
    }

    private static PanelRedondeado tarjeta(String titulo, JLabel valor) {
        PanelRedondeado t = new PanelRedondeado();
        t.setLayout(new BorderLayout(0, 4));
        t.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        JLabel etiqueta = new JLabel(titulo);
        etiqueta.setFont(Tema.fuente(11f));
        etiqueta.setForeground(Tema.TEXTO_SUAVE);
        t.add(etiqueta, BorderLayout.NORTH);
        t.add(valor, BorderLayout.CENTER);
        return t;
    }

    public void actualizar(int puntaje, int mejor, int intervaloMs) {
        puntos.setText(String.valueOf(puntaje));
        record.setText(String.valueOf(mejor));
        if (intervaloMs >= 200) {
            ritmo.setText("Normal");
            ritmo.setForeground(Tema.TEXTO);
        } else {
            ritmo.setText(intervaloMs > 50 ? "Rápido" : "Turbo");
            ritmo.setForeground(Tema.ACENTO);
        }
    }
}