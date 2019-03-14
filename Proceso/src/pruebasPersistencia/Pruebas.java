/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebasPersistencia;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import persistencia.Cliente;
import persistencia.Persistencia;
import persistencia.Platillo;
import persistencia.PlatilloMenu;
import persistencia.Producto;
import persistencia.ReservaPlatillo;
import persistencia.Usuario;
import persistencia.VentaCredito;
import persistencia.VentaPlatillos;

/**
 *
 * @author Eduardo Ramírez
 */
public class Pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Persistencia persistencia = Persistencia.instanciar();

        Usuario usuario1 = new Usuario(111, "Pedro", "123", true);
        Usuario usuario2 = new Usuario(222, "Lucas", "sf3", true);
        Usuario usuario3 = new Usuario(333, "Juan", "acv", false);

        persistencia.agregarUsuario(usuario1);
        persistencia.agregarUsuario(usuario2);
        persistencia.agregarUsuario(usuario3);
        System.out.println("Se agregaron 3 usuarios: ");
        System.out.println(Arrays.toString(persistencia.consultaUsuarios().toArray()));
        usuario3 = new Usuario(333, "CAMBIADO", "acv", false);
        persistencia.actualizarUsuario(usuario3);
        persistencia.eliminarUsuario(usuario1);
        System.out.println("Se actualizó el usuario 3 y se eliminó el 1: ");
        System.out.println(Arrays.toString(persistencia.consultaUsuarios().toArray()));
        System.out.println("--------");

        Producto producto1 = new Producto(111, "Tomate", 5);
        Producto producto2 = new Producto(222, "Pepino", 10);
        Producto producto3 = new Producto(333, "Papa", 3);

        persistencia.agregarProducto(producto1);
        persistencia.agregarProducto(producto2);
        persistencia.agregarProducto(producto3);
        System.out.println("Se agregaron 3 productos: ");
        System.out.println(Arrays.toString(persistencia.consultaProductos().toArray()));
        producto3 = new Producto(333, "Platano", 5);
        persistencia.actualizarProducto(producto3);
        persistencia.eliminarProducto(producto1);
        System.out.println("Se actualizó el producto 3 y se eliminó el 1:");
        System.out.println(Arrays.toString(persistencia.consultaProductos().toArray()));
        System.out.println("--------");

        Platillo platillo1 = new Platillo(111, "Taco", "DESAYUNO", 100f);
        Platillo platillo2 = new Platillo(222, "Hot-dog", "COMIDA", 200f);
        Platillo platillo3 = new Platillo(333, "Pizza", "CENA", 300f);

        persistencia.agregarPlatillo(platillo1);
        persistencia.agregarPlatillo(platillo2);
        persistencia.agregarPlatillo(platillo3);
        System.out.println("Se agregaron 3 platillos a la lista:");
        System.out.println(Arrays.toString(persistencia.consultaPlatillos().toArray()));
        platillo3 = new Platillo(333, "Taquitos", "COMIDA", 200f);
        persistencia.actualizarPlatillo(platillo3);
        persistencia.eliminarPlatillo(platillo1);
        System.out.println("Se actualizó el platillo 3 y se eliminó el 1:");
        System.out.println(Arrays.toString(persistencia.consultaPlatillos().toArray()));
        System.out.println("--------");

        Cliente cliente1 = new Cliente(111, "José", 1, 2, 4);
        Cliente cliente2 = new Cliente(222, "Tomás", 4, 3, 6);
        Cliente cliente3 = new Cliente(333, "Fulano", 5, 1, 0);

        persistencia.agregarCliente(cliente1);
        persistencia.agregarCliente(cliente2);
        persistencia.agregarCliente(cliente3);
        System.out.println("Se agregaron 3 clientes a la lista:");
        System.out.println(Arrays.toString(persistencia.consultaClientes().toArray()));
        cliente3 = new Cliente(333, "Mengano", 3, 2, 1);
        persistencia.actualizarCliente(cliente3);
        persistencia.eliminarCliente(cliente1);
        System.out.println("Se actualizó el cliente 3 y se eliminó el 1:");
        System.out.println(Arrays.toString(persistencia.consultaClientes().toArray()));
        System.out.println("--------");

        ReservaPlatillo reserva1 = new ReservaPlatillo(111, cliente2, platillo1, 1, new Date(2000 + 1900, 4, 5), 1);
        ReservaPlatillo reserva2 = new ReservaPlatillo(222, cliente1, platillo3, 4, new Date(2000 + 1900, 5, 3), 2);
        ReservaPlatillo reserva3 = new ReservaPlatillo(333, cliente3, platillo2, 5, new Date(2003 + 1900, 1, 2), 3);

        persistencia.agregarReservaPlatillo(reserva1);
        persistencia.agregarReservaPlatillo(reserva2);
        persistencia.agregarReservaPlatillo(reserva3);
        System.out.println("Se agregaron 3 reservas a la lista:");
        System.out.println(Arrays.toString(persistencia.consultaReservasPlatillo().toArray()));
        reserva3 = new ReservaPlatillo(333, cliente3, platillo3, 3, new Date(2003 + 1900, 1, 3), 3);
        persistencia.actualizarReservaPlatillo(reserva3);
        persistencia.eliminarReservaPlatillo(reserva1);
        System.out.println("Se actualizó la reserva 3 y se eliminó la 1:");
        System.out.println(Arrays.toString(persistencia.consultaReservasPlatillo().toArray()));
        System.out.println("--------");

        PlatilloMenu platilloMenu1 = new PlatilloMenu(platillo1, 1, 10);
        PlatilloMenu platilloMenu2 = new PlatilloMenu(platillo2, 3, 10);
        PlatilloMenu platilloMenu3 = new PlatilloMenu(platillo3, 6, 10);

        persistencia.agregarPlatilloMenu(platilloMenu1);
        persistencia.agregarPlatilloMenu(platilloMenu2);
        persistencia.agregarPlatilloMenu(platilloMenu3);
        System.out.println("Se agregaron 3 platillos a la lista de menú:");
        System.out.println(Arrays.toString(persistencia.consultaPlatillosMenu().toArray()));
        platilloMenu3 = new PlatilloMenu(platillo3, 2, 20);
        persistencia.actualizarPlatilloMenu(platilloMenu3);
        persistencia.eliminarPlatilloMenu(platilloMenu1);
        System.out.println("Se actualizó la el platillo 3 del menú y se eliminó el 1:");
        System.out.println(Arrays.toString(persistencia.consultaPlatillosMenu().toArray()));
        System.out.println("--------");

        VentaCredito ventaCr1 = new VentaCredito(111, 111, new Timestamp(0), 111, 2, 2, 2, 100f);
        VentaCredito ventaCr2 = new VentaCredito(222, 222, new Timestamp(0), 222, 4, 4, 4, 200f);
        VentaCredito ventaCr3 = new VentaCredito(333, 222, new Timestamp(0), 333, 3, 3, 3, 300f);

        persistencia.agregarVentaCredito(ventaCr1);
        persistencia.agregarVentaCredito(ventaCr2);
        persistencia.agregarVentaCredito(ventaCr3);
