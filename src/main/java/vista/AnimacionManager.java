package vista;

import javax.swing.*;
import java.awt.*;

public class AnimacionManager {
    private JPanel panelAnimacion;
    private Timer timer;
    private int frame;
    private int maxFrames;
    private String tipoAnimacion;
    
    public AnimacionManager(JPanel panel) {
        this.panelAnimacion = panel;
        this.frame = 0;
        this.maxFrames = 5;
        this.tipoAnimacion = "";
    }
    
    public void iniciarAnimacion(String tipo) {
        tipoAnimacion = tipo;
        frame = 0;
        
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
        
        timer = new Timer(100, e -> {
            frame++;
            panelAnimacion.repaint();
            
            if (frame >= maxFrames) {
                ((Timer) e.getSource()).stop();
            }
        });
        
        timer.start();
    }
    
    public void dibujarAnimacion(Graphics g) {
        if (timer == null || !timer.isRunning()) return;
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int ancho = panelAnimacion.getWidth();
        int alto = panelAnimacion.getHeight();
        
        switch (tipoAnimacion) {
            case "victoria":
                dibujarAnimacionVictoria(g2d, ancho, alto);
                break;
            case "derrota":
                dibujarAnimacionDerrota(g2d, ancho, alto);
                break;
            case "empate":
                dibujarAnimacionEmpate(g2d, ancho, alto);
                break;
            case "seleccion":
                dibujarAnimacionSeleccion(g2d, ancho, alto);
                break;
        }
    }
    
    private void dibujarAnimacionVictoria(Graphics2D g, int ancho, int alto) {
        float alpha = (float) frame / maxFrames;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.setColor(Color.GREEN);
        g.setStroke(new BasicStroke(3));
        
        int size = Math.min(ancho, alto) / 4;
        int x = ancho / 2 - size / 2;
        int y = alto / 2 - size / 2;
        
        // Dibujar estrella brillante
        for (int i = 0; i < 5; i++) {
            double angle = i * 2 * Math.PI / 5 - Math.PI / 2;
            int x2 = (int) (x + size/2 + size/2 * Math.cos(angle));
            int y2 = (int) (y + size/2 + size/2 * Math.sin(angle));
            g.drawLine(x + size/2, y + size/2, x2, y2);
        }
    }
    
    private void dibujarAnimacionDerrota(Graphics2D g, int ancho, int alto) {
        float alpha = (float) frame / maxFrames;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(3));
        
        int size = Math.min(ancho, alto) / 4;
        int x = ancho / 2 - size / 2;
        int y = alto / 2 - size / 2;
        
        // Dibujar X
        g.drawLine(x, y, x + size, y + size);
        g.drawLine(x + size, y, x, y + size);
    }
    
    private void dibujarAnimacionEmpate(Graphics2D g, int ancho, int alto) {
        float alpha = (float) frame / maxFrames;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.setColor(Color.BLUE);
        g.setStroke(new BasicStroke(3));
        
        int size = Math.min(ancho, alto) / 4;
        int x = ancho / 2 - size / 2;
        int y = alto / 2 - size / 2;
        
        // Dibujar =
        g.drawLine(x, y + size/3, x + size, y + size/3);
        g.drawLine(x, y + 2*size/3, x + size, y + 2*size/3);
    }
    
    private void dibujarAnimacionSeleccion(Graphics2D g, int ancho, int alto) {
        float alpha = (float) frame / maxFrames;
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g.setColor(Color.ORANGE);
        
        int size = Math.min(ancho, alto) / 4;
        int x = ancho / 2 - size / 2;
        int y = alto / 2 - size / 2;
        
        // Dibujar círculo expandiéndose
        int currentSize = (int)(size * alpha);
        g.drawOval(x + size/2 - currentSize/2, y + size/2 - currentSize/2, currentSize, currentSize);
    }
}
