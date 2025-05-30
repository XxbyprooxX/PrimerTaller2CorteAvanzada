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
    private boolean hayGanador = false;
    private String nombreGanador = "";
    private long tiempoGanador = 0;

    public ControlPrincipal() {
        controlGrafico = new ControlGrafico(this);
        controlCorredor = new ControlCorredor(this);
        ganadorRonda = new HashMap<>();
        this.cantidadCorredores = 0;
        this.contadorCorredores = 0;
    }

    public synchronized void registrarGanador(String nombre, long tiempo) {
        hayGanador = true;
        notifyAll();
        nombreGanador = nombre;
        tiempoGanador = tiempo;

    }

    public String getNombreGanador() {
        return nombreGanador;
    }

    public long getTiempoGanador() {
        return tiempoGanador;
    }

    public synchronized boolean isHayGanador() {
        return hayGanador;
    }

    public void setHayGanador(boolean hayGanador) {
        this.hayGanador = hayGanador;
    }

    public int getCantidadCorredores() {
        return cantidadCorredores;
    }

    public void setCantidadCorredores(int cantidadCorredores) {
        this.cantidadCorredores = cantidadCorredores;
    }

    public void crearCorredor(int id, String tipoObjeto, String nombre, String velocidadMaximaObtenida, String identificadorUnico) {
        controlCorredor.crearCorredor(id, tipoObjeto, nombre, velocidadMaximaObtenida, identificadorUnico);
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

    public void asignarPuntosComienzoMetaX(int puntoComienzo, int puntoMeta) {
        controlCorredor.asignarPuntosComienzoMetaX(puntoComienzo, puntoMeta);
    }

    public void moverPanelCorredor1(int cambioDistancia) {
        controlGrafico.moverPanelCorredor1(cambioDistancia);
    }

    public void moverPanelCorredor2(int cambioDistancia) {
        controlGrafico.moverPanelCorredor2(cambioDistancia);
    }

    public void moverPanelCorredor3(int cambioDistancia) {
        controlGrafico.moverPanelCorredor3(cambioDistancia);
    }

    public void moverPanelCorredor4(int cambioDistancia) {
        controlGrafico.moverPanelCorredor4(cambioDistancia);
    }
    
    public void iniciarYSicronizarHilosCorredor(){
        controlCorredor.iniciarYSicronizarHilosCorredor();
    }
    
}
