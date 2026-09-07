package org.example.proyectoicityliving.controller;

import javafx.stage.Stage;
import org.example.proyectoicityliving.model.IUsuario;
import org.example.proyectoicityliving.service.IUsuarioService;
import org.example.proyectoicityliving.views.CatalogoVista;
import org.example.proyectoicityliving.views.UsuarioVista;

/**
 * Controlador encagado del filtrado y navegación dentro del catálogo de inmuebles[cite: 23].
 *
 * @author Polanco Romero Erick
 * @author Jardines Bandala Luis Antonio
 * @version 2.8
 */
public class CatalogoController {

    /** Vista del catálogo gráfico[cite: 23]. */
    private final CatalogoVista vista;

    /** Servicio de lógica de negocio[cite: 23]. */
    private final IUsuarioService usuarioService;

    /** Usuario autenticado en la sesión activa[cite: 23]. */
    private final IUsuario usuarioAutenticado;

    /**
     * Inicializa el controlador del catálogo asociando la vista y el usuario activo[cite: 23].
     *
     * @param vista              Vista del catálogo gráfico {@link CatalogoVista}[cite: 23].
     * @param usuarioService     Servicio de lógica de negocio {@link IUsuarioService}[cite: 23].
     * @param usuarioAutenticado Instancia del usuario en sesión actual[cite: 23].
     */
    public CatalogoController(CatalogoVista vista, IUsuarioService usuarioService, IUsuario usuarioAutenticado) {
        this.vista = vista;
        this.usuarioService = usuarioService;
        this.usuarioAutenticado = usuarioAutenticado;
        inicializarEventos();
    }

    /**
     * Enlaza las acciones del usuario sobre los componentes interactivos de la vista[cite: 23].
     */
    private void inicializarEventos() {
        this.vista.getBotonBuscar().setOnAction(e -> filtrarPropiedades());
        this.vista.getBotonVolver().setOnAction(e -> volverPanelUsuario());
    }

    /**
     * Aplica el filtro de propiedades de acuerdo al texto ingresado en la barra de búsqueda[cite: 23].
     */
    private void filtrarPropiedades() {
        String criterio = vista.getTextoBusqueda();
        // Lógica de filtrado de propiedades según el criterio ingresado[cite: 23]
    }

    /**
     * Retorna la navegación al panel principal del usuario preservando los datos de la sesión[cite: 23].
     */
    private void volverPanelUsuario() {
        Stage stage = vista.getStage();
        UsuarioVista usuarioVista = new UsuarioVista(stage);
        if (usuarioAutenticado != null) {
            usuarioVista.setNombreUsuario(usuarioAutenticado.getNombre());
        }
        new UsuarioController(usuarioVista, usuarioService, usuarioAutenticado);
    }
}