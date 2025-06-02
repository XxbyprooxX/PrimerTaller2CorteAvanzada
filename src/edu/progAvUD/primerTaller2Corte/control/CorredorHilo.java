package edu.progAvUD.primerTaller2Corte.control;

import edu.progAvUD.primerTaller2Corte.modelo.Corredor;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class CorredorHilo extends Thread {

    private ControlCorredor controlCorredor;
    private Corredor corredor;
    private static boolean corredor1Accidentado = false;
    private static boolean corredor2Impulsado = false;
    private static final Object lockGanadores = new Object();
    private static List<String> ganadoresEmpate = new ArrayList<>();
    private static boolean carreraTerminada = false;

    public CorredorHilo(ControlCorredor controlCorredor, Corredor corredor) {
        this.controlCorredor = controlCorredor;
        this.corredor = corredor;
    }

    @Override
    public void run() {
        try {
            int puntoComienzoX = Corredor.getPuntoComienzoX();
            int puntoMetaX = Corredor.getPuntoMetaX();

            while (!controlCorredor.isHayGanador() && !carreraTerminada) {
                if (corredor.getId() == 1 && corredor1Accidentado) {
                    corredor1Accidentado = false;
                    Thread.sleep(1000);
                }

                if (corredor.getId() == 2 && corredor2Impulsado) {
                    corredor2Impulsado = false;
                    Random random = new Random();
                    int distanciaAMover = random.nextInt(40) + 1;
                    controlCorredor.moverPanelCorredor2(distanciaAMover);
                    corredor.setDistanciaRecorida(corredor.getDistanciaRecorida() + distanciaAMover);
                }

                moverCorredorHaciaMeta();

                // Verificar si llegó a la meta
                if (puntoComienzoX + corredor.getDistanciaRecorida() >= puntoMetaX) {
                    synchronized (lockGanadores) {
                        if (!carreraTerminada) {
                            // Agregar este corredor a la lista de ganadores
                            ganadoresEmpate.add(corredor.getNombre());

                            // Esperar un momento para ver si otros corredores también llegan
                            Thread.sleep(50);

                            // Si solo hay un ganador, registrarlo normalmente
                            if (ganadoresEmpate.size() == 1) {
                                controlCorredor.registrarGanador(corredor.getNombre());
                            } else {
                                // Si hay múltiples ganadores, registrar empate
                                controlCorredor.registrarEmpate(new ArrayList<>(ganadoresEmpate));
                            }

                            carreraTerminada = true;
                        }
                    }
                    break;
                }

                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void moverCorredorHaciaMeta() throws InterruptedException {
        int puntoComienzoX = Corredor.getPuntoComienzoX();
        int puntoMetaX = Corredor.getPuntoMetaX();

        // Mover solo si no ha alcanzado la meta
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
                default:
                    break;
            }

            corredor.setDistanciaRecorida(corredor.getDistanciaRecorida() + distanciaAMover);

            if (puntoComienzoX + corredor.getDistanciaRecorida() < puntoMetaX) {
                notifyAll();
            }
        }
    }

    public static void hacerAccidenteCorredor1() {
        corredor1Accidentado = true;
    }

    public static void hacerImpulsarCorredor2() {
        corredor2Impulsado = true;
    }

    public static void resetearEstadoCarrera() {
        synchronized (lockGanadores) {
            ganadoresEmpate.clear();
            carreraTerminada = false;
        }
    }

    public Corredor getCorredor() {
        return corredor;
    }

    public void setCorredor(Corredor corredor) {
        this.corredor = corredor;
    }
}
