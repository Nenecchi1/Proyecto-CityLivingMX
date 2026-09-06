package org.example.proyectoicityliving.exception;

/**
 * Excepción lanzada cuando el correo electrónico proporcionado no existe
 * en el archivo de persistencia {@code usuarios.txt}.
 * <p>
 * Permite al controlador interceptar la búsqueda fallida y coordinar la
 * redirección asistida hacia la vista de registro de usuario.
 * </p>
 *
 * @author Polanco Romero Erick
 * @version 1.0
 */
public class UsuarioNoEncontradoException extends Exception {
    public UsuarioNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}