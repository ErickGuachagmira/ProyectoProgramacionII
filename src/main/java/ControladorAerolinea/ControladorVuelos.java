package ControladorAerolinea;

import ModeloAerolinea.ModelReserva;
import VistaAerolinea.InterfazVistaVuelos;
import VistaAerolinea.VistaReserva;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class ControladorVuelos implements ActionListener {

    private final InterfazVistaVuelos vista;
    private final String origen;

    public ControladorVuelos(InterfazVistaVuelos vista, String origen) {
        this.vista = vista;
        this.origen = origen;

        for (JButton boton : vista.getBotonesReserva()) {

            for (ActionListener al : boton.getActionListeners()) {
                boton.removeActionListener(al);
            }

            boton.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String destino = "";

        Object src = e.getSource();

        if (src instanceof javax.swing.AbstractButton btn) {
            String actionCmd = btn.getActionCommand();
            String text = btn.getText();
            String name = btn.getName();
            String tip = btn.getToolTipText();

            System.out.println("DEBUG boton: text='" + text + "' actionCmd='" + actionCmd + "' name='" + name + "' tip='" + tip + "'");

            if (actionCmd != null && !actionCmd.trim().isEmpty()) {
                destino = actionCmd.trim();
            } else if (text != null && !text.trim().isEmpty()) {
                destino = text.trim();
            } else if (name != null && !name.trim().isEmpty()) {
                destino = name.trim();
            } else if (tip != null && !tip.trim().isEmpty()) {
                destino = tip.trim();
            } else {
                destino = "";
            }
        } else {
            String ac = e.getActionCommand();
            System.out.println("DEBUG non-button source, actionCommand='" + ac + "'");
            destino = (ac == null) ? "" : ac.trim();
        }

        if (destino == null) destino = "";

        System.out.println("Origen: " + origen + " | Destino: " + destino);

        VistaReserva vistaReserva = new VistaReserva();
        ModelReserva modelo = new ModelReserva();

        modelo.setOrigen(origen);
        modelo.setDestino(destino);

        new ControladorReserva(vistaReserva, modelo);
        vistaReserva.setVisible(true);
    }
}