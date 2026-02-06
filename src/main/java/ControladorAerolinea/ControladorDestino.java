package ControladorAerolinea;

import ControladorAerolinea.ControladorVuelos;
import VistaAerolinea.InterfazVistaVuelos;
import VistaAerolinea.VistaDestino;
import VistaAerolinea.VuelosDCuenca;
import VistaAerolinea.VuelosDGalapagos;
import VistaAerolinea.VuelosDGuayaquil;
import VistaAerolinea.VuelosDManta;
import VistaAerolinea.VuelosDQuito;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class ControladorDestino implements ActionListener {

    private VistaDestino vista;

    public ControladorDestino(VistaDestino vista) {
        this.vista = vista;
        
        this.vista.btnQuito.setActionCommand("Quito");
        this.vista.btnManta.setActionCommand("Manta");
        this.vista.btnGuayaquil.setActionCommand("Guayaquil");
        this.vista.btnGalapagos.setActionCommand("Galápagos");
        this.vista.btnCuenca.setActionCommand("Cuenca");

        this.vista.btnQuito.addActionListener(this);
        this.vista.btnManta.addActionListener(this);
        this.vista.btnGuayaquil.addActionListener(this);
        this.vista.btnGalapagos.addActionListener(this);
        this.vista.btnCuenca.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JPanel panel = null;
        String origen = null;

        if (e.getSource() == vista.btnQuito) {
            panel = new VuelosDQuito();
            origen = "Quito";

        } else if (e.getSource() == vista.btnGuayaquil) {
            panel = new VuelosDGuayaquil();
            origen = "Guayaquil";

        } else if (e.getSource() == vista.btnCuenca) {
            panel = new VuelosDCuenca();
            origen = "Cuenca";

        } else if (e.getSource() == vista.btnManta) {
            panel = new VuelosDManta();
            origen = "Manta";

        } else if (e.getSource() == vista.btnGalapagos) {
            panel = new VuelosDGalapagos();
            origen = "Galápagos";
        }

        if (panel != null && panel instanceof InterfazVistaVuelos) {
            mostrarPanel(panel);

            new ControladorVuelos(
                (InterfazVistaVuelos) panel,
                origen
            );
        }
    }

    private void mostrarPanel(JPanel p) {
        p.setSize(
            vista.ContenedorDestinos.getWidth(),
            vista.ContenedorDestinos.getHeight()
        );
        p.setLocation(0, 0);

        vista.ContenedorDestinos.removeAll();
        vista.ContenedorDestinos.add(p, java.awt.BorderLayout.CENTER);
        vista.ContenedorDestinos.revalidate();
        vista.ContenedorDestinos.repaint();
    }
}