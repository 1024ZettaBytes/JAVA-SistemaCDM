/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import Conexion.ConexionBD;
import Interfaces.IConexion;
import Interfaces.IControl;
import java.util.ArrayList;
import negocio.Cliente;
import negocio.Platillo;
import negocio.PlatilloMenu;
import negocio.Producto;
import negocio.ReservaPlatillo;
import negocio.Usuario;
import negocio.VentaCredito;
import negocio.VentaPlatillos;

/**
 *
 * @author Eduardo Ramírez
 */
public class Control implements IControl {
    protected static IConexion conexion = new ConexionBD();
    private static ArrayList<PlatilloMenu> listaPlatillosMenu;
    private static ArrayList<ReservaPlatillo> listaReservasPlatillo;
    private static ArrayList<Cliente> listaClientes;
   private  static ArrayList<Platillo> listaPlatillos;
    private static ArrayList<VentaCredito> listaVentasCredito;
    private static ArrayList<VentaPlatillos> listaVentasPlatillos;
    protected static ArrayList<Usuario> listaUsuarios;
    private static ArrayList<Producto> listaProductos;
    protected boolean extraerDatosBD(){
        return true;
    }
}
