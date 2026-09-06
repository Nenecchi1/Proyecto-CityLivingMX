package org.example.proyectoicityliving.model;

/**
 * Representa a un usuario estándar con perfil dual (Arrendador / Arrendatario).
 *
 * @author Polanco Romero Erick
 * @version 1.1
 */
public class UsuarioGeneral extends Usuario {

    /**
     * Construye una instancia de UsuarioGeneral asignando el ID y credenciales.
     *
     * @param id Identificador único numérico.
     * @param correo Correo electrónico.
     * @param contrasena Contraseña de acceso.
     * @param nombre Nombre completo.
     */
    public UsuarioGeneral(int id, String correo, String contrasena, String nombre) {
        super(id, correo, contrasena, nombre);
    }
    /**
     * Mètodo abstracto get para visualizar el rol del usuario
     * @return cadena de texto
     */
    @Override
    public String getRol() {
        return "Usuario";
    }
}