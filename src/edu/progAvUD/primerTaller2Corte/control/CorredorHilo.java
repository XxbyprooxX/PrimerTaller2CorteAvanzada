package edu.progAvUD.primerTaller2Corte.control;

import edu.progAvUD.primerTaller2Corte.modelo.Corredor;
import java.util.Random;

public class CorredorHilo extends Thread {

    private Corredor corredorHilo;
    private int posicionX;
    private int posicionY;
    private int objetivoX;
    private int objetivoY;
    private boolean haGanado = false;
    private Random aleatorio;
    private ControlCarrera controlCarrera;
    private long tiempoInicio;
    private boolean accidentado = false;

    public CorredorHilo(Corredor corredor, int inicioX, int inicioY, int objetivoX, int objetivoY) {
        this.corredorHilo = corredor;
        this.posicionX = inicioX;
        this.posicionY = inicioY;
        this.objetivoX = objetivoX;
        this.objetivoY = objetivoY;
        this.controlCarrera = new ControlCarrera();
        this.aleatorio = new Random();
    }

    private int cantidadMovimientoAleatorio() {
        return aleatorio.nextInt(10) + 1;
    }

    private void moverHaciaObjetivo() {
        if (haGanado) {
            return;
        }

        int cantidadMovimiento = cantidadMovimientoAleatorio();

        if (posicionX < objetivoX) {
            posicionX += cantidadMovimiento;
            if (posicionX > objetivoX) {
                posicionX = objetivoX;
            }
        } else if (posicionX > objetivoX) {
            posicionX -= cantidadMovimiento;
            if (posicionX < objetivoX) {
                posicionX = objetivoX;
            }
        }

        if (posicionY < objetivoY) {
            posicionY += cantidadMovimiento;
            if (posicionY > objetivoY) {
                posicionY = objetivoY;
            }
        } else if (posicionY > objetivoY) {
            posicionY -= cantidadMovimiento;
            if (posicionY < objetivoY) {
                posicionY = objetivoY;
            }
        }

        if (posicionX == objetivoX && posicionY == objetivoY) {
            haGanado = true;
            long tiempoFin = System.currentTimeMillis();
            long tiempoTotal = (tiempoFin - tiempoInicio) / 1000;

            boolean fuiElPrimero = controlCarrera.registrarGanador(corredorHilo.getNombre(), tiempoTotal);
            if (fuiElPrimero) {
                //System.out.println("Ganador: " + corredorHilo.getNombre() + " con tiempo: " + tiempoTotal + " segundos");
            }
        }
    }

    @Override
    public void run() {
        tiempoInicio = System.currentTimeMillis();

        try {
            while (!haGanado) {
                if (!accidentado) {
                    moverHaciaObjetivo();
                }
                sleep(100 + aleatorio.nextInt(200)); 
            }
        } catch (InterruptedException e) {
            //System.out.println(corredorHilo.getNombre() + " fue interrumpido.");
        }
    }

    public int getPosicionX() {
        return posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void detenerTemporalmente() {
        new Thread(() -> {
            accidentado = true;
            try {
                Thread.sleep(2000 + aleatorio.nextInt(2000)); 
            } catch (InterruptedException e) {
                
            }
            accidentado = false;
        }).start();
    }

    public void impulsar() {
        int impulso = aleatorio.nextInt(30) + 20; 
        posicionX += impulso;
        if (posicionX > objetivoX) {
            posicionX = objetivoX;
        }
    }
}
