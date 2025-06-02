package edu.progAvUD.primerTaller2Corte.modelo;

/**
 * Clase que representa una Persona que hereda de la clase Corredor.
 * Añade el atributo 'cedula' que identifica a la persona.
 * 
 * @author Andres Felipe
 */
public class Persona extends Corredor {

    /**
     * Cédula de la persona (documento de identidad).
     */
    public String cedula;

    /**
     * Constructor de la clase Persona.
     *
     * @param id Identificador único del corredor.
     * @param nombre Nombre del corredor.
     * @param velocidadMaximaObtenida Velocidad máxima alcanzada por el corredor.
     * @param cedula Cédula o documento de identidad de la persona.
     */
    public Persona(int id, String nombre, String velocidadMaximaObtenida, String cedula) {
        super(id, nombre, velocidadMaximaObtenida);
        this.cedula = cedula;
    }

    /**
     * Obtiene la cédula de la persona.
     *
     * @return La cédula como String.
     */
    public String getCedula() {
        return cedula;
    }

    /**
     * Establece la cédula de la persona.
     *
     * @param cedula La nueva cédula a asignar.
     */
    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

}