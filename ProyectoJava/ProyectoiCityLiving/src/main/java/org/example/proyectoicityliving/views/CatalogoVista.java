package org.example.proyectoicityliving.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Vista para la exploración y búsqueda de propiedades dentro de la plataforma City Living MX.
 * <p>
 * Despliega el catálogo interactivo de departamentos y casas disponibles para arrendamiento.
 * </p>
 *
 * @author Polanco Romero Erick
 * @author Jardines Bandala Luis Antonio
 * @version 1.0
 */
public class CatalogoVista extends VistaBase {

    private TextField campoBusqueda;
    private Button botonBuscar;
    private Button botonVolver;
    private ListView<String> listaPropiedades;

    /**
     * Construye la vista de catálogo inicializando sus contenedores y componentes gráficos.
     *
     * @param stage Escenario {@link Stage} asignado a la ventana.
     */
    public CatalogoVista(Stage stage) {
        super(stage);
        inicializarComponentes();
    }

    /**
     * Configura la interfaz de usuario, cajas de búsqueda y listas de resultados.
     */
    private void inicializarComponentes() {
        VBox contenedor = new VBox(15);
        contenedor.setAlignment(Pos.CENTER);
        contenedor.setPadding(new Insets(30));
        contenedor.setStyle("-fx-background-color: #1a1a2e;");

        Label labelTitulo = new Label("Catálogo de Propiedades Disponibles");
        labelTitulo.setFont(Font.font("System", FontWeight.BOLD, 22));
        labelTitulo.setTextFill(Color.WHITE);

        campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Buscar por ciudad, zona o precio...");
        campoBusqueda.setMaxWidth(400);
        campoBusqueda.setStyle("-fx-background-color: rgba(255, 255, 255, 0.05); -fx-text-fill: white; -fx-prompt-text-fill: gray;");

        botonBuscar = new Button("Buscar");
        botonBuscar.setStyle("-fx-background-color: #2979ff; -fx-text-fill: white;");

        listaPropiedades = new ListView<>();
        listaPropiedades.setMaxWidth(500);
        listaPropiedades.setPrefHeight(250);

        botonVolver = new Button("Volver al Panel Principal");
        botonVolver.setStyle("-fx-background-color: rgba(255, 255, 255, 0.1); -fx-text-fill: white;");

        contenedor.getChildren().addAll(labelTitulo, campoBusqueda, botonBuscar, listaPropiedades, botonVolver);

        Scene scene = new Scene(contenedor, 800, 600);
        this.stage.setTitle("City Living MX - Catálogo de Propiedades");
        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * Obtiene el texto ingresado en la barra de filtro o búsqueda de inmuebles.
     *
     * @return Cadena con el criterio de búsqueda.
     */
    public String getTextoBusqueda() {
        return campoBusqueda.getText().trim();
    }

    /**
     * Obtiene la referencia al botón que detona la búsqueda dentro del catálogo.
     *
     * @return Instancia de {@link Button} de búsqueda.
     */
    public Button getBotonBuscar() {
        return botonBuscar;
    }

    /**
     * Obtiene el botón para navegar de regreso a la vista principal.
     *
     * @return Instancia de {@link Button} de retorno.
     */
    public Button getBotonVolver() {
        return botonVolver;
    }

    /**
     * Obtiene el componente de lista donde se muestran las propiedades resultantes.
     *
     * @return Objeto {@link ListView} con los registros de propiedades.
     */
    public ListView<String> getListaPropiedades() {
        return listaPropiedades;
    }
}