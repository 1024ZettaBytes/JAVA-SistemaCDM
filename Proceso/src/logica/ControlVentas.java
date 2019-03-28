/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import Interfaces.IConexion;
import java.sql.Timestamp;
import java.util.ArrayList;
import negocio.Usuario;
import negocio.Venta;

/**
 *
 * @author Eduardo Ramírez
 */
public class ControlVentas {
     private ArrayList<Venta> listaVentas;
    private int ventaSiguiente;
    private IConexion conexion;

    public ControlVentas(IConexion conexion) {
        listaVentas = new ArrayList<>();
        this.conexion = conexion;
        if (this.conexion.conectar()) {
            ArrayList<Object[]> arreglosVentas = conexion.consultarVentas();
            for (Object[] arregloVenta : arreglosVentas) {
                Venta v = new Venta((Integer) arregloVenta[0], (Timestamp)arregloVenta[1], Control.usuarios.consultarPorId((Integer)arregloVenta[1]));
                listaVentas.add(v);
            }
            
            
        }
    }

    public boolean agregar(Venta nuevaVenta) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        if (conexion.insertarVenta(nuevaVenta.getFechaHora(), nuevaVenta.getUsuario().getIdUsuario())) {
           
            ventaSiguiente = conexion.obtenUltimoID()+1;
             nuevaVenta.setFolioVenta(ventaSiguiente-1);
            return !listaVentas.contains(nuevaVenta) && listaVentas.add(nuevaVenta);
        } else {
            return false;
        }
    }

    public boolean actualizar(Venta venta) {
if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        int index = listaVentas.indexOf(venta);
        if (index >= 0 && conexion.actualizarVenta(venta.getFolioVenta(), venta.getFechaHora(), venta.getUsuario().getIdUsuario())) {
            listaVentas.set(index, venta);
            return true;
        }
        return false;
    }

    public boolean eliminar(int folioVenta) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        Venta v = new Venta(folioVenta, null, null);
        return conexion.eliminarVenta(folioVenta) && listaVentas.remove(v);
    }

    public ArrayList<Venta> consultarLista() {

        return listaVentas;
    }
}
