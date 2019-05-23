/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import Interfaces.IConexion;
import java.util.ArrayList;
import negocio.VentaCredito;

/**
 *
 * @author ed000
 */
public class ControlVentasCredito {
     private ArrayList<VentaCredito> listaVentasCredito;
    private int ventaCreditoSiguiente;
    private IConexion conexion;

    public ControlVentasCredito(IConexion conexion) {
        listaVentasCredito = new ArrayList<>();
        this.conexion = conexion;
        if (this.conexion.conectar()) {
            ArrayList<Object[]> arreglosVentas = conexion.consultarVentasCredito();
            for (Object[] arregloVenta : arreglosVentas) {
                VentaCredito v = new VentaCredito((Integer) arregloVenta[0], Control.ventas.consultarVentaPorId((Integer) arregloVenta[1]), Control.clientes.consultarPorId((Integer)arregloVenta[2]), (Integer) arregloVenta[3], (Integer) arregloVenta[4],(Integer) arregloVenta[5], (Float) arregloVenta[6]);
                listaVentasCredito.add(v);
            }
        }

    }

    public boolean agregarVentaCredito(VentaCredito nuevaVenta) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        if (conexion.insertarVentaCredito(nuevaVenta.getVentaGeneral().getFolioVenta(), nuevaVenta.getCliente().getIdCliente(), nuevaVenta.getCantidadDesayunos(), nuevaVenta.getCantidadComidas(), nuevaVenta.getCantidadCenas(), nuevaVenta.getMonto())) {
            ventaCreditoSiguiente = conexion.obtenUltimoID() + 1;
            nuevaVenta.setIdVentaCredito(ventaCreditoSiguiente - 1);
            return true;
        } else {
            return false;
        }
    }
}
