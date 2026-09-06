package org.example.proyectoicityliving.controller;

import org.example.proyectoicityliving.DAO.IUsuarioDAO;
import org.example.proyectoicityliving.DAO.UsuarioTXT;
import org.example.proyectoicityliving.views.ILoginVista;
import org.example.proyectoicityliving.model.IUsuario;

import org.example.proyectoicityliving.exception.FormatoInvalidoException;
import org.example.proyectoicityliving.exception.UsuarioNoEncontradoException;
import org.example.proyectoicityliving.exception.CuentaBloqueadaException;

import java.io.IOException;

/**
 * Clase LoginController que se encarga de controlar la vista de login
 *
 * Maneja la interacción entre la vista de login y el modelo de usuario,
 * permitiendo que los usuarios inicien sesión en la aplicación.
 */
public class LoginController {

    private final ILoginVista vista;
    private final IUsuarioDAO usuarioDAO;

    //conteo de intentos de inicio de sesión fallidos
    private int intentosFallidos = 0;
    private static final int MAX_INTENTOS = 3;

    /**
     * Constructor de LoginController
     *
     * @param vista la vista de login que se controlará
     */
    public LoginController(ILoginVista vista) {
        this.vista = vista;// Inicializa la vista de login
        this.usuarioDAO = new UsuarioTXT(); // Inicializa el DAO de usuario

        this.vista.getBotonLogin().setOnAction(event -> procesarInicioSesion());
        this.vista.getLinkRegistro().setOnAction(event -> navegarARegistro());
    }

    /**
     * Lógica principal que se ejecuta al presionar "Iniciar Sesión"
     */
    private void procesarInicioSesion() {
        // Obtener datos de la vista a través de la interfaz
        String correo = vista.getCorreo();
        String contrasena = vista.getContrasena();

        try {
            // Validar formato (Regex) ANTES de consultar base de datos
            validarFormato(correo, contrasena);

            // Autenticar contra el DAO (Una sola lectura a disco para optimizar rendimiento)
            IUsuario usuario = usuarioDAO.buscarPorCorreo(correo);

            // Verificar si el usuario no existe
            if (usuario == null) {
                throw new UsuarioNoEncontradoException("El usuario no está registrado.");
            }

            // 4. Validar si la contraseña es correcta
            if (usuario.getContrasena().equals(contrasena)) {
                // Login exitoso: Resetear intentos
                intentosFallidos = 0;

                // Enviar mensaje de éxito a la vista
                vista.mostrarBienvenida(usuario.getNombre(), usuario.getRol());

                // TODO: Aquí navegaremos al Dashboard según el rol del usuario

            } else {
                // Login fallido: Incrementar contador ANTES de evaluar el límite
                intentosFallidos++;

                // Validar bloqueo inmediato si alcanza los 3 fallos
                if (intentosFallidos >= MAX_INTENTOS) {
                    throw new CuentaBloqueadaException("Cuenta bloqueada por seguridad.");
                } else {
                    vista.mostrarError("Contraseña incorrecta. Intento " + intentosFallidos + " de " + MAX_INTENTOS + ".");
                }
            }

            // Manejo de excepciones personalizadas
        } catch (FormatoInvalidoException e) {
            vista.mostrarError(e.getMessage());

        } catch (UsuarioNoEncontradoException e) {
            vista.mostrarError(e.getMessage() + " Regístrese.");

        } catch (CuentaBloqueadaException e) {
            // Deshabilita la interfaz respetando la sintaxis original de tu clase
            vista.inabilitarVista();

        } catch (IOException e) {
            // Captura específica del error de lectura de archivo en lugar de Exception genérica
            vista.mostrarError("Error del sistema: No se pudo acceder a la base de datos.");
            e.printStackTrace();
        }
    }

    /**
     * Valida el correo y contraseña con Expresiones Regulares
     */
    private void validarFormato(String correo, String contrasena) throws FormatoInvalidoException {
        // Regla de formato para correo electrónico
        if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new FormatoInvalidoException("Formato de correo inválido (ej: usuario@dominio.com).");
        }

        //Regla restrictiva para contraseña (min 8 chars, 1 mayus, 1 minus, 1 num, 1 especial restrictivo)
        if (!contrasena.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$&])[A-Za-z\\d#$&]{8,}$")) {
            throw new FormatoInvalidoException("La contraseña debe tener mínimo 8 caracteres, mayúsculas, minúsculas, números y un carácter especial (#, $, &).");
        }
    }

    /**
     * Lógica para cambiar la vista a la pantalla de registro
     */
    private void navegarARegistro() {
        System.out.println("Navegando a la vista de registro...");
        // TODO: Lógica para cambiar la escena en JavaFX
    }
}
