package src.main;

import src.logic.AnalizadorDatos;
import src.gui.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {
        AnalizadorDatos logica = new AnalizadorDatos();
        System.out.println("Iniciando aplicacion...");
        
        // Ruta relativa desde la raíz del proyecto
        logica.cargarDatos("data/results.csv");

        javax.swing.SwingUtilities.invokeLater(() -> {
            new VentanaPrincipal(logica).setVisible(true);
        });
    }
}