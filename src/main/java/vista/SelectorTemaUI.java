package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SelectorTemaUI extends JPanel {
    private JComboBox<String> comboTemas;
    private String[] temas = {"Clásico", "Oscuro", "Colorido"};
    
    public SelectorTemaUI(ActionListener listener) {
        setLayout(new GridBagLayout()); // Mejor alineación
        setBackground(new Color(50, 50, 50)); // Fondo oscuro elegante
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Espaciado uniforme
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        
        JLabel lblTema = new JLabel("Selecciona un tema:");
        lblTema.setFont(new Font("Arial", Font.BOLD, 14));
        lblTema.setForeground(new Color(220, 220, 220));
        add(lblTema, gbc);
        
        gbc.gridy = 1;
        comboTemas = new JComboBox<>(temas);
        comboTemas.setActionCommand("cambiarTema");
        comboTemas.addActionListener(listener);
        comboTemas.setFont(new Font("Arial", Font.PLAIN, 13));
        comboTemas.setBackground(new Color(30, 30, 30));
        comboTemas.setForeground(new Color(200, 200, 200));
        comboTemas.setPreferredSize(new Dimension(150, 30));
        add(comboTemas, gbc);
    }
    
    public String getTemaSeleccionado() {
        return (String) comboTemas.getSelectedItem();
    }
}