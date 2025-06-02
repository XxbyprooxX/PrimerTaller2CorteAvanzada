package edu.progAvUD.primerTaller2Corte.control;

import edu.progAvUD.primerTaller2Corte.modelo.Animal;
import edu.progAvUD.primerTaller2Corte.modelo.Corredor;
import edu.progAvUD.primerTaller2Corte.modelo.Persona;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Cristianlol789
 */
public class ControlCorredor {

    private ControlPrincipal controlPrincipal;
    private ArrayList<CorredorHilo> corredoresHilo;
    
    public ControlCorredor(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        corredoresHilo = new ArrayList<>();
    }

    public void iniciarJuego() {
        new Thread(() -> {
            iniciarYSicronizarHilosCorredor();
        }).start();
    }

    public boolean buscarCorredorExistente(String tipoObjeto, String identificadorUnico) {
        ArrayList<Corredor> corredores = new ArrayList<>();
        for (CorredorHilo hilo : corredoresHilo) {
            Corredor corredor = hilo.getCorredor();
            corredores.add(corredor);
        }
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

    public void crearCorredor(int id, String tipoObjeto, String nombre, String velocidadMaximaObtenida, String identificadorUnico) {
        Corredor corredor = null;
        CorredorHilo corredorHilo = null;
        if (!buscarCorredorExistente(tipoObjeto, identificadorUnico)) {
            if (tipoObjeto.equalsIgnoreCase("animal")) {
                corredor = new Animal(id, nombre, velocidadMaximaObtenida, identificadorUnico);
                corredorHilo = new CorredorHilo(this, corredor);
            } else if (tipoObjeto.equalsIgnoreCase("persona")) {
                corredor = new Persona(id, nombre, velocidadMaximaObtenida, identificadorUnico);
                corredorHilo = new CorredorHilo(this, corredor);
            } else {
                controlPrincipal.mostrarMensajeError("No se ha podido crear al corredor");
            }
        }
        if (corredor != null) {
            corredorHilo.setName("Corredor " + id + ": " + nombre);
            corredoresHilo.add(corredorHilo);
        }
    }

    public void asignarPuntosComienzoMetaX(int puntoComienzo, int puntoMeta) {
        Corredor.setPuntoComienzoX(puntoComienzo);
        Corredor.setPuntoMetaX(puntoMeta);
    }

    public void iniciarYSicronizarHilosCorredor() {
        // Resetear el estado de la carrera antes de comenzar
        CorredorHilo.resetearEstadoCarrera();
        
        ArrayList<CorredorHilo> hilosParaCarrera = new ArrayList<>();
        
        for (CorredorHilo hiloOriginal : corredoresHilo) {
            hiloOriginal.getCorredor().setDistanciaRecorida(0);
            CorredorHilo nuevoHilo = new CorredorHilo(this, hiloOriginal.getCorredor());
            nuevoHilo.setName(hiloOriginal.getName());
            hilosParaCarrera.add(nuevoHilo);
        }
        
        for (CorredorHilo hilo : hilosParaCarrera) {
            hilo.start();
        }
        
        for (CorredorHilo hilo : hilosParaCarrera) {
            try {
                hilo.join();
            } catch (InterruptedException ie) {
                controlPrincipal.mostrarMensajeError("Ha ocurrido un error");
            }
        }
    }
    
    public void hacerAccidenteCorredor1(){
        CorredorHilo.hacerAccidenteCorredor1();
    }
    
    public void hacerImpulsarCorredor2() {
        CorredorHilo.hacerImpulsarCorredor2();
    }
    
    public boolean isHayGanador() {
        return controlPrincipal.isHayGanador();
    }

    public void setHayGanador(boolean hayGanador) {
        controlPrincipal.setHayGanador(hayGanador);
    }

    public int darCantidadCorredores() {
        return corredoresHilo.size();
    }

    public void moverPanelCorredor1(int cambioDistancia) {
        controlPrincipal.moverPanelCorredor1(cambioDistancia);
    }

    public void moverPanelCorredor2(int cambioDistancia) {
        controlPrincipal.moverPanelCorredor2(cambioDistancia);
    }

    public void moverPanelCorredor3(int cambioDistancia) {
        controlPrincipal.moverPanelCorredor3(cambioDistancia);
    }

    public void moverPanelCorredor4(int cambioDistancia) {
        controlPrincipal.moverPanelCorredor4(cambioDistancia);
    }

    public void registrarGanador(String nombre) {
        controlPrincipal.registrarGanador(nombre);
    }
    
    // Nuevo método para manejar empates
    public void registrarEmpate(List<String> nombresGanadores) {
        controlPrincipal.registrarEmpate(nombresGanadores);
    }
}