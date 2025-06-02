package edu.progAvUD.primerTaller2Corte.control;

import edu.progAvUD.primerTaller2Corte.vista.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 *
 * @author Andres Felipe
 */
public class ControlGrafico implements ActionListener {

    private ControlPrincipal controlPrincipal;
    private VentanaPrincipal ventanaPrincipal;
    private Timer cronometro;
    private int centesimas = 0;

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
        ventanaPrincipal.panelCarrera.jButtonAccidente.addActionListener(this);
        ventanaPrincipal.panelCarrera.jButtonImpulsar.addActionListener(this);
        ventanaPrincipal.panelCarrera.jButtonSalir.addActionListener(this);

        this.cronometro = new Timer(10, this);
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
        if (e.getSource() == ventanaPrincipal.panelCarrera.jButtonIniciarCarrera) {
            ventanaPrincipal.panelCarrera.jButtonIniciarCarrera.setEnabled(false);
            ventanaPrincipal.panelCarrera.jButtonSalir.setEnabled(false);
            ventanaPrincipal.panelCarrera.jButtonImpulsar.setEnabled(true);
            ventanaPrincipal.panelCarrera.jButtonAccidente.setEnabled(true);
            controlPrincipal.iniciarYSicronizarHilosCorredor();
            cronometro.start();
        }
        if (e.getSource() == this.cronometro) {
            centesimas++;
            int segundos = (centesimas / 100) % 60;
            int minutos = (centesimas / 6000);
            int cent = centesimas % 100;

            String tiempo = String.format("%02d:%02d:%02d", minutos, segundos, cent);
            controlPrincipal.setTiempoGanadorString(tiempo);
        }
        if (e.getSource() == ventanaPrincipal.panelCarrera.jButtonAccidente) {
            controlPrincipal.hacerAccidenteCorredor1();
        }
        if (e.getSource() == ventanaPrincipal.panelCarrera.jButtonImpulsar) {
            controlPrincipal.hacerImpulsarCorredor2();
        }
        if (e.getSource() == ventanaPrincipal.panelCarrera.jButtonSalir) {
            controlPrincipal.mostrarResumenGanadores();
            System.exit(0);
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

        controlPrincipal.setContadorCorredores(controlPrincipal.getContadorCorredores() + 1);

        controlPrincipal.crearCorredor(controlPrincipal.getContadorCorredores(), tipoObjeto, nombre, velocidadMaximaObtenida, identificadorUnico);

        if (controlPrincipal.getContadorCorredores() == 1) {
            mostrarImagenCorredor1(tipoObjeto, identificadorUnico);
        } else if (controlPrincipal.getContadorCorredores() == 2) {
            mostrarImagenCorredor2(tipoObjeto, identificadorUnico);
        } else if (controlPrincipal.getContadorCorredores() == 3) {
            mostrarImagenCorredor3(tipoObjeto, identificadorUnico);
        } else if (controlPrincipal.getContadorCorredores() == 4) {
            mostrarImagenCorredor4(tipoObjeto, identificadorUnico);
        }

        ventanaPrincipal.dialogDatosCorredor.dispose();

        if (controlPrincipal.getContadorCorredores() < controlPrincipal.getCantidadCorredores()) {
            mostrarDialogoCorredor();
        } else {
            ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelCarrera);
            ocultarBotonesCarrera();
            controlPrincipal.asignarPuntosComienzoMetaX(ventanaPrincipal.panelCarrera.jPanelCorredor1.getX(), ventanaPrincipal.panelCarrera.jPanelMeta.getX());
        }

    }

    public void moverPanelCorredor1(int cambioDistancia) {
        int posicionX = ventanaPrincipal.panelCarrera.jPanelCorredor1.getX();
        int posicionY = ventanaPrincipal.panelCarrera.jPanelCorredor1.getY();
        ventanaPrincipal.panelCarrera.jPanelCorredor1.setLocation(posicionX + cambioDistancia, posicionY);
    }

    public void moverPanelCorredor2(int cambioDistancia) {
        int posicionX = ventanaPrincipal.panelCarrera.jPanelCorredor2.getX();
        int posicionY = ventanaPrincipal.panelCarrera.jPanelCorredor2.getY();
        ventanaPrincipal.panelCarrera.jPanelCorredor2.setLocation(posicionX + cambioDistancia, posicionY);
    }

    public void moverPanelCorredor3(int cambioDistancia) {
        int posicionX = ventanaPrincipal.panelCarrera.jPanelCorredor3.getX();
        int posicionY = ventanaPrincipal.panelCarrera.jPanelCorredor3.getY();
        ventanaPrincipal.panelCarrera.jPanelCorredor3.setLocation(posicionX + cambioDistancia, posicionY);
    }

    public void moverPanelCorredor4(int cambioDistancia) {
        int posicionX = ventanaPrincipal.panelCarrera.jPanelCorredor4.getX();
        int posicionY = ventanaPrincipal.panelCarrera.jPanelCorredor4.getY();
        ventanaPrincipal.panelCarrera.jPanelCorredor4.setLocation(posicionX + cambioDistancia, posicionY);
    }

    public void restablecerPanelCarrera() {
        ventanaPrincipal.panelCarrera.revalidate();
        ventanaPrincipal.panelCarrera.repaint();
        ventanaPrincipal.panelCarrera.jButtonIniciarCarrera.setEnabled(true);
        ventanaPrincipal.panelCarrera.jButtonSalir.setEnabled(true);
    }

    public void mostrarMensajeError(String mensaje) {
        ventanaPrincipal.mostrarMensajeError(mensaje);
    }

    public void mostrarMensajeExito(String mensaje) {
        ventanaPrincipal.mostrarMensajeExito(mensaje);
    }

    public void pararTiempo() {
        cronometro.stop();
    }

    public void ocultarBotonesCarrera() {
        ventanaPrincipal.panelCarrera.jButtonImpulsar.setEnabled(false);
        ventanaPrincipal.panelCarrera.jButtonAccidente.setEnabled(false);
    }

    public void mostrarImagenCorredor1(String tipoCorredor, String tipoAnimal) {
        if (tipoCorredor.equalsIgnoreCase("persona")) {
            ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                    new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/bolt.gif")
            );
        } else if (tipoCorredor.equalsIgnoreCase("animal")) {
            if (tipoAnimal.equalsIgnoreCase("periquin")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/periquin.gif")
                );
            } else if (tipoAnimal.equalsIgnoreCase("aguila")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/aguila.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("caballo")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/caballo.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("caiman")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/caiman.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("cebra")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/cebra.gif")
                );
            } else if (tipoAnimal.equalsIgnoreCase("elefante")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/elefante.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("gato")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/gato.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("jaguar")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/jaguar.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("leon")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/leon.gif")
                );
            } else if (tipoAnimal.equalsIgnoreCase("mono")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/mono.gif")
                );
            } else if (tipoAnimal.equalsIgnoreCase("osopanda")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/osoPanda.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("osoperezoso")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/osoPerezoso.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("perro")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/perro.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("tigre")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/tigre.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("tucan")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/tucan.png")
                );
            } else {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/otro.png")
                );
            }
        }
    }

    public void mostrarImagenCorredor2(String tipoCorredor, String tipoAnimal) {
        if (tipoCorredor.equalsIgnoreCase("persona")) {
            ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                    new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/bolt.gif")
            );
        } else if (tipoCorredor.equalsIgnoreCase("animal")) {
            if (tipoAnimal.equalsIgnoreCase("periquin")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/periquin.gif")
                );
            } else if (tipoAnimal.equalsIgnoreCase("aguila")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/aguila.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("caballo")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/caballo.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("caiman")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/caiman.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("cebra")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/cebra.gif")
                );
            } else if (tipoAnimal.equalsIgnoreCase("elefante")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/elefante.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("gato")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/gato.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("jaguar")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/jaguar.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("leon")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/leon.gif")
                );
            } else if (tipoAnimal.equalsIgnoreCase("mono")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/mono.gif")
                );
            } else if (tipoAnimal.equalsIgnoreCase("osopanda")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/osoPanda.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("osoperezoso")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/osoPerezoso.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("perro")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/perro.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("tigre")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/tigre.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("tucan")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/tucan.png")
                );
            } else {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/otro.png")
                );
            }
        }
    }

    public void mostrarImagenCorredor3(String tipoCorredor, String tipoAnimal) {
        if (tipoCorredor.equalsIgnoreCase("persona")) {
            ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                    new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/bolt.gif")
            );
        } else if (tipoCorredor.equalsIgnoreCase("animal")) {
            if (tipoAnimal.equalsIgnoreCase("periquin")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/periquin.gif")
                );
            } else if (tipoAnimal.equalsIgnoreCase("aguila")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/aguila.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("caballo")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/caballo.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("caiman")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/caiman.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("cebra")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/cebra.gif")
                );
            } else if (tipoAnimal.equalsIgnoreCase("elefante")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/elefante.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("gato")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/gato.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("jaguar")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/jaguar.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("leon")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/leon.gif")
                );
            } else if (tipoAnimal.equalsIgnoreCase("mono")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/mono.gif")
                );
            } else if (tipoAnimal.equalsIgnoreCase("osopanda")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/osoPanda.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("osoperezoso")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/osoPerezoso.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("perro")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/perro.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("tigre")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/tigre.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("tucan")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/tucan.png")
                );
            } else {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/otro.png")
                );
            }
        }
    }

    public void mostrarImagenCorredor4(String tipoCorredor, String tipoAnimal) {
        if (tipoCorredor.equalsIgnoreCase("persona")) {
            ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                    new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/bolt.gif")
            );
        } else if (tipoCorredor.equalsIgnoreCase("animal")) {
            if (tipoAnimal.equalsIgnoreCase("periquin")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/periquin.gif")
                );
            } else if (tipoAnimal.equalsIgnoreCase("aguila")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/aguila.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("caballo")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/caballo.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("caiman")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/caiman.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("cebra")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/cebra.gif")
                );
            } else if (tipoAnimal.equalsIgnoreCase("elefante")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/elefante.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("gato")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/gato.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("jaguar")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/jaguar.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("leon")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/leon.gif")
                );
            } else if (tipoAnimal.equalsIgnoreCase("mono")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/mono.gif")
                );
            } else if (tipoAnimal.equalsIgnoreCase("osopanda")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/osoPanda.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("osoperezoso")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/osoPerezoso.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("perro")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/perro.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("tigre")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/tigre.png")
                );
            } else if (tipoAnimal.equalsIgnoreCase("tucan")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/tucan.png")
                );
            } else {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/otro.png")
                );
            }
        }
    }

}
