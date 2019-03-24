/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Eduardo Ramírez
 */
public interface IConexion {
    public boolean conectar();
    public boolean desconectar();
    public int consultarSiguiente(String tabla);
    public boolean insertarUsuario(String nombre, String pass, boolean tipoAdmin);
    public boolean actualizarUsuario(int id, String nombre, String pass, boolean tipoAdmin);
    public boolean eliminarUsuario(int id);
    public ArrayList<Object> consultarUsuarios();
    
    public boolean insertarPlatillo(String nombre);
    public boolean actualizarPlatillo(int id, String nombre);
    public boolean eliminarPlatillo(int id);
    public ArrayList<Object> consultarPlatillos();
    
    public boolean insertarPlatilloMenu(int idPlatillo, int diaSemana, int cantidad, String categoría);
    public boolean actualizarPlatilloMenu(int idPlatilloMenu, int idPlatillo, int diaSemana, int cantidad, String categoría);
    public boolean eliminarPlatilloMenu(int idPlatilloMenu);
    public ArrayList<Object> consultarPlatillosMenu();
    
    public boolean insertarCliente(String nombre, int creditoDesayuno, int creditoComida, int creditoCena);
    public boolean actualizarCliente(int idCliente, String nombre, int creditoDesayuno, int creditoComida, int creditoCena);
    public boolean eliminarCliente(int idCliente);
    public ArrayList<Object> consultarClientes();
    
    public boolean insertarReservaPlatillo(int idCliente, int idPlatillo, int cantidad, Date fecha, int tipo);
    public boolean actualizarReservaPlatillo(int idReserva, int idCliente, int idPlatillo, int cantidad, Date fecha, int tipo);
    public boolean eliminarReservaPlatillo(int idReserva);
    public ArrayList<Object> consultarReservasPlatillos();
    
    public boolean insertarVenta(Timestamp fechaHora, int idUsuario);
    public boolean actualizarVenta(int folioVenta, Timestamp fechaHora, int idUsuario);
    public boolean eliminarVenta(int folioVenta);
    public ArrayList<Object> consultarVentas();
    
    public boolean insertarVentaPlatillo(int folioVentaGeneral, String nombreCliente, int idPlatillo, float costo);
    public boolean actualizarVentaPlatillo(int folioVentaGeneral, String nombreCliente, int idPlatillo, float costo);
    public boolean eliminarVentaPlatillo(int folioVenta, int idPlatillo, float costo);
    public ArrayList<Object> consultarVentasPlatillos();
    
    public boolean insertarVentaCredito(Timestamp fechaHora, float monto, int idUsuario,int idCliente, int cantidadDesayunos, int cantidadComidas, int cantidadCenas);
    public boolean actualizarVentaCredito(int folioVenta, Timestamp fechaHora, float monto, int idUsuario, String nombreCliente, int idPlatillo);
    public boolean eliminarEliminarCredito(int folioVenta);
    public ArrayList<Object> consultarVentasCredito();
    
    public boolean insertarEntradaProducto(int idProducto, float unidades, float gasto);
    public boolean actualizarEntradaProducto(int idEntrada, int idProducto, float unidades, float gasto);
    public boolean eliminarEntradaProducto(int idEntrada);
    public ArrayList<Object> consultarEntradasProducto();
    
    public boolean insertarProducto(String nombre, float stock);
    public boolean actualizarProducto(int idProducto, String nombre, float stock);
    public boolean eliminarProducto(int idProducto);
    public ArrayList<Object> consultarProductos();
    
    public boolean insertarSalidaProducto(int idProducto, float unidades);
    public boolean actualizarSalidaProducto(int idSalida, int idProducto, float unidades);
    public boolean eliminarSalidaProducto(int idSalida);
    public ArrayList<Object> consultarSalidasProducto();
}

