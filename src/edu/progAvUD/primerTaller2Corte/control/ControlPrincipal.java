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

    public ControlPrincipal() {
        controlGrafico = new ControlGrafico(this);
        controlCorredor = new ControlCorredor(this);
        ganadorRonda = new HashMap<>();
    }
     
}