package org.example.proyectoicityliving.model;

/**
 * Representa un usuario con privilegios administrativos para la gestión del sistema y Mesa de Ayuda.
 *
 * @author Polanco Romero Erick
 * @version 1.1
 */
public class Administrador extends Usuario {

    /**
     * Construye una instancia de Administrador asignando el ID y credenciales.
     *
     * @param id Identificador único numérico.
     * @param correo Correo electrónico.
     * @param contrasena Contraseña de acceso.
     * @param nombre Nombre completo.
     */
    public Administrador(int id, String correo, String contrasena, String nombre) {
        super(id, correo, contrasena, nombre);
    }
    /**
     * Mètodo abstracto get para visualizar el rol del usuario
     * @return cadena de texto
     */
    @Override
    public String getRol() {
        return "Administrador";
    }

    /**
     * Permite restablecer el acceso a una cuenta bloqueada por seguridad.
     *
     * @param correoUsuario Correo de la cuenta a habilitar.
     */
    public void desbloquearCuenta(String correoUsuario) {
        System.out.println("[HELP DESK] Acceso restablecido para la cuenta: " + correoUsuario);
    }
}