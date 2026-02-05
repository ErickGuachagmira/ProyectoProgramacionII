package ControladorAerolinea;

import ControladorAerolinea.ControladorFacturacion;
import ModeloAerolinea.ModelEstadoAsientos;
import ModeloAerolinea.ModelReserva;
import VistaAerolinea.VistaAsientosEconomicos;
import VistaAerolinea.VistaAsientosPremium;
import VistaAerolinea.VistaFacturacion;
import VistaAerolinea.VistaReserva;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicButtonUI;
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
        this.vista.btnFormP1.addActionListener(this);
        
        panelEconomico = new VistaAsientosEconomicos();
        panelPremium = new VistaAsientosPremium();
        gestionarBotonesAsiento(panelEconomico, "Economico");
        gestionarBotonesAsiento(panelPremium, "Premium");
        cambiarPanelIzquierdo(panelEconomico);
        this.vista.btnEcono.setSelected(true);
        
        inicializarTabla();
    }
    //codigo de contenedor de variable
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
        } else if (e.getSource() == vista.btnFormP1) {
            irAFacturacion();
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

        // solo botones asiento: btnPA1, btnEA2, etc.
        if (name == null || !name.matches("[PE][A-Z][0-9]+")) return;
            String nombreAsiento = name;


        // ocupado → bloqueado
        if (ModelEstadoAsientos.estaOcupado(nombreAsiento, tipoClase)) {
            boton.setEnabled(false);
            return;
        }

        boton.setEnabled(true);

        for (ActionListener al : boton.getActionListeners()) {
            boton.removeActionListener(al);
        }

        boton.addActionListener(e -> {

            // liberar selección anterior
            if (btnAsientoSeleccionado != null) {
                btnAsientoSeleccionado.setEnabled(true);
            }

            // seleccionar este asiento
            boton.setEnabled(false);
            btnAsientoSeleccionado = boton;
            asientoSeleccionado = nombreAsiento;

            System.out.println("Asiento seleccionado: " + asientoSeleccionado);
        });
    }

    // si es contenedor, seguir bajando
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

    // 👉 agregar a tabla
    model.addRow(new Object[]{
        pasajero,
        vista.jDateChooser2.getDate(),
        "Destino",   // aquí luego pones el real
        clase + " (" + asientoSeleccionado + ")",
        modelo.getTipoEquipaje()
    });

    // 👉 marcar asiento como ocupado + guardar en archivo
    ModelEstadoAsientos.ocuparAsiento(asientoSeleccionado, tipoClase);

    // 👉 limpiar selección
    asientoSeleccionado = null;
    btnAsientoSeleccionado = null;

    JOptionPane.showMessageDialog(vista, "Boleto agregado correctamente");
}
    
    private boolean validarYGuardar() {
        System.out.println("Asiento seleccionado: " + asientoSeleccionado);
        // 1. Validar Fecha
        Date fecha = vista.jDateChooser2.getDate();
        if (fecha == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione una fecha de vuelo.");
            return false;
        }

    // 2. Definir Trayecto
        String trayecto = "";
        if (vista.btnIda.isSelected()) {
            trayecto = "Ida";
        } else if (vista.btnIdayVuelta.isSelected()) {
            trayecto = "Ida y vuelta";
        } else {
            JOptionPane.showMessageDialog(vista, "Seleccione su tipo de trayecto.");
            return false;
        }

        // 3. Definir Clase
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
        
        // 4. Definir Equipaje
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

        // 5. Guardar en el Modelo (Corrigiendo las líneas rojas de tus setters)
        modelo.setFechaVuelo(fecha);
        modelo.setTipoTrayecto(trayecto);
        modelo.setClaseVuelo(clase);
        modelo.setTipoEquipaje(equipaje);

        System.out.println("Modelo actualizado: " + modelo.getClaseVuelo());
        return true;
    
    }
    private void irAFacturacion() {
        if (vista.tblVuelos.getRowCount() == 0) {
            JOptionPane.showMessageDialog(vista, "Debe agregar al menos un boleto a la lista antes de pagar.");
            return;
        }
        VistaFacturacion vistaFacturacion = new VistaFacturacion();
        new ControladorFacturacion(vistaFacturacion);
        vistaFacturacion.setVisible(true);
        vista.dispose();
    }
}

