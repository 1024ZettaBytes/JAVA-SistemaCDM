/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import Interfaces.IConexion;
import java.util.ArrayList;
import negocio.Producto;

/**
 *
 * @author Eduardo Ramírez
 */
public class ControlProductos {
 private ArrayList<Producto> listaProductos;
    private int productoSiguiente;
    private IConexion conexion;

    public ControlProductos(IConexion conexion) {
        this.conexion = conexion;
        this.listaProductos = new ArrayList<>();
        if (this.conexion.conectar()) {
            ArrayList<Object[]> arreglosProductos = conexion.consultarProductos();
            for (Object[] arregloProducto : arreglosProductos) {
                Producto p = new Producto((Integer)arregloProducto[0], (String)arregloProducto[1],(Float)arregloProducto[2]);
                listaProductos.add(p);
            }
            
            
        }
    }

    public boolean agregar(Producto nuevoProducto) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        if (conexion.insertarProducto(nuevoProducto.getNombre(), nuevoProducto.getStock())) {
            productoSiguiente = conexion.obtenUltimoID()+1;
            nuevoProducto.setIdProducto(productoSiguiente-1);
            return !listaProductos.contains(nuevoProducto) && listaProductos.add(nuevoProducto);
        } else {
            return false;
        }
    }

    public boolean actualizar(Producto producto) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        int index = listaProductos.indexOf(producto);
        if (index >= 0 && conexion.actualizarProducto(producto.getIdProducto(), producto.getNombre(), producto.getStock())) {
            listaProductos.set(index, producto);
            return true;
        }
        return false;
    }

    public boolean eliminar(int idProducto) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        Producto p = new Producto(idProducto,"", 0);
        return conexion.eliminarProducto(idProducto) && listaProductos.remove(p);
    }

    public ArrayList<Producto> consultarLista() {
        return listaProductos;
    }
     
}
