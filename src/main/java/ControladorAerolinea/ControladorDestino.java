package ControladorAerolinea;

import VistaAerolinea.VistaDestino;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

public class ControladorDestino implements ActionListener {

    private VistaDestino vista;

    public ControladorDestino(VistaDestino vista) {
        this.vista = vista;
        
        // Escuchar los clics de los botones de origen
        this.vista.btnQuito.addActionListener(this);
        this.vista.btnManta.addActionListener(this);
        this.vista.btnGuayaquil.addActionListener(this);
        this.vista.btnGalapagos.addActionListener(this);
        this.vista.btnCuenca.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Lógica para cambiar paneles según el botón presionado
        if (e.getSource() == vista.btnQuito) {
            mostrarPanel(new VistaAerolinea.VuelosDQuito());
        } else if (e.getSource() == vista.btnManta) {
            mostrarPanel(new VistaAerolinea.VuelosDManta());
        } else if (e.getSource() == vista.btnGuayaquil) {
            mostrarPanel(new VistaAerolinea.VuelosDGuayaquil());
        } else if (e.getSource() == vista.btnGalapagos) {
            mostrarPanel(new VistaAerolinea.VuelosDGalapagos());
        } else if (e.getSource() == vista.btnCuenca) {
            mostrarPanel(new VistaAerolinea.VuelosDCuenca());
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

