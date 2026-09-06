package org.example.proyectoicityliving.views;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginVista implements ILoginVista {

    private TextField campoCorreo;
    private PasswordField campoContrasena;
    private TextField campoContrasenaVisible;
    private CheckBox checkMostrarContrasena;
    private Button botonLogin;
    private Button botonLimpiar;
    private Hyperlink linkRegistro;
    private Label labelMensajeError;
    private Label labelBienvenida;
    private Stage stage;

    public LoginVista(Stage stage) {
        this.stage = stage;

        // Contenedor principal usando StackPane para poner la imagen de fondo
        StackPane root = new StackPane();
        root.setAlignment(Pos.CENTER);

        // CARGAR IMAGEN DE FONDO
        try {
            // Asegúrate de que "fondo.png" esté en la carpeta resources/.../images/
            Image imagenFondo = new Image(getClass().getResourceAsStream("/org/example/proyectoicityliving/imagenes/fondo.png"));
            if (imagenFondo != null && !imagenFondo.isError()) {
                BackgroundImage bImg = new BackgroundImage(imagenFondo,
                        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(100, 100, true, true, true, true));
                root.setBackground(new Background(bImg));
            } else {
                root.setStyle("-fx-background-color: #1a1a2e;"); // Respaldo si no encuentra la imagen
            }
        } catch (Exception e) {
            root.setStyle("-fx-background-color: #1a1a2e;"); // Respaldo si hay error
        }

        // Agregamos el formulario (tarjeta Glassmorphism) encima del fondo
        root.getChildren().add(crearTerjetaLogin());

        Scene scene = new Scene(root, 800, 600);
        this.stage.setTitle("City Living MX - Control de Accesos");
        this.stage.setScene(scene);
        this.stage.show();
    }

    private VBox crearTerjetaLogin() {
        VBox tarjetaLogin = new VBox(15);
        tarjetaLogin.setAlignment(Pos.CENTER);
        tarjetaLogin.setPadding(new Insets(40));
        tarjetaLogin.setMaxWidth(400);

        // 2. ESTILO GLASSMORPHISM (Fondo semi-transparente, bordes redondeados y sombra)
        tarjetaLogin.setStyle(
                "-fx-background-color: rgba(30, 35, 45, 0.7);" +
                        "-fx-background-radius: 15;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.15);" +
                        "-fx-border-radius: 15;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 20, 0, 0, 10);"
        );

        // 3. AGREGAR EL LOGO
        ImageView logoView = new ImageView();
        try {
            // Asegúrate de que "logo.png" esté en la carpeta resources/.../images/
            Image icono = new Image(getClass().getResourceAsStream("/org/example/proyectoicityliving/imagenes/logo.png"));
            if (icono != null && !icono.isError()) {
                logoView.setImage(icono);
                logoView.setFitWidth(100);
                logoView.setPreserveRatio(true);
            }
        } catch (Exception e) {
            // Si no carga la imagen, no falla el programa, solo no muestra logo
        }

        Label labelTitulo = new Label("City Living MX");
        labelTitulo.setFont(Font.font("System", FontWeight.BOLD, 28));
        labelTitulo.setTextFill(Color.WHITE);

        Label labelSubtitulo = new Label("Renta de departamentos y casas");
        labelSubtitulo.setFont(Font.font("System", 16));
        labelSubtitulo.setTextFill(Color.rgb(180, 180, 200));

        Label labelCorreo = new Label("Correo electrónico:");
        labelCorreo.setTextFill(Color.rgb(180, 180, 200));
        labelCorreo.setFont(Font.font("System", 12));

        campoCorreo = new TextField();
        campoCorreo.setPromptText("usuario@example.com");
        campoCorreo.setPrefHeight(40);
        campoCorreo.setStyle(estilosCampoTexto());

        Label labelContrasena = new Label("Contraseña:");
        labelContrasena.setTextFill(Color.rgb(180, 180, 200));
        labelContrasena.setFont(Font.font("System", 12));

        campoContrasena = new PasswordField();
        campoContrasena.setPromptText("Minimo 8 caracteres");
        campoContrasena.setPrefHeight(40);
        campoContrasena.setStyle(estilosCampoTexto());

        campoContrasenaVisible = new TextField();
        campoContrasenaVisible.setPromptText("Minimo 8 caracteres");
        campoContrasenaVisible.setPrefHeight(40);
        campoContrasenaVisible.setStyle(estilosCampoTexto());
        campoContrasenaVisible.setVisible(false);
        campoContrasenaVisible.setManaged(false);

        StackPane contenedorContrasena = new StackPane(campoContrasena, campoContrasenaVisible);

        checkMostrarContrasena = new CheckBox("Mostrar contraseña");
        checkMostrarContrasena.setTextFill(Color.rgb(180, 180, 200));
        checkMostrarContrasena.setFont(Font.font("System", 11));
        configurarToggleContrasena();

        labelMensajeError = new Label();
        labelMensajeError.setTextFill(Color.rgb(255, 100, 100));
        labelMensajeError.setFont(Font.font("System", 12));
        labelMensajeError.setWrapText(true);
        labelMensajeError.setMaxWidth(320);
        labelMensajeError.setVisible(false);

        labelBienvenida = new Label();
        labelBienvenida.setTextFill(Color.rgb(100, 255, 150));
        labelBienvenida.setFont(Font.font("System", FontWeight.BOLD, 14));
        labelBienvenida.setWrapText(true);
        labelBienvenida.setMaxWidth(320);
        labelBienvenida.setVisible(false);

        botonLogin = new Button("Iniciar sesión");
        botonLogin.setPrefHeight(42);
        botonLogin.setPrefWidth(320);
        botonLogin.setStyle(estilosBotonPrimario());
        botonLogin.setFont(Font.font("System", FontWeight.BOLD, 14));

        botonLimpiar = new Button("Limpiar");
        botonLimpiar.setPrefHeight(36);
        botonLimpiar.setPrefWidth(320);
        botonLimpiar.setStyle(estilosBotonSecundario());
        botonLimpiar.setFont(Font.font("System", 12));
        botonLimpiar.setOnAction(e -> limpiarCampos());

        linkRegistro = new Hyperlink("¿No tienes cuenta? Regístrate aquí");
        linkRegistro.setTextFill(Color.rgb(100, 150, 255));
        linkRegistro.setFont(Font.font("System", 12));
        linkRegistro.setStyle("-fx-border-color: transparent;");

        // Ensamblar
        if (logoView.getImage() != null) {
            tarjetaLogin.getChildren().add(logoView); // Agrega el logo arriba del título si existe
        }

        tarjetaLogin.getChildren().addAll(
                labelTitulo,
                labelSubtitulo,
                labelCorreo,
                campoCorreo,
                labelContrasena,
                contenedorContrasena,
                checkMostrarContrasena,
                labelMensajeError,
                labelBienvenida,
                botonLogin,
                botonLimpiar,
                linkRegistro
        );

        return tarjetaLogin;
    }

    private void configurarToggleContrasena() {
        checkMostrarContrasena.setOnAction(e -> {
            if (checkMostrarContrasena.isSelected()) {
                campoContrasenaVisible.setText(campoContrasena.getText());
                campoContrasena.setVisible(false);
                campoContrasena.setManaged(false);
                campoContrasenaVisible.setVisible(true);
                campoContrasenaVisible.setManaged(true);
            } else {
                campoContrasena.setText(campoContrasenaVisible.getText());
                campoContrasenaVisible.setVisible(false);
                campoContrasenaVisible.setManaged(false);
                campoContrasena.setVisible(true);
                campoContrasena.setManaged(true);
            }
        });
    }

    private void limpiarCampos() {
        campoCorreo.clear();
        campoContrasena.clear();
        campoContrasenaVisible.clear();
        checkMostrarContrasena.setSelected(false);
        labelMensajeError.setVisible(false);
        labelBienvenida.setVisible(false);
        campoContrasenaVisible.setVisible(false);
        campoContrasenaVisible.setManaged(false);
        campoContrasena.setVisible(true);
        campoContrasena.setManaged(true);
    }

    @Override
    public String getCorreo() {
        return campoCorreo.getText().trim();
    }

    @Override
    public String getContrasena() {
        if (checkMostrarContrasena.isSelected()) {
            return campoContrasenaVisible.getText();
        } else {
            return campoContrasena.getText();
        }
    }

    @Override
    public void mostrarError(String mensaje) {
        labelBienvenida.setVisible(false);
        labelMensajeError.setText("⚠ " + mensaje);
        labelMensajeError.setVisible(true);
    }

    @Override
    public void mostrarBienvenida(String nombreUsuario, String rolUsuario) {
        labelMensajeError.setVisible(false);
        // Corrección del espacio agregada aquí
        labelBienvenida.setText("✅ Bienvenido al sistema " + nombreUsuario + " (" + rolUsuario + ")");
        labelBienvenida.setVisible(true);
    }

    @Override
    public void inabilitarVista() {
        campoCorreo.setDisable(true);
        campoContrasena.setDisable(true);
        campoContrasenaVisible.setDisable(true);
        checkMostrarContrasena.setDisable(true);
        botonLogin.setDisable(true);
        botonLimpiar.setDisable(true);
        linkRegistro.setDisable(true);
        mostrarError("Cuenta bloqueada por seguridad. Contacte al centro de soporte.");
    }

    @Override
    public Button getBotonLogin() { return botonLogin; }

    @Override
    public Hyperlink getLinkRegistro() { return linkRegistro; }

    @Override
    public Stage getStage() { return stage; }

    // 4. ESTILOS MODIFICADOS PARA QUE SEAN TRANSPARENTES
    private String estilosCampoTexto() {
        return "-fx-background-color: rgba(255, 255, 255, 0.05);" + // Casi transparente
                "-fx-text-fill: white;" +
                "-fx-prompt-text-fill: rgba(255, 255, 255, 0.5);" + // Letra blanca tenue
                "-fx-background-radius: 8;" +
                "-fx-border-color: rgba(255, 255, 255, 0.4);" + // Borde blanco visible
                "-fx-border-radius: 8;" +
                "-fx-padding: 8 12;";
    }

    private String estilosBotonPrimario() {
        return "-fx-background-color: #2979ff;" + // Azul más vivo
                "-fx-text-fill: white;" +
                "-fx-background-radius: 8;" +
                "-fx-cursor: hand;";
    }

    private String estilosBotonSecundario() {
        return "-fx-background-color: rgba(255, 255, 255, 0.1);" +
                "-fx-text-fill: white;" +
                "-fx-background-radius: 8;" +
                "-fx-border-color: rgba(255, 255, 255, 0.3);" +
                "-fx-border-radius: 8;" +
                "-fx-cursor: hand;";
    }
}
