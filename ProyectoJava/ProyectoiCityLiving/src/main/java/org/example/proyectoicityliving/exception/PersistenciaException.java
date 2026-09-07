package org.example.proyectoicityliving.exception;

/**
 * Excepción de la capa de datos que encapsula fallos técnicos de entrada/salida (I/O)
 * al interactuar con el archivo de almacenamiento {@code usuarios.txt}.
 * <p>
 * Garantiza el desacoplamiento al no propagar clases nativas como {@link java.io.IOException}
 * a capas superiores, cumpliendo con la mantenibilidad de ISO/IEC 25010.
 * </p>
 *
 * @author Polanco Romero Erick
 * @version 1.0
 */
public class PersistenciaException extends Exception {

    /**
     * Construye una nueva excepción con un mensaje del error en el almacenamiento.
     *
     * @param mensaje Descripción del problema al acceder o escribir el archivo.
     */
    public PersistenciaException(String mensaje) {
        super(mensaje);
    }

    /**
     * Construye una nueva excepción envolviendo la falla de I/O original.
     *
     * @param mensaje Mensaje explicativo para el usuario/sistema.
     * @param causa   Excepción nativa origen (ej. IOException).
     */
    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}