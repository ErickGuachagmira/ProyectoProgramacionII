package ModeloAerolinea;

import java.util.Date;

public class ModelFacturacion {
    private ModelUsuario cliente;
    private ModelReserva reserva;
    private double subtotal;
    private double impuestoIVA;
    private double totalFinal;
    private String metodoPago;
    private Date fechaEmision; 

    public ModelFacturacion(){
        
    }
    
    public ModelFacturacion(ModelUsuario cliente, ModelReserva reserva, double subtotal, double impuestoIVA, double totalFinal, String metodoPago, Date fechaEmision) {
        this.cliente = cliente;
        this.reserva = reserva;
        this.subtotal = subtotal;
        this.impuestoIVA = impuestoIVA;
        this.totalFinal = totalFinal;
        this.metodoPago = metodoPago;
        this.fechaEmision = fechaEmision;
    }

    public ModelUsuario getCliente() {
        return cliente;
    }

    public ModelReserva getReserva() {
        return reserva;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getImpuestoIVA() {
        return impuestoIVA;
    }

    public double getTotalFinal() {
        return totalFinal;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setCliente(ModelUsuario cliente) {
        this.cliente = cliente;
    }

    public void setReserva(ModelReserva reserva) {
        this.reserva = reserva;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public void setImpuestoIVA(double impuestoIVA) {
        this.impuestoIVA = impuestoIVA;
    }

    public void setTotalFinal(double totalFinal) {
        this.totalFinal = totalFinal;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
    
    
}
