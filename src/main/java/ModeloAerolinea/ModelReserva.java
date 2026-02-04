package ModeloAerolinea;

import java.util.Date;

public class ModelReserva {
    private String destino;
    private String origen;    
    private Date fechaVuelo;
    private String tipoTrayecto;
    private String claseVuelo;
    private String tipoEquipaje;
    
    public ModelReserva(){
        
    }

    public String getDestino() {
        return destino;
    }

    public String getOrigen() {
        return origen;
    }

    public Date getFechaVuelo() {
        return fechaVuelo;
    }

    public String getTipoTrayecto() {
        return tipoTrayecto;
    }

    public String getClaseVuelo() {
        return claseVuelo;
    }

    public String getTipoEquipaje() {
        return tipoEquipaje;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public void setFechaVuelo(Date fechaVuelo) {
        this.fechaVuelo = fechaVuelo;
    }

    public void setTipoTrayecto(String tipoTrayecto) {
        this.tipoTrayecto = tipoTrayecto;
    }

    public void setClaseVuelo(String claseVuelo) {
        this.claseVuelo = claseVuelo;
    }

    public void setTipoEquipaje(String tipoEquipaje) {
        this.tipoEquipaje = tipoEquipaje;
    }

    
    
}