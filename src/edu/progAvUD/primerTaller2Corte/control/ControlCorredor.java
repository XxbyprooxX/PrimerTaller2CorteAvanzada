package edu.progAvUD.primerTaller2Corte.control;

import edu.progAvUD.primerTaller2Corte.modelo.Animal;
import edu.progAvUD.primerTaller2Corte.modelo.Corredor;
import edu.progAvUD.primerTaller2Corte.modelo.Persona;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase encargada de manejar la lógica relacionada con los corredores y sus hilos.
 * Controla la creación, gestión y sincronización de los hilos que simulan las carreras.
 */
public class ControlCorredor {

    private ControlPrincipal controlPrincipal; // Referencia al controlador principal que maneja la interfaz y eventos globales
    private ArrayList<CorredorHilo> corredoresHilo; // Lista que mantiene los hilos de cada corredor activo

    /**
     * Constructor que inicializa la lista de corredores y establece el controlador principal.
     * @param controlPrincipal Referencia al controlador principal del sistema
     */
    public ControlCorredor(ControlPrincipal controlPrincipal) {
        this.controlPrincipal = controlPrincipal;
        corredoresHilo = new ArrayList<>();
    }

    /**
     * Inicia un hilo independiente para manejar la sincronización y ejecución
     * de los hilos de corredores, permitiendo que la carrera comience.
     */
    public void iniciarJuego() {
        new Thread(() -> {
            iniciarYSicronizarHilosCorredor();
        }).start();
    }

    /**
     * Busca si ya existe un corredor registrado con un tipo y un identificador único específicos.
     * @param tipoObjeto "animal" o "persona"
     * @param identificadorUnico Identificador único del corredor (tipo animal o cédula)
     * @return true si el corredor ya existe, false si no existe
     */
    public boolean buscarCorredorExistente(String tipoObjeto, String identificadorUnico) {
        ArrayList<Corredor> corredores = new ArrayList<>();
        for (CorredorHilo hilo : corredoresHilo) {
            Corredor corredor = hilo.getCorredor();
            corredores.add(corredor);
        }

        for (Corredor corredorEncontrado : corredores) {
            if (tipoObjeto.equalsIgnoreCase("animal") && corredorEncontrado instanceof Animal) {
                Animal animal = (Animal) corredorEncontrado;
                if (identificadorUnico.equalsIgnoreCase(animal.getTipoAnimal())) {
                    return true; // Animal ya registrado
                }
            } else if (tipoObjeto.equalsIgnoreCase("persona") && corredorEncontrado instanceof Persona) {
                Persona persona = (Persona) corredorEncontrado;
                if (identificadorUnico.equalsIgnoreCase(persona.getCedula())) {
                    return true; // Persona ya registrada
                }
            }
        }
        return false; // No existe el corredor con esos datos
    }

    /**
     * Crea un nuevo corredor (Animal o Persona) y su hilo correspondiente si no existe uno con el mismo identificador.
     * Luego agrega el hilo a la lista de hilos activos.
     * @param id Identificador numérico del corredor
     * @param tipoObjeto Tipo de corredor ("animal" o "persona")
     * @param nombre Nombre del corredor
     * @param velocidadMaximaObtenida Velocidad máxima asignada al corredor
     * @param identificadorUnico Identificador único (tipo animal o cédula)
     */
    public void crearCorredor(int id, String tipoObjeto, String nombre, String velocidadMaximaObtenida, String identificadorUnico) {
        Corredor corredor = null;
        CorredorHilo corredorHilo = null;

        if (!buscarCorredorExistente(tipoObjeto, identificadorUnico)) {
            if (tipoObjeto.equalsIgnoreCase("animal")) {
                corredor = new Animal(id, nombre, velocidadMaximaObtenida, identificadorUnico);
                corredorHilo = new CorredorHilo(this, corredor);
            } else if (tipoObjeto.equalsIgnoreCase("persona")) {
                corredor = new Persona(id, nombre, velocidadMaximaObtenida, identificadorUnico);
                corredorHilo = new CorredorHilo(this, corredor);
            } else {
                controlPrincipal.mostrarMensajeError("No se ha podido crear al corredor");
            }
        }

        if (corredor != null) {
            corredorHilo.setName("Corredor " + id + ": " + nombre); // Nombra el hilo para identificarlo fácilmente
            corredoresHilo.add(corredorHilo); // Agrega el hilo a la lista de corredores activos
        }
    }

