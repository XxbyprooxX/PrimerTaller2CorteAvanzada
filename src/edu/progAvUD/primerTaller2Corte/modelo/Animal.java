package edu.progAvUD.primerTaller2Corte.modelo;

/**
 * Clase que representa un Animal participante en una carrera. Hereda de la
 * clase Corredor y agrega un atributo específico: tipo de animal.
 *
 * @author Andres Felipe
 */
public class Animal extends Corredor {

    private String tipoAnimal; // Especifica el tipo de animal (por ejemplo: perro, caballo, etc.)

    /**
     * Constructor de la clase Animal.
     *
     * @param id Identificador del corredor (animal).
     * @param nombre Nombre del animal.
     * @param velocidadMaximaObtenida Velocidad máxima alcanzada por el animal.
     * @param tipoAnimal Tipo de animal (ej. "perro", "gato", "caballo").
     */
    public Animal(int id, String nombre, String velocidadMaximaObtenida, String tipoAnimal) {
        super(id, nombre, velocidadMaximaObtenida);
        this.tipoAnimal = tipoAnimal;
    }

    /**
     * Obtiene el tipo de animal.
     *
     * @return El tipo de animal como String.
     */
    public String getTipoAnimal() {
        return tipoAnimal;
    }

    /**
     * Establece el tipo de animal.
     *
     * @param tipoAnimal El nuevo tipo de animal.
     */
    public void setTipoAnimal(String tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }

}