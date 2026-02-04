package ModeloAerolinea;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class UsuarioDAO {
    private File archivo;

    public UsuarioDAO() {
        // El archivo se creará en la carpeta raíz del proyecto
        archivo = new File("usuarios.txt");
    }
    public boolean registrarUsuario(ModelUsuario usuario){
        try(FileWriter fw = new FileWriter(archivo, true);
            PrintWriter pw = new PrintWriter(fw)) {
            pw.println(usuario.getNombreUsuario() + "," + 
                             usuario.getApellidoUsuario() + "," + 
                             usuario.getCedulaUsuario() + "," + 
                             usuario.getDireccionUsuario() + "," + 
                             usuario.getTelefonoUsuario());
            return true;
        }catch (IOException e){
            System.err.println("Error al guardar: " + e.getMessage());
            return false;
        }
    }
    
    
    //skdjkhf
}
