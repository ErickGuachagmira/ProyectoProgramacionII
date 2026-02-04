package ControladorAerolinea;

import ModeloAerolinea.ModelUsuario;
import ModeloAerolinea.ModeloSesion;
import VistaAerolinea.VistaFacturacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControladorFacturacion implements ActionListener{
    private VistaFacturacion vista;
    
    public ControladorFacturacion(VistaFacturacion vista){
        this.vista = vista;
        
        cargarDatosUsuario();
        
        this.vista.btnFinalizar.addActionListener(this);
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
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == vista.btnFinalizar){
            finalizar();
        }
    }
    private void finalizar(){
        JOptionPane.showMessageDialog(vista, "Factura generada");
        
        limpiarCampos();
        vista.dispose();
    }
    private void limpiarCampos(){
        vista.txtNombre.setText("");
        vista.txtCedula.setText("");
        vista.txtDireccion.setText("");
        vista.txtTelefono.setText("");
    }
}
