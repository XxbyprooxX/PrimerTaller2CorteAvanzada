package edu.progAvUD.primerTaller2Corte.control;

import edu.progAvUD.primerTaller2Corte.modelo.Corredor;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Clase que representa un hilo de ejecución para un corredor en una carrera.
 * Se encarga de mover al corredor, detectar si gana o hay empate, y manejar eventos como accidentes o impulsos.
 * Implementa control de sincronización para evitar condiciones de carrera en la detección del ganador.
 * 
 * @author User
 */
public class CorredorHilo extends Thread {

    private ControlCorredor controlCorredor;
    private Corredor corredor;

    // Estados especiales para corredores específicos
    private static boolean corredor1Accidentado = false;
    private static boolean corredor2Impulsado = false;

    // Elementos compartidos entre hilos para sincronización
    private static final Object lockGanadores = new Object();
    private static List<String> ganadoresEmpate = new ArrayList<>();
    private static boolean carreraTerminada = false;
    private static Timer timerEmpates = null;
    private static boolean timerIniciado = false;

    /**
     * Constructor del hilo que representa un corredor.
     *
     * @param controlCorredor Referencia al controlador de corredores para mover los paneles.
     * @param corredor Corredor asociado a este hilo.
     */
    public CorredorHilo(ControlCorredor controlCorredor, Corredor corredor) {
        this.controlCorredor = controlCorredor;
        this.corredor = corredor;
    }

    /**
     * Método que ejecuta el hilo: mueve al corredor hasta la meta.
     * Maneja eventos especiales como accidentes o impulsos y determina ganadores o empates.
     */
    @Override
    public void run() {
        try {
            int puntoComienzoX = Corredor.getPuntoComienzoX();
            int puntoMetaX = Corredor.getPuntoMetaX();

            while (!controlCorredor.isHayGanador() && !carreraTerminada) {

                // Simula un accidente para el corredor 1
                if (corredor.getId() == 1 && corredor1Accidentado) {
                    corredor1Accidentado = false;
                    Thread.sleep(1000); // pausa por accidente
                }

                // Simula un impulso para el corredor 2
                if (corredor.getId() == 2 && corredor2Impulsado) {
                    corredor2Impulsado = false;
                    Random random = new Random();
                    int distanciaAMover = random.nextInt(40) + 1;
                    controlCorredor.moverPanelCorredor2(distanciaAMover);
                    corredor.setDistanciaRecorida(corredor.getDistanciaRecorida() + distanciaAMover);
                }

                // Movimiento normal hacia la meta
                moverCorredorHaciaMeta();

                // Verifica si llegó a la meta
                if (puntoComienzoX + corredor.getDistanciaRecorida() >= puntoMetaX) {
                    synchronized (lockGanadores) {
                        if (!carreraTerminada) {
                            ganadoresEmpate.add(corredor.getNombre());

                            // Inicia el temporizador si es el primer corredor en llegar
                            if (!timerIniciado) {
                                timerIniciado = true;
                                timerEmpates = new Timer();
                                timerEmpates.schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        synchronized (lockGanadores) {
                                            if (!carreraTerminada) {
                                                carreraTerminada = true;
                                                if (ganadoresEmpate.size() == 1) {
                                                    controlCorredor.registrarGanador(ganadoresEmpate.get(0));
                                                } else {
                                                    controlCorredor.registrarEmpate(new ArrayList<>(ganadoresEmpate));
                                                }
                                            }
                                        }
                                    }
                                }, 300); // espera 300 ms para detectar posibles empates
                            }
                        }
                    }
                    break;
                }

                Thread.sleep(100); // pausa breve entre movimientos
            }
        } catch (InterruptedException e) {
        }
    }

    /**
     * Método sincronizado para mover al corredor hacia la meta con una distancia aleatoria.
     * Invoca el método correspondiente en el controlador según el ID del corredor.
     *
     * @throws InterruptedException si ocurre una interrupción.
     */
    public synchronized void moverCorredorHaciaMeta() throws InterruptedException {
        int puntoComienzoX = Corredor.getPuntoComienzoX();
        int puntoMetaX = Corredor.getPuntoMetaX();

        if (puntoComienzoX + corredor.getDistanciaRecorida() < puntoMetaX && !carreraTerminada) {
            Random random = new Random();
            int distanciaAMover = random.nextInt(15) + 1;

            switch (corredor.getId()) {
                case 1:
                    controlCorredor.moverPanelCorredor1(distanciaAMover);
                    break;
                case 2:
                    controlCorredor.moverPanelCorredor2(distanciaAMover);
                    break;
                case 3:
                    controlCorredor.moverPanelCorredor3(distanciaAMover);
                    break;
                case 4:
                    controlCorredor.moverPanelCorredor4(distanciaAMover);
                    break;
            }

            corredor.setDistanciaRecorida(corredor.getDistanciaRecorida() + distanciaAMover);

            if (puntoComienzoX + corredor.getDistanciaRecorida() < puntoMetaX) {
                notifyAll();
            }
        }
    }

    // Métodos estáticos para activar eventos especiales en la carrera

    /**
     * Marca al corredor 1 como accidentado.
     */
    public static void hacerAccidenteCorredor1() {
        corredor1Accidentado = true;
    }

    /**
     * Marca al corredor 2 para recibir un impulso.
     */
    public static void hacerImpulsarCorredor2() {
        corredor2Impulsado = true;
    }

    /**
     * Reinicia los estados compartidos de la carrera para permitir una nueva ejecución.
     */
    public static void resetearEstadoCarrera() {
        synchronized (lockGanadores) {
            ganadoresEmpate.clear();
            carreraTerminada = false;
            timerIniciado = false;
            if (timerEmpates != null) {
                timerEmpates.cancel();
                timerEmpates = null;
            }
        }
    }

    /**
     * Devuelve el corredor asociado al hilo.
     *
     * @return Corredor.
     */
    public Corredor getCorredor() {
        return corredor;
    }

    /**
     * Establece el corredor asociado al hilo.
     *
     * @param corredor Nuevo corredor.
     */
    public void setCorredor(Corredor corredor) {
        this.corredor = corredor;
    }
}
