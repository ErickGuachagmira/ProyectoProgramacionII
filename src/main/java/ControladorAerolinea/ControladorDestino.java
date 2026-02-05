package ControladorAerolinea;

import ControladorAerolinea.ControladorVuelos;
import VistaAerolinea.InterfazVistaVuelos;
import VistaAerolinea.VistaDestino;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class ControladorDestino implements ActionListener {

    private VistaDestino vista;

    public ControladorDestino(VistaDestino vista) {
        this.vista = vista;
        
        this.vista.btnQuito.addActionListener(this);
        this.vista.btnManta.addActionListener(this);
        this.vista.btnGuayaquil.addActionListener(this);
        this.vista.btnGalapagos.addActionListener(this);
        this.vista.btnCuenca.addActionListener(this);
    }

    @Override
public void actionPerformed(ActionEvent e) {

    JPanel panel = null;

    if (e.getSource() == vista.btnQuito) {
        panel = new VistaAerolinea.VuelosDQuito();
    } else if (e.getSource() == vista.btnManta) {
        panel = new VistaAerolinea.VuelosDManta();
    } else if (e.getSource() == vista.btnGuayaquil) {
        panel = new VistaAerolinea.VuelosDGuayaquil();
    } else if (e.getSource() == vista.btnGalapagos) {
        panel = new VistaAerolinea.VuelosDGalapagos();
    } else if (e.getSource() == vista.btnCuenca) {
        panel = new VistaAerolinea.VuelosDCuenca();
    }

    if (panel != null) {
        mostrarPanel(panel);

        if (panel instanceof InterfazVistaVuelos) {
            new ControladorVuelos((InterfazVistaVuelos) panel);
        } else {
            System.err.println(
                "El panel NO implementa InterfazVistaVuelos: "
                + panel.getClass().getSimpleName()
            );
        }
    }
}

    // Método para refrescar el área de contenido
    private void mostrarPanel(JPanel p) {
        p.setSize(vista.ContenedorDestinos.getWidth(), vista.ContenedorDestinos.getHeight());
        p.setLocation(0, 0);
        
        vista.ContenedorDestinos.removeAll();
        vista.ContenedorDestinos.add(p, java.awt.BorderLayout.CENTER);
        vista.ContenedorDestinos.revalidate();
        vista.ContenedorDestinos.repaint();
    }
}

