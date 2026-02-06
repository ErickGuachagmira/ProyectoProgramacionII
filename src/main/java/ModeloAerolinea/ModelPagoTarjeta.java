package ModeloAerolinea;

public class ModelPagoTarjeta {
    private String  numeroTarjeta;
    private String  fechaExpiracion;
    private String cvc;
    private String tipoTarjeta;
    private String nombreTitular;
    private String modalidad;
    private double monto;

    public ModelPagoTarjeta() {
    }
    
    public ModelPagoTarjeta(String numeroTarjeta, String fechaExpiracion, String cvc, String tipoTarjeta, String nombreTitular, String modalidad, double monto) {
        this.numeroTarjeta = numeroTarjeta;
        this.fechaExpiracion = fechaExpiracion;
        this.cvc = cvc;
        this.tipoTarjeta = tipoTarjeta;
        this.nombreTitular = nombreTitular;
        this.modalidad = modalidad;
        this.monto = monto;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public String getCvc() {
        return cvc;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public String getNombreTitular() {
        return nombreTitular;
    }

    public String getModalidad() {
        return modalidad;
    }

    public double getMonto() {
        return monto;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public void setNombreTitular(String nombreTitular) {
        this.nombreTitular = nombreTitular;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }
    
}
