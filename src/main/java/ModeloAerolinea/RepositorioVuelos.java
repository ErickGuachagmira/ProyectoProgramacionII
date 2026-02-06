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
        Date f1 = cal.getTime();
        cal.set(2026, Calendar.JUNE, 11);
        Date f2 = cal.getTime();
        cal.set(2026, Calendar.JUNE, 12);
        Date f3 = cal.getTime();
        cal.set(2026, Calendar.JUNE, 12);
        Date f4 = cal.getTime();;

        vuelos.add(new ModelVuelo("Galápagos", "Manta", f1, "08:00", 160, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Galápagos", "Manta", f2, "08:00", 290, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Galápagos", "Manta", f3, "08:00", 270, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Galápagos", "Manta", f4, "08:00", 490, "Premium", "Ida y Vuelta"));

        vuelos.add(new ModelVuelo("Galápagos", "Guayaquil", f1, "08:00", 115, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Galápagos", "Guayaquil", f2, "08:00", 230, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Galápagos", "Guayaquil", f3, "08:00", 200, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Galápagos", "Guayaquil", f4, "08:00", 390, "Premium", "Ida y Vuelta"));

        vuelos.add(new ModelVuelo("Galápagos", "Quito", f1, "08:00", 130, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Galápagos", "Quito", f2, "08:00", 250, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Galápagos", "Quito", f3, "08:00", 220, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Galápagos", "Quito", f4, "08:00", 425, "Premium", "Ida y Vuelta"));

        vuelos.add(new ModelVuelo("Galápagos", "Cuenca", f1, "08:00", 150, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Galápagos", "Cuenca", f2, "08:00", 280, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Galápagos", "Cuenca", f3, "08:00", 255, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Galápagos", "Cuenca", f4, "08:00", 480, "Premium", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Cuenca", "Galápagos", f1, "08:00", 150, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Cuenca", "Galápagos", f2, "08:00", 280, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Cuenca", "Galápagos", f3, "08:00", 255, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Cuenca", "Galápagos", f4, "08:00", 480, "Premium", "Ida y Vuelta"));

        vuelos.add(new ModelVuelo("Cuenca", "Quito", f1, "08:00", 60, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Cuenca", "Quito", f2, "08:00", 120, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Cuenca", "Quito", f3, "08:00", 100, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Cuenca", "Quito", f4, "08:00", 200, "Premium", "Ida y Vuelta"));

        vuelos.add(new ModelVuelo("Cuenca", "Guayaquil", f1, "08:00", 55, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Cuenca", "Guayaquil", f2, "08:00", 110, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Cuenca", "Guayaquil", f3, "08:00", 90, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Cuenca", "Guayaquil", f4, "08:00", 185, "Premium", "Ida y Vuelta"));

        vuelos.add(new ModelVuelo("Cuenca", "Manta", f1, "08:00", 75, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Cuenca", "Manta", f2, "08:00", 140, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Cuenca", "Manta", f3, "08:00", 130, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Cuenca", "Manta", f4, "08:00", 235, "Premium", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Manta", "Galápagos", f1, "08:00", 160, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Manta", "Galápagos", f2, "08:00", 290, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Manta", "Galápagos", f3, "08:00", 270, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Manta", "Galápagos", f4, "08:00", 490, "Premium", "Ida y Vuelta"));

        vuelos.add(new ModelVuelo("Manta", "Guayaquil", f1, "08:00", 60, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Manta", "Guayaquil", f2, "08:00", 120, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Manta", "Guayaquil", f3, "08:00", 100, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Manta", "Guayaquil", f4, "08:00", 200, "Premium", "Ida y Vuelta"));

        vuelos.add(new ModelVuelo("Manta", "Quito", f1, "08:00", 55, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Manta", "Quito", f2, "08:00", 110, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Manta", "Quito", f3, "08:00", 90, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Manta", "Quito", f4, "08:00", 185, "Premium", "Ida y Vuelta"));

        vuelos.add(new ModelVuelo("Manta", "Cuenca", f1, "08:00", 75, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Manta", "Cuenca", f2, "08:00", 140, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Manta", "Cuenca", f3, "08:00", 130, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Manta", "Cuenca", f4, "08:00", 235, "Premium", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Quito", "Galápagos", f1, "08:00", 130, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Quito", "Galápagos", f2, "08:00", 250, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Quito", "Galápagos", f3, "08:00", 220, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Quito", "Galápagos", f4, "08:00", 425, "Premium", "Ida y Vuelta"));

        vuelos.add(new ModelVuelo("Quito", "Cuenca", f1, "08:00", 60, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Quito", "Cuenca", f2, "08:00", 120, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Quito", "Cuenca", f3, "08:00", 100, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Quito", "Cuenca", f4, "08:00", 200, "Premium", "Ida y Vuelta"));

        vuelos.add(new ModelVuelo("Quito", "Manta", f1, "08:00", 55, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Quito", "Manta", f2, "08:00", 110, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Quito", "Manta", f3, "08:00", 90, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Quito", "Manta", f4, "08:00", 185, "Premium", "Ida y Vuelta"));

        vuelos.add(new ModelVuelo("Quito", "Guayaquil", f1, "08:00", 65, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Guayaquil", "Galápagos", f1, "08:00", 115, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Guayaquil", "Galápagos", f2, "08:00", 230, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Guayaquil", "Galápagos", f3, "08:00", 200, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Guayaquil", "Galápagos", f4, "08:00", 390, "Premium", "Ida y Vuelta"));

        vuelos.add(new ModelVuelo("Guayaquil", "Manta", f1, "08:00", 60, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Guayaquil", "Manta", f2, "08:00", 120, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Guayaquil", "Manta", f3, "08:00", 100, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Guayaquil", "Manta", f4, "08:00", 200, "Premium", "Ida y Vuelta"));

        vuelos.add(new ModelVuelo("Guayaquil", "Cuenca", f1, "08:00", 55, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Guayaquil", "Cuenca", f2, "08:00", 110, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Guayaquil", "Cuenca", f3, "08:00", 90, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Guayaquil", "Cuenca", f4, "08:00", 185, "Premium", "Ida y Vuelta"));

        vuelos.add(new ModelVuelo("Guayaquil", "Quito", f1, "08:00", 65, "Económico", "Ida"));
        vuelos.add(new ModelVuelo("Guayaquil", "Quito", f2, "08:00", 125, "Premium", "Ida"));
        vuelos.add(new ModelVuelo("Guayaquil", "Quito", f3, "08:00", 110, "Económico", "Ida y Vuelta"));
        vuelos.add(new ModelVuelo("Guayaquil", "Quito", f4, "08:00", 210, "Premium", "Ida y Vuelta"));
    }

    public static List<ModelVuelo> getVuelos() {
        return vuelos;
    }
}
