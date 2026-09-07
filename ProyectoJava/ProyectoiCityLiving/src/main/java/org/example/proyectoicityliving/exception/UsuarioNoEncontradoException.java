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
 * @version 1.2
 */
public class UsuarioNoEncontradoException extends Exception {

    /**
     * Construye una nueva excepción con el mensaje del usuario no localizado.
     *
     * @param mensaje Notificación de la inexistencia del correo en el almacén de datos.
     */
    public UsuarioNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    /**
     * Construye una nueva excepción con mensaje explicativo y causa raíz.
     *
     * @param mensaje Mensaje descriptivo.
     * @param causa   Excepción original.
     */
    public UsuarioNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}