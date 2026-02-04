package com.mycompany.proyectoprogramacion;

import ControladorAerolinea.ControladorUsuario;
import VistaAerolinea.VistaUsuario;

public class ProyectoProgramacion {

    public static void main(String[] args) {
        VistaUsuario vistaUsuario = new VistaUsuario();
        new ControladorUsuario(vistaUsuario);
        vistaUsuario.setVisible(true);
    }
}
