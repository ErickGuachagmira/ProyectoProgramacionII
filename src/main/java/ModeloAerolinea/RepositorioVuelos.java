package ModeloAerolinea;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RepositorioVuelos {
    private static List<ModelVuelo> vuelos = new ArrayList<>();

    static {
        Calendar cal = Calendar.getInstance();

        cal.set(2026, Calendar.JUNE, 10);
        Date fecha1 = cal.getTime();

        cal.set(2026, Calendar.JUNE, 11);
        Date fecha2 = cal.getTime();

        vuelos.add(new ModelVuelo("Quito", "Guayaquil", fecha1, "08:00", 120.00));
        vuelos.add(new ModelVuelo("Quito", "Guayaquil", fecha1, "16:00", 130.00));
        vuelos.add(new ModelVuelo("Quito", "Cuenca", fecha1, "14:00", 95.00));
        vuelos.add(new ModelVuelo("Quito", "Manta", fecha2, "09:30", 110.00));
        vuelos.add(new ModelVuelo("Quito", "Cuenca", fecha2, "18:00", 100.00));
    }

    public static List<ModelVuelo> getVuelos() {
        return vuelos;
    }
}
