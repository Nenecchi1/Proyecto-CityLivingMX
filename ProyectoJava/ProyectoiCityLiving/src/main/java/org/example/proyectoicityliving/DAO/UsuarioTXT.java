package org.example.proyectoicityliving.DAO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.example.proyectoicityliving.model.Administrador;
import org.example.proyectoicityliving.model.IUsuario;
import org.example.proyectoicityliving.model.UsuarioGeneral;

/**
 * Implementación de acceso a datos que utiliza un archivo de texto plano delimitado por tuberías.
 * <p>
 * Estructura del archivo {@code usuarios.txt}: {@code id|correo|contraseña|nombre|rol}
 * </p>

 * @author Polanco Romero Erick
 * @version 1.2
 */
public class UsuarioTXT implements IUsuarioDAO {

    /** Ruta relativa del archivo plano de almacenamiento. */
    private static final String RUTA_ARCHIVO = "usuarios.txt";

    /**
     * Verifica la existencia física del archivo. Si no existe, lo genera con datos iniciales.
     *
     * @return Objeto {@link File} del archivo de persistencia listo para operaciones.
     * @throws IOException Si ocurre un error de lectura o creación del archivo.
     */
    @Override
    public File verificarOCrearArchivo() throws IOException {
        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists()) {
            System.out.println("[INFO] El archivo 'usuarios.txt' no existe. Creando archivo...");
            if (archivo.createNewFile()) {
                System.out.println("[ÉXITO] Archivo 'usuarios.txt' creado correctamente.");
                inicializarUsuariosBase();
            }
        }
        return archivo;
    }

    /**
     * Realiza una búsqueda secuencial por correo dentro del archivo de texto.
     *
     * @param correo Correo a consultar.
     * @return Instancia polimórfica de {@link IUsuario} si se encuentra; {@code null} en caso contrario.
     * @throws IOException Si ocurre un error durante la lectura.
     */
    @Override
    public IUsuario buscarPorCorreo(String correo) throws IOException {
        File archivo = verificarOCrearArchivo();

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split("\\|");

                // Se requieren al menos 5 columnas: id | correo | contrasena | nombre | rol
                if (datos.length >= 5) {
                    int idTxt = Integer.parseInt(datos[0].trim());
                    String correoTxt = datos[1].trim();
                    String contrasenaTxt = datos[2].trim();
                    String nombreTxt = datos[3].trim();
                    String rolTxt = datos[4].trim();

                    if (correoTxt.equalsIgnoreCase(correo.trim())) {
                        if (rolTxt.equalsIgnoreCase("Administrador")) {
                            return new Administrador(idTxt, correoTxt, contrasenaTxt, nombreTxt);
                        } else {
                            return new UsuarioGeneral(idTxt, correoTxt, contrasenaTxt, nombreTxt);
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("[ERROR] Formato de ID inválido en una línea del archivo usuarios.txt");
        }
        return null;
    }

    /**
     * Compara las credenciales ingresadas con el registro existente.
     *
     * @param correo Correo de acceso.
     * @param contrasena Contraseña a validar.
     * @return {@code true} si coinciden las credenciales, {@code false} de lo contrario.
     * @throws IOException Si ocurre un error en la consulta.
     */
    @Override
    public boolean validarCredenciales(String correo, String contrasena) throws IOException {
        IUsuario usuario = buscarPorCorreo(correo);
        return (usuario != null) && usuario.getContrasena().equals(contrasena);
    }

    /**
     * Registra un nuevo usuario en el archivo de texto plano agregando una línea al final.
     *
     * @param usuario Objeto usuario a guardar.
     * @throws IOException Si ocurre un error de escritura.
     */
    @Override
    public void registrarUsuario(IUsuario usuario) throws IOException {
        verificarOCrearArchivo();

        try (PrintWriter escritor = new PrintWriter(new FileWriter(RUTA_ARCHIVO, true))) {
            escritor.println(usuario.getId() + "|" +
                    usuario.getCorreo() + "|" +
                    usuario.getContrasena() + "|" +
                    usuario.getNombre() + "|" +
                    usuario.getRol());
        }
        System.out.println("[ÉXITO] Usuario '" + usuario.getCorreo() + "' registrado correctamente.");
    }

    /**
     * Escribe la semilla de datos inicial con la columna ID integrada.
     *
     * @throws IOException Si ocurre un error al escribir el archivo.
     */
    private void inicializarUsuariosBase() throws IOException {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(RUTA_ARCHIVO, true))) {
            escritor.println("1|erick.polanco@cityliving.mx|Password123#|Erick Polanco|Administrador");
            escritor.println("2|antonio.jardines@cityliving.mx|User2026$&|Luis Antonio Jardines|Usuario");
            escritor.println("3|raul.jara@cityliving.mx|Profesor2026#|Raul Jara|Administrador");
        }
        System.out.println("[ÉXITO] Datos base (con ID) insertados en usuarios.txt.");
    }
}