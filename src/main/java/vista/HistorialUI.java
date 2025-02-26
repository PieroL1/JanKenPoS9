package vista;

import modelo.Partida;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.text.SimpleDateFormat;

public class HistorialUI extends JDialog {
    private JTextPane txtHistorial;
    
    public HistorialUI(JFrame parent, List<Partida> historial) {
        super(parent, "Historial de Partidas", true);
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        txtHistorial = new JTextPane();
        txtHistorial.setContentType("text/html");
        txtHistorial.setEditable(false);
        txtHistorial.setBackground(new Color(240, 240, 240));
        JScrollPane scrollPane = new JScrollPane(txtHistorial);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Historial de Partidas"));
        
        actualizarHistorial(historial);
        
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setFont(new Font("Girassol", Font.BOLD, 14));
        btnCerrar.setBackground(new Color(220, 53, 69));
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)));
        btnCerrar.addActionListener(e -> dispose());
        
        JPanel panelBotones = new JPanel();
        panelBotones.setBackground(Color.LIGHT_GRAY);
        panelBotones.add(btnCerrar);
        
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void actualizarHistorial(List<Partida> historial) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        
        sb.append("<html><body style='font-family:Girassol, sans-serif; padding:10px;'>");
        sb.append("<h2 style='text-align:center; color:#333;'>✨ Historial de Partidas ✨</h2>");
        
        if (historial.isEmpty()) {
            sb.append("<p style='text-align:center; font-size:18px; color:red;'>⚠ No hay partidas jugadas.</p>");
        } else {
            for (int i = 0; i < historial.size(); i++) {
                Partida p = historial.get(i);
                sb.append("<div style='border:2px solid #444; border-radius:10px; padding:10px; margin-bottom:10px; background:#f9f9f9; box-shadow: 3px 3px 5px rgba(0,0,0,0.1);'>");
                sb.append("<h2 style='color:#007BFF; font-size:22px;'>🔹 Partida #").append(i + 1).append("</h2>");
                sb.append("<p style='font-size:18px;'><b>📅 Fecha:</b> ").append(sdf.format(p.getFecha())).append("</p>");
                sb.append("<p style='font-size:16px;'><b>🏆 Jugador 1:</b> ").append(p.getJugador1()).append("</p>");
                sb.append("<p style='font-size:16px;'><b>🏆 Jugador 2:</b> ").append(p.getJugador2()).append("</p>");
                sb.append("<p style='font-size:16px; color:red;'><b>🔥 Resultado:</b> ").append(p.getResultado()).append("</p>");
                sb.append("</div>");
            }
        }
        
        sb.append("</body></html>");
        txtHistorial.setText(sb.toString());
        txtHistorial.setCaretPosition(0);
    }
}