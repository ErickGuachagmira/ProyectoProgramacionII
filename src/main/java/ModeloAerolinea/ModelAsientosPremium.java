package ModeloAerolinea;

public class ModelAsientosPremium {
    private String codigoPremium;
    private boolean ocupadoPremium;

    public ModelAsientosPremium(String codigoPremium, boolean ocupadoPremium) {
        this.codigoPremium = codigoPremium;
        this.ocupadoPremium = false;
    }

    public String getCodigoPremium() {
        return codigoPremium;
    }

    public boolean isOcupadoPremium() {
        return ocupadoPremium;
    }

    public void setCodigoPremium(String codigoPremium) {
        this.codigoPremium = codigoPremium;
    }

    public void setOcupadoPremium(boolean ocupadoPremium) {
        this.ocupadoPremium = ocupadoPremium;
    }
    
    
}
