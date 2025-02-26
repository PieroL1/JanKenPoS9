
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import vista.JuegoUI;

public class Main {
    public static void main(String[] args) {
        try {
            // Establecer Look & Feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            JuegoUI ui = new JuegoUI();
            ui.setVisible(true);
        });
    }
}