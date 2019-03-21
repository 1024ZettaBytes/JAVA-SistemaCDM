/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import Interfaces.IConexion;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Eduardo Ramírez
 */
public class ConexionBD implements IConexion {

    private final String driver = "com.mysql.jdbc.Driver";

    private final String usuarioBD = "root";
    private final String passBD = "sesamo";
    private final String servidorBD = "localhost";
    private final String nombreBD = "cdm";
    private final String puertoBD = "3306";
    private Connection conn = null;
    @Override
    public int consultarSiguiente(String tabla){
    String nomTabla = "";
    switch(tabla){
        case "u" : nomTabla = "usuarios";
        break;
        case "v" : nomTabla = "ventas";
        break;
        case "pt" : nomTabla = "platillos";
        break;
        case "rp" : nomTabla = "reservasplatillo";
        break;
        case "c" : nomTabla = "clientes";
        break;
        case "ptm" : nomTabla = "platillosmenu";
        break;
        case "p" : nomTabla = "productos";
        break;
        case "ep" : nomTabla = "entradasproductos";
        break;
        case "sp" : nomTabla = "salidasproductos";
        break;
    }
    String sSQL = "SELECT AUTO_INCREMENT\n" +
"FROM information_schema.TABLES\n" +
"WHERE TABLE_SCHEMA = \"cdm\"\n" +
"AND TABLE_NAME = "+nomTabla;
    int n=1;
        try {
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            ResultSet rs = pstm.executeQuery(sSQL);
            while (rs.next()) {
              n = rs.getInt(0);
            }
            pstm.close();
            return n;
        } catch (SQLException ex) {
            return -1;
        }
}
    @Override
    public boolean conectar() {
        boolean correcto = false;
        String url = "jdbc:mysql://" + servidorBD + ":" + puertoBD + "/" + nombreBD + "?useSSL=false";
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, usuarioBD, passBD);
            correcto = true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            correcto = false;
        }
        return correcto;
    }

    @Override
    public boolean desconectar() {
        try {
            conn.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    @Override
    public boolean insertarUsuario(String nombre, String pass, boolean tipoAdmin) {
        String sSQL = "INSERT INTO usuarios (nombre, contra, tipoAdmin) VALUES ('" + nombre + "', '" + pass + "', " + tipoAdmin + ")";
        try {
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean actualizarUsuario(int id, String nombre, String pass, boolean tipoAdmin) {
        String sSQL = "UPDATE usuarios SET nombre = '" + nombre + "', contra = '" + pass + "', tipoAdmin = " + tipoAdmin + " WHERE idUsuario = " + id;
        try {
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean eliminarUsuario(int id) {
        String sSQL = "DELETE FROM usuarios WHERE idUsuario = " + id;
        try {
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public ArrayList<Object> consultarUsuarios() {
        String sSQL = "SELECT * FROM usuarios";
        try {
            ArrayList usuarios = new ArrayList<Object>();
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            ResultSet rs = pstm.executeQuery(sSQL);
            while (rs.next()) {
                Object[] usuario = {rs.getInt("idUsuario"), rs.getString("nombre"), rs.getString("contra"), rs.getBoolean("tipoAdmin")};
                usuarios.add(usuario);
            }
            pstm.close();
            return usuarios;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public boolean insertarPlatillo(String nombre, float precio) {
        String sSQL = "INSERT INTO platillos (nombre, precio) VALUES ('" + nombre + "', " + precio + ")";
        try {
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean actualizarPlatillo(int id, String nombre, float precio) {
        String sSQL = "UPDATE platillos SET nombre = '" + nombre + "', precio = " + precio + " WHERE idPlatillo = " + id;
        try {
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean eliminarPlatillo(int id) {
        String sSQL = "DELETE FROM platillos WHERE idPlatillo = " + id;
        try {
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public ArrayList<Object> consultarPlatillos() {
        String sSQL = "SELECT * FROM platillos";
        try {
            ArrayList platillos = new ArrayList<Object>();
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            ResultSet rs = pstm.executeQuery(sSQL);
            while (rs.next()) {
                Object[] platillo = {rs.getInt("idPlatillo"), rs.getString("nombre"), rs.getFloat("precio")};
                platillos.add(platillo);
            }
            pstm.close();
            return platillos;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public boolean insertarPlatilloMenu(int idPlatillo, int diaSemana, int cantidad, String categoria) {
         String sSQL = "INSERT INTO platillosmenu (Platillos_idPlatillo, diaSemana, cantidad, categoria) VALUES (" + idPlatillo + ", " + diaSemana + ", " + cantidad + ", '"+categoria+"')";
        try {
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean actualizarPlatilloMenu(int idPlatilloMenu, int idPlatillo, int diaSemana, int cantidad, String categoria) {
         String sSQL = "UPDATE platillosmenu SET Platillos_idPlatillo = " + idPlatillo + ", diaSemana = " + diaSemana + ", cantidad = " + cantidad+  ", categoria = '" + categoria + "' WHERE idPlatilloMenu = " + idPlatilloMenu;
        try {
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    

    @Override
    public boolean eliminarPlatilloMenu(int idPlatilloMenu) {
         String sSQL = "DELETE FROM platillosmenu WHERE idPlatilloMenu = " + idPlatilloMenu;
        try {
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public ArrayList<Object> consultarPlatillosMenu() {
         String sSQL = "SELECT * FROM platillosmenu";
        try {
            ArrayList platillosMenu = new ArrayList<Object>();
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            ResultSet rs = pstm.executeQuery(sSQL);
            while (rs.next()) {
                Object[] platilloMenu = {rs.getInt("idPlatilloMenu"),rs.getInt("Platillos_idPlatillo"), rs.getInt("diaSemana"), rs.getInt("cantidad"), rs.getString("categoria")};
                platillosMenu.add(platilloMenu);
            }
            pstm.close();
            return platillosMenu;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public boolean insertarCliente(String nombre, int creditoDesayuno, int creditoComida, int creditoCena) {
    String sSQL = "INSERT INTO clientes (nombre, creditoDesayuno, creditoComida, creditoCena) VALUES ('" + nombre + "', " + creditoDesayuno + ", " + creditoComida + ", "+creditoCena+")";
        try {
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean actualizarCliente(int idCliente, String nombre, int creditoDesayuno, int creditoComida, int creditoCena) {
        String sSQL = "UPDATE clientes SET nombre = '" + nombre + "',creditoDesayuno = " + creditoDesayuno + ", creditoComida = " + creditoComida+  ", creditoCena = " + creditoCena + " WHERE idCliente = " + idCliente;
        try {
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean eliminarCliente(int idCliente) {
        String sSQL = "DELETE FROM clientes WHERE idCliente = " + idCliente;
        try {
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public ArrayList<Object> consultarClientes() {
         String sSQL = "SELECT * FROM clientes";
        try {
            ArrayList clientes = new ArrayList<Object>();
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            ResultSet rs = pstm.executeQuery(sSQL);
            while (rs.next()) {
                Object[] cliente = {rs.getInt("idCliente"),rs.getString("nombre"), rs.getInt("creditoDesayuno"), rs.getInt("creditoComida"), rs.getInt("creditoCena")};
                clientes.add(cliente);
                System.out.println(Arrays.asList(cliente).toString());
            }
            pstm.close();
            return clientes;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public boolean insertarReservaPlatillo(int idCliente, int idPlatillo, int cantidad, Date fecha, int tipo) {
        java.sql.Date timestamp = new java.sql.Date(fecha.getTime());
        String sSQL = "INSERT INTO reservasPlatillo (Clientes_idCliente, Platillos_idPlatillo, cantidad, fechaReserva, TipoReserva_idTipoReserva) VALUES (" + idCliente + ", " + idPlatillo + ", " + cantidad + ", '"+timestamp+"', "+tipo+")";
        try {
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean actualizarReservaPlatillo(int idReserva, int idCliente, int idPlatillo, int cantidad, Date fecha, int tipo) {
    java.sql.Date timestamp = new java.sql.Date(fecha.getTime());
        String sSQL = "UPDATE reservasPlatillo SET Clientes_idCliente = " + idCliente + ", Platillos_idPlatillo = " + idPlatillo + ", cantidad = " + cantidad+  ", fechaReserva = " + timestamp +", TipoReserva_idTipoReserva = "+ tipo+" WHERE idReserva = " + idReserva;
        try {
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public boolean eliminarReservaPlatillo(int idReserva) {
        String sSQL = "DELETE FROM reservasPlatillo WHERE idReserva = " + idReserva;
        try {
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            pstm.execute();
            pstm.close();
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public ArrayList<Object> consultarReservasPlatillos() {
        String sSQL = "SELECT * FROM reservasPlatillo";
        try {
            ArrayList reservas = new ArrayList<Object>();
            // PreparedStatement
            PreparedStatement pstm = conn.prepareStatement(sSQL);
            ResultSet rs = pstm.executeQuery(sSQL);
            while (rs.next()) {
                Object[] reserva = {rs.getInt("idReserva"),rs.getInt("Clientes_idCliente"), rs.getInt("Platillos_idPlatillo"), rs.getInt("cantidad"), rs.getString("fechaReserva"), rs.getInt("TipoReserva_idTipoReserva")};
                reservas.add(reserva);
                System.out.println(Arrays.asList(reserva).toString());
            }
            pstm.close();
            return reservas;
        } catch (SQLException ex) {
            return null;
        }
    }

    @Override
    public boolean insertarVentaPlatillo(int folioVenta, Timestamp fechaHora, float monto, int idUsuario, String nombreCliente, int idPlatillo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizarVentaPlatillo(int folioVenta, Timestamp fechaHora, float monto, int idUsuario, String nombreCliente, int idPlatillo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminarEliminarPlatilla(int folioVenta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Object> consultarVentasPlatillos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insertarVentaCredito(Timestamp fechaHora, float monto, int idUsuario, int idCliente, int cantidadDesayunos, int cantidadComidas, int cantidadCenas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizarVentaCredito(int folioVenta, Timestamp fechaHora, float monto, int idUsuario, String nombreCliente, int idPlatillo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminarEliminarCredito(int folioVenta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Object> consultarVentasCredito() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insertarEntradaProducto(int idProducto, float unidades, float gasto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizarEntradaProducto(int idEntrada, int idProducto, float unidades, float gasto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminarEntradaProducto(int idEntrada) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Object> consultarEntradasProducto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insertarProducto(String nombre, float stock) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizarProducto(int idProducto, String nombre, float stock) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminarProducto(int idProducto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Object> consultarProductos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insertarSalidaProducto(int idProducto, float unidades) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizarSalidaProducto(int idSalida, int idProducto, float unidades) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean eliminarSalidaProducto(int idSalida) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Object> consultarSalidasProducto() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
