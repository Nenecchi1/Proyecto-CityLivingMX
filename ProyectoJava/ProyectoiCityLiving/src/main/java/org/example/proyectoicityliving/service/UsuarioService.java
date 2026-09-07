package org.example.proyectoicityliving.service;

import java.util.List;
import org.example.proyectoicityliving.dao.IUsuarioDAO;
import org.example.proyectoicityliving.exception.FormatoInvalidoException;
import org.example.proyectoicityliving.exception.PasswordIncorrectoException;
import org.example.proyectoicityliving.exception.PersistenciaException;
import org.example.proyectoicityliving.exception.UsuarioNoEncontradoException;
import org.example.proyectoicityliving.model.IUsuario;
import org.example.proyectoicityliving.model.UsuarioGeneral;

/**
 * Implementación del servicio de gestión de usuarios y control de accesos para City Living MX.
 * <p>
 * Aplica la lógica de negocio para la autenticación, la validación de reglas de seguridad
 * (RS-01, RS-02 y RS-03) y la orquestación con la capa de almacenamiento.
 * </p>
 *
 * @author Polanco Romero Erick
 * @author Jardines Bandala Luis Antonio
 * @version 1.5
 */
public class UsuarioService implements IUsuarioService {

    /** Objeto para el acceso a datos de usuarios en la capa de persistencia. */
    private final IUsuarioDAO usuarioDAO;

    /** Expresión regular para validar la estructura estándar de correo electrónico (RS-01). */
    private static final String REGEX_CORREO = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    /**
     * Expresión regular para validar la complejidad de contraseña (RS-02):
     * Mínimo 8 caracteres, al menos 1 mayúscula, 1 minúscula, 1 número y 1 carácter especial (#, $, &).
     */
    private static final String REGEX_CONTRASENA = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$&]).{8,}$";

    /**
     * Construye e inicializa el servicio inyectando la abstracción DAO.
     *
     * @param usuarioDAO Implementación de la interfaz de acceso a datos {@link IUsuarioDAO}.
     */
    public UsuarioService(IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * Autentica a un usuario verificando sus credenciales, registrando intentos fallidos
     * y gestionando el estado de bloqueo de la cuenta.
     *
     * @param correo     Dirección de correo electrónico usada como credencial de acceso.
     * @param contrasena Clave de acceso ingresada por el usuario.
     * @return La instancia de {@link IUsuario} autenticada exitosamente.
     * @throws UsuarioNoEncontradoException Si la cuenta asociada al correo no existe.
     * @throws PasswordIncorrectoException  Si la contraseña es incorrecta o la cuenta está bloqueada.
     * @throws PersistenciaException       Si ocurre un error I/O al consultar o reescribir la persistencia.
     */
    @Override
    public IUsuario autenticar(String correo, String contrasena)
            throws UsuarioNoEncontradoException, PasswordIncorrectoException, PersistenciaException {

        IUsuario usuario = usuarioDAO.buscarPorCorreo(correo);

        if (usuario.esBloqueado()) {
            throw new PasswordIncorrectoException("La cuenta se encuentra BLOQUEADA. Contacte a la Mesa de Ayuda.");
        }

        if (!usuario.validarContrasena(contrasena)) {
            usuario.registrarIntentoFallido();
            usuarioDAO.actualizarUsuario(usuario);

            if (usuario.esBloqueado()) {
                throw new PasswordIncorrectoException("La cuenta ha sido BLOQUEADA tras acumular 3 intentos fallidos.");
            }
            throw new PasswordIncorrectoException("Contraseña incorrecta. Intento " + usuario.getIntentosFallidos() + " de 3.");
        }

        if (usuario.getIntentosFallidos() > 0) {
            usuario.reiniciarIntentos();
            usuarioDAO.actualizarUsuario(usuario);
        }

        return usuario;
    }

    /**
     * Registra un nuevo usuario verificando el cumplimiento de las reglas de formato RS-01 y RS-02,
     * evitando duplicados y asignando un identificador numérico único consecutivo.
     *
     * @param usuario Objeto {@link IUsuario} con los datos del nuevo usuario a registrar.
     * @throws FormatoInvalidoException Si el correo o la contraseña no cumplen las reglas sintácticas.
     * @throws PersistenciaException    Si el correo ya está registrado o se produce una falla I/O.
     */
    @Override
    public void registrarNuevoUsuario(IUsuario usuario) throws FormatoInvalidoException, PersistenciaException {
        if (usuario.getCorreo() == null || !usuario.getCorreo().matches(REGEX_CORREO)) {
            throw new FormatoInvalidoException("El correo debe tener un formato válido (ejemplo@dominio.com).");
        }

        if (usuario.getContrasena() == null || !usuario.getContrasena().matches(REGEX_CONTRASENA)) {
            throw new FormatoInvalidoException("La contraseña debe incluir mínimo 8 caracteres, al menos 1 mayúscula, 1 minúscula, 1 número y 1 especial (#, $, &).");
        }

        try {
            IUsuario existente = usuarioDAO.buscarPorCorreo(usuario.getCorreo());
            if (existente != null) {
                throw new PersistenciaException("El correo " + usuario.getCorreo() + " ya se encuentra registrado.");
            }
        } catch (UsuarioNoEncontradoException ignored) {
            // Flujo esperado cuando el correo no está registrado previa búsqueda
        }

        List<IUsuario> todos = usuarioDAO.obtenerTodos();
        int nuevoId = todos.stream()
                .mapToInt(IUsuario::getId)
                .max()
                .orElse(0) + 1;

        IUsuario nuevoUsuario = new UsuarioGeneral(
                nuevoId,
                usuario.getCorreo(),
                usuario.getContrasena(),
                usuario.getNombre()
        );

        usuarioDAO.registrarUsuario(nuevoUsuario);
    }

    /**
     * Restablece el acceso a una cuenta previamente bloqueada, reiniciando su contador de intentos fallidos.
     *
     * @param correo Dirección de correo electrónico de la cuenta a habilitar.
     * @throws UsuarioNoEncontradoException Si la cuenta no existe en el sistema.
     * @throws PersistenciaException       Si ocurre un error I/O durante la actualización.
     */
    @Override
    public void desbloquearCuenta(String correo) throws UsuarioNoEncontradoException, PersistenciaException {
        usuarioDAO.desbloquear(correo);
    }
}