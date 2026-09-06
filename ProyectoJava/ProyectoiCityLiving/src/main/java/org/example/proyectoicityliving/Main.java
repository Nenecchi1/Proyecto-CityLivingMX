package org.example.proyectoicityliving;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.proyectoicityliving.controller.LoginController;
import org.example.proyectoicityliving.views.LoginVista;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 1. Instanciar la Vista
        // Le pasamos el primaryStage para que la vista pueda configurar la ventana
        LoginVista vista = new LoginVista(primaryStage);

        // 2. Instanciar el Controlador (Inyección de Dependencias)
        // Al pasarle la vista, el controlador conecta los botones con la lógica.
        // No es necesario guardar el controlador en una variable aquí,
        // ya que sus referencias de memoria se mantienen vivas por los eventos (setOnAction).
        new LoginController(vista);
    }

    public static void main(String[] args) {
        // launch() es un método estático heredado de Application que inicia el ciclo de vida de JavaFX
        launch(args);
    }
}
