package ControladorAerolinea;

import ModeloAerolinea.ModelReserva;
import VistaAerolinea.InterfazVistaVuelos;
import VistaAerolinea.VistaReserva;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ControladorVuelos implements ActionListener{
    private InterfazVistaVuelos vista;

    public ControladorVuelos(InterfazVistaVuelos vista) {
        this.vista = vista;
       
        for (JButton btn : vista.getBotonesReserva()) {
            btn.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        VistaReserva vistaReserva = new VistaReserva();
        ModelReserva modelo = new ModelReserva();

        modelo.setDestino(vista.getDestino());

        new ControladorReserva(vistaReserva, modelo);
        vistaReserva.setVisible(true);
    }
}
