package org.example.proyectoicityliving.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.example.proyectoicityliving.exception.PasswordIncorrectoException;
import org.example.proyectoicityliving.exception.PersistenciaException;
import org.example.proyectoicityliving.exception.UsuarioNoEncontradoException;
import org.example.proyectoicityliving.model.Administrador;
import org.example.proyectoicityliving.model.IUsuario;
import org.example.proyectoicityliving.model.UsuarioGeneral;

/**
 * Implementación de la capa de acceso a datos que opera sobre un archivo de texto plano delimitado.
 * <p>
 * Gestiona la lectura, escritura y sincronización del archivo local {@code usuarios.txt},
 * garantizando la persistencia del estado de seguridad e intentos fallidos.
 * </p>
 *
 * @author Polanco Romero Erick
 * @version 1.5
 */
public class UsuarioTXT implements IUsuarioDAO {

    /** Ruta relativa del archivo de almacenamiento plano. */
    private static final String RUTA_ARCHIVO = "usuarios.txt";

    /**
     * Verifica la existencia del archivo de persistencia local y genera los datos base si no existe.
     *
     * @return Objeto {@link File} asociado a la ruta de almacenamiento.
     * @throws PersistenciaException Si ocurre una falla I/O al intentar crear el archivo.
     */
    @Override
    public File verificarOCrearArchivo() throws PersistenciaException {
        File archivo = new File(RUTA_ARCHIVO);

        if (!archivo.exists()) {
            try {
                if (archivo.createNewFile()) {
                    inicializarUsuariosBase();
                }
            } catch (IOException e) {
                throw new PersistenciaException("Error al intentar crear el archivo de persistencia local.", e);
            }
        }
        return archivo;
    }

