package edu.progAvUD.primerTaller2Corte.control;

/**
 *
 * @author Cristianlol789
 */
public class ControlCarrera {
    private boolean ganadorRegistrado = false;
    private String nombreGanador = "";
    private long tiempoGanador = 0;

    public synchronized boolean registrarGanador(String nombre, long tiempo) {
        if (!ganadorRegistrado) {
            ganadorRegistrado = true;
            nombreGanador = nombre;
            tiempoGanador = tiempo;
            return true;
        }
        return false;
    }

    public String getNombreGanador() {
        return nombreGanador;
    }

    public long getTiempoGanador() {
        return tiempoGanador;
    }
}
