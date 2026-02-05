    package ControladorAerolinea;

import ModeloAerolinea.ModelPagoTarjeta;
    import ModeloAerolinea.ModeloSesion;
    import VistaAerolinea.VistaFacturacion;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import javax.swing.ButtonGroup;
    import javax.swing.JOptionPane;
    

    public class ControladorFacturacion implements ActionListener{
        private VistaFacturacion vista;
        private ModelPagoTarjeta pago;
        private ButtonGroup grupoMetodoPago;
        private ButtonGroup grupoTipoTarjeta;

        public ControladorFacturacion(VistaFacturacion vista){
            this.vista = vista;

            cargarDatosUsuario();
            configurarGrupos();
            
            this.vista.btnFinalizar.addActionListener(this);
            this.vista.btnCredito.addActionListener(this);
            this.vista.btnDebito.addActionListener(this);
        }
        private void cargarDatosUsuario() {
            if (ModeloSesion.usuarioActivo != null) {
                vista.txtNombre.setText(ModeloSesion.usuarioActivo.getNombreUsuario());
                vista.txtApellido.setText(ModeloSesion.usuarioActivo.getApellidoUsuario());
                vista.txtCedula.setText(ModeloSesion.usuarioActivo.getCedulaUsuario());
                vista.txtDireccion.setText(ModeloSesion.usuarioActivo.getDireccionUsuario());
                vista.txtTelefono.setText(ModeloSesion.usuarioActivo.getTelefonoUsuario());
            } else {
                System.out.println("Error: No hay usuario en sesión");
            }
        }
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getSource() == vista.btnFinalizar) {
                finalizar();
            }
        }
        
        private void configurarGrupos() {

            grupoMetodoPago = new ButtonGroup();
            grupoMetodoPago.add(vista.btnCredito);
            grupoMetodoPago.add(vista.btnDebito);

            grupoTipoTarjeta = new ButtonGroup();
            grupoTipoTarjeta.add(vista.btnVisa);
            grupoTipoTarjeta.add(vista.btnMastercard);
        }
        
        private void seleccionarMetodoPago() {

            if (!validarDatosTarjeta()) return;

            pago = new ModelPagoTarjeta();
            pago.setNumeroTarjeta(vista.txtTarjeta.getText().trim());
            pago.setCvc(vista.txtCVC.getText().trim());
            pago.setFechaExpiracion(vista.txtMMAA.getText().trim());
            pago.setNombreTitular(vista.txtTitular.getText().trim());
            pago.setModalidad(obtenerModalidad());
            pago.setTipoTarjeta(obtenerTipoTarjeta());
            pago.setMonto(Double.parseDouble(vista.txtMonto.getText()));

            ModeloSesion.pagoActivo = pago;

            JOptionPane.showMessageDialog(
                vista,
                "Pago registrado: " +
                pago.getModalidad() + " - " + pago.getTipoTarjeta()
            );
        }
        
        private boolean validarDatosTarjeta() {

            String numero = vista.txtTarjeta.getText().trim();
            String cvc = vista.txtCVC.getText().trim();
            String mmaa = vista.txtMMAA.getText().trim();
            String titular = vista.txtTitular.getText().trim();

            if (!numero.matches("\\d{16}")) {
            JOptionPane.showMessageDialog(vista, "El número debe tener 16 dígitos");
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

            String[] partes = mmaa.split("/");
            if (!validarFechaExp(partes[0], partes[1])) {
                JOptionPane.showMessageDialog(vista, "Tarjeta expirada");
                return false;
            }

            if (titular.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Ingrese el nombre del titular");
                return false;
            }

            if (!vista.btnVisa.isSelected() && !vista.btnMastercard.isSelected()) {
                JOptionPane.showMessageDialog(vista, "Seleccione Visa o Mastercard");
                return false;
            }

            if (obtenerModalidad() == null) {
                JOptionPane.showMessageDialog(vista, "Seleccione Crédito o Débito");
                return false;
            }

            if (obtenerTipoTarjeta() == null) {
                JOptionPane.showMessageDialog(vista, "Seleccione Visa o Mastercard");
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
        private boolean validarLuhn(String numero) {
            int suma = 0;
            boolean alternar = false;

            for (int i = numero.length() - 1; i >= 0; i--) {
                int n = Character.getNumericValue(numero.charAt(i));
                if (alternar) {
                    n *= 2;
                    if (n > 9) n -= 9;
                }
                suma += n;
                alternar = !alternar;
            }
            return suma % 10 == 0;
        }

        private boolean validarFechaExp(String mes, String año) {
            try {
                int m = Integer.parseInt(mes);
                int a = Integer.parseInt("20" + año);

                if (m < 1 || m > 12) return false;

                java.time.YearMonth fechaExp = java.time.YearMonth.of(a, m);
                java.time.YearMonth actual = java.time.YearMonth.now();

                return !fechaExp.isBefore(actual);

            } catch (NumberFormatException e) {
                return false;
            }
        }
        
        private void finalizar() {

            if (!validarDatosTarjeta()) return;

            pago = new ModelPagoTarjeta();
            pago.setNumeroTarjeta(vista.txtTarjeta.getText().trim());
            pago.setCvc(vista.txtCVC.getText().trim());
            pago.setFechaExpiracion(vista.txtMMAA.getText().trim());
            pago.setNombreTitular(vista.txtTitular.getText().trim());
            pago.setModalidad(obtenerModalidad());
            pago.setTipoTarjeta(obtenerTipoTarjeta());
            pago.setMonto(Double.parseDouble(vista.txtMonto.getText()));

            ModeloSesion.pagoActivo = pago;

            JOptionPane.showMessageDialog(
                vista,
                "Pago registrado:\n" +
                pago.getModalidad() + " - " + pago.getTipoTarjeta()
            );

            vista.dispose();
        }
        
        private void limpiarCampos(){
            vista.txtNombre.setText("");
            vista.txtCedula.setText("");
            vista.txtDireccion.setText("");
            vista.txtTelefono.setText("");
        }
    }
