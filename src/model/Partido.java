// Definimos que esta clase pertenece al paquete 'src.model', donde se ubican nuestras clases de modelo de datos.
package src.model;

/**
 * La clase Partido es un objeto de modelo de datos, también conocido como POJO (Plain Old Java Object) o Entidad.
 * Su única responsabilidad es encapsular y representar la información de un único partido de fútbol.
 * No contiene lógica de negocio, solo datos y métodos para acceder a ellos (getters).
 * Esto es un pilar del principio de encapsulamiento en POO.
 */
public class Partido {
    // --- Atributos ---
    // Todos los atributos son privados para proteger los datos.
    // Solo se puede acceder a ellos a través de los métodos públicos (getters).
    private String equipoLocal;
    private String equipoVisitante;
    private int golesLocal;
    private int golesVisitante;
    private String torneo;

    /**
     * Constructor para crear una nueva instancia de Partido.
     * Permite inicializar un objeto Partido con todos sus datos de una sola vez.
     * @param equipoLocal Nombre del equipo local.
     * @param equipoVisitante Nombre del equipo visitante.
     * @param golesLocal Goles marcados por el equipo local.
     * @param golesVisitante Goles marcados por el equipo visitante.
     * @param torneo Nombre del torneo en el que se jugó el partido.
     */
    public Partido(String equipoLocal, String equipoVisitante, int golesLocal, int golesVisitante, String torneo) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.torneo = torneo;
    }
    
    // --- Métodos Getters ---
    // Métodos públicos que permiten a otras clases leer los valores de los atributos privados.
    // Al no tener métodos 'setters', hacemos que el objeto Partido sea inmutable una vez creado.
    // Esto es una excelente práctica, ya que garantiza que los datos de un partido no se modifiquen accidentalmente.
    public String getEquipoLocal() { return equipoLocal; }
    public String getEquipoVisitante() { return equipoVisitante; }
    public int getGolesLocal() { return golesLocal; }
    public int getGolesVisitante() { return golesVisitante; }
    public String getTorneo() { return torneo; }
}