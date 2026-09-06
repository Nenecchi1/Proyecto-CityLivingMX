package org.example.proyectoicityliving.model;

/**
 * La interfaz usuario consolida el principio de Inversión de Dependencias
 * (DIP) de SOLID y fortalece la mantenibilidad
 * bajo la norma ISO/IEC 25010.
 * @version 1.0
 */
public interface IUsuario {
    /**
     * Get para visualizar el id del usuario
     * @return cadena de texto
     */
    int getId();
    /**
     * Get para visualizar el correo del usuario
     * @return cadena de texto
     */
    String getCorreo();
    /**
     * Get para visualizar la contraseña del usuario
     * @return cadena de texto
     */
    String getContrasena();
    /**
     * Get para visualizar el nombre del usuario
     * @return cadena de texto
     */
    String getNombre();
    /**
     * Mètodo abstracto get para visualizar el rol del usuario
     * @return cadena de texto
     */
    String getRol();
}