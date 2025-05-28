package edu.progAvUD.primerTaller2Corte.modelo;

/**
 *
 * @author Andres Felipe
 */
public class Corredor {

    private String nombre;
    private String velocidadMaximaObtenida;

    public Corredor(String nombre, String velocidadMaximaObtenida) {
        this.nombre = nombre;
        this.velocidadMaximaObtenida = velocidadMaximaObtenida;
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

}
