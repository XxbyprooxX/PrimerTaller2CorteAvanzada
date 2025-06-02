package edu.progAvUD.primerTaller2Corte.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase principal de control de la lógica del juego de carreras.
 * Coordina los corredores, la interfaz gráfica y el registro de ganadores.
 */
public class ControlPrincipal {

    private ControlGrafico controlGrafico; // Maneja la parte gráfica del juego
    private ControlCorredor controlCorredor; // Controla los corredores del juego
    private HashMap<String, ArrayList<Integer>> ganadorRonda; // Registra los ganadores de cada ronda, con nombre y número de ronda
    private int cantidadRondas; // Contador de rondas jugadas
    private int cantidadCorredores; // Número total de corredores en la carrera
    private int contadorCorredores; // Contador auxiliar para corredores (puede usarse para asignación de IDs o control interno)
    private boolean hayGanador = false; // Indica si ya hay un ganador en la ronda actual
    private String tiempoGanador; // Tiempo registrado para el corredor ganador

    /**
     * Constructor que inicializa los controles y variables.
     */
    public ControlPrincipal() {
        controlGrafico = new ControlGrafico(this);
        controlCorredor = new ControlCorredor(this);
        ganadorRonda = new HashMap<>();
        this.cantidadCorredores = 0;
        this.contadorCorredores = 0;
        this.cantidadRondas = 0;
    }

    /**
     * Método sincronizado para registrar un ganador individual.
     * Actualiza el estado, notifica a los hilos y actualiza la interfaz gráfica.
     * @param nombre Nombre del corredor ganador
     */
    public synchronized void registrarGanador(String nombre) {
        cantidadRondas++;
        hayGanador = true;
        notifyAll(); // Notifica a hilos que esperan la finalización
        controlGrafico.pararTiempo(); // Detiene cronómetro de la carrera
        ganadorRonda.computeIfAbsent(nombre, k -> new ArrayList<>()).add(cantidadRondas);
        controlGrafico.mostrarMensajeExito(
                "El ganador de la ronda fue " + nombre
                + " con un tiempo de " + tiempoGanador
        );
        controlGrafico.restablecerPanelCarrera();
        controlGrafico.ocultarBotonesCarrera();
    }

    /**
     * Método sincronizado para registrar un empate entre varios corredores.
     * Actualiza el estado, notifica a los hilos y actualiza la interfaz gráfica.
     * @param nombresGanadores Lista con nombres de los corredores empatados
     */
    public synchronized void registrarEmpate(List<String> nombresGanadores) {
        cantidadRondas++;
        hayGanador = true;
        notifyAll();
        controlGrafico.pararTiempo();

        // Añade cada corredor empatado como ganador de la ronda
        for (String nombre : nombresGanadores) {
            ganadorRonda.computeIfAbsent(nombre, k -> new ArrayList<>()).add(cantidadRondas);
        }

        // Construye el mensaje de empate para mostrarlo
        StringBuilder mensajeEmpate = new StringBuilder();
        mensajeEmpate.append("¡EMPATE! Los siguientes corredores llegaron al mismo tiempo: ");
        for (int i = 0; i < nombresGanadores.size(); i++) {
            mensajeEmpate.append(nombresGanadores.get(i));
            if (i < nombresGanadores.size() - 1) {
                mensajeEmpate.append(", ");
            }
        }
        mensajeEmpate.append(" con un tiempo de ").append(tiempoGanador);

        controlGrafico.mostrarMensajeExito(mensajeEmpate.toString());
        controlGrafico.restablecerPanelCarrera();
        controlGrafico.ocultarBotonesCarrera();
    }

    /**
     * Consulta si ya hay un ganador registrado para la ronda actual.
     * @return true si ya hay ganador, false en caso contrario
     */
    public synchronized boolean isHayGanador() {
        return hayGanador;
    }

    /**
     * Crea un nuevo corredor con los atributos dados delegando a ControlCorredor.
     * @param id Identificador único del corredor
     * @param tipoObjeto Tipo del corredor ("animal" o "persona")
     * @param nombre Nombre del corredor
     * @param velocidadMaximaObtenida Velocidad máxima del corredor en formato String
     * @param identificadorUnico Identificador único (ej. cédula o tipo de animal)
     */
    public void crearCorredor(int id, String tipoObjeto, String nombre, String velocidadMaximaObtenida, String identificadorUnico) {
        controlCorredor.crearCorredor(id, tipoObjeto, nombre, velocidadMaximaObtenida, identificadorUnico);
    }

