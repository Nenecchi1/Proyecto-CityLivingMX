package org.example.proyectoicityliving;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.proyectoicityliving.controller.LoginController;
import org.example.proyectoicityliving.dao.IUsuarioDAO;
import org.example.proyectoicityliving.dao.UsuarioTXT;
import org.example.proyectoicityliving.service.IUsuarioService;
import org.example.proyectoicityliving.service.UsuarioService; // Cambia por UsuarioServiceImpl si tu clase implementa esa nomenclatura
import org.example.proyectoicityliving.views.ILoginVista;
import org.example.proyectoicityliving.views.LoginVista;

/**
 * Punto de entrada principal de la aplicación City Living MX.
 * <p>
 * Inicializa el entorno gráfico JavaFX, orquestando la vista de inicio de sesión,
 * la capa de servicios y el controlador correspondiente.
 * </p>
 *
 * @author Polanco Romero Erick
 * @author Jardines Bandala Luis Antonio
 * @version 2.5
 */
public class Main extends Application {

    /**
     * Arranca el ciclo de vida de la interfaz gráfica JavaFX.
     *
     * @param primaryStage Escenario principal proporcionado por el runtime de JavaFX.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);

        // 1. Instanciar el acceso a datos (DAO)
        IUsuarioDAO usuarioDAO = new UsuarioTXT();

        // 2. Instanciar el servicio inyectando el DAO
        IUsuarioService usuarioService = new UsuarioService(usuarioDAO);

        // 3. Instanciar la vista asignando el escenario principal
        ILoginVista loginVista = new LoginVista(primaryStage);

        // 4. Vincular el controlador con la vista y el servicio
        new LoginController(loginVista, usuarioService);
    }

    /**
     * Método de lanzamiento estándar en Java.
     *
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args);
    }
}