/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.progAvUD.primerTaller2Corte.vista;

/**
 *
 * @author Andres Felipe
 */
import javax.swing.*;
import java.awt.*;

public class PanelConFondo extends JPanel {
    private Image imagenFondo;

    public PanelConFondo() {
        // Cargar la imagen de fondo
        imagenFondo = new ImageIcon(getClass().getResource("/edu/progAvUD/primerTaller2Corte/imagenes/EscenarioCarrera.png")).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dibujar la imagen de fondo
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }

    
}
