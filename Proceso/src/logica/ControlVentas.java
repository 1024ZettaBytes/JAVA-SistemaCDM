/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import Interfaces.IConexion;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import negocio.Usuario;
import negocio.Venta;
import negocio.VentaPlatillo;

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
                String pattern = "yyyy-MM-dd hh:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                Date fecha = null;
                try {
                    fecha = simpleDateFormat.parse((String) arregloVenta[1]);
      
                } catch (ParseException ex) {
                    Logger.getLogger(ControlReservas.class.getName()).log(Level.SEVERE, null, ex);
                }
                Venta v = new Venta((Integer) arregloVenta[0], new Timestamp(fecha.getTime()), Control.usuarios.consultarPorId((Integer)arregloVenta[2]));
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
    public Venta consultarVentaPorId(int idVenta) {
        for (Venta listaVenta : listaVentas) {
            if(listaVenta.getFolioVenta() ==idVenta ){
                return listaVenta;
            }
        }
        return null;
        
    }
    public Venta consultarUltimaVenta(){
       return listaVentas.get(listaVentas.size()-1);
    }
}

