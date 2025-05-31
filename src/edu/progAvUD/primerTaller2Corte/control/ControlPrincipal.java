package edu.progAvUD.primerTaller2Corte.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Andres Felipe
 */
public class ControlPrincipal {

    private ControlGrafico controlGrafico;
    private ControlCorredor controlCorredor;
    private HashMap<String, ArrayList<Integer>> ganadorRonda;
    private int cantidadRondas;
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
        this.cantidadRondas = 0;
    }

    public synchronized void registrarGanador(String nombre) {
        cantidadRondas++;
        hayGanador = true;
        notifyAll();
        controlGrafico.pararTiempo();
        ganadorRonda.computeIfAbsent(nombre, k -> new ArrayList<>()).add(cantidadRondas);
        controlGrafico.mostrarMensajeExito(
                "El ganador de la ronda fue " + nombre
                + " con un tiempo de " + tiempoGanador
        );
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
        hayGanador = false;
        controlCorredor.iniciarJuego();
    }

    public void mostrarResumenGanadores() {
        for (Map.Entry<String, ArrayList<Integer>> entry : ganadorRonda.entrySet()) {
            String nombre = entry.getKey();
            List<Integer> rondas = entry.getValue();
            // Convertir la lista de rondas a una cadena "1, 2, 3"
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < rondas.size(); i++) {
                sb.append(rondas.get(i));
                if (i < rondas.size() - 1) {
                    sb.append(", ");
                }
            }
            int totalRondasGanadas = rondas.size();
            controlGrafico.mostrarMensajeExito(
                    "[ Estadísticas del jugador: " + nombre
                    + ", ganó las siguientes rondas: " + sb.toString()
                    + " dando un total de " + totalRondasGanadas + " rondas ganadas ]"
            );
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
