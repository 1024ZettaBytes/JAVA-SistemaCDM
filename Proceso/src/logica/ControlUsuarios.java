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
                Usuario u = new Usuario((Integer)arregloUsuario[0], (String)arregloUsuario[1],(String)arregloUsuario[2],(Boolean)arregloUsuario[3]);
                listaUsuarios.add(u);
            }
            
            
        }
    }

    public boolean agregarUsuario(Usuario nuevoUsuario) {
        
        if (conexion.insertarUsuario(nuevoUsuario.getNombre(), nuevoUsuario.getPass(), nuevoUsuario.isTipoAdmin())) {
            usuarioSiguiente = conexion.obtenUltimoID()+1;
            nuevoUsuario.setIdUsuario(usuarioSiguiente-1);
            return !listaUsuarios.contains(nuevoUsuario) && listaUsuarios.add(nuevoUsuario);
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
        Usuario u = new Usuario(id, "", "", false);
        return conexion.eliminarUsuario(id) && listaUsuarios.remove(u);
    }

    public ArrayList<Usuario> consultaUsuarios() {
        return listaUsuarios;
    }
    public Usuario usuarioPorId(int idUsuario){
        Usuario u = new Usuario(idUsuario, "", "", false);
        u = listaUsuarios.get(listaUsuarios.indexOf(u));
        return u;
    }
}