    /**
     * Lee de forma secuencial el archivo de texto y convierte cada línea en un objeto {@link IUsuario}.
     *
     * @return Lista con todos los usuarios encontrados en el archivo.
     * @throws PersistenciaException Si ocurre un error I/O durante la lectura del archivo.
     */
    @Override
    public List<IUsuario> obtenerTodos() throws PersistenciaException {
        File archivo = verificarOCrearArchivo();
        List<IUsuario> lista = new ArrayList<>();

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                IUsuario u = parsearLinea(linea);
                if (u != null) {
                    lista.add(u);
                }
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error I/O al recuperar todos los usuarios.", e);
        }
        return lista;
    }

    /**
     * Busca un usuario por su dirección de correo electrónico recorriendo el archivo de texto.
     *
     * @param correo Correo electrónico a consultar.
     * @return Objeto {@link IUsuario} hallado en el archivo.
     * @throws UsuarioNoEncontradoException Si no existe ningún registro asociado al correo.
     * @throws PersistenciaException        Si ocurre un error I/O durante la consulta.
     */
    @Override
    public IUsuario buscarPorCorreo(String correo) throws UsuarioNoEncontradoException, PersistenciaException {
        File archivo = verificarOCrearArchivo();

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                IUsuario u = parsearLinea(linea);
                if (u != null && u.getCorreo().equalsIgnoreCase(correo.trim())) {
                    return u;
                }
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error I/O al leer la base de datos local.", e);
        }

        throw new UsuarioNoEncontradoException("La cuenta con el correo '" + correo + "' no se encuentra registrada.");
    }

    /**
     * Valida las credenciales comprobando si la contraseña ingresada coincide con la almacenada.
     *
     * @param correo     Correo de acceso del usuario.
     * @param contrasena Contraseña ingresada para validar.
     * @return {@code true} si la autenticación es satisfactoria.
     * @throws UsuarioNoEncontradoException Si el correo no existe en el registro.
     * @throws PasswordIncorrectoException  Si la contraseña proporcionada es errónea.
     * @throws PersistenciaException        Si se produce una falla I/O durante la búsqueda.
     */
    @Override
    public boolean validarCredenciales(String correo, String contrasena)
            throws UsuarioNoEncontradoException, PasswordIncorrectoException, PersistenciaException {

        IUsuario usuario = buscarPorCorreo(correo);

        if (!usuario.validarContrasena(contrasena)) {
            throw new PasswordIncorrectoException("Contraseña incorrecta para la cuenta: " + correo);
        }

        return true;
    }

    /**
     * Añade una nueva línea con los datos serializados del usuario al final del archivo.
     *
     * @param usuario Instancia de {@link IUsuario} a añadir.
     * @throws PersistenciaException Si ocurre un error I/O durante el apéndice de texto.
     */
    @Override
    public void registrarUsuario(IUsuario usuario) throws PersistenciaException {
        verificarOCrearArchivo();

        try (PrintWriter escritor = new PrintWriter(new FileWriter(RUTA_ARCHIVO, true))) {
            escritor.println(construirLineaRegistro(usuario));
        } catch (IOException e) {
            throw new PersistenciaException("Error I/O al escribir el nuevo registro en usuarios.txt", e);
        }
    }

    /**
     * Reemplaza la línea correspondiente al usuario dado y reescribe completamente el archivo.
     *
     * @param usuarioModificado Instancia de {@link IUsuario} con la información actualizada.
     * @throws PersistenciaException Si no se localiza el usuario o falla la reescritura I/O.
     */
    @Override
    public void actualizarUsuario(IUsuario usuarioModificado) throws PersistenciaException {
        File archivo = verificarOCrearArchivo();
        List<String> lineasActualizadas = new ArrayList<>();
        boolean encontrado = false;

        try (BufferedReader lector = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split("\\|");
                if (datos.length >= 2 && datos[1].trim().equalsIgnoreCase(usuarioModificado.getCorreo())) {
                    lineasActualizadas.add(construirLineaRegistro(usuarioModificado));
                    encontrado = true;
                } else {
                    lineasActualizadas.add(linea);
                }
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error I/O al leer el archivo para actualización.", e);
        }

        if (!encontrado) {
            throw new PersistenciaException("No se encontró el usuario a actualizar: " + usuarioModificado.getCorreo());
        }

        try (PrintWriter escritor = new PrintWriter(new FileWriter(archivo, false))) {
            for (String l : lineasActualizadas) {
                escritor.println(l);
            }
        } catch (IOException e) {
            throw new PersistenciaException("Error I/O al reescribir el archivo de persistencia.", e);
        }
    }

    /**
     * Habilita de nuevo una cuenta previamente bloqueada aprovechando el método de dominio de la entidad.
     *
     * @param correo Dirección de correo electrónico del usuario a desbloquear.
     * @throws UsuarioNoEncontradoException Si la cuenta no está registrada.
     * @throws PersistenciaException        Si falla la actualización en disco.
     */
    @Override
    public void desbloquear(String correo) throws UsuarioNoEncontradoException, PersistenciaException {
        IUsuario usuario = buscarPorCorreo(correo);
        usuario.desbloquear();
        actualizarUsuario(usuario);
    }

    /**
     * Parsea una cadena de texto delimitada por tuberías y construye la instancia de usuario correspondiente.
     *
     * @param linea Cadena de texto proveniente de una línea del archivo.
     * @return Instancia de {@link IUsuario} (Administrador o UsuarioGeneral), o {@code null} si es inválida.
     * @throws PersistenciaException Si los datos numéricos o booleanos presentan corrupción de formato.
     */
    private IUsuario parsearLinea(String linea) throws PersistenciaException {
        String[] datos = linea.split("\\|");
        if (datos.length < 5) return null;

        try {
            int idTxt = Integer.parseInt(datos[0].trim());
            String correoTxt = datos[1].trim();
            String contrasenaTxt = datos[2].trim();
            String nombreTxt = datos[3].trim();
            String rolTxt = datos[4].trim();

            int intentosTxt = (datos.length >= 6) ? Integer.parseInt(datos[5].trim()) : 0;
            boolean bloqueadoTxt = (datos.length >= 7) && Boolean.parseBoolean(datos[6].trim());

            if (rolTxt.equalsIgnoreCase("Administrador")) {
                return new Administrador(idTxt, correoTxt, contrasenaTxt, nombreTxt, intentosTxt, bloqueadoTxt);
            } else {
                return new UsuarioGeneral(idTxt, correoTxt, contrasenaTxt, nombreTxt, intentosTxt, bloqueadoTxt);
            }
        } catch (NumberFormatException e) {
            throw new PersistenciaException("Corrupción de datos en usuarios.txt", e);
        }
    }

    /**
     * Convierte un objeto {@link IUsuario} en un formato plano serializado mediante tuberías (`|`).
     *
     * @param usuario Instancia de usuario a formatear.
     * @return Cadena formateada lista para persistir en archivo.
     */
    private String construirLineaRegistro(IUsuario usuario) {
        return usuario.getId() + "|" +
                usuario.getCorreo() + "|" +
                usuario.getContrasena() + "|" +
                usuario.getNombre() + "|" +
                usuario.getRol() + "|" +
                usuario.getIntentosFallidos() + "|" +
                usuario.esBloqueado();
    }

    /**
     * Genera el conjunto de registros por defecto con contraseñas que cumplen la regla RS-02.
     *
     * @throws PersistenciaException Si ocurre una falla I/O durante la generación de registros iniciales.
     */
    private void inicializarUsuariosBase() throws PersistenciaException {
        try (PrintWriter escritor = new PrintWriter(new FileWriter(RUTA_ARCHIVO, true))) {
            escritor.println("1|erick.polanco@cityliving.mx|Password123#|Erick Polanco|Administrador|0|false");
            escritor.println("2|antonio.jardines@cityliving.mx|Password2026$&|Luis Antonio Jardines|Administrador|0|false");
            escritor.println("3|raul.jara@cityliving.mx|Profesor2026#|Raul Jara|Usuario|0|false");
            escritor.println("4|ninja.copia@cityliving.mx|Obito2026#|Kakashi Hatake|Usuario|0|false");
            escritor.println("5|Yamsha.lindo@cityliving.mx|puar|Shamya Zepeda|Usuario|0|false");

            escritor.println("6|admin.bloqueado@cityliving.mx|AdminBloqueado2026#|Administrador Bloqueado|Administrador|3|true");
            escritor.println("7|usuario.bloqueado@cityliving.mx|UserBloqueado2026#|Usuario Bloqueado|Usuario|3|true");
        } catch (IOException e) {
            throw new PersistenciaException("Error I/O al generar los datos iniciales de usuarios.txt", e);
        }
    }
}