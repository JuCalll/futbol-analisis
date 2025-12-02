// Definimos que esta clase pertenece al paquete 'src.main', que es el punto de entrada de la aplicación.
package src.main;

// Importamos las clases que vamos a necesitar: el 'Modelo' y la 'Vista'.
import src.logic.AnalizadorDatos;
import src.gui.VentanaPrincipal;

/**
 * La clase Main es el punto de inicio (entry point) de nuestra aplicación.
 * Su única responsabilidad es crear e inicializar los objetos principales
 * y poner en marcha la aplicación.
 */
public class Main {
    /**
     * El método main es el primer método que se ejecuta cuando iniciamos el programa.
     * @param args Argumentos de línea de comandos (no los usamos en este proyecto).
     */
    public static void main(String[] args) {
        // 1. Creamos una instancia de la lógica de negocio (el Modelo).
        // Este objeto 'logica' se encargará de todos los cálculos.
        AnalizadorDatos logica = new AnalizadorDatos();
        
        // 2. Le indicamos al objeto de lógica que cargue los datos desde el archivo.
        // La ruta es relativa a la raíz del proyecto, lo que facilita su ejecución en diferentes entornos.
        logica.cargarDatos("data/results.csv");

        // 3. Preparamos y mostramos la interfaz gráfica (la Vista).
        // Usamos SwingUtilities.invokeLater para asegurar que la creación de la ventana
        // se ejecute en el hilo correcto de Swing, conocido como el Event Dispatch Thread (EDT).
        // Esta es la forma más segura y recomendada de iniciar una GUI en Swing para evitar problemas de concurrencia.
        javax.swing.SwingUtilities.invokeLater(() -> {
            // Dentro del hilo de Swing, creamos nuestra VentanaPrincipal.
            // Le pasamos el objeto 'logica' (Inyección de Dependencias) para que la vista pueda comunicarse con el modelo.
            new VentanaPrincipal(logica).setVisible(true);
        });
    }
}