System.out.println("Se agregaron 3 ventas a la lista de ventas de crédito:");
        System.out.println(Arrays.toString(persistencia.consultaVentasCredito().toArray()));
        ventaCr3 = new VentaCredito(333,111, new Timestamp(0),222, 3, 3, 3,  400f);
        persistencia.actualizarVentaCredito(ventaCr3);
        persistencia.eliminarVentaCredito(ventaCr1);
         System.out.println("Se actualizóla la venta de crédito 3 y se eliminó la 1:");
        System.out.println(Arrays.toString(persistencia.consultaVentasCredito().toArray()));
        System.out.println("--------");

        ArrayList<Platillo> listaPlatillo1 = new ArrayList<>();
        listaPlatillo1.add(platillo1);
        listaPlatillo1.add(platillo2);
        listaPlatillo1.add(platillo3);
        ArrayList<Platillo> listaPlatillo2 = new ArrayList<>();
        listaPlatillo1.add(platillo3);
        listaPlatillo1.add(platillo1);
        listaPlatillo1.add(platillo2);
        
        VentaPlatillos ventaP1 = new VentaPlatillos(111,"Marcos", new Timestamp(0),123, listaPlatillo1, 100f);
        VentaPlatillos ventaP2 = new VentaPlatillos(222,"Peter", new Timestamp(0), 456, listaPlatillo2, 200f);
        persistencia.agregarVentaPlatillo(ventaP2);
        persistencia.agregarVentaPlatillo(ventaP2);
        System.out.println("Se agregaron 2 ventas de platillos a la lista:");
        System.out.println(Arrays.toString(persistencia.consultaVentasPlatillos().toArray()));
        ventaP2 = new VentaPlatillos(222, "CAMBIADO", new Timestamp(0),456, listaPlatillo2, 300f);
        persistencia.actualizarVentaPlatillo(ventaP2);
        persistencia.eliminarVentaPlatillo(ventaP1);
        System.out.println("Se actualizó la venta 2 y se eliminó la 1:");
        System.out.println(Arrays.toString(persistencia.consultaVentasPlatillos().toArray()));
        System.out.println("--------");
    }
}
