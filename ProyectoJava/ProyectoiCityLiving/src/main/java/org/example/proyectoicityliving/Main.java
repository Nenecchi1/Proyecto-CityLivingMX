package org.example.proyectoicityliving;

import org.example.proyectoicityliving.DAO.IUsuarioDAO;
import org.example.proyectoicityliving.DAO.UsuarioTXT;
import org.example.proyectoicityliving.model.IUsuario;


public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("=== PRUEBA DE PERSISTENCIA Y MODELO ===");
            IUsuarioDAO dao = new UsuarioTXT();

            // Genera usuarios.txt si no existe
            dao.verificarOCrearArchivo();

            // Consulta usuario base
            IUsuario usuario = dao.buscarPorCorreo("erick.polanco@cityliving.mx");
            if (usuario != null) {
                System.out.println("-> Registro localizado con éxito:");
                System.out.println("   ID: " + usuario.getId());
                System.out.println("   Nombre: " + usuario.getNombre());
                System.out.println("   Rol: " + usuario.getRol());
            } else {
                System.out.println("-> No se encontró el usuario.");
            }
        } catch (Exception e) {
            System.err.println("Error en la prueba: " + e.getMessage());
        }
    }
}