package org.example.proyectoicityliving.views;

import javafx.stage.Stage;

/**
 * Clase base abstracta para todas las vistas de la aplicación.
 * Permite compartir el Stage y configuraciones comunes.
 */
public abstract class VistaBase {
    protected Stage stage;

    public VistaBase(Stage stage) {
        this.stage = stage;
    }
    
    public Stage getStage() {
        return stage;
    }
}
