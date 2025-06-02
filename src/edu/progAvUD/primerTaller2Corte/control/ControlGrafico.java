// Paquete donde se encuentra la clase
package edu.progAvUD.primerTaller2Corte.control;

// Importación de clases necesarias
import edu.progAvUD.primerTaller2Corte.vista.VentanaPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.Timer;

/**
 * Controlador gráfico principal de la aplicación. Maneja eventos de la interfaz
 * gráfica.
 *
 * @author Andres Felipe
 */
public class ControlGrafico implements ActionListener {

    private ControlPrincipal controlPrincipal; // Referencia al controlador principal (lógica de negocio)
    private VentanaPrincipal ventanaPrincipal; // Referencia a la interfaz gráfica principal
    private Timer cronometro; // Cronómetro para medir el tiempo de la carrera
    private int centesimas = 0; // Contador de centésimas para el cronómetro

    /**
     * Constructor que inicializa los componentes gráficos y añade listeners.
     *
     * @param controlPrincipal Referencia al controlador principal de la
     * aplicación.
     */
    public ControlGrafico(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        this.ventanaPrincipal = new VentanaPrincipal(this);

        // Muestra el panel principal de inicio
        ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelPrincipal);

        // Agrega listeners a los botones del panel principal y los diálogos
        ventanaPrincipal.panelPrincipal.jButtonSiguiente.addActionListener(this);
        ventanaPrincipal.dialogDatosCorredor.jButtonContinuar.addActionListener(this);
        ventanaPrincipal.dialogDatosCorredor.jRadioButtonAnimal.addActionListener(this);
        ventanaPrincipal.dialogDatosCorredor.jRadioButtonPersona.addActionListener(this);
        ventanaPrincipal.dialogDatosCorredor.jComboBoxTipoAnimal.addActionListener(this);

        // Agrega listeners a los botones del panel de carrera
        ventanaPrincipal.panelCarrera.jButtonIniciarCarrera.addActionListener(this);
        ventanaPrincipal.panelCarrera.jButtonAccidente.addActionListener(this);
        ventanaPrincipal.panelCarrera.jButtonImpulsar.addActionListener(this);
        ventanaPrincipal.panelCarrera.jButtonSalir.addActionListener(this);

