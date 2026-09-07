package org.example.proyectoicityliving.model;

/**
 * Representa a un usuario administrativo con facultades sobre la Mesa de Ayuda[cite: 27, 30].
 *
 * @author Polanco Romero Erick
 * @author Jardines Bandala Luis Antonio
 * @version 1.5
 */
public class Administrador extends Usuario {

    public Administrador(int id, String correo, String contrasena, String nombre) {
        super(id, correo, contrasena, nombre);
    }

    public Administrador(int id, String correo, String contrasena, String nombre, int intentosFallidos, boolean bloqueado) {
        super(id, correo, contrasena, nombre, intentosFallidos, bloqueado);
    }

    @Override
    public String getRol() {
        return "Administrador";
    }

    /**
     * Restablece el acceso de una cuenta delegando la operación al método del modelo[cite: 30].
     *
     * @param usuario Instancia de {@link IUsuario} a desbloquear[cite: 30].
     */
    public void desbloquearCuenta(IUsuario usuario) {
        if (usuario != null) {
            usuario.desbloquear();
        }
    }
}