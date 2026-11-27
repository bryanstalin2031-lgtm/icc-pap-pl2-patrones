package icc.pap.pl2.patrones.vista;

import java.awt.*;
import java.util.List;

import javax.swing.JPanel;

import icc.pap.pl2.patrones.decorator.EfectoActivo;
import icc.pap.pl2.patrones.models.TipoComida;

public class PanelEfectos extends JPanel {

    private final TarjetaEfecto doble = new TarjetaEfecto(TipoComida.PUNTUACION_DOBLE);
    private final TarjetaEfecto velocidad = new TarjetaEfecto(TipoComida.VELOCIDAD);

    public PanelEfectos() {
        setOpaque(false);
        setLayout(new GridLayout(1, 2, 10, 0));
        add(doble);
        add(velocidad);
    }

    public void actualizar(List<EfectoActivo> efectos) {
        doble.mostrar(buscar(efectos, TipoComida.PUNTUACION_DOBLE));
        velocidad.mostrar(buscar(efectos, TipoComida.VELOCIDAD));
    }

    private static EfectoActivo buscar(List<EfectoActivo> efectos, TipoComida tipo) {
        for (EfectoActivo e : efectos) {
            if (e.tipo() == tipo) {
                return e;
            }
        }
        return null;
    }

    private static class TarjetaEfecto extends PanelRedondeado {

        private final TipoComida tipo;
        private EfectoActivo efecto;

        TarjetaEfecto(TipoComida tipo) {
            this.tipo = tipo;
            setPreferredSize(new Dimension(100, 72));
        }

        void mostrar(EfectoActivo nuevo) {
            this.efecto = nuevo;
            Color c = Tema.colorDe(tipo);
            if (nuevo != null) {
                setColores(Tema.mezclar(Tema.TARJETA, c, 0.10), Tema.mezclar(Tema.BORDE, c, 0.45));
            } else {
                setColores(Tema.TARJETA, Tema.BORDE);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            Tema.suavizar(g2);
            boolean activo = efecto != null;
            Color color = activo ? Tema.colorDe(tipo) : new Color(0x4A5675);

            Tema.icono(g2, tipo, 26, 26, 10, color);
            g2.setFont(Tema.negrita(13f));
            g2.setColor(activo ? color : Tema.TEXTO_SUAVE);
            g2.drawString(activo ? efecto.nombre() : tipo.toString(), 46, 26);
            g2.setFont(Tema.fuente(12f));
            g2.setColor(Tema.TEXTO_SUAVE);
            g2.drawString(activo ? "Quedan " + efecto.ticksRestantes() + " ticks" : "Inactivo", 46, 44);

            int x = 14;
            int ancho = getWidth() - 28;
            g2.setColor(new Color(0x1D2845));
            g2.fillRoundRect(x, 54, ancho, 6, 6, 6);
            if (activo) {
                double fraccion = Math.max(0, Math.min(1, efecto.ticksRestantes() / (double) efecto.duracionTicks()));
                g2.setColor(color);
                g2.fillRoundRect(x, 54, (int) Math.round(ancho * fraccion), 6, 6, 6);
            }
            g2.dispose();
        }
    }
}
