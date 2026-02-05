package ControladorAerolinea;

import ModeloAerolinea.ModelVuelo;
import ModeloAerolinea.RepositorioVuelos;
import java.text.SimpleDateFormat;
import java.util.List;
import ControladorAerolinea.ControladorFacturacion;
import ModeloAerolinea.ModelEstadoAsientos;
import ModeloAerolinea.ModelReserva;
import VistaAerolinea.VistaAsientosEconomicos;
import VistaAerolinea.VistaAsientosPremium;
import VistaAerolinea.VistaReserva;
import VistaAerolinea.VistaUsuario;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class ControladorReserva implements ActionListener {
    private VistaReserva vista;
    private ModelReserva modelo;
    private String asientoSeleccionado = null;
    private JButton btnAsientoSeleccionado = null;
    private VistaAsientosEconomicos panelEconomico;
    private VistaAsientosPremium panelPremium;
    
    public ControladorReserva(VistaReserva vista, ModelReserva modelo){
        this.vista = vista;
        this.modelo = modelo;

        
        agruparBotones();
        this.vista.btnPrem.addActionListener(this);
        this.vista.btnEcono.addActionListener(this);
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnUsuario.addActionListener(this);
        
        panelEconomico = new VistaAsientosEconomicos();
        panelPremium = new VistaAsientosPremium();
        gestionarBotonesAsiento(panelEconomico, "Economico");
        gestionarBotonesAsiento(panelPremium, "Premium");
        cambiarPanelIzquierdo(panelEconomico);
        this.vista.btnEcono.setSelected(true);
        
        inicializarTabla();
    }
    private void agruparBotones() {
        ButtonGroup grupoTrayecto = new ButtonGroup();
        grupoTrayecto.add(vista.btnIda);
        grupoTrayecto.add(vista.btnIdayVuelta);

        ButtonGroup grupoClase = new ButtonGroup();
        grupoClase.add(vista.btnPrem);
        grupoClase.add(vista.btnEcono);
        
        ButtonGroup grupoPasajero = new ButtonGroup();
        grupoPasajero.add(vista.btnAdulto);
        grupoPasajero.add(vista.btnAM);
        grupoPasajero.add(vista.btnNiño);
        grupoPasajero.add(vista.btnBebe);
    }
    
    private void inicializarTabla(){
        DefaultTableModel model = (DefaultTableModel) vista.tblVuelos.getModel();
        model.setRowCount(0);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnPrem) {
            mostrarSeccionPremium();
        } else if (e.getSource() == vista.btnEcono) {
            mostrarSeccionEconomica(); // Faltaba el else if para Econo
        } else if (e.getSource() == vista.btnAgregar) {
            if (validarYGuardar()) {
                agregarBoletoATabla();
            }
        } else if (e.getSource() == vista.btnUsuario) {
            irAInformacionUsuario();
            }
        }
    
    
    public void cambiarPanelIzquierdo(JPanel panelHijo) {
        this.vista.BoletosContenedor.removeAll();
        this.vista.BoletosContenedor.setLayout(new BorderLayout());
        this.vista.BoletosContenedor.add(panelHijo, BorderLayout.CENTER);
        this.vista.BoletosContenedor.revalidate();
        this.vista.BoletosContenedor.repaint();
    }

    public void mostrarSeccionPremium() {
    cambiarPanelIzquierdo(panelPremium);
    }
    public void mostrarSeccionEconomica() {
    cambiarPanelIzquierdo(panelEconomico);
    }
    private void gestionarBotonesAsiento(JPanel panel, String tipoClase) {
    recorrerComponentes(panel, tipoClase);
}

