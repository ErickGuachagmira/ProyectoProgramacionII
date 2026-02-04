package ControladorAerolinea;

import ModeloAerolinea.ModelReserva;
import ModeloAerolinea.ModelUsuario;
import ModeloAerolinea.ModeloSesion;
import ModeloAerolinea.UsuarioDAO;
import VistaAerolinea.VistaReserva;
import VistaAerolinea.VistaUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ControladorUsuario implements ActionListener {
    private VistaUsuario vista;
    private UsuarioDAO dao;
    
    private boolean esValido10Digitos(String texto) {
    return texto.matches("\\d{10}");
    }
    
    public ControladorUsuario (VistaUsuario vista){
        this.vista = vista;
        this.vista.btnReservar.addActionListener(this);
        this.dao = new UsuarioDAO();
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == vista.btnReservar){
            guardarUsuario();
        }
    }
    
    private void guardarUsuario(){
        String nombre = vista.txtNombre.getText();
        String apellido = vista.txtApellido.getText();
        String cedula = vista.txtCedula.getText();
        String direccion = vista.txtDireccion.getText();
        String telefono = vista.txtTelefono.getText();
        
        if(nombre.isEmpty() || cedula.isEmpty()){
            JOptionPane.showMessageDialog(vista, "Ingrese el nombre y la cédula.");
            return;
        }
        
        if (!esValido10Digitos(cedula)) {
        JOptionPane.showMessageDialog(vista, "La cédula debe tener exactamente 10 dígitos.");
        return;
        }
        
        if (!esValido10Digitos(telefono)) {
        JOptionPane.showMessageDialog(vista, "El teléfono debe tener exactamente 10 dígitos.");
        return;
        }
        
        ModelUsuario nuevoUsuario = new ModelUsuario(nombre, apellido, cedula, 
                direccion, telefono);
        
        if(dao.registrarUsuario(nuevoUsuario)){
            ModeloSesion.usuarioActivo = nuevoUsuario;
            JOptionPane.showMessageDialog(vista, "Guardado con exito");
            limpiarCampos();
            VistaReserva vistaReserva = new VistaReserva();
            ModelReserva modeloReserva = new ModelReserva();
            
            new ControladorReserva(vistaReserva, modeloReserva);

            vistaReserva.setVisible(true);

            vista.dispose();
        }else{
            JOptionPane.showMessageDialog(vista, "Error al guardar");
        }
        
    }
    private void limpiarCampos(){
            vista.txtNombre.setText("");
            vista.txtApellido.setText("");
            vista.txtCedula.setText("");
            vista.txtDireccion.setText("");
            vista.txtTelefono.setText("");
        }
    
}
