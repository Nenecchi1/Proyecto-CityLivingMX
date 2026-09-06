package org.example.proyectoicityliving.DAO;

import java.io.File;
import java.io.IOException;
import org.example.proyectoicityliving.model.IUsuario;

/**
 * Interface de Base de Datos para desacoplar el modelo del controlador
 * @author Polanco Romero Erick
 */
public interface IUsuarioDAO {
    /**
     * Mètodo para verificar o crear Base de Datos
     * @throws IOException para advertir que no se puede crear o verficar la BD
     */
    File verificarOCrearArchivo() throws IOException;

    /**
     * Mètodo para buscar usuario por correo en la Base de Datos
     * @param correo del usuario que desea buscar
     * @return
     * @throws IOException
     */
    IUsuario buscarPorCorreo(String correo) throws IOException;

    /**
     * Mètodo para validar credenciales de usuario
     * @param correo
     * @param contrasena
     * @return
     * @throws IOException
     */
    boolean validarCredenciales (String correo, String contrasena) throws IOException;
    /**
     * Mètodo para registrar usuario
     * @param correo
     * @param contrasena
     * @return
     * @throws IOException
     */
    void registrarUsuario(IUsuario usuario) throws IOException;
}