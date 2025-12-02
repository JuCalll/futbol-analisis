// Definimos que esta clase pertenece al paquete 'src.gui', organizando así las clases de la interfaz gráfica.
package src.gui;

// Importamos la clase que contiene la lógica de negocio para analizar los datos.
import src.logic.AnalizadorDatos;
// Importamos las clases de Swing, que son fundamentales para crear la interfaz gráfica de usuario (GUI).
import javax.swing.*;
// Importamos clases de AWT (Abstract Window Toolkit) para el manejo de layouts y fuentes.
import java.awt.*;

/**
 * La clase VentanaPrincipal es la vista principal de nuestra aplicación.
 * Hereda de JFrame, lo que la convierte en una ventana de escritorio.
 * Su propósito es mostrar una interfaz para que el usuario pueda buscar
 * estadísticas de un equipo de fútbol.
 */
public class VentanaPrincipal extends JFrame {
    // --- Atributos de la Clase (Componentes de la GUI y Lógica) ---

    // Campo de texto para que el usuario ingrese el nombre del equipo.
    private JTextField txtEquipo;
    // Etiquetas para mostrar los resultados: victorias, derrotas, empates y goles.
    private JLabel lblVictorias, lblDerrotas, lblEmpates, lblGoles;
    // Una referencia al objeto que se encargará de toda la lógica de análisis de datos.
    // Esta es una pieza clave de la POO: la colaboración entre objetos.
    private AnalizadorDatos analizador;

    /**
     * Constructor de la VentanaPrincipal.
     * Recibe una instancia de AnalizadorDatos, aplicando el principio de Inyección de Dependencias.
     * Esto desacopla la vista (esta ventana) de la lógica (el analizador).
     * @param analizador El objeto que procesará los datos del equipo.
     */
    public VentanaPrincipal(AnalizadorDatos analizador) {
        // Guardamos la referencia al analizador de datos para poder usarlo más tarde.
        this.analizador = analizador;
        // Llamamos a métodos privados para organizar la configuración de la ventana.
        configurarVentana();
        iniciarComponentes();
    }

    /**
     * Configura las propiedades principales de la ventana (JFrame).
     * Como el título, tamaño, comportamiento al cerrar y su disposición en pantalla.
     */
    private void configurarVentana() {
        setTitle("Analizador de Futbol FIFA");
        setSize(400, 300);
        // Define que la aplicación se cerrará completamente cuando se cierre esta ventana.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Centra la ventana en la pantalla al iniciar.
        setLocationRelativeTo(null);
        // Establecemos un layout de tipo GridLayout para organizar los componentes en una cuadrícula.
        // 6 filas, 1 columna, con 10 píxeles de espacio vertical y horizontal.
        setLayout(new GridLayout(6, 1, 10, 10));
    }

    /**
     * Crea e inicializa todos los componentes gráficos (widgets)
     * y los añade a la ventana.
     */
    private void iniciarComponentes() {
        // Creamos un panel para agrupar la etiqueta, el campo de texto y el botón de búsqueda.
        JPanel panelInput = new JPanel();
        panelInput.add(new JLabel("Equipo:"));
        txtEquipo = new JTextField(15);
        JButton btnBuscar = new JButton("Buscar");
        
        // Aquí ocurre la magia del manejo de eventos.
        // Usamos una expresión lambda para asignar una acción al botón "Buscar".
        // Cuando el usuario haga clic, se ejecutará el método buscar().
        btnBuscar.addActionListener(e -> buscar());
        
        // Añadimos los componentes al panel.
        panelInput.add(txtEquipo);
        panelInput.add(btnBuscar);
        // Y finalmente, añadimos el panel a la ventana (que es un JFrame).
        add(panelInput);

        // Creamos las etiquetas donde se mostrarán las estadísticas.
        // Usamos un método auxiliar 'crearEtiqueta' para no repetir código (principio DRY).
        lblVictorias = crearEtiqueta("Victorias: -");
        lblDerrotas = crearEtiqueta("Derrotas: -");
        lblEmpates = crearEtiqueta("Empates: -");
        lblGoles = crearEtiqueta("Goles a Favor: -");

        // Añadimos las etiquetas a la ventana.
        add(lblVictorias);
        add(lblDerrotas);
        add(lblEmpates);
        add(lblGoles);
    }

    /**
     * Método de fábrica (factory method) para crear y configurar una JLabel.
     * Esto nos ayuda a mantener un estilo consistente y a reutilizar código.
     * @param texto El texto inicial que mostrará la etiqueta.
     * @return Una instancia de JLabel configurada.
     */
    private JLabel crearEtiqueta(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        return lbl;
    }

    /**
     * Este método se ejecuta cuando el usuario presiona el botón "Buscar".
     * Orquesta la obtención de datos del usuario, la llamada a la lógica de negocio
     * y la actualización de la interfaz con los resultados.
     */
    private void buscar() {
        // Obtenemos el texto que el usuario escribió en el campo de texto.
        String equipo = txtEquipo.getText();
        // Validamos que el usuario haya escrito algo.
        if(!equipo.isEmpty()) {
            // Le pedimos al objeto 'analizador' que calcule las estadísticas para el equipo.
            // Este es el punto de comunicación entre la Vista y el Modelo (lógica).
            int[] res = analizador.calcularEstadisticas(equipo);
            // Actualizamos el texto de las etiquetas con los resultados obtenidos.
            lblVictorias.setText("Victorias: " + res[0]);
            lblDerrotas.setText("Derrotas: " + res[1]);
            lblEmpates.setText("Empates: " + res[2]);
            lblGoles.setText("Goles a Favor: " + res[3]);
            
            // Si todas las estadísticas son cero, es probable que el equipo no se haya encontrado.
            if(res[0] == 0 && res[1] == 0 && res[2] == 0) {
                // Mostramos un cuadro de diálogo para informar al usuario.
                JOptionPane.showMessageDialog(this, "Sin datos para: " + equipo);
            }
        }
    }
}