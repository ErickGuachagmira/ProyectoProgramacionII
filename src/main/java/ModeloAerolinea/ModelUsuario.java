package ModeloAerolinea;

public class ModelUsuario {
    private String nombreUsuario;
    private String apellidoUsuario;
    private String cedulaUsuario;
    private String direccionUsuario;
    private String telefonoUsuario;

    public ModelUsuario(String nombreUsuario, String apellidoUsuario, String cedulaUsuario, String direccionUsuario, String telefonoUsuario) {
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.cedulaUsuario = cedulaUsuario;
        this.direccionUsuario = direccionUsuario;
        this.telefonoUsuario = telefonoUsuario;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getApellidoUsuario() {
        return apellidoUsuario;
    }

    public String getCedulaUsuario() {
        return cedulaUsuario;
    }

    public String getDireccionUsuario() {
        return direccionUsuario;
    }

    public String getTelefonoUsuario() {
        return telefonoUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setApellidoUsuario(String apellidoUsuario) {
        this.apellidoUsuario = apellidoUsuario;
    }

    public void setCedulaUsuario(String cedulaUsuario) {
        this.cedulaUsuario = cedulaUsuario;
    }

    public void setDireccionUsuario(String direccionUsuario) {
        this.direccionUsuario = direccionUsuario;
    }

    public void setTelefonoUsuario(String telefonoUsuario) {
        this.telefonoUsuario = telefonoUsuario;
    }
    
    
    
    
    
}
