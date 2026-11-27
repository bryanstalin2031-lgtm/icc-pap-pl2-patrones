package icc.pap.pl2.patrones.vista;

import java.util.function.Consumer;

import javax.swing.*;
import java.awt.*;

import com.google.common.base.Supplier;

import icc.pap.pl2.patrones.juego.Partida;
import icc.pap.pl2.patrones.juego.ResultadoOperacion;

/** Botones Iniciar, Pausar, Reanudar y Reiniciar, y el estado actual. Los listeners solo delegan. */
public class PanelPartida extends PanelRedondeado {

    private final JLabel estado = new JLabel("Estado actual: PREPARADO");

    public PanelPartida(Partida partida, Consumer<ResultadoOperacion> alEjecutar) {
        setLayout(new BorderLayout(0, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 14, 14, 14));

        JLabel titulo = new JLabel("PARTIDA");
        titulo.setFont(Tema.negrita(12f));
        titulo.setForeground(Tema.TEXTO);
        estado.setFont(Tema.fuente(12f));
        estado.setForeground(Tema.TEXTO_SUAVE);
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setOpaque(false);
        encabezado.add(titulo, BorderLayout.WEST);
        encabezado.add(estado, BorderLayout.EAST);

        JPanel botones = new JPanel(new GridLayout(1, 4, 8, 0));
        botones.setOpaque(false);
        botones.add(boton("Iniciar", BotonRedondeado.Estilo.PRIMARIO, partida::iniciar, alEjecutar));
        botones.add(boton("Pausar", BotonRedondeado.Estilo.NORMAL, partida::pausar, alEjecutar));
        botones.add(boton("Reanudar", BotonRedondeado.Estilo.NORMAL, partida::reanudar, alEjecutar));
        botones.add(boton("Reiniciar", BotonRedondeado.Estilo.NORMAL, partida::reiniciar, alEjecutar));

        add(encabezado, BorderLayout.NORTH);
        add(botones, BorderLayout.CENTER);
    }

    private static BotonRedondeado boton(String texto, BotonRedondeado.Estilo estilo,
            Supplier<ResultadoOperacion> accion, Consumer<ResultadoOperacion> alEjecutar) {
        BotonRedondeado b = new BotonRedondeado(texto, estilo);
        b.addActionListener(e -> alEjecutar.accept(accion.get()));
        return b;
    }

    public void actualizar(String nombreEstado) {
        estado.setText("Estado actual: " + nombreEstado);
    }
}
