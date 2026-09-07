package org.example.proyectoicityliving.controller;

import javafx.stage.Stage;
import org.example.proyectoicityliving.exception.FormatoInvalidoException;
import org.example.proyectoicityliving.exception.PersistenciaException;
import org.example.proyectoicityliving.model.IUsuario;
import org.example.proyectoicityliving.model.UsuarioGeneral;
import org.example.proyectoicityliving.service.IUsuarioService;
import org.example.proyectoicityliving.views.ILoginVista;
import org.example.proyectoicityliving.views.LoginVista;
import org.example.proyectoicityliving.views.RegistroVista;

/**
 * Controlador encargado de gestionar la vista de registro de usuarios.
 * <p>
 * Intercepta los eventos del formulario y evalúa secuencialmente la integridad de las entradas:
 * valida primeramente que ningún campo quede vacío (evaluando el prefijo real del correo),
 * verifica que el nombre contenga únicamente letras y espacios, descarta el uso del símbolo '@'
 * en la casilla de usuario y confirma la igualdad de contraseñas previo al registro.
 * </p>
 *
 * @author Polanco Romero Erick
 * @author Jardines Bandala Luis Antonio
 * @version 3.2
 */
public class RegistroController {

    /** Expresión regular para validar caracteres permitidos en el usuario de correo sin '@'. */
    private static final String REGEX_USUARIO_PREFIJO = "^[a-zA-Z0-9._%+-]+$";

    /** Expresión regular para validar el nombre (letras mayúsculas, minúsculas, acentos y espacios). */
    private static final String REGEX_NOMBRE = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$";

    /** Vista gráfica del formulario de registro. */
    private final RegistroVista vista;

    /** Servicio de gestión de usuarios. */
    private final IUsuarioService usuarioService;

    /**
     * Construye e inicializa el controlador de registro.
     *
     * @param vista          Vista del formulario de registro {@link RegistroVista}.
     * @param usuarioService Servicio de datos para el almacenamiento {@link IUsuarioService}.
     */
    public RegistroController(RegistroVista vista, IUsuarioService usuarioService) {
        this.vista = vista;
        this.usuarioService = usuarioService;
        inicializarEventos();
    }

    /**
     * Asigna los manejadores de eventos a los botones de la vista.
     */
    private void inicializarEventos() {
        this.vista.getBotonRegistrar().setOnAction(e -> registrarUsuario());
        this.vista.getBotonVolverLogin().setOnAction(e -> volverLogin());
    }

    /**
     * Procesa la solicitud de registro verificando estricta y secuencialmente:
     * 1. Presencia de datos en todos los campos (evaluando el prefijo del correo sin el dominio).
     * 2. Formato del nombre (solo alfabético y espacios).
     * 3. Ausencia del carácter '@' y caracteres inválidos en la casilla de correo.
     * 4. Coincidencia entre contraseñas.
     * 5. Cumplimiento de la regla de contraseña RS-02 delegada a la capa de servicios.
     */
    private void registrarUsuario() {
        String nombre = vista.getNombre();
        String correoCompleto = vista.getCorreo();
        String contrasena = vista.getContrasena();
        String confirmarContrasena = vista.getConfirmarContrasena();

        // Extraer la entrada real introducida por el usuario en el campo de correo
        String prefijoUsuario = correoCompleto.replace("@cityliving.mx", "").trim();

        // 1. Verificar obligatoriedad de TODOS los campos antes de cualquier validación sintáctica
        if (nombre.isEmpty() || prefijoUsuario.isEmpty() || contrasena.isEmpty() || confirmarContrasena.isEmpty()) {
            vista.mostrarMensaje("Todos los campos son obligatorios.", true);
            return;
        }

        // 2. Validar formato del nombre (solo letras y espacios)
        if (!nombre.matches(REGEX_NOMBRE)) {
            vista.mostrarMensaje("El nombre solo debe contener letras y espacios.", true);
            return;
        }

        // 3. Validar que la casilla de usuario no contenga '@' ni caracteres no permitidos
        if (prefijoUsuario.contains("@") || !prefijoUsuario.matches(REGEX_USUARIO_PREFIJO)) {
            vista.mostrarMensaje("El correo no debe incluir '@' ni caracteres inválidos (el dominio @cityliving.mx se agrega automáticamente).", true);
            return;
        }

        // 4. Verificar coincidencia entre ambas contraseñas
        if (!contrasena.equals(confirmarContrasena)) {
            vista.mostrarMensaje("Las contraseñas no coinciden.", true);
            return;
        }

        // 5. Proceder al registro delegando el protocolo de seguridad RS-02 a la capa de negocio
        try {
            IUsuario nuevoUsuario = new UsuarioGeneral(0, correoCompleto, contrasena, nombre);
            usuarioService.registrarNuevoUsuario(nuevoUsuario);

            vista.mostrarMensaje("¡Usuario registrado exitosamente!", false);
            vista.limpiarCampos();

        } catch (FormatoInvalidoException e) {
            vista.mostrarMensaje(e.getMessage(), true);
        } catch (PersistenciaException e) {
            vista.mostrarMensaje("Error en el registro: " + e.getMessage(), true);
        }
    }

    /**
     * Cancela la operación de registro y regresa a la ventana de inicio de sesión.
     */
    private void volverLogin() {
        Stage stage = vista.getStage();
        ILoginVista loginVista = new LoginVista(stage);
        new LoginController(loginVista, usuarioService);
    }
}