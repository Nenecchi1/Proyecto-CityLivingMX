package org.example.proyectoicityliving.dao;

import java.io.File;
import java.util.List;
import org.example.proyectoicityliving.exception.PasswordIncorrectoException;
import org.example.proyectoicityliving.exception.PersistenciaException;
import org.example.proyectoicityliving.exception.UsuarioNoEncontradoException;
import org.example.proyectoicityliving.model.IUsuario;

/**
 * Interfaz para la capa de acceso a datos de usuarios en la plataforma City Living MX.
 * <p>
 * Aplica el principio de Inversión de Dependencias (DIP) abstraendo la tecnología
 * de almacenamiento persistente para gestionar la información de los usuarios.
 * </p>
 *
 * @author Polanco Romero Erick
 * @version 1.5
 */
public interface IUsuarioDAO {

    /**
     * Verifica la existencia del recurso de almacenamiento local y lo crea si no existe.
     *
     * @return Objeto {@link File} que representa el recurso de persistencia.
     * @throws PersistenciaException Si ocurre una falla I/O durante la verificación o creación.
     */
    File verificarOCrearArchivo() throws PersistenciaException;

    /**
     * Recupera la lista completa de usuarios registrados en la persistencia local.
     *
     * @return Lista con todas las instancias de {@link IUsuario}.
     * @throws PersistenciaException Si ocurre un fallo de lectura en el archivo de datos.
     */
    List<IUsuario> obtenerTodos() throws PersistenciaException;

    /**
     * Localiza una cuenta de usuario a partir de su dirección de correo electrónico.
     *
     * @param correo Correo electrónico que sirve como identificador de acceso.
     * @return Instancia de {@link IUsuario} correspondiente al correo proporcionado.
     * @throws UsuarioNoEncontradoException Si el correo no existe en el sistema.
     * @throws PersistenciaException        Si ocurre una falla I/O o corrupción de datos.
     */
    IUsuario buscarPorCorreo(String correo) throws UsuarioNoEncontradoException, PersistenciaException;

    /**
     * Valida las credenciales de un usuario comparando el correo y la contraseña ingresados.
     *
     * @param correo     Correo electrónico de la cuenta a validar.
     * @param contrasena Contraseña provista para la verificación.
     * @return {@code true} si las credenciales coinciden correctamente.
     * @throws UsuarioNoEncontradoException Si la cuenta especificada no está registrada.
     * @throws PasswordIncorrectoException  Si la contraseña proporcionada es errónea.
     * @throws PersistenciaException        Si ocurre un fallo de lectura en el archivo.
     */
    boolean validarCredenciales(String correo, String contrasena)
            throws UsuarioNoEncontradoException, PasswordIncorrectoException, PersistenciaException;

    /**
     * Almacena una nueva entidad de usuario en el registro de persistencia.
     *
     * @param usuario Instancia de {@link IUsuario} que contiene los datos del nuevo registro.
     * @throws PersistenciaException Si se produce una falla I/O durante la escritura.
     */
    void registrarUsuario(IUsuario usuario) throws PersistenciaException;

    /**
     * Sobrescribe los datos de un usuario existente para actualizar su información o estado.
     *
     * @param usuario Instancia de {@link IUsuario} con los datos modificados.
     * @throws PersistenciaException Si el usuario no existe o se produce una falla I/O.
     */
    void actualizarUsuario(IUsuario usuario) throws PersistenciaException;

    /**
     * Restablece la cuenta de un usuario desbloqueándola y reiniciando sus intentos fallidos.
     *
     * @param correo Dirección de correo electrónico de la cuenta a habilitar.
     * @throws UsuarioNoEncontradoException Si el correo especificado no se encuentra registrado.
     * @throws PersistenciaException        Si se produce una falla I/O durante la actualización.
     */
    void desbloquear(String correo) throws UsuarioNoEncontradoException, PersistenciaException;
}