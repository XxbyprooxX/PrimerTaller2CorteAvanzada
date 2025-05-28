package edu.progAvUD.primerTaller2Corte.control;

import edu.progAvUD.primerTaller2Corte.vista.VentanaPrincipal;

/**
 *
 * @author Andres Felipe
 */
public class ControlGrafico {
    
    private ControlPrincipal controlPrincipal;
    private VentanaPrincipal ventanaPrincipal;

    public ControlGrafico(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.ventanaPrincipal = new VentanaPrincipal(this);
    }
    
    
}