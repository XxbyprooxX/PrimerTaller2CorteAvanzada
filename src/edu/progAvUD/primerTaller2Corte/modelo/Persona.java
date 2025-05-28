package edu.progAvUD.primerTaller2Corte.modelo;

/**
 *
 * @author Andres Felipe
 */
public class Persona extends Corredor{
    
    public String cedula;

    public Persona(String nombre, String velocidadMaximaObtenida, String cedula) {
        super(nombre, velocidadMaximaObtenida);
        this.cedula = cedula;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

}