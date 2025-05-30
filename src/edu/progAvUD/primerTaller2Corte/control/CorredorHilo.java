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
                    controlCorredor.registrarGanador(corredor.getNombre(), 1000);
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

            // Actualizar la distancia recorrida
            corredor.setDistanciaRecorida(corredor.getDistanciaRecorida() + distanciaAMover);

            // Notificar si el corredor no ha alcanzado la meta
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
//    private int cantidadMovimientoAleatorio() {
//        return aleatorio.nextInt(10) + 1;
//    }
//
//    private void moverHaciaObjetivo() {
//        if (haGanado) {
//            return;
//        }
//
//        int cantidadMovimiento = cantidadMovimientoAleatorio();
//
//        if (posicionX < objetivoX) {
//            posicionX += cantidadMovimiento;
//            if (posicionX > objetivoX) {
//                posicionX = objetivoX;
//            }
//        } else if (posicionX > objetivoX) {
//            posicionX -= cantidadMovimiento;
//            if (posicionX < objetivoX) {
//                posicionX = objetivoX;
//            }
//        }
//
//        if (posicionY < objetivoY) {
//            posicionY += cantidadMovimiento;
//            if (posicionY > objetivoY) {
//                posicionY = objetivoY;
//            }
//        } else if (posicionY > objetivoY) {
//            posicionY -= cantidadMovimiento;
//            if (posicionY < objetivoY) {
//                posicionY = objetivoY;
//            }
//        }
//
//        if (posicionX == objetivoX && posicionY == objetivoY) {
//            haGanado = true;
//            long tiempoFin = System.currentTimeMillis();
//            long tiempoTotal = (tiempoFin - tiempoInicio) / 1000;
//
//            boolean fuiElPrimero = controlCarrera.registrarGanador(corredor.getNombre(), tiempoTotal);
//            if (fuiElPrimero) {
//                //System.out.println("Ganador: " + corredorHilo.getNombre() + " con tiempo: " + tiempoTotal + " segundos");
//            }
//        }
//    }
//
//    @Override
//    public void run() {
//        tiempoInicio = System.currentTimeMillis();
//
//        try {
//            while (!haGanado) {
//                if (!accidentado) {
//                    moverHaciaObjetivo();
//                }
//                sleep(100 + aleatorio.nextInt(200)); 
//            }
//        } catch (InterruptedException e) {
//            //System.out.println(corredorHilo.getNombre() + " fue interrumpido.");
//        }
//    }
//
//    public int getPosicionX() {
//        return posicionX;
//    }
//
//    public int getPosicionY() {
//        return posicionY;
//    }
//
//    public void detenerTemporalmente() {
//        new Thread(() -> {
//            accidentado = true;
//            try {
//                Thread.sleep(2000 + aleatorio.nextInt(2000)); 
//            } catch (InterruptedException e) {
//                
//            }
//            accidentado = false;
//        }).start();
//    }
//
//    public void impulsar() {
//        int impulso = aleatorio.nextInt(30) + 20; 
//        posicionX += impulso;
//        if (posicionX > objetivoX) {
//            posicionX = objetivoX;
//        }
//    }
