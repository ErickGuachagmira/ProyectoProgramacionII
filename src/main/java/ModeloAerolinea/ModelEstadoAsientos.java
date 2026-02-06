package ModeloAerolinea;

import java.util.HashSet;
import java.util.Set;

public class ModelEstadoAsientos {
    private static Set<String> ocupadosEconomicos = new HashSet<>();
    private static Set<String> ocupadosPremium = new HashSet<>();

    public static boolean estaOcupado(String nombreAsiento, String tipo) {
        if (tipo.equals("Premium")) {
            return ocupadosPremium.contains(nombreAsiento);
        }
        return ocupadosEconomicos.contains(nombreAsiento);
    }

    public static void ocuparAsiento(String nombreAsiento, String tipo) {
        if (tipo.equals("Premium")) {
            ocupadosPremium.add(nombreAsiento);
        } else {
            ocupadosEconomicos.add(nombreAsiento);
        }
    }
}
