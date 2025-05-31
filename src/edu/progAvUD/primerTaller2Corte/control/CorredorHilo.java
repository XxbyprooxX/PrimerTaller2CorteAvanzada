package edu.progAvUD.primerTaller2Corte.control;

import edu.progAvUD.primerTaller2Corte.modelo.Corredor;
import java.util.Random;

public class CorredorHilo extends Thread {

    private ControlCorredor controlCorredor;
    private Corredor corredor;

    public CorredorHilo(ControlCorredor controlCorredor, Corredor corredor) {
        this.controlCorredor = controlCorredor;
        this.corredor = corredor;
    }

    @Override
    public void run() {
        try {
            int puntoComienzoX = Corredor.getPuntoComienzoX();
            int puntoMetaX = Corredor.getPuntoMetaX();
            while (!controlCorredor.isHayGanador()) {
                moverCorredorHaciaMeta();
                if (puntoComienzoX + corredor.getDistanciaRecorida() >= puntoMetaX) {
                    controlCorredor.registrarGanador(corredor.getNombre());
                }
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.getStackTrace();
        }
    }

    public synchronized void moverCorredorHaciaMeta() throws InterruptedException {
        int puntoComienzoX = Corredor.getPuntoComienzoX();
        int puntoMetaX = Corredor.getPuntoMetaX();

        // Mover solo si no ha alcanzado la meta
        if (puntoComienzoX + corredor.getDistanciaRecorida() < puntoMetaX) {
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

    public Corredor getCorredor() {
        return corredor;
    }

    public void setCorredor(Corredor corredor) {
        this.corredor = corredor;
    }

}
