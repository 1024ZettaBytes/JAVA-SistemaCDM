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

    public static ControlUsuarios usuarios;
    public static ControlClientes clientes;
    public static ControlPlatillos platillos;
    public static ControlVentas ventas;
    protected static IConexion conexion;
    
    
    
    protected ArrayList<Producto> listaProductos;

    protected ArrayList<PlatilloMenu> listaPlatillosMenu;
    protected ArrayList<ReservaPlatillo> listaReservasPlatillo;
    protected ArrayList<VentaCredito> listaVentasCredito;
    protected ArrayList<VentaPlatillo> listaVentasPlatillos;

    public Control() {
        conexion = new ConexionBD();
        usuarios = new ControlUsuarios(conexion);
        clientes = new ControlClientes(conexion);
        platillos = new ControlPlatillos(conexion);
        ventas = new ControlVentas(conexion);
        
    }

    @Override
    public boolean extraerDatosBD() {
//        if(conexion.conectar()){
//            
//            
//             ArrayList<Object[]> arreglosUsuarios = conexion.consultarUsuarios();
//            for (Object[] arregloUsuario : arreglosUsuarios) {
//                Usuario u = new Usuario((Integer)arregloUsuario[0], (String)arregloUsuario[1], (String)arregloUsuario[2], (Boolean)arregloUsuario[3]);
//                listaUsuarios.add(u);
//                
//            }
//            return true;
//        }
//        return false;
        return false;
    }

}
