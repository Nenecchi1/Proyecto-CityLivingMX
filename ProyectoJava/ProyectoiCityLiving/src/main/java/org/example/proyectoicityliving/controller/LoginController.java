package org.example.proyectoicityliving.controller;

import javafx.stage.Stage;
import org.example.proyectoicityliving.exception.PasswordIncorrectoException;
import org.example.proyectoicityliving.exception.PersistenciaException;
import org.example.proyectoicityliving.exception.UsuarioNoEncontradoException;
import org.example.proyectoicityliving.model.IUsuario;
import org.example.proyectoicityliving.service.IUsuarioService;
import org.example.proyectoicityliving.views.AdministradorVista;
import org.example.proyectoicityliving.views.ILoginVista;
import org.example.proyectoicityliving.views.RegistroVista;
import org.example.proyectoicityliving.views.UsuarioVista;

/**
 * Controlador encargado de coordinar el proceso de inicio de sesión.
 * <p>
 * Vincula la interfaz {@link ILoginVista} con la capa {@link IUsuarioService},
 * ejecutando la validación sintáctica del dominio en el cliente antes de la consulta
 * en la capa de almacenamiento y redirigiendo hacia el panel correspondiente.
 * </p>
 *
 * @author Polanco Romero Erick
 * @author Jardines Bandala Luis Antonio
 * @version 3.0
 */
public class LoginController {

    /** Expresión regular para validar el formato de correo con el dominio institucional. */
    private static final String REGEX_CORREO_DOMINIO = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    /** Vista de inicio de sesión. */
    private final ILoginVista vista;

    /** Servicio de autenticación y lógica de negocio. */
    private final IUsuarioService usuarioService;

    /**
     * Construye e inicializa el controlador de inicio de sesión vinculando eventos de la vista.
     *
     * @param vista          Instancia de la vista que implementa {@link ILoginVista}.
     * @param usuarioService Servicio de negocio que implementa {@link IUsuarioService}.
     */
    public LoginController(ILoginVista vista, IUsuarioService usuarioService) {
        this.vista = vista;
        this.usuarioService = usuarioService;
        inicializarEventos();
    }

    /**
     * Asigna las acciones de los botones a sus métodos correspondientes.
     */
    private void inicializarEventos() {
        this.vista.getBotonLogin().setOnAction(e -> procesarAutenticacion());
        this.vista.getLinkRegistro().setOnAction(e -> abrirRegistro());
    }

    /**
     * Captura las credenciales, valida previamente la sintaxis y el dominio del correo en el cliente,
     * e invoca la autenticación en el servicio solo si el formato es correcto.
     */
    public void procesarAutenticacion() {
        String correo = vista.getCorreo();
        String contrasena = vista.getContrasena();

        if (correo.isEmpty() || contrasena.isEmpty()) {
            vista.mostrarError("Por favor, ingrese su correo y contraseña.");
            return;
        }

        if (!correo.matches(REGEX_CORREO_DOMINIO)) {
            vista.mostrarError("Dominio o formato de correo incorrecto. Formato esperado: usuario@cityliving.mx");
            return;
        }

        try {
            IUsuario usuarioAutenticado = usuarioService.autenticar(correo, contrasena);
            vista.mostrarBienvenida(usuarioAutenticado.getNombre(), usuarioAutenticado.getRol());

            Stage stage = vista.getStage();
            String rol = usuarioAutenticado.getRol();

            if (rol != null && (rol.equalsIgnoreCase("ADMINISTRADOR") || rol.equalsIgnoreCase("ADMIN"))) {
                AdministradorVista adminVista = new AdministradorVista(stage);
                adminVista.setNombreAdministrador(usuarioAutenticado.getNombre());
                new AdministradorController(adminVista, usuarioService);
            } else {
                UsuarioVista usuarioVista = new UsuarioVista(stage);
                usuarioVista.setNombreUsuario(usuarioAutenticado.getNombre());
                new UsuarioController(usuarioVista, usuarioService, usuarioAutenticado);
            }

        } catch (UsuarioNoEncontradoException | PasswordIncorrectoException e) {
            vista.mostrarError(e.getMessage());

            if (e.getMessage().toLowerCase().contains("bloqueada")) {
                vista.inhabilitarVista();
            }

        } catch (PersistenciaException e) {
            vista.mostrarError("Error crítico de almacenamiento: " + e.getMessage());
        }
    }

    /**
     * Redirige la navegación a la pantalla de registro e inicializa su controlador.
     */
    private void abrirRegistro() {
        Stage stage = vista.getStage();
        RegistroVista registroVista = new RegistroVista(stage);
        new RegistroController(registroVista, usuarioService);
    }
}