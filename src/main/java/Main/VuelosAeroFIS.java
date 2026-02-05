package Main;

import ControladorAerolinea.ControladorDestino;
import VistaAerolinea.VistaDestino;

public class VuelosAeroFIS {

    public static void main(String[] args) {
        VistaDestino vistaDestino = new VistaDestino();
        new ControladorDestino(vistaDestino);
        vistaDestino.setVisible(true);
    }
}
