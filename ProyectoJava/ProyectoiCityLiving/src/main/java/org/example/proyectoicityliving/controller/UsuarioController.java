package org.example.proyectoicityliving.controller;

import javafx.stage.Stage;
import org.example.proyectoicityliving.model.IUsuario;
import org.example.proyectoicityliving.service.IUsuarioService;
import org.example.proyectoicityliving.views.CatalogoVista;
import org.example.proyectoicityliving.views.ILoginVista;
import org.example.proyectoicityliving.views.LoginVista;
import org.example.proyectoicityliving.views.UsuarioVista;

/**
 * Controlador para la interacción con la interfaz gráfica del portal principal del cliente[cite: 26].
 *
 * @author Jardines Bandala Luis Antonio
 * @version 1.8
 */
public class UsuarioController {

    /** Vista del portal de usuario[cite: 26]. */
    private final UsuarioVista vista;

    /** Servicio de lógica de negocio[cite: 26]. */
    private final IUsuarioService usuarioService;

    /** Datos del usuario autenticado en sesión activa[cite: 26]. */
    private final IUsuario usuarioAutenticado;

    /**
     * Construye el controlador asociando la vista del portal con la sesión activa[cite: 26].
     *
     * @param vista              Vista interactiva del usuario[cite: 26].
     * @param usuarioService     Servicio de lógica de negocio[cite: 26].
     * @param usuarioAutenticado Datos del usuario en sesión[cite: 26].
     */
    public UsuarioController(UsuarioVista vista, IUsuarioService usuarioService, IUsuario usuarioAutenticado) {
        this.vista = vista;
        this.usuarioService = usuarioService;
        this.usuarioAutenticado = usuarioAutenticado;
        inicializarEventos();
    }

    /**
     * Configura los oyentes de eventos de la vista[cite: 26].
     */
    private void inicializarEventos() {
        this.vista.getBotonVerCatálogo().setOnAction(e -> abrirCatalogo());
        if (this.vista.getBotonBuscar() != null) {
            this.vista.getBotonBuscar().setOnAction(e -> filtrarBusqueda());
        }
        this.vista.getBotonCerrarSesion().setOnAction(e -> cerrarSesion());
    }

    /**
     * Navega a la vista independiente del catálogo[cite: 26].
     */
    private void abrirCatalogo() {
        Stage stage = vista.getStage();
        CatalogoVista catalogoVista = new CatalogoVista(stage);
        new CatalogoController(catalogoVista, usuarioService, usuarioAutenticado);
    }

    /**
     * Ejecuta la búsqueda o filtrado de propiedades dentro del panel[cite: 26].
     */
    private void filtrarBusqueda() {
        String texto = vista.getTextoBusqueda();
        String filtroTipo = vista.getFiltroTipoSeleccionado();
    }

    /**
     * Cierra la sesión activa y retorna a la pantalla de inicio de sesión[cite: 26].
     */
    private void cerrarSesion() {
        Stage stage = vista.getStage();
        ILoginVista loginVista = new LoginVista(stage);
        new LoginController(loginVista, usuarioService);
    }
}