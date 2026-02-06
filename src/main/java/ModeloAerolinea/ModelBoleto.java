package ModeloAerolinea;

import java.util.Date;

public class ModelBoleto {
    private int numero;
    private ModelVuelo vuelo;
    private Date fechaIda;
    private Date fechaVuelta;
    private String clase;
    private String trayecto;
    private String pasajero;
    private String equipaje;
    private String asiento;
    private double precioFinal;

    public ModelBoleto() {}

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public ModelVuelo getVuelo() {
        return vuelo;
    }

    public void setVuelo(ModelVuelo vuelo) {
        this.vuelo = vuelo;
    }

    public Date getFechaIda() {
        return fechaIda;
    }

    public void setFechaIda(Date fechaIda) {
        this.fechaIda = fechaIda;
    }

    public Date getFechaVuelta() {
        return fechaVuelta;
    }

    public void setFechaVuelta(Date fechaVuelta) {
        this.fechaVuelta = fechaVuelta;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getTrayecto() {
        return trayecto;
    }

    public void setTrayecto(String trayecto) {
        this.trayecto = trayecto;
    }

    public String getPasajero() {
        return pasajero;
    }

    public void setPasajero(String pasajero) {
        this.pasajero = pasajero;
    }

    public String getEquipaje() {
        return equipaje;
    }

    public void setEquipaje(String equipaje) {
        this.equipaje = equipaje;
    }

    public String getAsiento() {
        return asiento;
    }

    public void setAsiento(String asiento) {
        this.asiento = asiento;
    }

    public double getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(double precioFinal) {
        this.precioFinal = precioFinal;
    }
}