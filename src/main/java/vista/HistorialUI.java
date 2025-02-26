package vista;

import modelo.Partida;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.text.SimpleDateFormat;

public class HistorialUI extends JDialog {
    private JTextArea txtHistorial;
    
    public HistorialUI(JFrame parent, List<Partida> historial) {
        super(parent, "Historial de Partidas", true);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        
        txtHistorial = new JTextArea();
        txtHistorial.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtHistorial);
        
        actualizarHistorial(historial);
        
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(e -> dispose());
        
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnCerrar);
        
        getContentPane().add(panelBotones, BorderLayout.SOUTH);
    }
    
    private void actualizarHistorial(List<Partida> historial) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        
        sb.append("HISTORIAL DE PARTIDAS\n");
        sb.append("====================\n\n");
        
        if (historial.isEmpty()) {
            sb.append("No hay partidas jugadas.\n");
        } else {
            for (int i = 0; i < historial.size(); i++) {
                Partida p = historial.get(i);
                sb.append("Partida #").append(i + 1).append(" - ");
                sb.append(sdf.format(p.getFecha())).append("\n");
                sb.append("Jugador 1: ").append(p.getJugador1()).append("\n");
                sb.append("Jugador 2: ").append(p.getJugador2()).append("\n");
                sb.append("Resultado: ").append(p.getResultado()).append("\n");
                sb.append("--------------------\n");
            }
        }
        
        txtHistorial.setText(sb.toString());
        txtHistorial.setCaretPosition(0);
    }
}