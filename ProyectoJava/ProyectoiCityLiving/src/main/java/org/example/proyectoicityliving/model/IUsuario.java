package org.example.proyectoicityliving.model;

/**
 * Interfaz base que consolida el contrato de comportamiento para los usuarios[cite: 31].
 * <p>
 * Aplica el principio de Inversión de Dependencias (DIP) e integra la gestión
 * de comportamiento para el control de accesos y bloqueo por seguridad[cite: 27, 31].
 * </p>
 *
 * @author Polanco Romero Erick
 * @version 1.5
 */
public interface IUsuario {

    /**
     * Obtiene el identificador único numérico del usuario[cite: 31].
     *
     * @return El ID del usuario como número entero[cite: 31].
     */
    int getId();

    /**
     * Obtiene el correo electrónico registrado[cite: 31].
     *
     * @return Cadena con la dirección de correo electrónico[cite: 31].
     */
    String getCorreo();

    /**
     * Obtiene la contraseña almacenada del usuario[cite: 31].
     *
     * @return Cadena con la clave de acceso[cite: 31].
     */
    String getContrasena();

    /**
     * Obtiene el nombre completo del usuario[cite: 31].
     *
     * @return Cadena con el nombre y apellidos[cite: 31].
     */
    String getNombre();

    /**
     * Obtiene el rol asignado al usuario dentro de la plataforma[cite: 31].
     *
     * @return Cadena con la descripción del rol[cite: 31].
     */
    String getRol();

    /**
     * Valida si la contraseña ingresada coincide con la clave registrada (RS-03)[cite: 27, 31].
     *
     * @param contrasenaIngresada Contraseña proporcionada en la autenticación[cite: 31].
     * @return {@code true} si la contraseña coincide; {@code false} en caso contrario[cite: 31].
     */
    boolean validarContrasena(String contrasenaIngresada);

    /**
     * Obtiene el número acumulado de intentos fallidos de inicio de sesión[cite: 31].
     *
     * @return Entero con la cantidad de intentos erróneos[cite: 31].
     */
    int getIntentosFallidos();

    /**
     * Establece el número acumulado de intentos fallidos[cite: 31].
     *
     * @param intentos Cifra acumulada de intentos erróneos[cite: 31].
     */
    void setIntentosFallidos(int intentos);

    /**
     * Consulta si la cuenta se encuentra suspendida o bloqueada por seguridad[cite: 31].
     *
     * @return {@code true} si la cuenta está bloqueada; {@code false} si está activa[cite: 31].
     */
    boolean esBloqueado();

    /**
     * Modifica el estado de bloqueo de la cuenta del usuario[cite: 31].
     *
     * @param bloqueado {@code true} para suspender el acceso; {@code false} para habilitarlo[cite: 31].
     */
    void setBloqueado(boolean bloqueado);

    /**
     * Incremente en 1 el contador de intentos fallidos y bloquea la cuenta automáticamente al alcanzar 3 intentos[cite: 27].
     */
    void registrarIntentoFallido();

    /**
     * Restablece el contador de intentos fallidos a 0 tras un inicio de sesión exitoso[cite: 27].
     */
    void reiniciarIntentos();

    /**
     * Restablece la cuenta a estado activo, limpiando los intentos fallidos y retirando el bloqueo[cite: 27, 30].
     */
    void desbloquear();
}