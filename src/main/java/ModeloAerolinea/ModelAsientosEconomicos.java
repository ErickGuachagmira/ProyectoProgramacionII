package ModeloAerolinea;

public class ModelAsientosEconomicos {
    private String codigoEconomico;
    private boolean ocupadoEconomico;

    public ModelAsientosEconomicos(String codigoEconomico, boolean ocupadoEconomico) {
        this.codigoEconomico = codigoEconomico;
        this.ocupadoEconomico = false;
    }

    public String getCodigoEconomico() {
        return codigoEconomico;
    }

    public boolean isOcupadoEconomico() {
        return ocupadoEconomico;
    }

    public void setCodigoEconomico(String codigoEconomico) {
        this.codigoEconomico = codigoEconomico;
    }

    public void setOcupadoEconomico(boolean ocupadoEconomico) {
        this.ocupadoEconomico = ocupadoEconomico;
    }

    
    
    
    
}
