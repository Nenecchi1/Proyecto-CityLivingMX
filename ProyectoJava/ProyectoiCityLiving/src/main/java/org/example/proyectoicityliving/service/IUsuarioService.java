package org.example.proyectoicityliving.service;

import org.example.proyectoicityliving.exception.FormatoInvalidoException;
import org.example.proyectoicityliving.exception.PasswordIncorrectoException;
import org.example.proyectoicityliving.exception.PersistenciaException;
import org.example.proyectoicityliving.exception.UsuarioNoEncontradoException;
import org.example.proyectoicityliving.model.IUsuario;

/**
 * Interfaz de la capa de servicio que define el contrato para la gestión de usuarios,
 * autenticación, registro y control de accesos[cite: 27, 28].
 *
 * @author Polanco Romero Erick
 * @author Jardines Bandala Luis Antonio
 * @version 1.5
 */
public interface IUsuarioService {

    /**
     * Autentica a un usuario verificando sus credenciales y estado de bloqueo[cite: 27].
     *
     * @param correo     Correo electrónico de acceso[cite: 27].
     * @param contrasena Contraseña ingresada[cite: 27].
     * @return Instancia de {@link IUsuario} autenticada[cite: 27].
     * @throws UsuarioNoEncontradoException Si el correo no existe en el almacenamiento[cite: 27].
     * @throws PasswordIncorrectoException  Si la contraseña es errónea o la cuenta está bloqueada[cite: 27].
     * @throws PersistenciaException       Si ocurre una falla en el almacén local[cite: 27].
     */
    IUsuario autenticar(String correo, String contrasena)
            throws UsuarioNoEncontradoException, PasswordIncorrectoException, PersistenciaException;

    /**
     * Valida los requisitos de seguridad RS-01 y RS-02 y registra un nuevo usuario[cite: 27].
     *
     * @param usuario Instancia con los datos del nuevo usuario a registrar[cite: 27].
     * @throws FormatoInvalidoException Si el correo o la contraseña no cumplen las reglas de formato[cite: 27].
     * @throws PersistenciaException    Si el correo ya está registrado o falla el almacenamiento[cite: 27].
     */
    void registrarNuevoUsuario(IUsuario usuario) throws FormatoInvalidoException, PersistenciaException;

    /**
     * Restablece el acceso a una cuenta inhabilitada y reinicia el contador de intentos[cite: 27].
     *
     * @param correo Correo electrónico de la cuenta a restablecer[cite: 27].
     * @throws UsuarioNoEncontradoException Si el correo no está registrado[cite: 27].
     * @throws PersistenciaException       Si falla la reescritura en el almacenamiento[cite: 27].
     */
    void desbloquearCuenta(String correo) throws UsuarioNoEncontradoException, PersistenciaException;
}