package org.example.proyectoicityliving.views;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

/**
 * Contrato de la vista de Login. 
 * Permite que el Controller no dependa directamente de JavaFX.
 */
public interface ILoginVista {
    
    // Obtención de datos
    String getCorreo();
    String getContrasena();
    
    // Retroalimentación al usuario
    void mostrarError(String mensaje);
    void mostrarBienvenida(String nombre, String rol);
    void inabilitarVista(); // Se respeta la sintaxis original del controlador
    
    // Controles para eventos
    Button getBotonLogin();
    Hyperlink getLinkRegistro();
    Stage getStage();
}
