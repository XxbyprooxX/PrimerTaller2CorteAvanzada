package edu.progAvUD.primerTaller2Corte.modelo;

/**
 * Clase que representa a un Corredor con atributos como nombre, velocidad máxima,
 * distancia recorrida, tiempo de recorrido y puntos de inicio y meta de la carrera.
 * Esta clase puede ser utilizada como base para herencia.
 * 
 * @author Andres Felipe
 */
public class Corredor {

    private int id; // Identificador único del corredor
    private String nombre; // Nombre del corredor
    private String velocidadMaximaObtenida; // Velocidad máxima alcanzada por el corredor
    private static int puntoComienzoX; // Posición X donde comienza la carrera (valor común para todos los corredores)
    private static int puntoMetaX; // Posición X donde termina la carrera (valor común para todos los corredores)
    private int distanciaRecorida; // Distancia que ha recorrido el corredor (en unidades X)
    private long tiempoRecorrido; // Tiempo total que ha tardado en recorrer la distancia

    /**
     * Constructor de la clase Corredor.
     *
     * @param id Identificador del corredor.
     * @param nombre Nombre del corredor.
     * @param velocidadMaximaObtenida Velocidad máxima registrada del corredor.
     */
    public Corredor(int id, String nombre, String velocidadMaximaObtenida) {
        this.id = id;
        this.nombre = nombre;
        this.velocidadMaximaObtenida = velocidadMaximaObtenida;
        this.distanciaRecorida = 0;
        this.tiempoRecorrido = 0;
    }

    /**
     * Devuelve el nombre del corredor.
     *
     * @return Nombre del corredor.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del corredor.
     *
     * @param nombre Nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve la velocidad máxima obtenida por el corredor.
     *
     * @return Velocidad máxima como String.
     */
    public String getVelocidadMaximaObtenida() {
        return velocidadMaximaObtenida;
    }

    /**
     * Establece la velocidad máxima obtenida por el corredor.
     *
     * @param velocidadMaximaObtenida Velocidad máxima como String.
     */
    public void setVelocidadMaximaObtenida(String velocidadMaximaObtenida) {
        this.velocidadMaximaObtenida = velocidadMaximaObtenida;
    }

    /**
     * Devuelve la distancia recorrida por el corredor.
     *
     * @return Distancia recorrida en unidades X.
     */
    public int getDistanciaRecorida() {
        return distanciaRecorida;
    }

    /**
     * Establece la distancia recorrida por el corredor.
     *
     * @param distanciaRecorida Distancia en unidades X.
     */
    public void setDistanciaRecorida(int distanciaRecorida) {
        this.distanciaRecorida = distanciaRecorida;
    }

    /**
     * Devuelve el punto de comienzo común para todos los corredores.
     *
     * @return Punto X de inicio.
     */
    public static int getPuntoComienzoX() {
        return puntoComienzoX;
    }

    /**
     * Establece el punto de comienzo común para todos los corredores.
     *
     * @param puntoComienzoX Valor X inicial.
     */
    public static void setPuntoComienzoX(int puntoComienzoX) {
        Corredor.puntoComienzoX = puntoComienzoX;
    }

    /**
     * Devuelve el punto de meta común para todos los corredores.
     *
     * @return Punto X de meta.
     */
    public static int getPuntoMetaX() {
        return puntoMetaX;
    }

    /**
     * Establece el punto de meta común para todos los corredores.
     *
     * @param puntoMetaX Valor X final.
     */
    public static void setPuntoMetaX(int puntoMetaX) {
        Corredor.puntoMetaX = puntoMetaX;
    }

    /**
     * Devuelve el tiempo total recorrido por el corredor.
     *
     * @return Tiempo en milisegundos.
     */
    public long getTiempoRecorrido() {
        return tiempoRecorrido;
    }

    /**
     * Establece el tiempo total recorrido por el corredor.
     *
     * @param tiempoRecorrido Tiempo en milisegundos.
     */
    public void setTiempoRecorrido(long tiempoRecorrido) {
        this.tiempoRecorrido = tiempoRecorrido;
    }

    /**
     * Devuelve el ID del corredor.
     *
     * @return Identificador único.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del corredor.
     *
     * @param id Identificador único.
     */
    public void setId(int id) {
        this.id = id;
    }

}