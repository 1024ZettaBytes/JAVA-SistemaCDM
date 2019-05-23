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
import Interfaces.IImpresion;
import ModuloImpresion.Impresion;

/**
 *
 * @author Eduardo Ramírez
 */
public class Control implements IControl {
    public static Usuario usuarioActivo;
    protected static IConexion conexion;
    public static IImpresion impresion;
    public static ControlUsuarios usuarios;
    public static ControlClientes clientes;
    public static ControlPlatillos platillos;
    public static ControlVentas ventas;
    public static ControlProductos productos;
    public static ControlMenu menu;
    public static ControlReservas reservas;
    public static ControlVentasPlatillos ventasPlatillos;
    public static ControlVentasCredito ventasCredito;
 

  
    protected ArrayList<VentaCredito> listaVentasCredito;
    protected ArrayList<VentaPlatillo> listaVentasPlatillos;

    public Control() throws ExceptionInInitializerError{
        usuarioActivo = null;
        conexion = new ConexionBD();
        if(conexion.conectar()){
        impresion = new Impresion();
        usuarios = new ControlUsuarios(conexion);
        clientes = new ControlClientes(conexion);
        platillos = new ControlPlatillos(conexion);
        ventas = new ControlVentas(conexion);
        productos = new ControlProductos(conexion);
        menu = new ControlMenu(conexion);
        reservas = new ControlReservas(conexion);
        ventasPlatillos = new ControlVentasPlatillos(conexion);
        ventasCredito = new ControlVentasCredito(conexion);
        // Se agregan los restantes
        }
        else throw new ExceptionInInitializerError();
    }

    
    public static boolean login(String nombre, String pass) {
         for (Usuario usuarioN : usuarios.consultarLista()) {
            if(usuarioN.getNombre().equals(nombre) && usuarioN.getPass().equals(pass)){
                usuarioActivo = usuarioN;
                return true;
            }
        }
         return false;
    }
    

    
    public static void logout() {
        usuarioActivo = null;
    }
   

   

}