    /**
     * Establece para todos los corredores el punto de inicio y el punto meta en el eje X.
     * Estos puntos definen la pista en la que se realizará la carrera.
     * @param puntoComienzo Posición inicial en X
     * @param puntoMeta Posición final/meta en X
     */
    public void asignarPuntosComienzoMetaX(int puntoComienzo, int puntoMeta) {
        Corredor.setPuntoComienzoX(puntoComienzo);
        Corredor.setPuntoMetaX(puntoMeta);
    }

    /**
     * Reinicia la carrera y sincroniza los hilos de todos los corredores para comenzar de nuevo.
     * Crea nuevos hilos para evitar problemas con hilos ya terminados.
     */
    public void iniciarYSicronizarHilosCorredor() {
        CorredorHilo.resetearEstadoCarrera(); // Reinicia estado de carrera para todos los hilos
        ArrayList<CorredorHilo> hilosParaCarrera = new ArrayList<>();

        // Para cada hilo existente, reinicia la distancia y crea un nuevo hilo con el mismo corredor
        for (CorredorHilo hiloOriginal : corredoresHilo) {
            hiloOriginal.getCorredor().setDistanciaRecorida(0);
            CorredorHilo nuevoHilo = new CorredorHilo(this, hiloOriginal.getCorredor());
            nuevoHilo.setName(hiloOriginal.getName());
            hilosParaCarrera.add(nuevoHilo);
        }

        // Inicia todos los hilos nuevos para la carrera
        for (CorredorHilo hilo : hilosParaCarrera) {
            hilo.start();
        }

        // Espera que todos los hilos terminen (que finalicen la carrera)
        for (CorredorHilo hilo : hilosParaCarrera) {
            try {
                hilo.join();
            } catch (InterruptedException ie) {
                controlPrincipal.mostrarMensajeError("Ha ocurrido un error");
            }
        }
    }

    /**
     * Simula un accidente para el corredor 1, afectando su desempeño en la carrera.
     */
    public void hacerAccidenteCorredor1(){
        CorredorHilo.hacerAccidenteCorredor1();
    }

    /**
     * Aplica un impulso extra al corredor 2, acelerando temporalmente su avance.
     */
    public void hacerImpulsarCorredor2() {
        CorredorHilo.hacerImpulsarCorredor2();
    }

    /**
     * Consulta si ya hay un ganador declarado en la carrera.
     * @return true si hay ganador, false en caso contrario
     */
    public boolean isHayGanador() {
        return controlPrincipal.isHayGanador();
    }

    /**
     * Establece el estado de existencia de un ganador.
     * @param hayGanador true si hay ganador, false si no
     */
    public void setHayGanador(boolean hayGanador) {
        controlPrincipal.setHayGanador(hayGanador);
    }

    /**
     * Retorna la cantidad de corredores actualmente registrados para la carrera.
     * @return número de corredores
     */
    public int darCantidadCorredores() {
        return corredoresHilo.size();
    }

    // Métodos para mover la representación gráfica de cada corredor en la interfaz
    // según la distancia recorrida que reportan los hilos.

    /**
     * Mueve el panel gráfico que representa al corredor 1.
     * @param cambioDistancia Incremento en la posición del corredor
     */
    public void moverPanelCorredor1(int cambioDistancia) {
        controlPrincipal.moverPanelCorredor1(cambioDistancia);
    }

    /**
     * Mueve el panel gráfico que representa al corredor 2.
     * @param cambioDistancia Incremento en la posición del corredor
     */
    public void moverPanelCorredor2(int cambioDistancia) {
        controlPrincipal.moverPanelCorredor2(cambioDistancia);
    }

    /**
     * Mueve el panel gráfico que representa al corredor 3.
     * @param cambioDistancia Incremento en la posición del corredor
     */
    public void moverPanelCorredor3(int cambioDistancia) {
        controlPrincipal.moverPanelCorredor3(cambioDistancia);
    }

    /**
     * Mueve el panel gráfico que representa al corredor 4.
     * @param cambioDistancia Incremento en la posición del corredor
     */
    public void moverPanelCorredor4(int cambioDistancia) {
        controlPrincipal.moverPanelCorredor4(cambioDistancia);
    }

    /**
     * Notifica al controlador principal que un corredor ha ganado la carrera.
     * @param nombre Nombre del corredor ganador
     */
    public void registrarGanador(String nombre) {
        controlPrincipal.registrarGanador(nombre);
    }

    /**
     * Notifica al controlador principal que varios corredores han empatado.
     * @param nombresGanadores Lista con los nombres de los corredores empatados
     */
    public void registrarEmpate(List<String> nombresGanadores) {
        controlPrincipal.registrarEmpate(nombresGanadores);
    }
}