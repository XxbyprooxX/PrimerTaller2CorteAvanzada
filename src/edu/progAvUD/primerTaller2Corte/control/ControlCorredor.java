package edu.progAvUD.primerTaller2Corte.control;

import edu.progAvUD.primerTaller2Corte.modelo.Animal;
import edu.progAvUD.primerTaller2Corte.modelo.Corredor;
import edu.progAvUD.primerTaller2Corte.modelo.Persona;
import java.util.ArrayList;

/**
 *
 * @author Cristianlol789
 */
public class ControlCorredor {

    private ArrayList<Corredor> corredores;

    public ControlCorredor(ControlPrincipal controlPrincipal) {
        corredores = new ArrayList<>();
    }

    public boolean corredorExistente(String tipoObjeto, String identificadorUnico) {
        for (Corredor corredorEncontrado : corredores) {
            if (tipoObjeto.equalsIgnoreCase("animal")) {
                if (identificadorUnico.equalsIgnoreCase(((Animal) corredorEncontrado).getTipoAnimal())) {
                    return true;
                }
                return false;
            } else if (tipoObjeto.equalsIgnoreCase("persona")) {
                if (identificadorUnico.equalsIgnoreCase(((Persona) corredorEncontrado).getCedula())) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public String crearCorredor(String tipoObjeto, String nombre, String velocidadMaximaObtenida, String identificadorUnico) {
        if (!corredorExistente(tipoObjeto, identificadorUnico)) {
            Corredor corredor;
            CorredorHilo hiloCorredor;
            if (tipoObjeto.equalsIgnoreCase("animal")) {
                corredor = new Animal(nombre, velocidadMaximaObtenida, identificadorUnico);
                hiloCorredor= new CorredorHilo(corredor);
            } else if (tipoObjeto.equalsIgnoreCase("persona")) {
                corredor = new Persona(nombre, velocidadMaximaObtenida, identificadorUnico);
                hiloCorredor= new CorredorHilo(corredor);
            } else {
                corredor = null;
            }
            if (corredor == null) {
                return "No se ha podido crear al corredor";
            } else {
                corredores.add(corredor);
                return "Se ha creado el corredor con exito";
            }
        } else {
            return "No se ha podido crear al corredor";
        }
    }
    
}