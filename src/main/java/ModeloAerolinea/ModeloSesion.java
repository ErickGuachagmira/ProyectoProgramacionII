package ModeloAerolinea;

import VistaAerolinea.VistaFacturacion;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;

public class ModeloSesion {

    public static ModelUsuario usuarioActivo;
    public static List<ModelBoleto> boletos = new ArrayList<>();
    public static ModelPagoTarjeta pagoActivo;

    public static VistaFacturacion vistaFacturacion = null;
    public static JFrame vistaFactura = null;
    public static JFrame vistaDestinos = null;

    public static void agregarBoleto(ModelBoleto b) {
        if (b != null) {
            boletos.add(b);
        }
    }

    public static void eliminarBoleto(ModelBoleto b) {
        if (b != null) {
            boletos.remove(b);
        }
    }

    public static List<ModelBoleto> obtenerBoletos() {
        return Collections.unmodifiableList(boletos);
    }

    public static double calcularTotalBase() {
        double suma = 0.0;
        for (ModelBoleto b : boletos) {
            if (b != null) {
                try {
                    suma += b.getPrecioFinal();
                } catch (Exception ignored) {
                }
            }
        }
        return suma;
    }

    public static double calcularIva(double porcentaje) {
        return calcularTotalBase() * porcentaje;
    }

    public static double calcularTotalFinal(double porcentajeIva) {
        double base = calcularTotalBase();
        return base + (base * porcentajeIva);
    }

    public static void limpiarBoletos() {
        boletos.clear();
    }

    public static void resetSesion() {
        usuarioActivo = null;
        pagoActivo = null;
        boletos.clear();
    }

    public static void limpiarVistas() {
        vistaFacturacion = null;
        vistaFactura = null;
        vistaDestinos = null;
    }

    private static String safe(Object o) {
        return (o == null) ? "" : o.toString();
    }

    private static String abbreviate(String s, int max) {
        if (s == null) return "";
        s = s.replaceAll("\\s+", " ");
        if (s.length() <= max) return s;
        return s.substring(0, max - 1) + ".";
    }

    private static double safeDouble(Double d) {
        return (d == null) ? 0.0 : d;
    }
}