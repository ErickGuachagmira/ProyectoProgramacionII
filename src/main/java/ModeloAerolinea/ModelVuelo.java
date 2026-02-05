package ModeloAerolinea;

import java.util.Date;

public class ModelVuelo {
    private String origen;
    private String destino;
    private Date fecha;
    private String hora;
    private double precio;

    public ModelVuelo(String origen, String destino, Date fecha, String hora, double precio) {
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public double getPrecio() {
        return precio;
    }
}
