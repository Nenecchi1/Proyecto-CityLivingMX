package org.example.proyectoicityliving.model;

/**
 * Representa a un cliente con perfil dual (Arrendador / Arrendatario)[cite: 27, 33].
 *
 * @author Polanco Romero Erick
 * @author Jardines Bandala Luis Antonio
 * @version 1.5
 */
public class UsuarioGeneral extends Usuario {

    public UsuarioGeneral(int id, String correo, String contrasena, String nombre) {
        super(id, correo, contrasena, nombre);
    }

    public UsuarioGeneral(int id, String correo, String contrasena, String nombre, int intentosFallidos, boolean bloqueado) {
        super(id, correo, contrasena, nombre, intentosFallidos, bloqueado);
    }

    @Override
    public String getRol() {
        return "Usuario";
    }
}