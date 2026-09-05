package org.example.proyectocityliving.DAO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Clase para verificar o crear el arichvo usuarios.txt para
 * simular la base de datos en el prototipo 1
 * @author Polanco Romero Erick
 *
 */
public class UsuarioTXT implements IUsuarioDAO{

    private static final String URL = "usuarios.txt";

    public static void verificarOCrear() trows IOException {
        File archivo = new File("usuarios.txt");

        if (!archivo.exists()){
            System.out.println("[INFO] El archivo 'usuarios.txt' no existe. Creando archivo...");

            boolean seCreo = archivo.createNewFile();
            if (seCreo){
                System.out.println("[ÉXITO] Archivo 'usuarios.txt' creado correctamente.");
                inicializarUsuariosBase();
            }
        }
        else {
            System.out.println("[INFO] El archivo 'usuarios.txt' ya existe y está listo.");
        }
    }

    Usuario buscarPorCorreo(String correo) throws IOException{

    }

    boolean validarCredenciales(String correo, String contrasena) throws IOException {

    }

    private static void inicializarUsuariosBase() throws IOException {

        try (PrintWriter escritor = new PrintWriter(new FileWriter(RUTA_ARCHIVO, true))) {
            escritor.println("erick.polanco@cityliving.mx|Password123#|Erick Polanco|Administrador");
            escritor.println("antonio.jardines@cityliving.mx|User2026$&|Luis Antonio Jardines|Usuario");
            escritor.println("raul.jara@cityliving.mx|Profesor2026#|Raul Jara|Administrador");
        }
        System.out.println("[ÉXITO] Datos iniciales insertados en usuarios.txt.");
    }

}