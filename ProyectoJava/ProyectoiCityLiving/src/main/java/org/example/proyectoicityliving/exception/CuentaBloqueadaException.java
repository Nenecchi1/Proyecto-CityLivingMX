package org.example.proyectoicityliving.exception;

/**
 * Excepción de seguridad lanzada al acumular 3 intentos fallidos consecutivos
 * de autenticación.
 * <p>
 * Implementa la protección contra ataques de fuerza bruta contemplada en el
 * criterio de Seguridad de la norma ISO/IEC 25010, inhabilitando la interfaz
 * y remitiendo al Help Desk.
 * </p>
 *
 * @author Polanco Romero Erick
 * @version 1.0
 */
public class CuentaBloqueadaException extends Exception {
    public CuentaBloqueadaException(String mensaje) {
        super(mensaje);
    }
}