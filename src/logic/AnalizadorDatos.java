package src.logic;

import src.model.Partido;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalizadorDatos {
    private List<Partido> partidos;
    // Regla de Sonar: Usar Logger en lugar de System.out
    private static final Logger LOGGER = Logger.getLogger(AnalizadorDatos.class.getName());

    public AnalizadorDatos() {
        this.partidos = new ArrayList<>();
    }

    public void cargarDatos(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            br.readLine(); // Leer cabecera
            String linea;
            
            while ((linea = br.readLine()) != null) {
                procesarLinea(linea);
            }
            LOGGER.log(Level.INFO, "Carga exitosa: {0} partidos.", partidos.size());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error critico al leer el archivo", e);
        }
    }

    // Refactorización: Extraer método para reducir complejidad cognitiva
    private void procesarLinea(String linea) {
        String[] datos = linea.split(",");
        if (datos.length < 5) {
            return;
        }
        
        try {
            String local = datos[1];
            String visitante = datos[2];
            int golesL = Integer.parseInt(datos[3]);
            int golesV = Integer.parseInt(datos[4]);
            String torneo = datos[5];

            partidos.add(new Partido(local, visitante, golesL, golesV, torneo));
        } catch (NumberFormatException e) {
            // Se ignora la línea corrupta pero no se detiene el proceso
            LOGGER.log(Level.WARNING, "Linea ignorada por formato incorrecto: {0}", linea);
        }
    }

    public int[] calcularEstadisticas(String equipo) {
        int[] stats = new int[4]; // [V, D, E, Goles]
        
        for (Partido p : partidos) {
            procesarPartidoParaEstadisticas(p, equipo, stats);
        }
        return stats;
    }

    // Refactorización: Lógica de cálculo aislada para mayor claridad
    private void procesarPartidoParaEstadisticas(Partido p, String equipo, int[] stats) {
        // Usamos equalsIgnoreCase para mayor robustez
        boolean esLocal = p.getEquipoLocal().equalsIgnoreCase(equipo);
        boolean esVisitante = p.getEquipoVisitante().equalsIgnoreCase(equipo);

        if (!esLocal && !esVisitante) {
            return; // Si no es el equipo, pasamos al siguiente
        }

        // Determinar goles a favor y en contra
        int golesFavor = esLocal ? p.getGolesLocal() : p.getGolesVisitante();
        int golesContra = esLocal ? p.getGolesVisitante() : p.getGolesLocal();

        stats[3] += golesFavor; // Sumar goles

        // Determinar resultado
        if (golesFavor > golesContra) {
            stats[0]++; // Victoria
        } else if (golesFavor < golesContra) {
            stats[1]++; // Derrota
        } else {
            stats[2]++; // Empate
        }
    }
}