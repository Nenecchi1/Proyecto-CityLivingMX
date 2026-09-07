package org.example.proyectoicityliving.controller;

import javafx.stage.Stage;
import org.example.proyectoicityliving.exception.PersistenciaException;
import org.example.proyectoicityliving.exception.UsuarioNoEncontradoException;
import org.example.proyectoicityliving.service.IUsuarioService;
import org.example.proyectoicityliving.views.AdministradorVista;
import org.example.proyectoicityliving.views.ILoginVista;
import org.example.proyectoicityliving.views.LoginVista;

/**
 * Controlador para el panel de administración y atención en Mesa de Ayuda[cite: 22].
 * <p>
 * Gestiona el restablecimiento de cuentas de usuario suspendidas y el cierre
 * de sesión dentro del contexto administrativo[cite: 22].
 * </p>
 *
 * @author Polanco Romero Erick
 * @author Jardines Bandala Luis Antonio
 * @version 2.8
 */
public class AdministradorController {

    /** Vista gráfica del panel de administración[cite: 22]. */
    private final AdministradorVista vista;

    /** Servicio de lógica de negocio e interacción con datos[cite: 22]. */
    private final IUsuarioService usuarioService;

    /**
     * Construye e inicializa el controlador del panel de administración[cite: 22].
     *
     * @param vista          Instancia de la vista administrativa {@link AdministradorVista}[cite: 22].
     * @param usuarioService Servicio de gestión de usuarios {@link IUsuarioService}[cite: 22].
     */
    public AdministradorController(AdministradorVista vista, IUsuarioService usuarioService) {
        this.vista = vista;
        this.usuarioService = usuarioService;
        inicializarEventos();
    }

    /**
     * Configura los controladores de eventos para los componentes interactivos de la vista[cite: 22].
     */
    private void inicializarEventos() {
        this.vista.getBotonDesbloquearCuenta().setOnAction(e -> desbloquearCuenta());
        this.vista.getBotonCerrarSesion().setOnAction(e -> cerrarSesion());
    }

    /**
     * Procesa la solicitud de desbloqueo para la cuenta seleccionada en la lista[cite: 22].
     * <p>
     * Invoca la capa de servicio para restablecer el acceso y actualiza la interfaz
     * ante respuestas exitosas o excepciones[cite: 22].
     * </p>
     */
    private void desbloquearCuenta() {
        String correoSeleccionado = vista.getListaCuentasBloqueadas().getSelectionModel().getSelectedItem();
        if (correoSeleccionado != null) {
            try {
                usuarioService.desbloquearCuenta(correoSeleccionado);
                vista.getListaCuentasBloqueadas().getItems().remove(correoSeleccionado);
                vista.mostrarMensaje("La cuenta " + correoSeleccionado + " ha sido desbloqueada con éxito.");
            } catch (UsuarioNoEncontradoException | PersistenciaException e) {
                vista.mostrarError("Error al desbloquear la cuenta: " + e.getMessage());
            }
        }
    }

    /**
     * Finaliza la sesión administrativa actual y retorna a la vista de inicio de sesión[cite: 22].
     */
    private void cerrarSesion() {
        Stage stage = vista.getStage();
        ILoginVista loginVista = new LoginVista(stage);
        new LoginController(loginVista, usuarioService);
    }
}