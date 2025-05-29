package edu.progAvUD.primerTaller2Corte.control;

import edu.progAvUD.primerTaller2Corte.modelo.Corredor;

/**
 *
 * @author Cristianlol789
 */
public class CorredorHilo extends Thread {

    private Corredor hiloCorredor;
    
    public CorredorHilo(Corredor corredor){
        hiloCorredor = corredor;
    }
    
    @Override
    public void run() {

    }
}