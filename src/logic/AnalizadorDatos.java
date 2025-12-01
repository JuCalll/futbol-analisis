package src.logic;

import src.model.Partido;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnalizadorDatos {
    private List<Partido> partidos;

    public AnalizadorDatos() {
        this.partidos = new ArrayList<>();
    }

    public void cargarDatos(String rutaArchivo) {
        String linea = "";
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            br.readLine(); // Leer cabecera
            
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if(datos.length >= 5) {
                    try {
                        String local = datos[1];
                        String visitante = datos[2];
                        int golesL = Integer.parseInt(datos[3]);
                        int golesV = Integer.parseInt(datos[4]);
                        String torneo = datos[5];
    
                        partidos.add(new Partido(local, visitante, golesL, golesV, torneo));
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }
            }
            System.out.println("Carga exitosa: " + partidos.size() + " partidos.");
        } catch (IOException e) {
            System.err.println("Error critico: " + e.getMessage());
        }
    }

    public int[] calcularEstadisticas(String equipo) {
        int[] stats = new int[4]; // [V, D, E, Goles]
        String busqueda = equipo.trim().toLowerCase();

        for (Partido p : partidos) {
            boolean esLocal = p.getEquipoLocal().toLowerCase().equals(busqueda);
            boolean esVisitante = p.getEquipoVisitante().toLowerCase().equals(busqueda);

            if (esLocal || esVisitante) {
                stats[3] += esLocal ? p.getGolesLocal() : p.getGolesVisitante();

                if (p.getGolesLocal() == p.getGolesVisitante()) {
                    stats[2]++;
                } else if ((esLocal && p.getGolesLocal() > p.getGolesVisitante()) || 
                           (esVisitante && p.getGolesVisitante() > p.getGolesLocal())) {
                    stats[0]++;
                } else {
                    stats[1]++;
                }
            }
        }
        return stats;
    }
}