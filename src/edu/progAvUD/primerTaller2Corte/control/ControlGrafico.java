package edu.progAvUD.primerTaller2Corte.control;

import edu.progAvUD.primerTaller2Corte.vista.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Andres Felipe
 */
public class ControlGrafico implements ActionListener {

    private ControlPrincipal controlPrincipal;
    private VentanaPrincipal ventanaPrincipal;

    public ControlGrafico(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.ventanaPrincipal = new VentanaPrincipal(this);

        ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelPrincipal);

        ventanaPrincipal.panelPrincipal.jButtonSiguiente.addActionListener(this);

        ventanaPrincipal.dialogDatosCorredor.jButtonContinuar.addActionListener(this);
        ventanaPrincipal.dialogDatosCorredor.jRadioButtonAnimal.addActionListener(this);
        ventanaPrincipal.dialogDatosCorredor.jRadioButtonPersona.addActionListener(this);
        ventanaPrincipal.dialogDatosCorredor.jComboBoxTipoAnimal.addActionListener(this);
        
        ventanaPrincipal.panelCarrera.jButtonIniciarCarrera.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ventanaPrincipal.panelPrincipal.jButtonSiguiente) {
            controlPrincipal.setCantidadCorredores((int) ventanaPrincipal.panelPrincipal.jSpinnerCantidadCorredores.getValue());
            mostrarDialogoCorredor();
        }

        if (e.getSource() == ventanaPrincipal.dialogDatosCorredor.jButtonContinuar) {
            cargarDatosCorredor();
        }
        if (e.getSource() == ventanaPrincipal.dialogDatosCorredor.jRadioButtonAnimal) {
            ventanaPrincipal.dialogDatosCorredor.jTextFieldNombre.setEnabled(true);
            ventanaPrincipal.dialogDatosCorredor.jSpinnerVelocidadMaxima.setEnabled(true);
            ventanaPrincipal.dialogDatosCorredor.jComboBoxTipoAnimal.setEnabled(true);
            ventanaPrincipal.dialogDatosCorredor.jTextFieldCedula.setEnabled(false);
            ventanaPrincipal.dialogDatosCorredor.jButtonContinuar.setEnabled(true);

            if ("Otro".equals((String) ventanaPrincipal.dialogDatosCorredor.jComboBoxTipoAnimal.getSelectedItem())) {
                ventanaPrincipal.dialogDatosCorredor.jLabelOtroAnimal.setVisible(true);
                ventanaPrincipal.dialogDatosCorredor.jTextFieldOtroAnimal.setVisible(true);
            } else {
                ventanaPrincipal.dialogDatosCorredor.jLabelOtroAnimal.setVisible(false);
                ventanaPrincipal.dialogDatosCorredor.jTextFieldOtroAnimal.setVisible(false);
            }
        }
        if (e.getSource() == ventanaPrincipal.dialogDatosCorredor.jRadioButtonPersona) {
            ventanaPrincipal.dialogDatosCorredor.jTextFieldNombre.setEnabled(true);
            ventanaPrincipal.dialogDatosCorredor.jSpinnerVelocidadMaxima.setEnabled(true);
            ventanaPrincipal.dialogDatosCorredor.jComboBoxTipoAnimal.setEnabled(false);
            ventanaPrincipal.dialogDatosCorredor.jTextFieldCedula.setEnabled(true);
            ventanaPrincipal.dialogDatosCorredor.jButtonContinuar.setEnabled(true);

            ventanaPrincipal.dialogDatosCorredor.jLabelOtroAnimal.setVisible(false);
            ventanaPrincipal.dialogDatosCorredor.jTextFieldOtroAnimal.setVisible(false);
        }
        if (e.getSource() == ventanaPrincipal.dialogDatosCorredor.jComboBoxTipoAnimal) {
            if ("Otro".equals((String) ventanaPrincipal.dialogDatosCorredor.jComboBoxTipoAnimal.getSelectedItem())) {
                ventanaPrincipal.dialogDatosCorredor.jLabelOtroAnimal.setVisible(true);
                ventanaPrincipal.dialogDatosCorredor.jTextFieldOtroAnimal.setVisible(true);
            } else {
                ventanaPrincipal.dialogDatosCorredor.jLabelOtroAnimal.setVisible(false);
                ventanaPrincipal.dialogDatosCorredor.jTextFieldOtroAnimal.setVisible(false);
            }

        }
        if(e.getSource() == ventanaPrincipal.panelCarrera.jButtonIniciarCarrera){
            System.out.println("Posicion inicial de los corredores en X: "+ventanaPrincipal.panelCarrera.jPanelCorredor1.getX());
            
            System.out.println("Posicion inicial del corredor 1 en Y: "+ventanaPrincipal.panelCarrera.jPanelCorredor1.getY());
            System.out.println("Posicion inicial del corredor 2 en Y: "+ventanaPrincipal.panelCarrera.jPanelCorredor2.getY());
            System.out.println("Posicion inicial del corredor 3 en Y: "+ventanaPrincipal.panelCarrera.jPanelCorredor3.getY());
            System.out.println("Posicion inicial del corredor 4 en Y: "+ventanaPrincipal.panelCarrera.jPanelCorredor4.getY());
            
            System.out.println("Posicion de la meta en X: "+ventanaPrincipal.panelCarrera.jPanelMeta.getX());
            
            controlPrincipal.iniciarYSicronizarHilosCorredor();
            
//            moverCorredor1(10);
//            moverCorredor2(15);
//            moverCorredor3(20);
//            moverCorredor4(30);
        }
    }

    private void mostrarDialogoCorredor() {
        ventanaPrincipal.dialogDatosCorredor.limpiarCampos();
        ventanaPrincipal.dialogDatosCorredor.jLabelNumeroCorredor.setText((controlPrincipal.getContadorCorredores() + 1) + "");
        ventanaPrincipal.dialogDatosCorredor.setVisible(true);
    }

    public void cargarDatosCorredor() {
        if (ventanaPrincipal.dialogDatosCorredor.buttonGroupTipoCorredor.getSelection() == null
                || ventanaPrincipal.dialogDatosCorredor.jTextFieldNombre.getText().isBlank()) {
            ventanaPrincipal.mostrarMensajeError("Hay campos vacios o no se ha seleccionado una opcion");
            return;
        }

        String tipoObjeto = "";
        String identificadorUnico = "";
        if (ventanaPrincipal.dialogDatosCorredor.jRadioButtonAnimal.isSelected()) {
            tipoObjeto = "animal";

            if ("Otro".equals((String) ventanaPrincipal.dialogDatosCorredor.jComboBoxTipoAnimal.getSelectedItem())) {
                identificadorUnico = ventanaPrincipal.dialogDatosCorredor.jTextFieldOtroAnimal.getText();
                if (identificadorUnico.isBlank()) {
                    ventanaPrincipal.mostrarMensajeError("Hay campos vacios o no se ha seleccionado una opcion");
                    return;
                }
            } else {
                identificadorUnico = (String) ventanaPrincipal.dialogDatosCorredor.jComboBoxTipoAnimal.getSelectedItem();
            }

        } else if (ventanaPrincipal.dialogDatosCorredor.jRadioButtonPersona.isSelected()) {
            tipoObjeto = "persona";
            identificadorUnico = ventanaPrincipal.dialogDatosCorredor.jTextFieldCedula.getText();
            if (identificadorUnico.isBlank()) {
                ventanaPrincipal.mostrarMensajeError("Hay campos vacios o no se ha seleccionado una opcion");
                return;
            }
        }

        String nombre = ventanaPrincipal.dialogDatosCorredor.jTextFieldNombre.getText();
        int velocidadMaximaObtenidaInt = (int) ventanaPrincipal.dialogDatosCorredor.jSpinnerVelocidadMaxima.getValue();
        String velocidadMaximaObtenida = velocidadMaximaObtenidaInt + "";

        boolean corredorExistente = controlPrincipal.getControlCorredor().buscarCorredorExistente(tipoObjeto, identificadorUnico);

        if (corredorExistente) {
            ventanaPrincipal.mostrarMensajeError("Ya existe un corredor con ese identificador único. Por favor ingrese otro.");
            return; 
        }

        controlPrincipal.setContadorCorredores(controlPrincipal.getContadorCorredores()+ 1);
        
        controlPrincipal.crearCorredor(controlPrincipal.getContadorCorredores(),tipoObjeto, nombre, velocidadMaximaObtenida, identificadorUnico);

        ventanaPrincipal.dialogDatosCorredor.dispose();

        if (controlPrincipal.getContadorCorredores() < controlPrincipal.getCantidadCorredores()) {
            mostrarDialogoCorredor();
        } else {
            ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelCarrera);
            controlPrincipal.asignarPuntosComienzoMetaX(ventanaPrincipal.panelCarrera.jPanelCorredor1.getX(), ventanaPrincipal.panelCarrera.jPanelMeta.getX());
        }
    }
    
    public void moverPanelCorredor1(int cambioDistancia){
        int posicionX= ventanaPrincipal.panelCarrera.jPanelCorredor1.getX();
        int posicionY= ventanaPrincipal.panelCarrera.jPanelCorredor1.getY();
        ventanaPrincipal.panelCarrera.jPanelCorredor1.setLocation(posicionX+cambioDistancia,posicionY);
        ventanaPrincipal.panelCarrera.revalidate();
        ventanaPrincipal.panelCarrera.repaint();
    }
    
    public void moverPanelCorredor2(int cambioDistancia){
        int posicionX= ventanaPrincipal.panelCarrera.jPanelCorredor2.getX();
        int posicionY= ventanaPrincipal.panelCarrera.jPanelCorredor2.getY();
        ventanaPrincipal.panelCarrera.jPanelCorredor2.setLocation(posicionX+cambioDistancia,posicionY);
        ventanaPrincipal.panelCarrera.revalidate();
        ventanaPrincipal.panelCarrera.repaint();
    }
    
    public void moverPanelCorredor3(int cambioDistancia){
        int posicionX= ventanaPrincipal.panelCarrera.jPanelCorredor3.getX();
        int posicionY= ventanaPrincipal.panelCarrera.jPanelCorredor3.getY();
        ventanaPrincipal.panelCarrera.jPanelCorredor3.setLocation(posicionX+cambioDistancia,posicionY);
        ventanaPrincipal.panelCarrera.revalidate();
        ventanaPrincipal.panelCarrera.repaint();
        
    }
    public void moverPanelCorredor4(int cambioDistancia){
        int posicionX= ventanaPrincipal.panelCarrera.jPanelCorredor4.getX();
        int posicionY= ventanaPrincipal.panelCarrera.jPanelCorredor4.getY();
        ventanaPrincipal.panelCarrera.jPanelCorredor4.setLocation(posicionX+cambioDistancia,posicionY);
        ventanaPrincipal.panelCarrera.revalidate();
        ventanaPrincipal.panelCarrera.repaint();
    }
    

    public void mostrarMensajeError(String mensaje) {
        ventanaPrincipal.mostrarMensajeError(mensaje);
    }

    public void mostrarMensajeExito(String mensaje) {
        ventanaPrincipal.mostrarMensajeExito(mensaje);
    }

}
