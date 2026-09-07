package org.example.proyectoicityliving.views;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

/**
 * Contrato de la vista de Control de Accesos (Login).
 * <p>
 * Desacopla el controlador {@code LoginController} de la implementación concreta
 * de la interfaz gráfica JavaFX.
 * </p>
 *
 * @author Jardines Bandala Luis Antonio
 * @version 1.2
 */
public interface ILoginVista {

    /**
     * Obtiene el correo electrónico capturado en la interfaz sin espacios periféricos.
     *
     * @return Cadena con el correo ingresado.
     */
    String getCorreo();

    /**
     * Obtiene la contraseña capturada desde el campo activo (enmascarado o visible).
     *
     * @return Cadena con la contraseña ingresada.
     */
    String getContrasena();

    /**
     * Despliega un mensaje de error o advertencia en la interfaz.
     *
     * @param mensaje Texto explicativo del fallo.
     */
    void mostrarError(String mensaje);

    /**
     * Despliega la confirmación de autenticación exitosa indicando el usuario y su rol.
     *
     * @param nombreNombre Nombre completo del usuario autenticado.
     * @param rolUsuario   Rol asignado en el sistema.
     */
    void mostrarBienvenida(String nombreNombre, String rolUsuario);

    /**
     * Inhabilita todos los controles interactivos de la vista por motivos de seguridad.
     */
    void inhabilitarVista();

    /**
     * Habilita nuevamente los controles de la interfaz y limpia las entradas.
     */
    void habilitarVista();

    /**
     * Obtiene la referencia al botón de inicio de sesión.
     *
     * @return Objeto {@link Button} de Login.
     */
    Button getBotonLogin();

    /**
     * Obtiene la referencia al enlace de registro.
     *
     * @return Objeto {@link Hyperlink} de Registro.
     */
    Hyperlink getLinkRegistro();

    /**
     * Obtiene el escenario JavaFX de la ventana.
     *
     * @return Instancia de {@link Stage}.
     */
    Stage getStage();
}