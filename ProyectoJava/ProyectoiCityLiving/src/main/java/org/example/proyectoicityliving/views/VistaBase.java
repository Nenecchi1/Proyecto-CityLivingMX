package org.example.proyectoicityliving.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * Clase base abstracta para la gestión unificada de las vistas en City Living MX.
 * <p>
 * Define las dimensiones estándar de ventana, el fondo visual, los componentes del logotipo,
 * los diálogos de alerta y las hojas de estilos CSS compartidas.
 * </p>
 *
 * @author Jardines Bandala Luis Antonio
 * @version 2.5
 */
public abstract class VistaBase {

    /** Ancho estándar para todas las ventanas del sistema. */
    public static final double ANCHO_VENTANA = 800;

    /** Alto estándar para todas las ventanas del sistema. */
    public static final double ALTO_VENTANA = 600;

    /** Referencia al escenario principal de JavaFX. */
    protected Stage stage;

    /**
     * Constructor principal de la base de vistas.
     *
     * @param stage Escenario primario sobre el que se representarán las escenas.
     */
    public VistaBase(Stage stage) {
        this.stage = stage;
    }

    /**
     * Obtiene el escenario actual.
     *
     * @return Objeto Stage asociado.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Construye el contenedor raíz uniforme (StackPane) con el fondo corporativo.
     *
     * @return StackPane configurado con la imagen de fondo o color sólido alternativo.
     */
    protected StackPane crearContenedorRaizConFondo() {
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);

        try {
            Image imagenFondo = new Image(getClass().getResourceAsStream("/org/example/proyectoicityliving/imagenes/fondo.png"));
            if (imagenFondo != null && !imagenFondo.isError()) {
                BackgroundImage bImg = new BackgroundImage(
                        imagenFondo,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(100, 100, true, true, true, true)
                );
                root.setBackground(new Background(bImg));
            } else {
                root.setStyle("-fx-background-color: #1a1a2e;");
            }
        } catch (Exception e) {
            root.setStyle("-fx-background-color: #1a1a2e;");
        }
        return root;
    }

    /**
     * Genera un componente de imagen con el logotipo del sistema.
     *
     * @param ancho Ancho en píxeles que tomará el logotipo manteniendo su proporción.
     * @return Componente ImageView con el logo cargado.
     */
    protected ImageView crearLogo(double ancho) {
        ImageView logoView = new ImageView();
        try {
            Image icono = new Image(getClass().getResourceAsStream("/org/example/proyectoicityliving/imagenes/logo.png"));
            if (icono != null && !icono.isError()) {
                logoView.setImage(icono);
                logoView.setFitWidth(ancho);
                logoView.setPreserveRatio(true);
            }
        } catch (Exception ignored) { }
        return logoView;
    }

    /**
     * Construye una barra superior para vistas de usuario, posicionando un logo pequeño
     * en la esquina superior izquierda junto con un título de sección.
     *
     * @param tituloSeccion Texto del título a mostrar junto al logotipo.
     * @return HBox configurado con alineación superior izquierda.
     */
    protected HBox crearEncabezadoUsuario(String tituloSeccion) {
        HBox barraSuperior = new HBox(15);
        barraSuperior.setAlignment(Pos.CENTER_LEFT);
        barraSuperior.setPadding(new Insets(15, 20, 15, 20));

        ImageView logoPequeno = crearLogo(45);

        Label labelTitulo = new Label(tituloSeccion);
        labelTitulo.setFont(Font.font("System", FontWeight.BOLD, 18));
        labelTitulo.setTextFill(Color.WHITE);

        if (logoPequeno.getImage() != null) {
            barraSuperior.getChildren().add(logoPequeno);
        }
        barraSuperior.getChildren().add(labelTitulo);

        return barraSuperior;
    }

    /**
     * Muestra una ventana modal emergente de error.
     *
     * @param mensaje Mensaje descriptivo de la falla detectada.
     */
    public void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error - City Living MX");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra una ventana modal emergente de información.
     *
     * @param mensaje Contenido informativo que se notificará al usuario.
     */
    public void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información - City Living MX");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Retorna la cadena CSS estilizada para campos de texto.
     *
     * @return String con reglas de estilo CSS.
     */
    protected String estilosCampoTexto() {
        return "-fx-background-color: rgba(255, 255, 255, 0.05);" +
                "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: rgba(255, 255, 255, 0.5);" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: rgba(255, 255, 255, 0.4);" +
                "-fx-border-radius: 8;" +
                "-fx-padding: 8 12;";
    }

    /**
     * Retorna la cadena CSS estilizada para botones principales.
     *
     * @return String con reglas de estilo CSS.
     */
    protected String estilosBotonPrimario() {
        return "-fx-background-color: #2979ff;" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 8;" +
                "-fx-cursor: hand;";
    }

    /**
     * Retorna la cadena CSS estilizada para botones secundarios.
     *
     * @return String con reglas de estilo CSS.
     */
    protected String estilosBotonSecundario() {
        return "-fx-background-color: rgba(255, 255, 255, 0.1);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: rgba(255, 255, 255, 0.3);" +
                "-fx-border-radius: 8;" +
                "-fx-cursor: hand;";
    }
}