    /**
     * Muestra un mensaje de error en la interfaz gráfica.
     * @param mensaje Texto del mensaje de error
     */
    public void mostrarMensajeError(String mensaje) {
        controlGrafico.mostrarMensajeError(mensaje);
    }

    /**
     * Muestra un mensaje de éxito en la interfaz gráfica.
     * @param mensaje Texto del mensaje de éxito
     */
    public void mostrarMensajeExito(String mensaje) {
        controlGrafico.mostrarMensajeExito(mensaje);
    }

    /**
     * Devuelve la cantidad actual de corredores registrados.
     * @return número de corredores
     */
    public int pedirCantidadCorredores() {
        return controlCorredor.darCantidadCorredores();
    }

    /**
     * Asigna los puntos de inicio y meta para los corredores en el eje X.
     * @param puntoComienzo Coordenada de inicio
     * @param puntoMeta Coordenada de meta
     */
    public void asignarPuntosComienzoMetaX(int puntoComienzo, int puntoMeta) {
        controlCorredor.asignarPuntosComienzoMetaX(puntoComienzo, puntoMeta);
    }

    /**
     * Mueve el panel del corredor 1 una distancia dada.
     * @param cambioDistancia distancia a mover
     */
    public void moverPanelCorredor1(int cambioDistancia) {
        controlGrafico.moverPanelCorredor1(cambioDistancia);
    }

    /**
     * Mueve el panel del corredor 2 una distancia dada.
     * @param cambioDistancia distancia a mover
     */
    public void moverPanelCorredor2(int cambioDistancia) {
        controlGrafico.moverPanelCorredor2(cambioDistancia);
    }

    /**
     * Mueve el panel del corredor 3 una distancia dada.
     * @param cambioDistancia distancia a mover
     */
    public void moverPanelCorredor3(int cambioDistancia) {
        controlGrafico.moverPanelCorredor3(cambioDistancia);
    }

    /**
     * Mueve el panel del corredor 4 una distancia dada.
     * @param cambioDistancia distancia a mover
     */
    public void moverPanelCorredor4(int cambioDistancia) {
        controlGrafico.moverPanelCorredor4(cambioDistancia);
    }

    /**
     * Inicia y sincroniza los hilos de los corredores para comenzar la carrera.
     * Reinicia el estado interno indicando que no hay ganador aún.
     */
    public void iniciarYSicronizarHilosCorredor() {
        hayGanador = false;
        controlCorredor.iniciarJuego();
    }

    /**
     * Muestra en la interfaz gráfica un resumen con estadísticas de ganadores por ronda.
     */
    public void mostrarResumenGanadores() {
        for (Map.Entry<String, ArrayList<Integer>> entry : ganadorRonda.entrySet()) {
            String nombre = entry.getKey();
            List<Integer> rondas = entry.getValue();

            // Construye la lista de rondas en formato texto
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < rondas.size(); i++) {
                sb.append(rondas.get(i));
                if (i < rondas.size() - 1) {
                    sb.append(", ");
                }
            }

            int totalRondasGanadas = rondas.size();
            controlGrafico.mostrarMensajeExito(
                    "[ Estadísticas del jugador: " + nombre
                    + ", ganó las siguientes rondas: " + sb.toString()
                    + " dando un total de " + totalRondasGanadas + " rondas ganadas ]"
            );
        }
    }

    // Métodos getter y setter

    public int getCantidadCorredores() {
        return cantidadCorredores;
    }

    public void setCantidadCorredores(int cantidadCorredores) {
        this.cantidadCorredores = cantidadCorredores;
    }

    public int getContadorCorredores() {
        return contadorCorredores;
    }

    public void setContadorCorredores(int contadorCorredores) {
        this.contadorCorredores = contadorCorredores;
    }

    public ControlCorredor getControlCorredor() {
        return controlCorredor;
    }

    public void setHayGanador(boolean hayGanador) {
        this.hayGanador = hayGanador;
    }

    public String getTiempoGanadorString() {
        return tiempoGanador;
    }

    public void setTiempoGanadorString(String tiempoGanadorString) {
        this.tiempoGanador = tiempoGanadorString;
    }

    /**
     * Provoca un accidente en el corredor 1.
     */
    public void hacerAccidenteCorredor1() {
        controlCorredor.hacerAccidenteCorredor1();
    }

    /**
     * Aplica un impulso al corredor 2.
     */
    public void hacerImpulsarCorredor2() {
        controlCorredor.hacerImpulsarCorredor2();
    }
}