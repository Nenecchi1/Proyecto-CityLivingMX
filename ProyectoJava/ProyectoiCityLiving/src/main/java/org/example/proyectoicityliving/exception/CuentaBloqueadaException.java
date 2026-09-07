package org.example.proyectoicityliving.exception;

/**
 * Excepción de seguridad lanzada al acumular 3 intentos fallidos consecutivos de autenticación.
 * <p>
 * Apoya el atributo de Seguridad de la norma ISO/IEC 25010 mediante la protección contra
 * ataques de fuerza bruta, inhabilitando la interfaz gráfica y remitiendo al Help Desk.
 * </p>
 *
 * @author Polanco Romero Erick
 * @version 1.2
 */
public class CuentaBloqueadaException extends Exception {

    /**
     * Construye una nueva excepción con un mensaje descriptivo específico.
     *
     * @param mensaje Mensaje explicativo de la razón del bloqueo.
     */
    public CuentaBloqueadaException(String mensaje) {
        super(mensaje);
    }

    /**
     * Construye una nueva excepción con un mensaje descriptivo y la causa raíz.
     *
     * @param mensaje Mensaje explicativo del error.
     * @param causa   Excepción original que desencadenó el fallo.
     */
    public CuentaBloqueadaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}