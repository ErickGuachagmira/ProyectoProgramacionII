package Main;

import ControladorAerolinea.ControladorUsuario;
import VistaAerolinea.VistaUsuario;

public class VuelosAeroFIS {

    public static void main(String[] args) {
        VistaUsuario vistaUsuario = new VistaUsuario();
        new ControladorUsuario(vistaUsuario);
        vistaUsuario.setVisible(true);
    }
}
