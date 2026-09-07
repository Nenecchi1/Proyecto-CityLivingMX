package org.example.proyectoicityliving.model;

/**
 * Clase abstracta plantilla para todos los tipos de usuarios con lógica encapsulada de seguridad[cite: 27, 32].
 *
 * @author Polanco Romero Erick
 * @author Jardines Bandala Luis Antonio
 * @version 1.5
 */
public abstract class Usuario implements IUsuario {

    private final int id;
    private String correo;
    private String contrasena;
    private String nombre;
    private int intentosFallidos;
    private boolean bloqueado;

    /**
     * Constructor base para usuarios nuevos (sin estado de bloqueo)[cite: 32].
     *
     * @param id         Identificador único numérico[cite: 32].
     * @param correo     Correo electrónico válido[cite: 32].
     * @param contrasena Contraseña que cumple con la política de seguridad[cite: 27, 32].
     * @param nombre     Nombre completo[cite: 32].
     */
    public Usuario(int id, String correo, String contrasena, String nombre) {
        this(id, correo, contrasena, nombre, 0, false);
    }

    /**
     * Constructor completo para reconstrucción desde persistencia[cite: 32].
     *
     * @param id               Identificador único numérico[cite: 32].
     * @param correo           Correo electrónico[cite: 32].
     * @param contrasena       Contraseña registrada[cite: 32].
     * @param nombre           Nombre completo[cite: 32].
     * @param intentosFallidos Número acumulado de fallos[cite: 32].
     * @param bloqueado        Estado de bloqueo de la cuenta[cite: 32].
     */
    public Usuario(int id, String correo, String contrasena, String nombre, int intentosFallidos, boolean bloqueado) {
        this.id = id;
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombre = nombre;
        this.intentosFallidos = intentosFallidos;
        this.bloqueado = bloqueado;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int getIntentosFallidos() {
        return intentosFallidos;
    }

    @Override
    public void setIntentosFallidos(int intentosFallidos) {
        this.intentosFallidos = intentosFallidos;
    }

    @Override
    public boolean esBloqueado() {
        return bloqueado;
    }

    @Override
    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    @Override
    public boolean validarContrasena(String contrasenaIngresada) {
        return this.contrasena != null && this.contrasena.equals(contrasenaIngresada);
    }

    @Override
    public void registrarIntentoFallido() {
        this.intentosFallidos++;
        if (this.intentosFallidos >= 3) {
            this.bloqueado = true;
        }
    }

    @Override
    public void reiniciarIntentos() {
        this.intentosFallidos = 0;
    }

    @Override
    public void desbloquear() {
        this.intentosFallidos = 0;
        this.bloqueado = false;
    }

    @Override
    public abstract String getRol();
}