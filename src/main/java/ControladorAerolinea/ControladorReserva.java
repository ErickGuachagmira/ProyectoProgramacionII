package ControladorAerolinea;

import ModeloAerolinea.ModelBoleto;
import ModeloAerolinea.ModelEstadoAsientos;
import ModeloAerolinea.ModelReserva;
import ModeloAerolinea.ModelVuelo;
import ModeloAerolinea.RepositorioVuelos;
import ModeloAerolinea.ModeloSesion;
import VistaAerolinea.VistaAsientosEconomicos;
import VistaAerolinea.VistaAsientosPremium;
import VistaAerolinea.VistaReserva;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class ControladorReserva implements ActionListener {

    private final VistaReserva vista;
    private final ModelReserva modelo;

    private final List<ModelVuelo> vuelosMostrados = new ArrayList<>();
    private ModelVuelo vueloSeleccionado;

    private String asientoSeleccionado;
    private JButton btnAsientoSeleccionado;

    private VistaAsientosEconomicos panelEconomico;
    private VistaAsientosPremium panelPremium;

    private static final double PREMIUM_MULTIPLIER = 1.5;
    private static final double ECONOMY_MULTIPLIER = 1.0;
    private static final double TRAYECTO_IDA_VUELTA_MULTIPLIER = 1.8;
    private static final double CARGO_MALETA = 20.0;
    private static final double CARGO_BOLSO = 5.0;
    private static final double CARGO_COMBO = 10.0;
    private static final double MULT_ADULTO = 1.0;
    private static final double MULT_ADULTO_MAYOR = 0.90;
    private static final double MULT_NINO = 0.50;
    private static final double MULT_BEBE = 0.0;

    public ControladorReserva(VistaReserva vista, ModelReserva modelo) {
        this.vista = vista;
        this.modelo = modelo;
        System.out.println("DEBUG ControladorReserva: modelo.origen=" + (modelo == null ? "null" : modelo.getOrigen())
                + " modelo.destino=" + (modelo == null ? "null" : modelo.getDestino()));
        System.out.println("DEBUG Repositorio size=" + (RepositorioVuelos.getVuelos() == null ? 0 : RepositorioVuelos.getVuelos().size()));

        System.out.println("ControladorReserva iniciado. modelo.origen="
                + (modelo == null ? "null" : modelo.getOrigen())
                + " modelo.destino=" + (modelo == null ? "null" : modelo.getDestino()));

        DefaultTableModel tablaModel = new DefaultTableModel(
                new Object[]{"Origen", "Destino", "Fecha", "Hora", "Precio"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 2 -> Date.class;
                    case 4 -> Double.class;
                    default -> String.class;
                };
            }
        };
        vista.tblVuelos.setModel(tablaModel);

        agruparBotones();
        vista.dtVuelta.setEnabled(false);
        vista.btnIda.setSelected(true);
        vista.btnEcono.setSelected(true);
        vista.btnAdulto.setSelected(true);

        removeActionListenersSafe(vista.btnPrem);
        removeActionListenersSafe(vista.btnEcono);
        removeActionListenersSafe(vista.btnAgregar);
        removeActionListenersSafe(vista.btnIda);
        removeActionListenersSafe(vista.btnIdayVuelta);
        removeActionListenersSafe(vista.btnUsuario);

        vista.btnPrem.addActionListener(this);
        vista.btnEcono.addActionListener(this);
        vista.btnAgregar.addActionListener(this);
        vista.btnIda.addActionListener(this);
        vista.btnIdayVuelta.addActionListener(this);
        vista.btnUsuario.addActionListener(this);

        panelEconomico = new VistaAsientosEconomicos();
        panelPremium = new VistaAsientosPremium();
        gestionarBotonesAsiento(panelEconomico, "Economico");
        gestionarBotonesAsiento(panelPremium, "Premium");
        cambiarPanelIzquierdo(panelEconomico);

        inicializarTabla();
        filtrarVuelosPorRuta(modelo == null ? "" : modelo.getOrigen(), modelo == null ? "" : modelo.getDestino());
        if (vuelosMostrados.isEmpty()) {
            System.out.println("DEBUG: vuelosMostrados vacío tras filtrar -> mostrando todos los vuelos del repositorio");
            vuelosMostrados.clear();
            if (RepositorioVuelos.getVuelos() != null) {
                vuelosMostrados.addAll(RepositorioVuelos.getVuelos());
            }
            cargarTabla();
        }

        vista.dtIda.addPropertyChangeListener("date", evt -> {
            Date fecha = vista.dtIda.getDate();
            if (fecha == null) {
                filtrarVuelosPorRuta(modelo == null ? "" : modelo.getOrigen(), modelo == null ? "" : modelo.getDestino());
            } else {
                filtrarVuelosPorRutaYFecha(modelo == null ? "" : modelo.getOrigen(), modelo == null ? "" : modelo.getDestino(), fecha);
            }
        });

        vista.tblVuelos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarVueloDesdeTabla();
            }
        });
    }

    private void removeActionListenersSafe(javax.swing.AbstractButton b) {
        if (b == null) {
            return;
        }
        for (java.awt.event.ActionListener al : b.getActionListeners()) {
            b.removeActionListener(al);
        }
    }

    private String normalize(String s) {
        if (s == null) {
            return "";
        }
        String n = java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD);
        n = n.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return n.trim().toLowerCase();
    }

    private void filtrarVuelosPorRuta(String origen, String destino) {
        vuelosMostrados.clear();
        List<ModelVuelo> all = RepositorioVuelos.getVuelos();
        System.out.println("filtrarVuelosPorRuta: repo size = " + (all == null ? 0 : all.size()));
        if (all == null || all.isEmpty()) {
            cargarTabla();
            return;
        }

        String norigen = normalize(origen);
        String ndestino = normalize(destino);

        for (ModelVuelo v : all) {
            String vo = normalize(v.getOrigen());
            String vd = normalize(v.getDestino());

            boolean matchOrigen = norigen.isEmpty() || vo.equals(norigen);
            boolean matchDestino = ndestino.isEmpty() || vd.equals(ndestino);

            if (matchOrigen && matchDestino) {
                vuelosMostrados.add(v);
            }
        }
        System.out.println("filtrarVuelosPorRuta: vuelosMostrados=" + vuelosMostrados.size());
        cargarTabla();
    }

    private boolean sameDay(Date a, Date b) {
        if (a == null || b == null) {
            return false;
        }
        Calendar ca = Calendar.getInstance();
        Calendar cb = Calendar.getInstance();
        ca.setTime(a);
        cb.setTime(b);
        return ca.get(Calendar.YEAR) == cb.get(Calendar.YEAR)
                && ca.get(Calendar.MONTH) == cb.get(Calendar.MONTH)
                && ca.get(Calendar.DAY_OF_MONTH) == cb.get(Calendar.DAY_OF_MONTH);
    }

    private void filtrarVuelosPorRutaYFecha(String origen, String destino, Date fecha) {
        vuelosMostrados.clear();
        List<ModelVuelo> all = RepositorioVuelos.getVuelos();
        System.out.println("filtrarVuelosPorRutaYFecha: repo size = " + (all == null ? 0 : all.size()));
        if (all == null || all.isEmpty()) {
            cargarTabla();
            return;
        }

        String norigen = origen == null ? "" : origen.trim();
        String ndestino = destino == null ? "" : destino.trim();
        boolean rutaVacia = norigen.isEmpty() && ndestino.isEmpty();

        for (ModelVuelo v : all) {
            String vo = v.getOrigen() == null ? "" : v.getOrigen().trim();
            String vd = v.getDestino() == null ? "" : v.getDestino().trim();

            boolean rutaOk = rutaVacia || (vo.equalsIgnoreCase(norigen) && vd.equalsIgnoreCase(ndestino));
            boolean fechaOk;
            if (fecha == null || v.getFecha() == null) {
                fechaOk = true;
            } else {
                fechaOk = sameDay(v.getFecha(), fecha) || !v.getFecha().before(fecha);
            }
            if (rutaOk && fechaOk) {
                vuelosMostrados.add(v);
            }
        }
        System.out.println("filtrarVuelosPorRutaYFecha: vuelosMostrados=" + vuelosMostrados.size());
        cargarTabla();
    }

    private void cargarTabla() {
        DefaultTableModel model = (DefaultTableModel) vista.tblVuelos.getModel();
        model.setRowCount(0);
        System.out.println("cargarTabla: rows to add = " + vuelosMostrados.size());
        for (ModelVuelo v : vuelosMostrados) {
            model.addRow(new Object[]{
                v.getOrigen(),
                v.getDestino(),
                v.getFecha(),
                v.getHora(),
                v.getPrecio()
            });
        }
        System.out.println("cargarTabla: filas ahora = " + vista.tblVuelos.getRowCount());
    }

    private void seleccionarVueloDesdeTabla() {
        int fila = vista.tblVuelos.getSelectedRow();
        if (fila >= 0 && fila < vuelosMostrados.size()) {
            vueloSeleccionado = vuelosMostrados.get(fila);
            System.out.println("Fila seleccionada: " + fila + " -> " + vueloSeleccionado.getOrigen() + "->" + vueloSeleccionado.getDestino());
        } else {
            vueloSeleccionado = null;
        }
    }

    private void agregarBoleto() {
        if (!validarYGuardar()) {
            return;
        }

        String pasajero
                = vista.btnAdulto.isSelected() ? "Adulto"
                : vista.btnAM.isSelected() ? "Adulto Mayor"
                : vista.btnNiño.isSelected() ? "Niño" : "Bebé";

        String clase = vista.btnPrem.isSelected() ? "Premium" : "Económico";
        String tipoClase = vista.btnPrem.isSelected() ? "Premium" : "Economico";
        String trayecto = vista.btnIdayVuelta.isSelected() ? "Ida y vuelta" : "Ida";

        String equipaje = "";
        if (vista.btnBolso.isSelected()) {
            equipaje = "Bolso";
        }
        if (vista.btnMaleta.isSelected()) {
            if (!equipaje.isEmpty()) {
                equipaje += " + ";
            }
            equipaje += "Maleta";
        }

        ModelBoleto b = new ModelBoleto();
        b.setNumero(ModeloSesion.boletos.size() + 1);
        b.setVuelo(vueloSeleccionado);
        b.setFechaIda(vista.dtIda.getDate());
        b.setClase(clase);
        b.setPasajero(pasajero);
        b.setEquipaje(equipaje);
        b.setAsiento(asientoSeleccionado);
        b.setTrayecto(trayecto);

        double precio = calcularPrecioFinal(
                vueloSeleccionado == null ? 0.0 : vueloSeleccionado.getPrecio(),
                clase, pasajero, equipaje, trayecto
        );
        b.setPrecioFinal(precio);

        ModeloSesion.agregarBoleto(b);

        ModelEstadoAsientos.ocuparAsiento(asientoSeleccionado, tipoClase);

        JOptionPane.showMessageDialog(vista, "Boleto agregado correctamente");
        asientoSeleccionado = null;
        if (btnAsientoSeleccionado != null) {
            btnAsientoSeleccionado.setEnabled(true);
            btnAsientoSeleccionado = null;
        }
    }

    private void irAVistaUsuario() {
        if (ModeloSesion.obtenerBoletos().isEmpty()) {
            JOptionPane.showMessageDialog(
                    vista,
                    "Debe agregar al menos un boleto antes de continuar"
            );
            return;
        }

        VistaAerolinea.VistaUsuario vistaUsuario = new VistaAerolinea.VistaUsuario();
        new ControladorAerolinea.ControladorUsuario(vistaUsuario);

        vistaUsuario.setVisible(true);
        vista.dispose();
    }

    private void agruparBotones() {
        ButtonGroup trayecto = new ButtonGroup();
        trayecto.add(vista.btnIda);
        trayecto.add(vista.btnIdayVuelta);

        ButtonGroup clase = new ButtonGroup();
        clase.add(vista.btnPrem);
        clase.add(vista.btnEcono);

        try {
            vista.BototesTipo.add(vista.btnAdulto);
            vista.BototesTipo.add(vista.btnAM);
            vista.BototesTipo.add(vista.btnNiño);
            vista.BototesTipo.add(vista.btnBebe);
        } catch (Exception ex) {
            ButtonGroup pasajero = new ButtonGroup();
            pasajero.add(vista.btnAdulto);
            pasajero.add(vista.btnAM);
            pasajero.add(vista.btnNiño);
            pasajero.add(vista.btnBebe);
        }
    }

    private void inicializarTabla() {
        ((DefaultTableModel) vista.tblVuelos.getModel()).setRowCount(0);
    }

    private double calcularPrecioFinal(double base, String clase, String pasajero,
            String equipaje, String trayecto) {

        double multClase = "Premium".equalsIgnoreCase(clase) ? PREMIUM_MULTIPLIER : ECONOMY_MULTIPLIER;
        double multPasajero = "Adulto Mayor".equalsIgnoreCase(pasajero) ? MULT_ADULTO_MAYOR
                : "Niño".equalsIgnoreCase(pasajero) ? MULT_NINO
                : "Bebé".equalsIgnoreCase(pasajero) ? MULT_BEBE : MULT_ADULTO;

        double total = base * multClase * multPasajero;

        if (equipaje != null && equipaje.contains("Maleta")) {
            total += CARGO_MALETA;
        }
        if (equipaje != null && equipaje.contains("Bolso")) {
            total += CARGO_BOLSO;
        }
        if (equipaje != null && equipaje.contains("Maleta") && equipaje.contains("Bolso")) {
            total += CARGO_COMBO;
        }

        if ("Ida y vuelta".equalsIgnoreCase(trayecto)) {
            total *= TRAYECTO_IDA_VUELTA_MULTIPLIER;
        }

        return Math.round(total * 100.0) / 100.0;
    }

    private void gestionarBotonesAsiento(JPanel panel, String tipoClase) {
        recorrer(panel, tipoClase);
    }

    private void recorrer(Component c, String tipoClase) {
        if (c instanceof JButton) {
            JButton b = (JButton) c;
            String name = b.getName();
            if (name != null && name.matches("[PE][A-Z][0-9]+")) {
                for (ActionListener al : b.getActionListeners()) {
                    b.removeActionListener(al);
                }

                if (ModelEstadoAsientos.estaOcupado(name, tipoClase)) {
                    b.setEnabled(false);
                } else {
                    b.setEnabled(true);
                    b.addActionListener(e -> {
                        if (btnAsientoSeleccionado != null) {
                            btnAsientoSeleccionado.setEnabled(true);
                        }
                        b.setEnabled(false);
                        asientoSeleccionado = name;
                        btnAsientoSeleccionado = b;
                    });
                }
            }
        }

        if (c instanceof Container) {
            for (Component hijo : ((Container) c).getComponents()) {
                recorrer(hijo, tipoClase);
            }
        }
    }

    private boolean validarYGuardar() {

        if (vueloSeleccionado == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione un vuelo de la tabla");
            return false;
        }

        if (vista.dtIda.getDate() == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione la fecha de ida");
            return false;
        }

        if (vista.btnIdayVuelta.isSelected() && vista.dtVuelta.getDate() == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione la fecha de vuelta");
            return false;
        }

        if (!vista.btnAdulto.isSelected()
                && !vista.btnAM.isSelected()
                && !vista.btnNiño.isSelected()
                && !vista.btnBebe.isSelected()) {
            JOptionPane.showMessageDialog(vista, "Seleccione tipo de pasajero");
            return false;
        }

        if (!vista.btnPrem.isSelected() && !vista.btnEcono.isSelected()) {
            JOptionPane.showMessageDialog(vista, "Seleccione la clase del vuelo");
            return false;
        }

        if (!vista.btnBolso.isSelected() && !vista.btnMaleta.isSelected()) {
            JOptionPane.showMessageDialog(vista, "Seleccione el equipaje");
            return false;
        }

        if (asientoSeleccionado == null || asientoSeleccionado.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Seleccione un asiento");
            return false;
        }

        return true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnIda) {
            vista.dtVuelta.setEnabled(false);
            vista.dtVuelta.setDate(null);
            return;
        }

        if (e.getSource() == vista.btnIdayVuelta) {
            vista.dtVuelta.setEnabled(true);
            return;
        }

        if (e.getSource() == vista.btnPrem) {
            cambiarPanelIzquierdo(panelPremium);
            return;
        }

        if (e.getSource() == vista.btnEcono) {
            cambiarPanelIzquierdo(panelEconomico);
            return;
        }

        if (e.getSource() == vista.btnAgregar) {
            agregarBoleto();
            return;
        }

        if (e.getSource() == vista.btnUsuario) {
            irAVistaUsuario();
            return;
        }
    }

    private void cambiarPanelIzquierdo(JPanel p) {
        vista.BoletosContenedor.removeAll();
        vista.BoletosContenedor.setLayout(new BorderLayout());
        vista.BoletosContenedor.add(p, BorderLayout.CENTER);
        vista.BoletosContenedor.revalidate();
        vista.BoletosContenedor.repaint();
    }
}