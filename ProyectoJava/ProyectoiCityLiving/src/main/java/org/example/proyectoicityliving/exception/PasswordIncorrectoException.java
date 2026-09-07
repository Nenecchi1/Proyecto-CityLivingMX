package org.example.proyectoicityliving.exception;

/**
 * Excepción lanzada cuando el correo existe en la persistencia pero la contraseña
 * ingresada no coincide con el registro.
 * <p>
 * Permite al controlador identificar un intento fallido legítimo para
 * incrementar el contador de intentos y eventualmente detonar {@link CuentaBloqueadaException}.
 * </p>
 *
 * @author Polanco Romero Erick
 * @version 1.0
 */
public class PasswordIncorrectoException extends Exception {

    /**
     * Construye una nueva excepción al fallar la verificación de la contraseña.
     *
     * @param mensaje Notificación del fallo de coincidencia de contraseña.
     */
    public PasswordIncorrectoException(String mensaje) {
        super(mensaje);
    }

    /**
     * Construye una nueva excepción con mensaje explicativo y causa original.
     *
     * @param mensaje Detalle de la inconsistencia de contraseña.
     * @param causa   Excepción previa si existiese.
     */
    public PasswordIncorrectoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
