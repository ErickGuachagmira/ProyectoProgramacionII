package ModeloAerolinea;

import java.util.Date;

public class ModelVuelo {
    private String origen;
    private String destino;
    private Date fecha;
    private String hora;
    private double precio;
    private String clase; 
    private String trayecto;
    

    public ModelVuelo(String origen, String destino, Date fecha, String hora, double precio, String clase, String trayecto) {
        this.origen = origen;
        this.destino = destino;
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.clase = clase;
        this.trayecto = trayecto;
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

    public String getClase() {
        return clase;
    }

    public String getTrayecto() {
        return trayecto;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public void setTrayecto(String trayecto) {
        this.trayecto = trayecto;
    }

    

    @Override
    public String toString() {
        return origen + " -> " + destino + " | " + fecha + " " + hora + " $" + precio;
    }
}
