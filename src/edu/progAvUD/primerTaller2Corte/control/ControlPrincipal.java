package edu.progAvUD.primerTaller2Corte.control;

import java.util.HashMap;

/**
 *
 * @author Andres Felipe
 */
public class ControlPrincipal {

    private ControlGrafico controlGrafico;
    private ControlCorredor controlCorredor;
    private HashMap<String, Integer> ganadorRonda;
    private int cantidadCorredores;
    private int contadorCorredores;

    public ControlPrincipal() {
        controlGrafico = new ControlGrafico(this);
        controlCorredor = new ControlCorredor(this);
        ganadorRonda = new HashMap<>();
        this.cantidadCorredores = 0;
        this.contadorCorredores = 0;
    }

    public int getCantidadCorredores() {
        return cantidadCorredores;
    }

    public void setCantidadCorredores(int cantidadCorredores) {
        this.cantidadCorredores = cantidadCorredores;
    }

    public void crearCorredor(String tipoObjeto, String nombre, String velocidadMaximaObtenida, String identificadorUnico) {
        controlCorredor.crearCorredor(tipoObjeto, nombre, velocidadMaximaObtenida, identificadorUnico);
    }

    public void mostrarMensajeError(String mensaje) {
        controlGrafico.mostrarMensajeError(mensaje);
    }

    public void mostrarMensajeExito(String mensaje) {
        controlGrafico.mostrarMensajeExito(mensaje);
    }

    public int pedirCantidadCorredores() {
        return controlCorredor.darCantidadCorredores();
    }

    public ControlCorredor getControlCorredor() {
        return controlCorredor;
    }

    public int getContadorCorredores() {
        return contadorCorredores;
    }

    public void setContadorCorredores(int contadorCorredores) {
        this.contadorCorredores = contadorCorredores;
    }
    
}