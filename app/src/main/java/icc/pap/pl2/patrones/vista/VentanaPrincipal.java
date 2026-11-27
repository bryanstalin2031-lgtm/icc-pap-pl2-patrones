package icc.pap.pl2.patrones.vista;

import javax.swing.JFrame;

import icc.pap.pl2.patrones.juego.Partida;


public class VentanaPrincipal extends JFrame {

    public VentanaPrincipal(Partida partida) {
        super("SnakeLab · Minijuego");
        setContentPane(new PanelPrincipal(partida));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
