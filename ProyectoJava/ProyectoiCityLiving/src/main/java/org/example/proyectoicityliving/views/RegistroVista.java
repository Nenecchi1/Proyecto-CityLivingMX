package org.example.proyectoicityliving.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Vista del formulario de registro de nuevos usuarios en la plataforma City Living MX.
 * <p>
 * Proporciona campos parametrizados con etiquetas descriptivas (Label) para cada entrada,
 * enmascaramiento reactivo de contraseña y retroalimentación visual de errores y
 * confirmaciones de éxito multilínea sin truncamiento.
 * </p>
 *
 * @author Jardines Bandala Luis Antonio
 * @version 3.2
 */
public class RegistroVista extends VistaBase {

    private TextField campoNombre;
    private TextField campoUsuarioCorreo;
    private PasswordField campoContrasena;
    private TextField campoContrasenaVisible;
    private PasswordField campoConfirmarContrasena;
    private TextField campoConfirmarContrasenaVisible;
    private CheckBox checkMostrarContrasenas;
    private Button botonRegistrar;
    private Button botonVolver;
    private Label labelMensajeError;
    private Label labelMensajeExito;

    /**
     * Inicializa la interfaz gráfica de registro de usuarios.
     *
     * @param stage Escenario principal de la aplicación JavaFX.
     */
    public RegistroVista(Stage stage) {
        super(stage);

        StackPane root = crearContenedorRaizConFondo();
        root.getChildren().add(crearTarjetaRegistro());

        Scene scene = new Scene(root, ANCHO_VENTANA, ALTO_VENTANA);
        this.stage.setTitle("City Living MX - Crear Cuenta");
        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * Construye la tarjeta contenedora con todos los elementos, etiquetas descriptivas
     * y controles interactivos del registro.
     *
     * @return Contenedor {@link VBox} estilizado con diseño Glassmorphism.
     */
    private VBox crearTarjetaRegistro() {
        VBox tarjeta = new VBox(8);
        tarjeta.setAlignment(Pos.CENTER);
        tarjeta.setPadding(new Insets(20, 35, 20, 35));
        tarjeta.setMaxWidth(420);

        tarjeta.setStyle(
                "-fx-background-color: rgba(30, 35, 45, 0.75);" +
                        "-fx-background-radius: 15;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.15);" +
                        "-fx-border-radius: 15;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 20, 0, 0, 10);"
        );

        ImageView logoView = crearLogo(65);

        Label labelTitulo = new Label("Crear Cuenta");
        labelTitulo.setFont(Font.font("System", FontWeight.BOLD, 22));
        labelTitulo.setTextFill(Color.WHITE);

        // --- Campo: Nombre Completo ---
        Label labelNombre = new Label("Nombre y Apellidos:");
        labelNombre.setTextFill(Color.rgb(180, 180, 200));

        campoNombre = new TextField();
        campoNombre.setPromptText("Ej. Juan Pérez López");
        campoNombre.setPrefHeight(36);
        campoNombre.setStyle(estilosCampoTexto());

        // --- Campo: Usuario de Correo ---
        Label labelCorreo = new Label("Nombre de usuario (correo):");
        labelCorreo.setTextFill(Color.rgb(180, 180, 200));

        campoUsuarioCorreo = new TextField();
        campoUsuarioCorreo.setPromptText("usuario");
        campoUsuarioCorreo.setPrefHeight(36);
        campoUsuarioCorreo.setStyle(estilosCampoTexto());

        Label labelDominio = new Label("@cityliving.mx");
        labelDominio.setTextFill(Color.rgb(180, 180, 200));
        labelDominio.setFont(Font.font("System", FontWeight.BOLD, 13));

        HBox contenedorCorreo = new HBox(5, campoUsuarioCorreo, labelDominio);
        contenedorCorreo.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(campoUsuarioCorreo, Priority.ALWAYS);

        // --- Campo: Contraseña ---
        Label labelContrasena = new Label("Contraseña:");
        labelContrasena.setTextFill(Color.rgb(180, 180, 200));

        Label labelFormatoContrasena = new Label("Mín. 8 caracteres (mayúsculas, minúsculas, números y #, $, &)");
        labelFormatoContrasena.setFont(Font.font("System", 10));
        labelFormatoContrasena.setTextFill(Color.rgb(160, 160, 180));
        labelFormatoContrasena.setWrapText(true);
        labelFormatoContrasena.setPrefWidth(350);
        labelFormatoContrasena.setMaxWidth(350);
        labelFormatoContrasena.setMinHeight(Region.USE_PREF_SIZE);

        campoContrasena = new PasswordField();
        campoContrasena.setPromptText("Escriba su contraseña");
        campoContrasena.setPrefHeight(36);
        campoContrasena.setStyle(estilosCampoTexto());

        campoContrasenaVisible = new TextField();
        campoContrasenaVisible.setPromptText("Escriba su contraseña");
        campoContrasenaVisible.setPrefHeight(36);
        campoContrasenaVisible.setStyle(estilosCampoTexto());

        StackPane stackContrasena = new StackPane(campoContrasena, campoContrasenaVisible);

        // --- Campo: Confirmar Contraseña ---
        Label labelConfirmarContrasena = new Label("Confirmar contraseña:");
        labelConfirmarContrasena.setTextFill(Color.rgb(180, 180, 200));

        campoConfirmarContrasena = new PasswordField();
        campoConfirmarContrasena.setPromptText("Repita su contraseña");
        campoConfirmarContrasena.setPrefHeight(36);
        campoConfirmarContrasena.setStyle(estilosCampoTexto());

        campoConfirmarContrasenaVisible = new TextField();
        campoConfirmarContrasenaVisible.setPromptText("Repita su contraseña");
        campoConfirmarContrasenaVisible.setPrefHeight(36);
        campoConfirmarContrasenaVisible.setStyle(estilosCampoTexto());

        StackPane stackConfirmar = new StackPane(campoConfirmarContrasena, campoConfirmarContrasenaVisible);

        checkMostrarContrasenas = new CheckBox("Mostrar contraseñas");
        checkMostrarContrasenas.setTextFill(Color.rgb(180, 180, 200));
        configurarToggleContrasenas();

        labelMensajeError = new Label();
        labelMensajeError.setTextFill(Color.rgb(255, 100, 100));
        labelMensajeError.setWrapText(true);
        labelMensajeError.setPrefWidth(350);
        labelMensajeError.setMaxWidth(350);
        labelMensajeError.setMinHeight(Region.USE_PREF_SIZE);
        labelMensajeError.setAlignment(Pos.CENTER);
        labelMensajeError.setTextAlignment(TextAlignment.CENTER);
        labelMensajeError.setVisible(false);

        labelMensajeExito = new Label();
        labelMensajeExito.setTextFill(Color.rgb(100, 255, 150));
        labelMensajeExito.setWrapText(true);
        labelMensajeExito.setPrefWidth(350);
        labelMensajeExito.setMaxWidth(350);
        labelMensajeExito.setMinHeight(Region.USE_PREF_SIZE);
        labelMensajeExito.setAlignment(Pos.CENTER);
        labelMensajeExito.setTextAlignment(TextAlignment.CENTER);
        labelMensajeExito.setVisible(false);

        botonRegistrar = new Button("Registrar Cuenta");
        botonRegistrar.setPrefHeight(38);
        botonRegistrar.setPrefWidth(350);
        botonRegistrar.setStyle(estilosBotonPrimario());

        botonVolver = new Button("Volver al Inicio de Sesión");
        botonVolver.setPrefHeight(34);
        botonVolver.setPrefWidth(350);
        botonVolver.setStyle(estilosBotonSecundario());

        if (logoView.getImage() != null) {
            tarjeta.getChildren().add(logoView);
        }

        tarjeta.getChildren().addAll(
                labelTitulo,
                labelNombre, campoNombre,
                labelCorreo, contenedorCorreo,
                labelContrasena, labelFormatoContrasena, stackContrasena,
                labelConfirmarContrasena, stackConfirmar,
                checkMostrarContrasenas, labelMensajeError, labelMensajeExito,
                botonRegistrar, botonVolver
        );

        return tarjeta;
    }

    /**
     * Sincroniza bidireccionalmente las entradas de texto y vincula la visibilidad
     * reactiva de los campos enmascarados y visibles con el CheckBox.
     */
    private void configurarToggleContrasenas() {
        campoContrasena.textProperty().bindBidirectional(campoContrasenaVisible.textProperty());
        campoConfirmarContrasena.textProperty().bindBidirectional(campoConfirmarContrasenaVisible.textProperty());

        campoContrasenaVisible.visibleProperty().bind(checkMostrarContrasenas.selectedProperty());
        campoContrasenaVisible.managedProperty().bind(checkMostrarContrasenas.selectedProperty());
        campoContrasena.visibleProperty().bind(checkMostrarContrasenas.selectedProperty().not());
        campoContrasena.managedProperty().bind(checkMostrarContrasenas.selectedProperty().not());

        campoConfirmarContrasenaVisible.visibleProperty().bind(checkMostrarContrasenas.selectedProperty());
        campoConfirmarContrasenaVisible.managedProperty().bind(checkMostrarContrasenas.selectedProperty());
        campoConfirmarContrasena.visibleProperty().bind(checkMostrarContrasenas.selectedProperty().not());
        campoConfirmarContrasena.managedProperty().bind(checkMostrarContrasenas.selectedProperty().not());
    }

    /**
     * Despliega un mensaje de error o éxito en la vista según el parámetro booleano.
     *
     * @param mensaje Texto explicativo que se mostrará al usuario.
     * @param esError {@code true} para desplegar una alerta de error; {@code false} para una confirmación de éxito.
     */
    public void mostrarMensaje(String mensaje, boolean esError) {
        if (esError) {
            mostrarError(mensaje);
        } else {
            mostrarExito(mensaje);
        }
    }

    /**
     * Despliega un mensaje de error en la interfaz gráfica.
     *
     * @param mensaje Descripción del problema detectado.
     */
    public void mostrarError(String mensaje) {
        labelMensajeExito.setVisible(false);
        labelMensajeError.setText("⚠ " + mensaje);
        labelMensajeError.setVisible(true);
    }

    /**
     * Despliega una notificación de confirmación en la interfaz gráfica.
     *
     * @param mensaje Mensaje de éxito para el usuario.
     */
    public void mostrarExito(String mensaje) {
        labelMensajeError.setVisible(false);
        labelMensajeExito.setText("✅ " + mensaje);
        labelMensajeExito.setVisible(true);
    }

    /**
     * Limpia las entradas de texto del formulario sin ocultar los mensajes de notificación activos.
     */
    public void limpiarCampos() {
        campoNombre.clear();
        campoUsuarioCorreo.clear();
        campoContrasena.clear();
        campoConfirmarContrasena.clear();
        checkMostrarContrasenas.setSelected(false);
    }

    /**
     * Restablece completamente el formulario, incluyendo la ocultación de mensajes.
     */
    public void limpiarFormulario() {
        limpiarCampos();
        labelMensajeError.setVisible(false);
        labelMensajeExito.setVisible(false);
    }

    /**
     * Obtiene el nombre completo introducido por el usuario.
     *
     * @return Nombre y apellidos limpios de espacios periféricos.
     */
    public String getNombre() {
        return campoNombre.getText().trim();
    }

    /**
     * Obtiene la dirección de correo electrónico completa concatenando el usuario con el dominio predeterminado.
     *
     * @return Dirección de correo electrónico completa con dominio @cityliving.mx.
     */
    public String getCorreo() {
        String usuario = campoUsuarioCorreo.getText().trim();
        if (usuario.endsWith("@cityliving.mx")) {
            return usuario;
        }
        return usuario + "@cityliving.mx";
    }

    /**
     * Alias de {@link #getCorreo()} para mantener compatibilidad.
     *
     * @return Dirección de correo completa.
     */
    public String getCorreoCompleto() {
        return getCorreo();
    }

    /**
     * Obtiene la contraseña principal ingresada en el formulario.
     *
     * @return Cadena con la contraseña.
     */
    public String getContrasena() {
        return campoContrasena.getText();
    }

    /**
     * Obtiene la confirmación de contraseña ingresada en el formulario.
     *
     * @return Cadena con la confirmación de contraseña.
     */
    public String getConfirmarContrasena() {
        return campoConfirmarContrasena.getText();
    }

    /**
     * Obtiene el botón de envío del formulario de registro.
     *
     * @return Componente {@link Button} de registro.
     */
    public Button getBotonRegistrar() {
        return botonRegistrar;
    }

    /**
     * Obtiene el botón para regresar a la pantalla de inicio de sesión.
     *
     * @return Componente {@link Button} de retorno al login.
     */
    public Button getBotonVolverLogin() {
        return botonVolver;
    }

    /**
     * Obtiene la referencia al escenario JavaFX donde se aloja la vista.
     *
     * @return Escenario principal {@link Stage}.
     */
    @Override
    public Stage getStage() {
        return this.stage;
    }
}