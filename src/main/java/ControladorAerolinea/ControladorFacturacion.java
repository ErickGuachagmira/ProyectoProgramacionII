package ControladorAerolinea;

import ModeloAerolinea.ModelBoleto;
import ModeloAerolinea.ModelPagoTarjeta;
import ModeloAerolinea.ModeloSesion;
import VistaAerolinea.VistaFacturacion;
import VistaAerolinea.VistaFactura;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ControladorFacturacion implements ActionListener {

    private VistaFacturacion vista;
    private VistaFactura vistaFactura;
    private ModelPagoTarjeta pago;

    private ButtonGroup grupoMetodoPago;
    private ButtonGroup grupoTipoTarjeta;

    public ControladorFacturacion(VistaFacturacion vista) {
        this.vista = vista;

        configurarGrupos();
        ModeloSesion.vistaFacturacion = vista;

        cargarDatosUsuario();
        cargarBoletosEnTabla();

        actualizarCamposResumen();

        if (vista != null) {
            vista.btnFinalizar.addActionListener(this);
            vista.btnCredito.addActionListener(this);
            vista.btnDebito.addActionListener(this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnFinalizar) {
            finalizar();
        }
    }

    private void cargarDatosUsuario() {
        if (ModeloSesion.usuarioActivo != null && vista != null) {
            vista.txtNombre.setText(ModeloSesion.usuarioActivo.getNombreUsuario());
            vista.txtApellido.setText(ModeloSesion.usuarioActivo.getApellidoUsuario());
            vista.txtCedula.setText(ModeloSesion.usuarioActivo.getCedulaUsuario());
            vista.txtDireccion.setText(ModeloSesion.usuarioActivo.getDireccionUsuario());
            vista.txtTelefono.setText(ModeloSesion.usuarioActivo.getTelefonoUsuario());
        }
    }

    private void cargarBoletosEnTabla() {
        if (vista == null || ModeloSesion.boletos == null) return;

        DefaultTableModel model = (DefaultTableModel) vista.tblFactura.getModel();
        model.setRowCount(0);

        for (ModelBoleto b : ModeloSesion.boletos) {
            double precio = obtenerPrecioComoDouble(b);
            model.addRow(new Object[]{
                b.getNumero(),
                b.getFechaIda(),
                b.getVuelo() != null ? b.getVuelo().getOrigen() : "",
                b.getVuelo() != null ? b.getVuelo().getDestino() : "",
                b.getAsiento(),
                b.getPasajero(),
                b.getClase(),
                b.getEquipaje(),
                precio
            });
        }

        actualizarCamposResumen();
    }

    private void configurarGrupos() {
        grupoMetodoPago = new ButtonGroup();
        grupoMetodoPago.add(vista.btnCredito);
        grupoMetodoPago.add(vista.btnDebito);

        grupoTipoTarjeta = new ButtonGroup();
        grupoTipoTarjeta.add(vista.btnVisa);
        grupoTipoTarjeta.add(vista.btnMastercard);
    }

    private double obtenerPrecioComoDouble(ModelBoleto b) {
        if (b == null) return 0.0;
        Object precioObj;
        try {
            precioObj = b.getPrecioFinal();
        } catch (Exception e) {
            return 0.0;
        }

        if (precioObj == null) return 0.0;
        if (precioObj instanceof Number) {
            return ((Number) precioObj).doubleValue();
        }
        String s = precioObj.toString().trim();
        if (s.isEmpty()) return 0.0;
        s = s.replaceAll("[^0-9\\-.,]", "");
        try {
            s = s.replace(",", "");
            return Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            try {
                String alt = s.replace(".", "").replace(',', '.');
                return Double.parseDouble(alt);
            } catch (NumberFormatException ex2) {
                return 0.0;
            }
        }
    }

    private boolean validarDatosTarjeta() {
        String numero = vista.txtTarjeta.getText().trim();
        String cvc = vista.txtCVC.getText().trim();
        String mmaa = vista.txtMMAA.getText().trim();
        String titular = vista.txtTitular.getText().trim();

        if (!numero.matches("\\d{16}")) {
            JOptionPane.showMessageDialog(vista, "Número de tarjeta inválido");
            return false;
        }

        if (!cvc.matches("\\d{3,4}")) {
            JOptionPane.showMessageDialog(vista, "CVC inválido");
            return false;
        }

        if (!mmaa.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            JOptionPane.showMessageDialog(vista, "Fecha inválida (MM/AA)");
            return false;
        }

        if (titular.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el titular");
            return false;
        }

        if (!vista.btnVisa.isSelected() && !vista.btnMastercard.isSelected()) {
            JOptionPane.showMessageDialog(vista, "Seleccione tipo de tarjeta");
            return false;
        }

        if (obtenerModalidad() == null) {
            JOptionPane.showMessageDialog(vista, "Seleccione Crédito o Débito");
            return false;
        }

        return true;
    }

    private String obtenerModalidad() {
        if (vista.btnCredito.isSelected()) return "Crédito";
        if (vista.btnDebito.isSelected()) return "Débito";
        return null;
    }

    private String obtenerTipoTarjeta() {
        if (vista.btnVisa.isSelected()) return "Visa";
        if (vista.btnMastercard.isSelected()) return "Mastercard";
        return null;
    }

    private double calcularMontoDesdeBoletos() {
        double suma = 0.0;
        if (ModeloSesion.boletos == null) return 0.0;
        for (ModelBoleto b : ModeloSesion.boletos) {
            try {
                suma += b.getPrecioFinal();
            } catch (Exception ex) {
            }
        }
        return suma;
    }

    private void finalizar() {
        if (!validarDatosTarjeta()) return;

        double monto = calcularMontoDesdeBoletos();

        if (monto <= 0.0) {
            JOptionPane.showMessageDialog(vista, "No hay un monto válido para facturar (no hay boletos o precios inválidos).");
            return;
        }

        double iva = monto * 0.12;
        double total = monto + iva;

        pago = new ModelPagoTarjeta();
        pago.setNumeroTarjeta(vista.txtTarjeta.getText());
        pago.setCvc(vista.txtCVC.getText());
        pago.setFechaExpiracion(vista.txtMMAA.getText());
        pago.setNombreTitular(vista.txtTitular.getText());
        pago.setModalidad(obtenerModalidad());
        pago.setTipoTarjeta(obtenerTipoTarjeta());
        pago.setMonto(monto);

        ModeloSesion.pagoActivo = pago;

        try {
            vista.txtbase.setText(String.format("%.2f", monto));
            vista.txtMonto.setText(String.format("%.2f", monto));
            vista.txtIVA.setText(String.format("%.2f", iva));
            vista.txtfinal.setText(String.format("%.2f", total));
        } catch (Exception ex) {
            actualizarCamposResumen();
            System.err.println("Error actualizando campos resumen directamente: " + ex.getMessage());
        }

        String factura = generarFacturaTexto(monto, iva, total);
        mostrarFactura(factura);

        limpiarReservas();
    }

    private void actualizarCamposResumen() {
        double base = calcularMontoDesdeBoletos();
        double iva = base * 0.12;
        double total = base + iva;

        DecimalFormat df = new DecimalFormat("#0.00");
        String sBase = df.format(base);
        String sIva = df.format(iva);
        String sTotal = df.format(total);

        try {
            if (vista.txtbase != null) vista.txtbase.setText(sBase);
        } catch (Exception ex) { }
        try {
            if (vista.txtMonto != null) vista.txtMonto.setText(sBase);
        } catch (Exception ex) { }
        try {
            if (vista.txtIVA != null) vista.txtIVA.setText(sIva);
        } catch (Exception ex) { }
        try {
            if (vista.txtfinal != null) vista.txtfinal.setText(sTotal);
        } catch (Exception ex) { }

        setViewFieldText("txtBase", sBase);
        setViewFieldText("txtbase", sBase);
        setViewFieldText("txtMonto", sBase);
        setViewFieldText("txtMontoPagar", sBase);
        setViewFieldText("txtMontoAPagar", sBase);

        setViewFieldText("txtIVA", sIva);
        setViewFieldText("txtIva", sIva);

        setViewFieldText("txtFinal", sTotal);
        setViewFieldText("txtfinal", sTotal);
        setViewFieldText("txtTotal", sTotal);
        setViewFieldText("txtTotalPagar", sTotal);
    }

    private void setViewFieldText(String fieldName, String value) {
        if (vista == null) return;
        try {
            java.lang.reflect.Field f = vista.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            Object obj = f.get(vista);
            if (obj instanceof JTextField) {
                ((JTextField) obj).setText(value);
            }
        } catch (NoSuchFieldException nsf) {
        } catch (Exception ex) {
            System.err.println("No se pudo asignar campo " + fieldName + ": " + ex.getMessage());
        }
    }

    private String generarFacturaTexto(double base, double iva, double total) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SimpleDateFormat sdfDateOnly = new SimpleDateFormat("yyyy-MM-dd");

        sb.append("********** FACTURA **********\n");
        sb.append("Fecha: ").append(sdfDateTime.format(new Date())).append("\n");
        sb.append("Factura #: ").append(generarNumeroFactura()).append("\n\n");

        if (ModeloSesion.usuarioActivo != null) {
            sb.append("Cliente: ").append(nullToEmpty(ModeloSesion.usuarioActivo.getNombreUsuario()))
              .append(" ").append(nullToEmpty(ModeloSesion.usuarioActivo.getApellidoUsuario())).append("\n");
            sb.append("Cédula: ").append(nullToEmpty(ModeloSesion.usuarioActivo.getCedulaUsuario())).append("\n");
            sb.append("Dirección: ").append(nullToEmpty(ModeloSesion.usuarioActivo.getDireccionUsuario())).append("\n");
            sb.append("Teléfono: ").append(nullToEmpty(ModeloSesion.usuarioActivo.getTelefonoUsuario())).append("\n\n");
        } else {
            sb.append("Cliente: (no identificado)\n\n");
        }

        sb.append("----- Boletos -----\n");
        List<ModelBoleto> boletos = ModeloSesion.boletos;
        if (boletos != null && !boletos.isEmpty()) {
            for (ModelBoleto b : boletos) {
                sb.append("Boleto #").append(b.getNumero()).append("\n");
                if (b.getFechaIda() != null) {
                    sb.append("  Fecha Ida: ").append(sdfDateOnly.format(b.getFechaIda())).append("\n");
                } else {
                    sb.append("  Fecha Ida: (no definida)\n");
                }
                if (b.getFechaVuelta() != null) {
                    sb.append("  Fecha Vuelta: ").append(sdfDateOnly.format(b.getFechaVuelta())).append("\n");
                }
                if (b.getVuelo() != null) {
                   sb.append("  Vuelo: ")
                      .append(nullToEmpty(b.getVuelo().getOrigen()))
                      .append(" -> ")
                      .append(nullToEmpty(b.getVuelo().getDestino()))
                      .append("\n");
                } else {
                    sb.append("  Vuelo: (no asignado)\n");
                }
                sb.append("  Asiento: ").append(nullToEmpty(b.getAsiento())).append("\n");
                sb.append("  Pasajero: ").append(nullToEmpty(b.getPasajero())).append("\n");
                sb.append("  Clase: ").append(nullToEmpty(b.getClase())).append("\n");
                sb.append("  Equipaje: ").append(nullToEmpty(b.getEquipaje())).append("\n");
                sb.append(String.format("  Precio: %s\n", formatCurrency(b.getPrecioFinal())));
                sb.append("------------------------------\n");
            }
        } else {
            sb.append("(No hay boletos listados)\n");
        }

        sb.append("\n----- Pago -----\n");
        ModelPagoTarjeta pago = ModeloSesion.pagoActivo;
        if (pago != null) {
            sb.append("Modalidad: ").append(nullToEmpty(pago.getModalidad())).append("\n");
            sb.append("Tipo Tarjeta: ").append(nullToEmpty(pago.getTipoTarjeta())).append("\n");
            sb.append("Titular: ").append(nullToEmpty(pago.getNombreTitular())).append("\n");
            sb.append("Número Tarjeta: ").append(maskCard(pago.getNumeroTarjeta())).append("\n");
            if (pago.getFechaExpiracion() != null) sb.append("Expiración: ").append(pago.getFechaExpiracion()).append("\n");
        } else {
            sb.append("(Pago no registrado en sesión)\n");
        }

        sb.append("\n----- Resumen -----\n");
        sb.append("Total Base: ").append(formatCurrency(base)).append("\n");
        sb.append("IVA (12%): ").append(formatCurrency(iva)).append("\n");
        sb.append("TOTAL A PAGAR: ").append(formatCurrency(total)).append("\n");

        sb.append("\nGracias por su compra. ¡Buen viaje!\n");
        sb.append("*****************************\n");

        return sb.toString();
    }

    private String maskCard(String numeroTarjeta) {
        if (numeroTarjeta == null) return "(no especificado)";
        String s = numeroTarjeta.replaceAll("\\s+", "");
        int len = s.length();
        if (len <= 4) return s;
        String last4 = s.substring(len - 4);
        StringBuilder masked = new StringBuilder();
        for (int i = 0; i < len - 4; i++) {
            if ((i % 4) == 0 && i > 0) masked.append(' ');
            masked.append('X');
        }
        masked.append(' ').append(last4);
        return masked.toString();
    }

    private String generarNumeroFactura() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return "FAC-" + sdf.format(new Date());
    }

    private String formatCurrency(double value) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        return df.format(value);
    }

    private String nullToEmpty(String s) {
        return s == null ? "" : s;
    }

    private void mostrarFactura(String texto) {
        vistaFactura = new VistaFactura();
        vistaFactura.txtFactura.setText(texto);
        vistaFactura.setVisible(true);
        vista.dispose();
    }

    private void limpiarReservas() {
        if (ModeloSesion.boletos != null) {
            ModeloSesion.boletos.clear();
        }
    }
}