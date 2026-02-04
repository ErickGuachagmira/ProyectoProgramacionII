package VistaAerolinea;
    
import com.mycompany.proyectoprogramacion.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class GraficosAerolinea extends JPanel{

    private Color color2 = new Color(0x63A6F8);
    private Color color1 = Color.WHITE;
    
    public GraficosAerolinea() {
        // Esto es vital: permite que los componentes internos se acomoden
        setLayout(new BorderLayout()); 
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D G2D = (Graphics2D) g;

        int w = getWidth();
        int h = getHeight();

        GradientPaint gp = new GradientPaint( 0, 0, color2, 0, (int)(h * 0.3), color1);

        G2D.setPaint(gp);
        G2D.fillRect(0, 0, w, h);

    }



}

