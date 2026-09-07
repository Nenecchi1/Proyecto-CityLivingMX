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
 * Vista del panel de administración help-desk para desbloqueo de usuarios.
 * <p>
 * Implementa la infraestructura de {@link VistaBase} para garantizar
 * la estética corporativa y las dimensiones estándar de ventana.
 * </p>
 *
 * @author Polanco Romero Erick
 * @author Jardines Bandala Luis Antonio
 * @version 2.8
 */
public class AdministradorVista extends VistaBase {

    private Label labelBienvenida;
    private Button botonDesbloquearCuenta;
    private Button botonCerrarSesion;
    private ListView<String> listaCuentasBloqueadas;

    /**
     * Construye e inicializa la ventana de administración.
     *
     * @param stage Escenario en el que se proyecta la escena.
     */
    public AdministradorVista(Stage stage) {
        super(stage);
        inicializarComponentes();
    }

    /**
     * Configura la distribución visual de componentes y controles.
     */
    private void inicializarComponentes() {
        StackPane root = crearContenedorRaizConFondo();

        BorderPane layoutPrincipal = new BorderPane();

        // Encabezado corporativo superior con el logo en la esquina izquierda
        HBox encabezado = crearEncabezadoUsuario("Mesa de Ayuda - Panel Administrativo");
        layoutPrincipal.setTop(encabezado);

        VBox tarjetaAdmin = new VBox(15);
        tarjetaAdmin.setAlignment(Pos.CENTER);
        tarjetaAdmin.setPadding(new Insets(25));
        tarjetaAdmin.setMaxWidth(580);
        tarjetaAdmin.setMaxHeight(480);

        tarjetaAdmin.setStyle(
                "-fx-background-color: rgba(30, 35, 45, 0.85);" +
                        "-fx-background-radius: 15;" +
                        "-fx-border-color: rgba(255, 255, 255, 0.15);" +
                        "-fx-border-radius: 15;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 15, 0, 0, 8);"
        );

        labelBienvenida = new Label("Panel de Administración de Cuentas");
        labelBienvenida.setFont(Font.font("System", FontWeight.BOLD, 18));
        labelBienvenida.setTextFill(Color.WHITE);

        Label labelInstrucciones = new Label("Cuentas bloqueadas por intentos fallidos de autenticación:");
        labelInstrucciones.setTextFill(Color.rgb(180, 180, 200));

        listaCuentasBloqueadas = new ListView<>();
        listaCuentasBloqueadas.setMaxWidth(480);
        listaCuentasBloqueadas.setPrefHeight(210);
        listaCuentasBloqueadas.setStyle("-fx-background-color: rgba(255, 255, 255, 0.05); -fx-control-inner-background: rgba(20, 24, 33, 0.8);");

        HBox cajaBotones = new HBox(15);
        cajaBotones.setAlignment(Pos.CENTER);

        botonDesbloquearCuenta = new Button("Restablecer Acceso Seleccionado");
        botonDesbloquearCuenta.setPrefWidth(280);
        botonDesbloquearCuenta.setStyle(estilosBotonPrimario());

        botonCerrarSesion = new Button("Cerrar Sesión");
        botonCerrarSesion.setPrefWidth(150);
        botonCerrarSesion.setStyle(estilosBotonSecundario());

        cajaBotones.getChildren().addAll(botonDesbloquearCuenta, botonCerrarSesion);

        tarjetaAdmin.getChildren().addAll(
                labelBienvenida,
                labelInstrucciones,
                listaCuentasBloqueadas,
                cajaBotones
        );

        layoutPrincipal.setCenter(tarjetaAdmin);
        root.getChildren().add(layoutPrincipal);

        Scene scene = new Scene(root, ANCHO_VENTANA, ALTO_VENTANA);
        this.stage.setTitle("City Living MX - Panel de Administración");
        this.stage.setScene(scene);
        this.stage.show();
    }

    /**
     * Actualiza el saludo inicial con el nombre del administrador activo.
     *
     * @param nombreAdmin Nombre del usuario administrativo.
     */
    public void setNombreAdministrador(String nombreAdmin) {
        labelBienvenida.setText("Bienvenido Administrador: " + nombreAdmin);
    }

    /** @return Botón para desbloquear la cuenta seleccionada. */
    public Button getBotonDesbloquearCuenta() {
        return botonDesbloquearCuenta;
    }

    /** @return Botón para cerrar la sesión administrativa. */
    public Button getBotonCerrarSesion() {
        return botonCerrarSesion;
    }

    /** @return Lista con los correos de cuentas bloqueadas. */
    public ListView<String> getListaCuentasBloqueadas() {
        return listaCuentasBloqueadas;
    }
}