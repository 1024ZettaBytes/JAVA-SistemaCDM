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
import java.util.Arrays;
import negocio.*;

/**
 *
 * @author Eduardo Ramírez
 */
public class Control implements IControl {
    private static Usuario usuarioActivo;
    public static ControlUsuarios usuarios;
    public static ControlClientes clientes;
    public static ControlPlatillos platillos;
    public static ControlVentas ventas;
    public static ControlProductos productos;
    public static ControlMenu menu;
    protected static IConexion conexion;
    
    
    
    protected ArrayList<Producto> listaProductos;

    protected ArrayList<PlatilloMenu> listaPlatillosMenu;
    protected ArrayList<ReservaPlatillo> listaReservasPlatillo;
    protected ArrayList<VentaCredito> listaVentasCredito;
    protected ArrayList<VentaPlatillo> listaVentasPlatillos;

    public Control() throws ExceptionInInitializerError{
        conexion = new ConexionBD();
        if(conexion.conectar()){
        usuarios = new ControlUsuarios(conexion);
        clientes = new ControlClientes(conexion);
        platillos = new ControlPlatillos(conexion);
        ventas = new ControlVentas(conexion);
        productos = new ControlProductos(conexion);
        // Se agregan los restantes
        }
        else throw new ExceptionInInitializerError();
    }

    @Override
    public boolean login(String usuario, String pass) {
         
    }
   

   

}
