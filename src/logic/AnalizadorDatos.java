// Definimos que esta clase pertenece al paquete 'src.logic', donde reside la lógica de negocio.
package src.logic;

// Importamos la clase 'Partido' que representa nuestro modelo de datos, es decir, un único partido.
import src.model.Partido;
// Importamos las clases necesarias para leer archivos de texto.
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
// Importamos las estructuras de datos que usaremos para almacenar los partidos en memoria.
import java.util.ArrayList;
import java.util.List;
// Importamos el sistema de logging de Java para registrar eventos y errores.
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * La clase AnalizadorDatos es el cerebro de la aplicación.
 * Su responsabilidad es cargar los datos de los partidos desde un archivo,
 * almacenarlos y procesarlos para calcular estadísticas.
 * No tiene conocimiento alguno sobre la interfaz gráfica; es un componente reutilizable.
 */
public class AnalizadorDatos {
    // --- Atributos de la Clase ---

    // Una lista para guardar en memoria todos los objetos de tipo 'Partido' que leamos del archivo.
    // Usamos la interfaz 'List' para ser flexibles y la implementación 'ArrayList' por su eficiencia.
    private List<Partido> partidos;
    // Un objeto 'Logger' para registrar información o errores. Es una mejor práctica que usar 'System.out.println'.
    // Es 'static' y 'final' porque es una constante compartida por todas las instancias de esta clase.
    private static final Logger LOGGER = Logger.getLogger(AnalizadorDatos.class.getName());

    /**
     * Constructor de AnalizadorDatos.
     * Se encarga de inicializar el estado del objeto. En este caso,
     * crea una lista vacía que estará lista para ser llenada con los datos de los partidos.
     */
    public AnalizadorDatos() {
        this.partidos = new ArrayList<>();
    }

    /**
     * Carga los datos de los partidos desde un archivo CSV.
     * @param rutaArchivo La ruta completa del archivo que se va a leer.
     */
    public void cargarDatos(String rutaArchivo) {
        // Usamos un bloque 'try-with-resources'. Esto asegura que el 'BufferedReader' se cierre automáticamente
        // al final, incluso si ocurre un error, evitando fugas de recursos.
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            // Leemos y descartamos la primera línea del archivo, que asumimos es la cabecera (ej: "equipo_local,goles,...").
            br.readLine(); 
            String linea;
            
            // Leemos el archivo línea por línea hasta que no queden más.
            while ((linea = br.readLine()) != null) {
                // Cada línea se envía a un método auxiliar para ser procesada.
                // Esto mantiene este método limpio y enfocado solo en la lectura del archivo.
                procesarLinea(linea);
            }
            // Registramos un mensaje informativo para saber que la carga fue exitosa y cuántos partidos se cargaron.
            LOGGER.log(Level.INFO, "Carga exitosa: {0} partidos.", partidos.size());
        } catch (IOException e) {
            // Si ocurre un error de entrada/salida (ej: el archivo no existe), lo registramos como un error grave.
            LOGGER.log(Level.SEVERE, "Error critico al leer el archivo", e);
        }
    }

    /**
     * Procesa una sola línea del archivo CSV, la convierte en un objeto Partido y la añade a la lista.
     * Este es un método de ayuda (helper method) para aplicar el Principio de Responsabilidad Única.
     * @param linea La cadena de texto que representa una fila del archivo.
     */
    private void procesarLinea(String linea) {
        // Dividimos la línea usando la coma como separador para obtener los datos individuales.
        String[] datos = linea.split(",");
        // Verificamos si la línea tiene la cantidad mínima de columnas esperadas. Si no, la ignoramos.
        if (datos.length < 5) {
            return;
        }
        
        try {
            // Extraemos cada dato de la matriz 'datos' y lo asignamos a variables con nombres claros.
            String local = datos[1];
            String visitante = datos[2];
            // Convertimos los goles de texto a números enteros.
            int golesL = Integer.parseInt(datos[3]);
            int golesV = Integer.parseInt(datos[4]);
            String torneo = datos[5];

            // Creamos una nueva instancia de la clase 'Partido' con los datos extraídos.
            partidos.add(new Partido(local, visitante, golesL, golesV, torneo));
        } catch (NumberFormatException e) {
            // Si los goles no son un número válido, se producirá esta excepción.
            // En lugar de detener toda la carga, registramos una advertencia y continuamos con la siguiente línea.
            LOGGER.log(Level.WARNING, "Linea ignorada por formato incorrecto: {0}", linea);
        }
    }

    /**
     * Calcula las estadísticas para un equipo específico.
     * @param equipo El nombre del equipo a buscar.
     * @return Un array de enteros con 4 posiciones: [Victorias, Derrotas, Empates, Goles a Favor].
     */
    public int[] calcularEstadisticas(String equipo) {
        // Creamos un array para almacenar los resultados. La posición de cada estadística está predefinida.
        int[] stats = new int[4]; // [V, D, E, Goles]
        
        // Recorremos toda la lista de partidos que tenemos en memoria.
        for (Partido p : partidos) {
            // Para cada partido, llamamos a un método auxiliar que se encarga de actualizar las estadísticas.
            procesarPartidoParaEstadisticas(p, equipo, stats);
        }
        return stats;
    }

    /**
     * Analiza un único partido y actualiza el array de estadísticas si el equipo buscado participó.
     * Aislar esta lógica en un método hace que el bucle principal sea más legible.
     * @param p El objeto Partido a analizar.
     * @param equipo El nombre del equipo que nos interesa.
     * @param stats El array de estadísticas que se va a actualizar (se pasa por referencia).
     */
    private void procesarPartidoParaEstadisticas(Partido p, String equipo, int[] stats) {
        // Comparamos los nombres de los equipos ignorando mayúsculas/minúsculas para ser más flexibles.
        boolean esLocal = p.getEquipoLocal().equalsIgnoreCase(equipo);
        boolean esVisitante = p.getEquipoVisitante().equalsIgnoreCase(equipo);

        // Si el equipo que buscamos no jugó en este partido, no hacemos nada y terminamos la ejecución de este método.
        if (!esLocal && !esVisitante) {
            return; // Si no es el equipo, pasamos al siguiente
        }

        // Usamos un operador ternario para determinar limpiamente los goles a favor y en contra.
        int golesFavor = esLocal ? p.getGolesLocal() : p.getGolesVisitante();
        int golesContra = esLocal ? p.getGolesVisitante() : p.getGolesLocal();

        // Acumulamos los goles a favor en la cuarta posición del array.
        stats[3] += golesFavor; // Sumar goles

        // Comparamos los goles para determinar si fue victoria, derrota o empate y actualizamos el contador correspondiente.
        if (golesFavor > golesContra) {
            stats[0]++; // Posición 0: Victorias
        } else if (golesFavor < golesContra) {
            stats[1]++; // Posición 1: Derrotas
        } else {
            stats[2]++; // Posición 2: Empates
        }
    }
}