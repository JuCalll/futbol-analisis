package src.gui;

import src.logic.AnalizadorDatos;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private JTextField txtEquipo;
    private JLabel lblVictorias, lblDerrotas, lblEmpates, lblGoles;
    private AnalizadorDatos analizador;

    public VentanaPrincipal(AnalizadorDatos analizador) {
        this.analizador = analizador;
        configurarVentana();
        iniciarComponentes();
    }

    private void configurarVentana() {
        setTitle("Analizador de Futbol FIFA");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));
    }

    private void iniciarComponentes() {
        JPanel panelInput = new JPanel();
        panelInput.add(new JLabel("Equipo:"));
        txtEquipo = new JTextField(15);
        JButton btnBuscar = new JButton("Buscar");
        
        btnBuscar.addActionListener(e -> buscar());
        
        panelInput.add(txtEquipo);
        panelInput.add(btnBuscar);
        add(panelInput);

        lblVictorias = crearEtiqueta("Victorias: -");
        lblDerrotas = crearEtiqueta("Derrotas: -");
        lblEmpates = crearEtiqueta("Empates: -");
        lblGoles = crearEtiqueta("Goles a Favor: -");

        add(lblVictorias);
        add(lblDerrotas);
        add(lblEmpates);
        add(lblGoles);
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        return lbl;
    }

    private void buscar() {
        String equipo = txtEquipo.getText();
        if(!equipo.isEmpty()) {
            int[] res = analizador.calcularEstadisticas(equipo);
            lblVictorias.setText("Victorias: " + res[0]);
            lblDerrotas.setText("Derrotas: " + res[1]);
            lblEmpates.setText("Empates: " + res[2]);
            lblGoles.setText("Goles a Favor: " + res[3]);
            
            if(res[0] == 0 && res[1] == 0 && res[2] == 0) {
                JOptionPane.showMessageDialog(this, "Sin datos para: " + equipo);
            }
        }
    }
}