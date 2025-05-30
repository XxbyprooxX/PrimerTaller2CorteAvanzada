package edu.progAvUD.primerTaller2Corte.modelo;

/**
 *
 * @author Andres Felipe
 */
public class Animal extends Corredor{
    
    private String tipoAnimal;

    public Animal(int id,String nombre, String velocidadMaximaObtenida, String tipoAnimal) {
        super(id,nombre, velocidadMaximaObtenida);
        this.tipoAnimal = tipoAnimal;
    }

    public String getTipoAnimal() {
        return tipoAnimal;
    }

    public void setTipoAnimal(String tipoAnimal) {
        this.tipoAnimal = tipoAnimal;
    }
    
}
