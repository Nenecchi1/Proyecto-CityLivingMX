package org.example.proyectoicityliving.exception;
/**
 * Excepción lanzada cuando las credenciales ingresadas no cumplen con los
 * patrones de Expresiones Regulares (Regex) establecidos para correo o contraseña.
 * <p>
 * Apoya el pilar de Usabilidad de ISO/IEC 25010 al validar sintaxis en el cliente
 * antes de realizar consultas de lectura I/O.
 * </p>
 *
 * @author Polanco Romero Erick
 * @version 1.0
 */
public class FormatoInvalidoException extends Exception {
    public FormatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}