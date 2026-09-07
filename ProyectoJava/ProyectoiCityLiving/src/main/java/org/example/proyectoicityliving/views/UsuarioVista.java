package org.example.proyectoicityliving.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Vista interactiva del portal de usuarios en City Living MX.
 * <p>
 * Proporciona el entorno de exploración de inmuebles, búsqueda por criterios,
 * filtros de categorías y gestión de sesión.
 * </p>
 *
 * @author Polanco Romero Erick
 * @author Jardines Bandala Luis Antonio
 * @version 2.8
 */
public class UsuarioVista extends VistaBase {

    private Label labelBienvenida;
    private TextField campoBusqueda;
    private ComboBox<String> comboFiltroTipo;
    private Button botonBuscar;
    private ListView<String> listaInmuebles;
    private Button botonVerDetalles;
    private Button botonVerCatalogo;
    private Button botonCerrarSesion;

    /**
     * Construye e inicializa la vista de usuario.
     *
     * @param stage Escenario asignado para renderizar la interfaz.
     */
    public UsuarioVista(Stage stage) {
        super(stage);
        inicializarComponentes();
    }

    /**
     * Ensambla el diseño visual combinando encabezado, filtros, lista de propiedades y acciones.
     */
    private void inicializarComponentes() {
        StackPane root = crearContenedorRaizConFondo();

        BorderPane layoutPrincipal = new BorderPane();

        // 1. Encabezado con logotipo en esquina superior izquierda
        HBox encabezado = crearEncabezadoUsuario("Portal de Inmuebles - City Living MX");
        layoutPrincipal.setTop(encabezado);

        // 2. Tarjeta central interactiva
        VBox panelCentral = new VBox(12);
        panelCentral.setAlignment(Pos.TOP_CENTER);
        panelCentral.setPadding(new Insets(20));
        panelCentral.setMaxWidth(720);
        panelCentral.setMaxHeight(480);

        panelCentral.setStyle(
                "-fx-background-color: rgba(30, 35, 45, 0.82);" +
                        "-fx-background-radius: 15;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.15);" +
                        "-fx-border-radius: 15;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 15, 0, 0, 8);"
        );

        labelBienvenida = new Label("¡Bienvenido al catálogo de propiedades!");
        labelBienvenida.setFont(Font.font("System", FontWeight.BOLD, 16));
        labelBienvenida.setTextFill(Color.WHITE);

        // Barra de búsqueda y filtrado
        HBox barraFiltros = new HBox(10);
        barraFiltros.setAlignment(Pos.CENTER);

        campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Buscar por ciudad, zona o precio...");
        campoBusqueda.setStyle(estilosCampoTexto());
        HBox.setHgrow(campoBusqueda, Priority.ALWAYS);

        comboFiltroTipo = new ComboBox<>();
        comboFiltroTipo.getItems().addAll("Todos", "Departamento", "Casa", "Loft", "Penthouse");
        comboFiltroTipo.setValue("Todos");
        comboFiltroTipo.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-text-fill: white; -fx-background-radius: 8;");

        botonBuscar = new Button("Buscar");
        botonBuscar.setStyle(estilosBotonPrimario());

        barraFiltros.getChildren().addAll(campoBusqueda, comboFiltroTipo, botonBuscar);

        // Listado interactivo de inmuebles
        listaInmuebles = new ListView<>();
        listaInmuebles.setPrefHeight(230);
        listaInmuebles.setStyle("-fx-background-color: rgba(255, 255, 255, 0.05); -fx-control-inner-background: rgba(20, 24, 33, 0.8);");

        // Botonera de acciones inferiores
        HBox barraAcciones = new HBox(15);
        barraAcciones.setAlignment(Pos.CENTER);

        botonVerDetalles = new Button("Ver Detalle de Inmueble");
        botonVerDetalles.setPrefWidth(200);
        botonVerDetalles.setStyle(estilosBotonPrimario());

        botonVerCatalogo = new Button("Actualizar Catálogo");
        botonVerCatalogo.setPrefWidth(180);
        botonVerCatalogo.setStyle(estilosBotonSecundario());

        botonCerrarSesion = new Button("Cerrar Sesión");
        botonCerrarSesion.setPrefWidth(140);
        botonCerrarSesion.setStyle(estilosBotonSecundario());

        barraAcciones.getChildren().addAll(botonVerDetalles, botonVerCatalogo, botonCerrarSesion);

        panelCentral.getChildren().addAll(
                labelBienvenida,
                barraFiltros,
                listaInmuebles,
                barraAcciones
        );

        layoutPrincipal.setCenter(panelCentral);
        root.getChildren().add(layoutPrincipal);

        Scene scene = new Scene(root, ANCHO_VENTANA, ALTO_VENTANA);
        this.stage.setTitle("City Living MX - Portal de Usuario");
        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * Actualiza el mensaje de bienvenida con el nombre del usuario.
     *
     * @param nombreUsuario Nombre completo del cliente autenticado.
     */
    public void setNombreUsuario(String nombreUsuario) {
        labelBienvenida.setText("¡Hola, " + nombreUsuario + "! Consulta nuestros inmuebles disponibles.");
    }

    /** @return Texto capturado en la barra de búsqueda. */
    public String getTextoBusqueda() {
        return campoBusqueda.getText().trim();
    }

    /** @return Tipo de propiedad seleccionado en el combo box. */
    public String getFiltroTipoSeleccionado() {
        return comboFiltroTipo.getValue();
    }

    /** @return Campo de texto de la búsqueda. */
    public TextField getCampoBusqueda() {
        return campoBusqueda;
    }

    /** @return ComboBox con las opciones de filtrado. */
    public ComboBox<String> getComboFiltroTipo() {
        return comboFiltroTipo;
    }

    /** @return Lista gráfica con las propiedades desplegadas. */
    public ListView<String> getListaInmuebles() {
        return listaInmuebles;
    }

    /** @return Botón para ejecutar la búsqueda. */
    public Button getBotonBuscar() {
        return botonBuscar;
    }

    /** @return Botón para consultar detalles de un inmueble. */
    public Button getBotonVerDetalles() {
        return botonVerDetalles;
    }

    /** @return Botón para recargar el catálogo. */
    public Button getBotonVerCatalogo() {
        return botonVerCatalogo;
    }

    /** @return Alias con tilde ortográfica del getter del catálogo. */
    public Button getBotonVerCatálogo() {
        return botonVerCatalogo;
    }

    /** @return Botón para cerrar la sesión actual. */
    public Button getBotonCerrarSesion() {
        return botonCerrarSesion;
    }
}