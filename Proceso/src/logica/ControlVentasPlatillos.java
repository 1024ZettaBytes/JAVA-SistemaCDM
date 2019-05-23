/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import Interfaces.IConexion;
import java.util.ArrayList;
import negocio.VentaPlatillo;

/**
 *
 * @author ed000
 */
public class ControlVentasPlatillos {

    private ArrayList<VentaPlatillo> listaVentasPlatillos;
    private int ventaPlatilloSiguiente;
    private IConexion conexion;

    public ControlVentasPlatillos(IConexion conexion) {
        listaVentasPlatillos = new ArrayList<>();
        this.conexion = conexion;
        if (this.conexion.conectar()) {
            ArrayList<Object[]> arreglosVentas = conexion.consultarVentasPlatillos();
            for (Object[] arregloVenta : arreglosVentas) {
                VentaPlatillo v = new VentaPlatillo((Integer) arregloVenta[0], Control.ventas.consultarVentaPorId((Integer) arregloVenta[1]), (String) arregloVenta[2], Control.platillos.consultarPorId((Integer) arregloVenta[3]), (Integer) arregloVenta[5], (Float) arregloVenta[4]);
                listaVentasPlatillos.add(v);
            }
        }

    }

    public boolean agregarVentaPlatillo(VentaPlatillo nuevaVenta) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        if (conexion.insertarVentaPlatillo(nuevaVenta.getVentaGeneral().getFolioVenta(), nuevaVenta.getNombreCliente(), nuevaVenta.getPlatillo().getIdPlatillo(), nuevaVenta.getCosto(), nuevaVenta.getCantidad())) {
            ventaPlatilloSiguiente = conexion.obtenUltimoID() + 1;
            nuevaVenta.setIdVentaPlatillo(ventaPlatilloSiguiente - 1);
            return true;
        } else {
            return false;
        }
    }
}
