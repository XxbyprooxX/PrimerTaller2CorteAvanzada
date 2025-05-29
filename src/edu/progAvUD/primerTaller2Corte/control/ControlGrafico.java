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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ventanaPrincipal.panelPrincipal.jButtonSiguiente) {

            int cantidadCorredores = (int) ventanaPrincipal.panelPrincipal.jSpinnerCantidadCorredores.getValue();
            controlPrincipal.setCantidadCorredores(cantidadCorredores);

            int contador = 0;
            do {
                contador++;

                ventanaPrincipal.dialogDatosCorredor.jTextFieldNombre.setEnabled(false);
                ventanaPrincipal.dialogDatosCorredor.jSpinnerVelocidadMaxima.setEnabled(false);
                ventanaPrincipal.dialogDatosCorredor.jComboBoxTipoAnimal.setEnabled(false);
                ventanaPrincipal.dialogDatosCorredor.jLabelOtroAnimal.setVisible(false);
                ventanaPrincipal.dialogDatosCorredor.jTextFieldOtroAnimal.setVisible(false);
                ventanaPrincipal.dialogDatosCorredor.jTextFieldCedula.setEnabled(false);
                ventanaPrincipal.dialogDatosCorredor.jButtonContinuar.setEnabled(false);

                ventanaPrincipal.dialogDatosCorredor.limpiarCampos();
                ventanaPrincipal.dialogDatosCorredor.jLabelNumeroCorredor.setText(contador + "");
                ventanaPrincipal.dialogDatosCorredor.setVisible(true);

            } while (contador != cantidadCorredores);

            ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelCarrera);

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
            }
        }
        if (e.getSource() == ventanaPrincipal.dialogDatosCorredor.jRadioButtonPersona) {
            ventanaPrincipal.dialogDatosCorredor.jTextFieldNombre.setEnabled(true);
            ventanaPrincipal.dialogDatosCorredor.jSpinnerVelocidadMaxima.setEnabled(true);
            ventanaPrincipal.dialogDatosCorredor.jComboBoxTipoAnimal.setEnabled(false);
            ventanaPrincipal.dialogDatosCorredor.jTextFieldCedula.setEnabled(true);
            ventanaPrincipal.dialogDatosCorredor.jButtonContinuar.setEnabled(true);

            if ("Otro".equals((String) ventanaPrincipal.dialogDatosCorredor.jComboBoxTipoAnimal.getSelectedItem())) {
                ventanaPrincipal.dialogDatosCorredor.jLabelOtroAnimal.setVisible(false);
                ventanaPrincipal.dialogDatosCorredor.jTextFieldOtroAnimal.setVisible(false);
            }
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
        String velocidadMaximaObtenida = velocidadMaximaObtenidaInt +"";

        controlPrincipal.crearCorredor(tipoObjeto, nombre, velocidadMaximaObtenida, identificadorUnico);

        ventanaPrincipal.dialogDatosCorredor.dispose();

    }

    public void mostrarMensajeError(String mensaje){
        ventanaPrincipal.mostrarMensajeError(mensaje);
    }
    
    public void mostrarMensajeExito(String mensaje){
        ventanaPrincipal.mostrarMensajeExito(mensaje);
    }
    
}
