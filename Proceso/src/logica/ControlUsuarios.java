/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import Conexion.ConexionBD;
import Interfaces.IConexion;
import java.util.ArrayList;
import negocio.Cliente;
import negocio.Usuario;

/**
 *
 * @author Eduardo Ramírez
 */
public class ControlUsuarios {

    private ArrayList<Usuario> listaUsuarios;
    private int usuarioSiguiente;
    private IConexion conexion;

    public ControlUsuarios(IConexion conexion) {
        this.conexion = conexion;
        this.listaUsuarios = new ArrayList<>();
        if (this.conexion.conectar()) {
            ArrayList<Object[]> arreglosUsuarios = conexion.consultarUsuarios();
            for (Object[] arregloUsuario : arreglosUsuarios) {
                System.out.println();
                Usuario u = new Usuario((Integer)arregloUsuario[0], (String)arregloUsuario[1],(String)arregloUsuario[2],(Boolean)arregloUsuario[3]);
                listaUsuarios.add(u);
            }
            
            
        }
    }

    public boolean agregar(Usuario nuevoUsuario) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        if (conexion.insertarUsuario(nuevoUsuario.getNombre(), nuevoUsuario.getPass(), nuevoUsuario.isTipoAdmin())) {
            usuarioSiguiente = conexion.obtenUltimoID()+1;
            nuevoUsuario.setIdUsuario(usuarioSiguiente-1);
            return !listaUsuarios.contains(nuevoUsuario) && listaUsuarios.add(nuevoUsuario);
        } else {
            return false;
        }
    }

    public boolean actualizar(Usuario usuario) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        int index = listaUsuarios.indexOf(usuario);
        if (index >= 0 && conexion.actualizarUsuario(usuario.getIdUsuario(), usuario.getNombre(), usuario.getPass(), usuario.isTipoAdmin())) {
            listaUsuarios.set(index, usuario);
            return true;
        }
        return false;
    }

    public boolean eliminar(int id) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        Usuario u = new Usuario(id, "", "", false);
        return conexion.eliminarUsuario(id) && listaUsuarios.remove(u);
    }

    public ArrayList<Usuario> consultarLista() {
        return listaUsuarios;
    }
    public Usuario consultarPorId(int idUsuario){
        Usuario u = new Usuario(idUsuario, "", "", false);
        u = listaUsuarios.get(listaUsuarios.indexOf(u));
        return u;
    }
}
