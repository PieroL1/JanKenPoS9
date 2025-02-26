package vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SelectorTemaUI extends JPanel {
    private JComboBox<String> comboTemas;
    private String[] temas = {"Clásico", "Oscuro", "Colorido"};
    
    public SelectorTemaUI(ActionListener listener) {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JLabel lblTema = new JLabel("Tema:");
        comboTemas = new JComboBox<>(temas);
        comboTemas.setActionCommand("cambiarTema");
        comboTemas.addActionListener(listener);
        
        add(lblTema);
        add(comboTemas);
    }
    
    public String getTemaSeleccionado() {
        return (String) comboTemas.getSelectedItem();
    }
}