package org.example.proyectocityliving.DAO;

import java.io.IOException;
import org.example.proyectocityliving.Model.Usuario;

/**
 * Interface de Base de Datos para desacoplar el modelo del controlador
 * @author Polanco Romero Erick
 */
public interface IUsuarioDAO {
    /**
     * Mètodo para verificar o crear Base de Datos
     * @throws IOException para advertir que no se puede crear o verficar la BD
     */
    void verificarOCrearArchivo() throws IOException;

    /**
     * Mètodo para buscar usuario por correo en la Base de Datos
     * @param correo del usuario que desea buscar
     * @return
     * @throws IOException
     */
    Usuario buscarPorCorreo(String correo) throws IOException;

    /**
     * Mètodo para validar credenciales de usuario
     * @param correo
     * @param contrasena
     * @return
     * @throws IOException
     */
    boolean validarCredenciales(String correo, String contrasena) throws IOException;
}