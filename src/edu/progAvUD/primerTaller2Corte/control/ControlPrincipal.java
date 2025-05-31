package edu.progAvUD.primerTaller2Corte.control;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Andres Felipe
 */
public class ControlPrincipal {

    private ControlGrafico controlGrafico;
    private ControlCorredor controlCorredor;
    private HashMap<String, Integer> ganadorRonda;
    private int numeroRonda;
    private int cantidadCorredores;
    private int contadorCorredores;
    private boolean hayGanador = false;
    private String tiempoGanador;

    public ControlPrincipal() {
        controlGrafico = new ControlGrafico(this);
        controlCorredor = new ControlCorredor(this);
        ganadorRonda = new HashMap<>();
        this.cantidadCorredores = 0;
        this.contadorCorredores = 0;
        this.numeroRonda = 0;
    }

    public synchronized void registrarGanador(String nombre) {
        numeroRonda++;
        hayGanador = true;
        notifyAll();
        controlGrafico.pararTiempo();
        ganadorRonda.put(nombre, numeroRonda);
        controlGrafico.mostrarMensajeExito("El ganador de la ronda fue " + nombre + " con un tiempo de " + tiempoGanador);
        controlGrafico.restablecerPanelCarrera();
        controlGrafico.ocultarBotonesCarrera();
    }

    public synchronized boolean isHayGanador() {
        return hayGanador;
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

    public void iniciarYSicronizarHilosCorredor() {
        controlCorredor.iniciarJuego();
    }

    public void mostrarResumenGanadores() {
        HashMap<String, Integer> resumen = new HashMap<>();
        for (String nombre : ganadorRonda.keySet()) {
            resumen.put(nombre, resumen.getOrDefault(nombre, 0) + 1);
        }
        int totalRondasGanadas = resumen.values().stream().mapToInt(Integer::intValue).sum();
        for (Map.Entry<String, Integer> entry : resumen.entrySet()) {
            String nombre = entry.getKey();
            int rondasGanadas = entry.getValue();
            System.out.println("[" + nombre + ", " + rondasGanadas + ", " + totalRondasGanadas + "]");
        }
    }

    public int getCantidadCorredores() {
        return cantidadCorredores;
    }

    public void setCantidadCorredores(int cantidadCorredores) {
        this.cantidadCorredores = cantidadCorredores;
    }

    public int getContadorCorredores() {
        return contadorCorredores;
    }

    public void setContadorCorredores(int contadorCorredores) {
        this.contadorCorredores = contadorCorredores;
    }

    public ControlCorredor getControlCorredor() {
        return controlCorredor;
    }

    public void setGanadorRonda(HashMap<String, Integer> ganadorRonda) {
        this.ganadorRonda = ganadorRonda;
    }

    public void setHayGanador(boolean hayGanador) {
        this.hayGanador = hayGanador;
    }

    public String getTiempoGanadorString() {
        return tiempoGanador;
    }

    public void setTiempoGanadorString(String tiempoGanadorString) {
        this.tiempoGanador = tiempoGanadorString;
    }

}
