package edu.progAvUD.primerTaller2Corte.vista;

import javax.swing.*;
import java.awt.*;

/**
 * Clase PanelConFondo que extiende JPanel para pintar una imagen de fondo
 * que se ajusta al tamaño del panel.
 * 
 * Esta clase permite que el panel tenga un fondo personalizado con una imagen,
 * útil para darle un estilo visual a la interfaz.
 * 
 * @author User
 */
public class PanelConFondo extends JPanel {
    // Imagen que se usará como fondo del panel
    private Image imagenFondo;

    /**
     * Constructor por defecto que carga la imagen de fondo desde los recursos.
     * La imagen debe estar ubicada en la ruta /edu/progAvUD/primerTaller2Corte/imagenes/EscenarioCarrera.png
     */
    public PanelConFondo() {
        // Cargar la imagen de fondo desde los recursos del paquete
        imagenFondo = new ImageIcon(getClass().getResource("/edu/progAvUD/primerTaller2Corte/imagenes/EscenarioCarrera.png")).getImage();
    }

    /**
     * Método sobrescrito de JPanel para pintar el componente.
     * Se dibuja la imagen de fondo ajustada al tamaño actual del panel.
     * 
     * @param g objeto Graphics que permite el dibujo sobre el componente
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar la imagen de fondo escalada al tamaño del panel
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }
}