private void recorrerComponentes(Component comp, String tipoClase) {

    if (comp instanceof JButton) {
        JButton boton = (JButton) comp;

        String name = boton.getName();

        if (name == null || !name.matches("[PE][A-Z][0-9]+")) return;
            String nombreAsiento = name;

            if (ModelEstadoAsientos.estaOcupado(nombreAsiento, tipoClase)) {
            boton.setEnabled(false);
            return;
        }

        boton.setEnabled(true);

        for (ActionListener al : boton.getActionListeners()) {
            boton.removeActionListener(al);
        }

        boton.addActionListener(e -> {
            if (btnAsientoSeleccionado != null) {
                btnAsientoSeleccionado.setEnabled(true);
            }
            boton.setEnabled(false);
            btnAsientoSeleccionado = boton;
            asientoSeleccionado = nombreAsiento;

        });
    }
    if (comp instanceof Container) {
        for (Component hijo : ((Container) comp).getComponents()) {
            recorrerComponentes(hijo, tipoClase);
        }
    }
}

    private void agregarBoletoATabla() {

    DefaultTableModel model = (DefaultTableModel) vista.tblVuelos.getModel();

    String pasajero = "";
    if (vista.btnAdulto.isSelected()) pasajero = "Adulto";
    else if (vista.btnAM.isSelected()) pasajero = "Adulto Mayor";
    else if (vista.btnNiño.isSelected()) pasajero = "Niño";
    else if (vista.btnBebe.isSelected()) pasajero = "Bebé";

    String clase = vista.btnPrem.isSelected() ? "Premium" : "Económico";
    String tipoClase = vista.btnPrem.isSelected() ? "Premium" : "Economico";

    model.addRow(new Object[]{
    pasajero,
    vista.jDateChooser2.getDate(),
    modelo.getDestino(),
    clase + " (" + asientoSeleccionado + ")",
    modelo.getTipoEquipaje()
});

    ModelEstadoAsientos.ocuparAsiento(asientoSeleccionado, tipoClase);

    asientoSeleccionado = null;
    btnAsientoSeleccionado = null;

    JOptionPane.showMessageDialog(vista, "Boleto agregado correctamente");
}
    
    private boolean validarYGuardar() {
        System.out.println("Asiento seleccionado: " + asientoSeleccionado);
        Date fecha = vista.jDateChooser2.getDate();
        if (fecha == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione una fecha de vuelo.");
            return false;
        }

        String trayecto = "";
        if (vista.btnIda.isSelected()) {
            trayecto = "Ida";
        } else if (vista.btnIdayVuelta.isSelected()) {
            trayecto = "Ida y vuelta";
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione su tipo de trayecto.");
            return false;
        }

        String clase = "";
        if (vista.btnPrem.isSelected()) {
            clase = "Premium";
        } else if (vista.btnEcono.isSelected()) {
            clase = "Económico";
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione la clase del vuelo.");
            return false;
        }

        if (!vista.btnAdulto.isSelected() && !vista.btnAM.isSelected() && 
            !vista.btnNiño.isSelected() && !vista.btnBebe.isSelected()) {
            JOptionPane.showMessageDialog(vista, "Seleccione el tipo de pasajero.");
            return false;
        }
        
        
        if (asientoSeleccionado == null || asientoSeleccionado.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar un asiento del mapa.");
            return false;
        }
        
        String equipaje = "";
        if (vista.btnBolso.isSelected()) {
            equipaje = "Bolso de Mano";
        }
        if (vista.btnMaleta.isSelected()) {
            if(!equipaje.isEmpty()){
                equipaje += " + ";
            }
            equipaje += "Maleta";
        }
        
        if (equipaje.isEmpty()){
            JOptionPane.showMessageDialog(vista, "Seleccione el tipo de equipaje.");
            return false;
        }

        modelo.setFechaVuelo(fecha);
        modelo.setTipoTrayecto(trayecto);
        modelo.setClaseVuelo(clase);
        modelo.setTipoEquipaje(equipaje);

        System.out.println("Modelo actualizado: " + modelo.getClaseVuelo());
        return true;
    
    }
    private void irAInformacionUsuario() {
        if (vista.tblVuelos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(vista, 
                    "Debe agregar al menos un boleto a la lista antes de pagar.");
            return;
        }
        VistaUsuario vistaUsuario = new VistaUsuario();
        new ControladorUsuario(vistaUsuario);
        vistaUsuario.setVisible(true);
        vista.dispose();
    }
}