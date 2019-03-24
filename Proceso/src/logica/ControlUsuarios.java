/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ArrayList;
import negocio.Usuario;

/**
 *
 * @author Eduardo Ramírez
 */
public class ControlUsuarios extends Control {

    private int usuarioSiguiente;

    public ControlUsuarios() {
        this.usuarioSiguiente = conexion.consultarSiguiente("u");
    }

    public boolean agregarUsuario(String nombre, String pass, boolean tipoAdmin) {
        Usuario u = new Usuario(usuarioSiguiente, nombre, pass, tipoAdmin);
        if (conexion.insertarUsuario(nombre, pass, tipoAdmin)) {
            usuarioSiguiente++;
            return !listaUsuarios.contains(u) && listaUsuarios.add(u);
        } else {
            return false;
        }
    }

    public boolean actualizarUsuario(int id, String nombre, String pass, boolean tipoAdmin) {
        Usuario u = new Usuario(id, nombre, pass, tipoAdmin);
        int index = listaUsuarios.indexOf(u);
        if (index >= 0 && conexion.actualizarUsuario(id, nombre, pass, tipoAdmin)) {
            listaUsuarios.set(index, u);
            return true;
        }
        return false;
    }

    public boolean eliminarUsuario(int id) {
        Usuario  u = new Usuario(id, "", "", false);
        return conexion.eliminarUsuario(id) && listaUsuarios.remove(u);
    }

    public ArrayList<Object> consultaUsuarios() {
        ArrayList usuarios = new ArrayList<Object>();
        for (Usuario u : listaUsuarios) {
            Object[] usuario = {u.getIdUsuario(), u.getNombre(), u.getPass(), u.isTipoAdmin()};
            usuarios.add(usuario);
        }
        return usuarios;
    }
}
