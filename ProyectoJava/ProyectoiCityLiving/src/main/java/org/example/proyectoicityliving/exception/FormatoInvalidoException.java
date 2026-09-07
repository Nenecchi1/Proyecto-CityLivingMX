package org.example.proyectoicityliving.exception;

/**
 * Excepción personalizada para notificar el incumplimiento de las reglas
 * de formato en credenciales (RS-01 y RS-02)[cite: 27].
 *
 * @author Polanco Romero Erick
 * @version 1.3
 */
public class FormatoInvalidoException extends Exception {

    /**
     * Construye la excepción con un mensaje explicativo del fallo de formato.
     *
     * @param mensaje Detalle del requisito de seguridad no cumplido.
     */
    public FormatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}