        // Inicializa el cronómetro con un delay de 10 ms
        this.cronometro = new Timer(10, this);
    }

    /**
     * Maneja todos los eventos de acción generados en la interfaz gráfica.
     *
     * @param e Evento de acción detectado.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Botón siguiente en la pantalla principal
        if (e.getSource() == ventanaPrincipal.panelPrincipal.jButtonSiguiente) {
            controlPrincipal.setCantidadCorredores((int) ventanaPrincipal.panelPrincipal.jSpinnerCantidadCorredores.getValue());
            mostrarDialogoCorredor();
        }

        // Botón continuar del diálogo de ingreso de datos
        if (e.getSource() == ventanaPrincipal.dialogDatosCorredor.jButtonContinuar) {
            cargarDatosCorredor();
        }

        // RadioButton "Animal" seleccionado
        if (e.getSource() == ventanaPrincipal.dialogDatosCorredor.jRadioButtonAnimal) {
            ventanaPrincipal.dialogDatosCorredor.jTextFieldNombre.setEnabled(true);
            ventanaPrincipal.dialogDatosCorredor.jSpinnerVelocidadMaxima.setEnabled(true);
            ventanaPrincipal.dialogDatosCorredor.jComboBoxTipoAnimal.setEnabled(true);
            ventanaPrincipal.dialogDatosCorredor.jTextFieldCedula.setEnabled(false);
            ventanaPrincipal.dialogDatosCorredor.jButtonContinuar.setEnabled(true);

            // Mostrar campos si el tipo de animal es "Otro"
            if ("Otro".equals((String) ventanaPrincipal.dialogDatosCorredor.jComboBoxTipoAnimal.getSelectedItem())) {
                ventanaPrincipal.dialogDatosCorredor.jLabelOtroAnimal.setVisible(true);
                ventanaPrincipal.dialogDatosCorredor.jTextFieldOtroAnimal.setVisible(true);
            } else {
                ventanaPrincipal.dialogDatosCorredor.jLabelOtroAnimal.setVisible(false);
                ventanaPrincipal.dialogDatosCorredor.jTextFieldOtroAnimal.setVisible(false);
            }
        }

        // RadioButton "Persona" seleccionado
        if (e.getSource() == ventanaPrincipal.dialogDatosCorredor.jRadioButtonPersona) {
            ventanaPrincipal.dialogDatosCorredor.jTextFieldNombre.setEnabled(true);
            ventanaPrincipal.dialogDatosCorredor.jSpinnerVelocidadMaxima.setEnabled(true);
            ventanaPrincipal.dialogDatosCorredor.jComboBoxTipoAnimal.setEnabled(false);
            ventanaPrincipal.dialogDatosCorredor.jTextFieldCedula.setEnabled(true);
            ventanaPrincipal.dialogDatosCorredor.jButtonContinuar.setEnabled(true);

            ventanaPrincipal.dialogDatosCorredor.jLabelOtroAnimal.setVisible(false);
            ventanaPrincipal.dialogDatosCorredor.jTextFieldOtroAnimal.setVisible(false);
        }

        // Selección del tipo de animal
        if (e.getSource() == ventanaPrincipal.dialogDatosCorredor.jComboBoxTipoAnimal) {
            if ("Otro".equals((String) ventanaPrincipal.dialogDatosCorredor.jComboBoxTipoAnimal.getSelectedItem())) {
                ventanaPrincipal.dialogDatosCorredor.jLabelOtroAnimal.setVisible(true);
                ventanaPrincipal.dialogDatosCorredor.jTextFieldOtroAnimal.setVisible(true);
            } else {
                ventanaPrincipal.dialogDatosCorredor.jLabelOtroAnimal.setVisible(false);
                ventanaPrincipal.dialogDatosCorredor.jTextFieldOtroAnimal.setVisible(false);
            }
        }

        // Iniciar la carrera
        if (e.getSource() == ventanaPrincipal.panelCarrera.jButtonIniciarCarrera) {
            ventanaPrincipal.panelCarrera.jButtonIniciarCarrera.setEnabled(false);
            ventanaPrincipal.panelCarrera.jButtonSalir.setEnabled(false);
            ventanaPrincipal.panelCarrera.jButtonImpulsar.setEnabled(true);
            ventanaPrincipal.panelCarrera.jButtonAccidente.setEnabled(true);
            controlPrincipal.iniciarYSicronizarHilosCorredor();
            cronometro.start();
        }

        // Actualización del cronómetro cada 10ms
        if (e.getSource() == this.cronometro) {
            centesimas++;
            int segundos = (centesimas / 100) % 60;
            int minutos = (centesimas / 6000);
            int cent = centesimas % 100;

            String tiempo = String.format("%02d:%02d:%02d", minutos, segundos, cent);
            controlPrincipal.setTiempoGanadorString(tiempo);
        }

        // Botón para provocar accidente al corredor 1
        if (e.getSource() == ventanaPrincipal.panelCarrera.jButtonAccidente) {
            controlPrincipal.hacerAccidenteCorredor1();
        }

        // Botón para impulsar al corredor 2
        if (e.getSource() == ventanaPrincipal.panelCarrera.jButtonImpulsar) {
            controlPrincipal.hacerImpulsarCorredor2();
        }

        // Botón para salir y mostrar resumen
        if (e.getSource() == ventanaPrincipal.panelCarrera.jButtonSalir) {
            controlPrincipal.mostrarResumenGanadores();
            System.exit(0);
        }
    }

    // Muestra el diálogo para ingresar los datos de un nuevo corredor
    private void mostrarDialogoCorredor() {
        ventanaPrincipal.dialogDatosCorredor.limpiarCampos();
        ventanaPrincipal.dialogDatosCorredor.jLabelNumeroCorredor.setText((controlPrincipal.getContadorCorredores() + 1) + "");
        ventanaPrincipal.dialogDatosCorredor.setVisible(true);
    }

    /**
     * Carga los datos del corredor ingresados por el usuario desde el diálogo.
     * Valida campos y crea el corredor correspondiente.
     */
    public void cargarDatosCorredor() {
        // Validaciones básicas
        if (ventanaPrincipal.dialogDatosCorredor.buttonGroupTipoCorredor.getSelection() == null
                || ventanaPrincipal.dialogDatosCorredor.jTextFieldNombre.getText().isBlank()) {
            ventanaPrincipal.mostrarMensajeError("Hay campos vacios o no se ha seleccionado una opcion");
            return;
        }

        // Determinar tipo de objeto (animal o persona) e identificador
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

        // Recopilar otros datos
        String nombre = ventanaPrincipal.dialogDatosCorredor.jTextFieldNombre.getText();
        int velocidadMaximaObtenidaInt = (int) ventanaPrincipal.dialogDatosCorredor.jSpinnerVelocidadMaxima.getValue();
        String velocidadMaximaObtenida = velocidadMaximaObtenidaInt + "";

        // Validar si el corredor ya existe
        boolean corredorExistente = controlPrincipal.getControlCorredor().buscarCorredorExistente(tipoObjeto, identificadorUnico);
        if (corredorExistente) {
            ventanaPrincipal.mostrarMensajeError("Ya existe un corredor con ese identificador único. Por favor ingrese otro.");
            return;
        }

        // Incrementar el contador y crear el corredor
        controlPrincipal.setContadorCorredores(controlPrincipal.getContadorCorredores() + 1);
        controlPrincipal.crearCorredor(controlPrincipal.getContadorCorredores(), tipoObjeto, nombre, velocidadMaximaObtenida, identificadorUnico);

        // Mostrar imagen correspondiente al corredor
        if (controlPrincipal.getContadorCorredores() == 1) {
            mostrarImagenCorredor1(tipoObjeto, identificadorUnico);
        } else if (controlPrincipal.getContadorCorredores() == 2) {
            mostrarImagenCorredor2(tipoObjeto, identificadorUnico);
        } else if (controlPrincipal.getContadorCorredores() == 3) {
            mostrarImagenCorredor3(tipoObjeto, identificadorUnico);
        } else if (controlPrincipal.getContadorCorredores() == 4) {
            mostrarImagenCorredor4(tipoObjeto, identificadorUnico);
        }

        // Cerrar diálogo
        ventanaPrincipal.dialogDatosCorredor.dispose();

        // Mostrar siguiente panel o iniciar carrera
        if (controlPrincipal.getContadorCorredores() < controlPrincipal.getCantidadCorredores()) {
            mostrarDialogoCorredor();
        } else {
            ventanaPrincipal.mostrarPanel(ventanaPrincipal.panelCarrera);
            ocultarBotonesCarrera();
            controlPrincipal.asignarPuntosComienzoMetaX(
                    ventanaPrincipal.panelCarrera.jPanelCorredor1.getX(),
                    ventanaPrincipal.panelCarrera.jPanelMeta.getX()
            );
        }
    }

    /**
     * Mueve el panel del corredor 1 en el eje X.
     *
     * @param cambioDistancia distancia a mover.
     */
    public void moverPanelCorredor1(int cambioDistancia) {
        int posicionX = ventanaPrincipal.panelCarrera.jPanelCorredor1.getX();
        int posicionY = ventanaPrincipal.panelCarrera.jPanelCorredor1.getY();
        ventanaPrincipal.panelCarrera.jPanelCorredor1.setLocation(posicionX + cambioDistancia, posicionY);
    }

    /**
     * Mueve el panel del corredor 2 en el eje X.
     * @param cambioDistancia
     */
    public void moverPanelCorredor2(int cambioDistancia) {
        int posicionX = ventanaPrincipal.panelCarrera.jPanelCorredor2.getX();
        int posicionY = ventanaPrincipal.panelCarrera.jPanelCorredor2.getY();
        ventanaPrincipal.panelCarrera.jPanelCorredor2.setLocation(posicionX + cambioDistancia, posicionY);
    }

    /**
     * Mueve el panel del corredor 3 en el eje X.
     * @param cambioDistancia
     */
    public void moverPanelCorredor3(int cambioDistancia) {
        int posicionX = ventanaPrincipal.panelCarrera.jPanelCorredor3.getX();
        int posicionY = ventanaPrincipal.panelCarrera.jPanelCorredor3.getY();
        ventanaPrincipal.panelCarrera.jPanelCorredor3.setLocation(posicionX + cambioDistancia, posicionY);
    }

    /**
     * Mueve el panel del corredor 4 en el eje X.
     * @param cambioDistancia
     */
    public void moverPanelCorredor4(int cambioDistancia) {
        int posicionX = ventanaPrincipal.panelCarrera.jPanelCorredor4.getX();
        int posicionY = ventanaPrincipal.panelCarrera.jPanelCorredor4.getY();
        ventanaPrincipal.panelCarrera.jPanelCorredor4.setLocation(posicionX + cambioDistancia, posicionY);
    }

    /**
     * Restaura el panel de carrera a su estado inicial.
     */
    public void restablecerPanelCarrera() {
        ventanaPrincipal.panelCarrera.revalidate();
        ventanaPrincipal.panelCarrera.repaint();
        ventanaPrincipal.panelCarrera.jButtonIniciarCarrera.setEnabled(true);
        ventanaPrincipal.panelCarrera.jButtonSalir.setEnabled(true);
    }

    /**
     * Muestra un mensaje de error en la interfaz principal.
     *
     * @param mensaje El mensaje de error a mostrar.
     */
    public void mostrarMensajeError(String mensaje) {
        ventanaPrincipal.mostrarMensajeError(mensaje); // Llama al método de la interfaz para mostrar el mensaje de error.
    }

    /**
     * Muestra un mensaje de éxito en la interfaz principal.
     *
     * @param mensaje El mensaje de éxito a mostrar.
     */
    public void mostrarMensajeExito(String mensaje) {
        ventanaPrincipal.mostrarMensajeExito(mensaje); // Llama al método de la interfaz para mostrar el mensaje de éxito.
    }

    /**
     * Detiene el cronómetro (usualmente al finalizar la carrera).
     */
    public void pararTiempo() {
        cronometro.stop(); // Detiene el objeto cronómetro.
    }

    /**
     * Desactiva los botones de la carrera para evitar más interacciones.
     */
    public void ocultarBotonesCarrera() {
        ventanaPrincipal.panelCarrera.jButtonImpulsar.setEnabled(false); // Desactiva el botón de "Impulsar".
        ventanaPrincipal.panelCarrera.jButtonAccidente.setEnabled(false); // Desactiva el botón de "Accidente".
    }

    /**
     * Muestra la imagen correspondiente al primer corredor según su tipo
     * (persona o animal) y el tipo de animal.
     *
     * @param tipoCorredor "persona" o "animal".
     * @param tipoAnimal Tipo específico del animal (si el corredor es animal).
     */
    public void mostrarImagenCorredor1(String tipoCorredor, String tipoAnimal) {
        if (tipoCorredor.equalsIgnoreCase("persona")) {
            // Muestra la imagen del corredor humano.
            ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                    new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/bolt.gif")
            );
        } else if (tipoCorredor.equalsIgnoreCase("animal")) {
            // Según el tipo de animal, se muestra la imagen correspondiente.
            if (tipoAnimal.equalsIgnoreCase("periquin")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/periquin.gif")
                );
            } else if (tipoAnimal.equalsIgnoreCase("aguila")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/aguila.png")
                );
            } // [Se repite esta lógica para cada tipo de animal...]
            else if (tipoAnimal.equalsIgnoreCase("tucan")) {
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/tucan.png")
                );
            } else {
                // Si el animal no está en la lista, se muestra una imagen por defecto.
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor1.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/otro.png")
                );
            }
        }
    }

    /**
     * Muestra la imagen del corredor 2 según su tipo (persona o animal) y el
     * tipo específico de animal. Si es persona, se muestra una imagen
     * predeterminada (bolt.gif). Si es animal, se selecciona la imagen
     * correspondiente al tipo de animal.
     *
     * @param tipoCorredor indica si el corredor es "persona" o "animal"
     * @param tipoAnimal especifica el tipo de animal si tipoCorredor es
     * "animal"
     */
    public void mostrarImagenCorredor2(String tipoCorredor, String tipoAnimal) {
        if (tipoCorredor.equalsIgnoreCase("persona")) {
            // Imagen predeterminada para persona
            ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                    new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/bolt.gif")
            );
        } else if (tipoCorredor.equalsIgnoreCase("animal")) {
            // Selección de imagen según tipo de animal
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
                // Imagen por defecto si el animal no está listado
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor2.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/otro.png")
                );
            }
        }
    }

    /**
     * Muestra la imagen del corredor 3 según su tipo (persona o animal) y el
     * tipo específico de animal. Funciona igual que mostrarImagenCorredor2 pero
     * para el tercer corredor.
     *
     * @param tipoCorredor indica si el corredor es "persona" o "animal"
     * @param tipoAnimal especifica el tipo de animal si tipoCorredor es
     * "animal"
     */
    public void mostrarImagenCorredor3(String tipoCorredor, String tipoAnimal) {
        if (tipoCorredor.equalsIgnoreCase("persona")) {
            // Imagen predeterminada para persona
            ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                    new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/bolt.gif")
            );
        } else if (tipoCorredor.equalsIgnoreCase("animal")) {
            // Selección de imagen según tipo de animal
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
                // Imagen por defecto si el animal no está listado
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor3.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/otro.png")
                );
            }
        }
    }

    /**
     * Muestra la imagen correspondiente para el corredor 4 según su tipo. Si el
     * corredor es una persona, se muestra la imagen predeterminada "bolt.gif".
     * Si el corredor es un animal, se muestra la imagen específica según el
     * tipo de animal proporcionado. Si el tipo de animal no coincide con
     * ninguna opción conocida, se muestra una imagen por defecto "otro.png".
     *
     * @param tipoCorredor Indica si el corredor es "persona" o "animal".
     * @param tipoAnimal Especifica el tipo de animal si tipoCorredor es
     * "animal".
     */
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
                // Imagen por defecto para animales no listados
                ventanaPrincipal.panelCarrera.jLabelImagenCorredor4.setIcon(
                        new ImageIcon(System.getProperty("user.dir") + "/src/edu/progAvUD/primerTaller2Corte/imagenes/otro.png")
                );
            }
        }
    }
}