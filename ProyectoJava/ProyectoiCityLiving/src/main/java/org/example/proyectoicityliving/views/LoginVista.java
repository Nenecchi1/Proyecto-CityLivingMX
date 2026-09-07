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
 * Vista de inicio de sesión integrada con las dimensiones globales y utilidades de {@link VistaBase}.
 * <p>
 * Proporciona la interfaz gráfica para el control de accesos de usuarios,
 * con soporte para enmascaramiento reactivo de contraseña, sincronización bidireccional
 * de texto y protección contra pegado no autorizado durante bloqueos.
 * </p>
 *
 * @author Jardines Bandala Luis Antonio
 * @version 1.6
 */
public class LoginVista extends VistaBase implements ILoginVista {

    private TextField campoCorreo;
    private PasswordField campoContrasena;
    private TextField campoContrasenaVisible;
    private CheckBox checkMostrarContrasena;
    private Button botonLogin;
    private Button botonLimpiar;
    private Hyperlink linkRegistro;
    private Label labelMensajeError;
    private Label labelBienvenida;

    /**
     * Construye e inicializa la pantalla de autenticación y control de acceso.
     *
     * @param stage Escenario primario de JavaFX asignado a la ventana.
     */
    public LoginVista(Stage stage) {
        super(stage);

        StackPane root = crearContenedorRaizConFondo();
        root.getChildren().add(crearTarjetaLogin());

        Scene scene = new Scene(root, ANCHO_VENTANA, ALTO_VENTANA);
        this.stage.setTitle("City Living MX - Control de Accesos");
        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * Genera la tarjeta contenedora con todos los componentes y controles interactivos del Login.
     *
     * @return Contenedor {@link VBox} configurado con estilo Glassmorphism y dimensiones ajustadas.
     */
    private VBox crearTarjetaLogin() {
        VBox tarjetaLogin = new VBox(12);
        tarjetaLogin.setAlignment(Pos.CENTER);
        tarjetaLogin.setPadding(new Insets(30, 35, 30, 35));
        tarjetaLogin.setMaxWidth(420);

        tarjetaLogin.setStyle(
                "-fx-background-color: rgba(30, 35, 45, 0.75);" +
                        "-fx-background-radius: 15;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.15);" +
                        "-fx-border-radius: 15;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 20, 0, 0, 10);"
        );

        ImageView logoView = crearLogo(90);

        Label labelTitulo = new Label("City Living MX");
        labelTitulo.setFont(Font.font("System", FontWeight.BOLD, 26));
        labelTitulo.setTextFill(Color.WHITE);

        Label labelCorreo = new Label("Correo electrónico:");
        labelCorreo.setTextFill(Color.rgb(180, 180, 200));

        campoCorreo = new TextField();
        campoCorreo.setPromptText("usuario@example.com");
        campoCorreo.setPrefHeight(38);
        campoCorreo.setStyle(estilosCampoTexto());

        Label labelContrasena = new Label("Contraseña:");
        labelContrasena.setTextFill(Color.rgb(180, 180, 200));

        campoContrasena = new PasswordField();
        campoContrasena.setPromptText("Mínimo 8 caracteres");
        campoContrasena.setPrefHeight(38);
        campoContrasena.setStyle(estilosCampoTexto());

        campoContrasenaVisible = new TextField();
        campoContrasenaVisible.setPromptText("Mínimo 8 caracteres");
        campoContrasenaVisible.setPrefHeight(38);
        campoContrasenaVisible.setStyle(estilosCampoTexto());

        StackPane contenedorContrasena = new StackPane(campoContrasena, campoContrasenaVisible);

        checkMostrarContrasena = new CheckBox("Mostrar contraseña");
        checkMostrarContrasena.setTextFill(Color.rgb(180, 180, 200));

        configurarToggleContrasena();

        labelMensajeError = new Label();
        labelMensajeError.setTextFill(Color.rgb(255, 100, 100));
        labelMensajeError.setWrapText(true);
        labelMensajeError.setPrefWidth(350);
        labelMensajeError.setMaxWidth(350);
        labelMensajeError.setMinHeight(Region.USE_PREF_SIZE);
        labelMensajeError.setAlignment(Pos.CENTER);
        labelMensajeError.setTextAlignment(TextAlignment.CENTER);
        labelMensajeError.setVisible(false);

        labelBienvenida = new Label();
        labelBienvenida.setTextFill(Color.rgb(100, 255, 150));
        labelBienvenida.setWrapText(true);
        labelBienvenida.setPrefWidth(350);
        labelBienvenida.setMaxWidth(350);
        labelBienvenida.setMinHeight(Region.USE_PREF_SIZE);
        labelBienvenida.setAlignment(Pos.CENTER);
        labelBienvenida.setTextAlignment(TextAlignment.CENTER);
        labelBienvenida.setVisible(false);

        botonLogin = new Button("Iniciar sesión");
        botonLogin.setPrefHeight(38);
        botonLogin.setPrefWidth(350);
        botonLogin.setStyle(estilosBotonPrimario());

        botonLimpiar = new Button("Limpiar");
        botonLimpiar.setPrefHeight(34);
        botonLimpiar.setPrefWidth(350);
        botonLimpiar.setStyle(estilosBotonSecundario());
        botonLimpiar.setOnAction(e -> habilitarVista());

        linkRegistro = new Hyperlink("¿No tienes cuenta? Regístrate aquí");
        linkRegistro.setTextFill(Color.rgb(100, 150, 255));

        if (logoView.getImage() != null) {
            tarjetaLogin.getChildren().add(logoView);
        }

        tarjetaLogin.getChildren().addAll(
                labelTitulo, labelCorreo, campoCorreo,
                labelContrasena, contenedorContrasena, checkMostrarContrasena,
                labelMensajeError, labelBienvenida, botonLogin, botonLimpiar, linkRegistro
        );

        return tarjetaLogin;
    }

    /**
     * Enlaza bidireccionalmente el texto entre ambos campos y vincula su visibilidad
     * de forma estrictamente reactiva con el estado del CheckBox.
     */
    private void configurarToggleContrasena() {
        campoContrasena.textProperty().bindBidirectional(campoContrasenaVisible.textProperty());

        campoContrasenaVisible.visibleProperty().bind(checkMostrarContrasena.selectedProperty());
        campoContrasenaVisible.managedProperty().bind(checkMostrarContrasena.selectedProperty());

        campoContrasena.visibleProperty().bind(checkMostrarContrasena.selectedProperty().not());
        campoContrasena.managedProperty().bind(checkMostrarContrasena.selectedProperty().not());
    }

    /**
     * Restablece los valores de las entradas de texto e inhabilita las etiquetas de retroalimentación.
     */
    private void limpiarCampos() {
        campoCorreo.clear();
        campoContrasena.clear();
        checkMostrarContrasena.setSelected(false);
        labelMensajeError.setVisible(false);
        labelBienvenida.setVisible(false);
    }

    /**
     * Obtiene el correo electrónico ingresado en la interfaz sin espacios periféricos.
     *
     * @return Cadena de texto con el correo del usuario.
     */
    @Override
    public String getCorreo() {
        return campoCorreo.getText().trim();
    }

    /**
     * Obtiene la contraseña ingresada en el formulario.
     *
     * @return Cadena de texto con la contraseña activa.
     */
    @Override
    public String getContrasena() {
        return campoContrasena.getText();
    }

    /**
     * Despliega un mensaje de error o advertencia formateado y envuelto según el ancho disponible.
     *
     * @param mensaje Texto explicativo del fallo.
     */
    @Override
    public void mostrarError(String mensaje) {
        labelBienvenida.setVisible(false);
        labelMensajeError.setText("⚠ " + mensaje);
        labelMensajeError.setVisible(true);
    }

    /**
     * Despliega la confirmación de autenticación exitosa indicando el nombre de usuario y su rol.
     *
     * @param nombreUsuario Nombre completo del usuario autenticado.
     * @param rolUsuario   Rol asignado en el sistema.
     */
    @Override
    public void mostrarBienvenida(String nombreUsuario, String rolUsuario) {
        labelMensajeError.setVisible(false);
        labelBienvenida.setText("✅ Bienvenido " + nombreUsuario + " (" + rolUsuario + ")");
        labelBienvenida.setVisible(true);
    }

    /**
     * Inhabilita la edición e interacción con los campos tras un bloqueo de cuenta
     * y transfiere el foco al botón de limpieza para mitigar atajos de teclado como Ctrl+V.
     */
    @Override
    public void inhabilitarVista() {
        campoCorreo.setEditable(false);
        campoContrasena.setEditable(false);
        campoContrasenaVisible.setEditable(false);

        campoCorreo.setDisable(true);
        campoContrasena.setDisable(true);
        campoContrasenaVisible.setDisable(true);
        checkMostrarContrasena.setDisable(true);
        botonLogin.setDisable(true);
        linkRegistro.setDisable(true);

        botonLimpiar.requestFocus();
        mostrarError("Cuenta bloqueada por seguridad. Presione 'Limpiar' para reiniciar.");
    }

    /**
     * Habilita nuevamente la edición en los controles de la interfaz y limpia el formulario.
     */
    @Override
    public void habilitarVista() {
        campoCorreo.setEditable(true);
        campoContrasena.setEditable(true);
        campoContrasenaVisible.setEditable(true);

        campoCorreo.setDisable(false);
        campoContrasena.setDisable(false);
        campoContrasenaVisible.setDisable(false);
        checkMostrarContrasena.setDisable(false);
        botonLogin.setDisable(false);
        linkRegistro.setDisable(false);

        limpiarCampos();
    }

    /**
     * Obtiene la referencia al botón de inicio de sesión.
     *
     * @return Componente {@link Button} de inicio de sesión.
     */
    @Override
    public Button getBotonLogin() {
        return botonLogin;
    }

    /**
     * Obtiene la referencia al enlace de registro.
     *
     * @return Componente {@link Hyperlink} para la navegación al registro.
     */
    @Override
    public Hyperlink getLinkRegistro() {
        return linkRegistro;
    }
}