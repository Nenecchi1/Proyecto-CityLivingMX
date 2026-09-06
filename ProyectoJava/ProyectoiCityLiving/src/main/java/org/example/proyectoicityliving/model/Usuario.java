package org.example.proyectoicityliving.model;

/**
 * Clase abtracta para usuarios, sera la clase padre
 * @author Polanco Romero Erick
 * @version 1.1
 */
public abstract class Usuario implements IUsuario{
    protected int id;
    protected String correo;
    protected String contrasena;
    protected String nombre;

    /**
     * Cosntructor de la clase usuario
     * @param correo cadena de texto
     * @param contrasena cadena de texto
     * @param nombre cadena de texto
     */
    public Usuario(int id, String correo, String contrasena, String nombre) {
        this.id = id;
        this.correo = correo;
        this.contrasena = contrasena;
        this.nombre = nombre;
    }

    /**
     * Mètodo abstracto get para visualizar el id del usuario
     * @return cadena de texto
     */
    public int getId() {
        return id;
    }

    /**
     * Get para visualizar el correo del usuario
     * @return cadena de texto
     */
    public String getCorreo() { return correo; }
    /**
     * Set para modificar el correo del usuario
     */
    public void setCorreo(String correo) { this.correo = correo; }

    /**
     * Get para visualizar la contraseña del usuario
     * @return cadena de texto
     */
    public String getContrasena() { return contrasena; }

    /**
     * Set para modificar la contraseña del usuario
     */
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    /**
     * Get para visualizar el nombre del usuario
     * @return cadena de texto
     */
    public String getNombre() { return nombre; }

    /**
     * Set para modificar el nombre del usuario
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /**
     * Mètodo abstracto get para visualizar el rol del usuario
     * @return cadena de texto
     */
    public abstract String getRol();
}