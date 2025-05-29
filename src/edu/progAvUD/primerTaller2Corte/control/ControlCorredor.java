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

    private ControlPrincipal controlPrincipal;
    private ArrayList<Corredor> corredores;
    private ArrayList<CorredorHilo> corredoresHilo;

    public ControlCorredor(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        corredores = new ArrayList<>();
        corredoresHilo = new ArrayList<>();
    }

    public boolean buscarCorredorExistente(String tipoObjeto, String identificadorUnico) {
        for (Corredor corredorEncontrado : corredores) {
            if (tipoObjeto.equalsIgnoreCase("animal") && corredorEncontrado instanceof Animal) {
                Animal animal = (Animal) corredorEncontrado;
                if (identificadorUnico.equalsIgnoreCase(animal.getTipoAnimal())) {
                    return true;
                }
            } else if (tipoObjeto.equalsIgnoreCase("persona") && corredorEncontrado instanceof Persona) {
                Persona persona = (Persona) corredorEncontrado;
                if (identificadorUnico.equalsIgnoreCase(persona.getCedula())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void crearCorredor(String tipoObjeto, String nombre, String velocidadMaximaObtenida, String identificadorUnico) {
        Corredor corredor = null;
        if (!buscarCorredorExistente(tipoObjeto, identificadorUnico)) {
            if (tipoObjeto.equalsIgnoreCase("animal")) {
                corredor = new Animal(nombre, velocidadMaximaObtenida, identificadorUnico);
            } else if (tipoObjeto.equalsIgnoreCase("persona")) {
                corredor = new Persona(nombre, velocidadMaximaObtenida, identificadorUnico);
            } else {
                controlPrincipal.mostrarMensajeError("No se ha podido crear al corredor");
            }
        }
        if (corredor != null) {
            corredores.add(corredor);
        }
    }

    public boolean buscarHiloExistente(String tipoObjeto, String identificadorUnico) {
        for (Corredor hiloEncontrado : corredores) {
            if (tipoObjeto.equalsIgnoreCase("animal")) {
                if (identificadorUnico.equalsIgnoreCase(((Animal) hiloEncontrado).getTipoAnimal())) {
                    return true;
                }
                return false;
            } else if (tipoObjeto.equalsIgnoreCase("persona")) {
                if (identificadorUnico.equalsIgnoreCase(((Persona) hiloEncontrado).getCedula())) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public void crearHilo(String tipoObjeto, String identificadorUnico, int inicioX, int inicioY, int objetivoX, int objetivoY) {
        if (!buscarHiloExistente(tipoObjeto, identificadorUnico)) {
            CorredorHilo corredorHilo;
            for (Corredor corredorEncontrado : corredores) {
                if (tipoObjeto.equalsIgnoreCase("animal")) {
                    if (identificadorUnico.equalsIgnoreCase(((Animal) corredorEncontrado).getTipoAnimal())) {
                        corredorHilo = new CorredorHilo(corredorEncontrado, inicioX, inicioY, objetivoX, objetivoY);
                        corredoresHilo.add(corredorHilo);
                    }
                } else if (tipoObjeto.equalsIgnoreCase("persona")) {
                    if (identificadorUnico.equalsIgnoreCase(((Persona) corredorEncontrado).getCedula())) {
                        corredorHilo = new CorredorHilo(corredorEncontrado, inicioX, inicioY, objetivoX, objetivoY);
                        corredoresHilo.add(corredorHilo);
                    }
                }
            }
        }
    }

    public int darCantidadCorredores() {
        return corredores.size();
    }
}
