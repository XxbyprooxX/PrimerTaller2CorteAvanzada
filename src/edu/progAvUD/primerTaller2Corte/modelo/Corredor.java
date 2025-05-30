package edu.progAvUD.primerTaller2Corte.modelo;

/**
 *
 * @author Andres Felipe
 */
public class Corredor {

    private int id;
    private String nombre;
    private String velocidadMaximaObtenida;
    private static int puntoComienzoX;
    private static int puntoMetaX;
    private int distanciaRecorida;
    private long tiempoRecorrido;

    public Corredor(int id,String nombre, String velocidadMaximaObtenida) {
        this.id= id;
        this.nombre = nombre;
        this.velocidadMaximaObtenida = velocidadMaximaObtenida;
        this.distanciaRecorida = 0;
        this.tiempoRecorrido = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVelocidadMaximaObtenida() {
        return velocidadMaximaObtenida;
    }

    public void setVelocidadMaximaObtenida(String velocidadMaximaObtenida) {
        this.velocidadMaximaObtenida = velocidadMaximaObtenida;
    }

    public int getDistanciaRecorida() {
        return distanciaRecorida;
    }

    public void setDistanciaRecorida(int distanciaRecorida) {
        this.distanciaRecorida = distanciaRecorida;
    }

    public static int getPuntoComienzoX() {
        return puntoComienzoX;
    }

    public static void setPuntoComienzoX(int puntoComienzoX) {
        Corredor.puntoComienzoX = puntoComienzoX;
    }

    public static int getPuntoMetaX() {
        return puntoMetaX;
    }

    public static void setPuntoMetaX(int puntoMetaX) {
        Corredor.puntoMetaX = puntoMetaX;
    }

    public long getTiempoRecorrido() {
        return tiempoRecorrido;
    }

    public void setTiempoRecorrido(long tiempoRecorrido) {
        this.tiempoRecorrido = tiempoRecorrido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
}
