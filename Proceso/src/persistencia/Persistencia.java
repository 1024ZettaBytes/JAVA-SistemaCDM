package persistencia;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
 public class Persistencia {
    private ArrayList<PlatilloMenu> listaPlatillosMenu;
    private ArrayList<ReservaPlatillo> listaReservasPlatillo;
    private ArrayList<Cliente> listaClientes;
   private  ArrayList<Platillo> listaPlatillos;
    private ArrayList<VentaCredito> listaVentasCredito;
    private ArrayList<VentaPlatillos> listaVentasPlatillos;
    private ArrayList<Usuario> listaUsuarios;
    private ArrayList<Producto> listaProductos;
    private static Persistencia instancia;
   private Persistencia(){
       this.listaPlatillosMenu = new ArrayList<>();
       this.listaReservasPlatillo = new ArrayList<>();
       this.listaClientes = new ArrayList<>();
       this.listaPlatillos = new ArrayList<>();
       this.listaVentasCredito = new ArrayList<>();
       this.listaVentasPlatillos = new ArrayList<>();
       this.listaUsuarios = new ArrayList<>();
       this.listaProductos = new ArrayList<>();
   }
    public static Persistencia instanciar(){
      if(instancia == null){
          instancia = new Persistencia();
      }  
      return instancia;
    }

 
    public boolean agregarPlatilloMenu(PlatilloMenu platilloMenu) {
        return !this.listaPlatillosMenu.contains(platilloMenu) && this.listaPlatillosMenu.add(platilloMenu);
    }
    
    public boolean actualizarPlatilloMenu(PlatilloMenu platilloMenu) {
        int index = this.listaPlatillos.indexOf(platilloMenu);
        if (index>=0){
        this.listaPlatillosMenu.set(index, platilloMenu);
        return true;
                }
        return false;
    }
    
    public boolean eliminarPlatilloMenu(PlatilloMenu platilloMenu) {
        return this.listaPlatillosMenu.remove(platilloMenu);
    }
    
    public ArrayList<PlatilloMenu> consultaPlatillosMenu() {
        return this.listaPlatillosMenu;
    }
    
    public boolean agregarReservaPlatillo(ReservaPlatillo reservaPlatillo) {
         return !this.listaReservasPlatillo.contains(reservaPlatillo) && this.listaReservasPlatillo.add(reservaPlatillo);
    }
    
    public boolean actualizarReservaPlatillo(ReservaPlatillo reservaPlatillo) {
         int index = this.listaReservasPlatillo.indexOf(reservaPlatillo);
        if (index>=0){
        this.listaReservasPlatillo.set(index, reservaPlatillo);
        return true;
                }
        return false;
    }
    
    public boolean eliminarReservaPlatillo(ReservaPlatillo reservaPlatillo) {
        return this.listaReservasPlatillo.remove(reservaPlatillo);
    }
    
    public ArrayList<ReservaPlatillo> consultaReservasPlatillo() {
        return this.listaReservasPlatillo;
    }
    
    public boolean agregarCliente(Cliente cliente) {
         return !this.listaClientes.contains(cliente) && this.listaClientes.add(cliente);
    }
    
    public boolean actualizarCliente(Cliente cliente) {
         int index = this.listaClientes.indexOf(cliente);
        if (index>=0){
        this.listaClientes.set(index, cliente);
        return true;
                }
        return false;
    }
    
    public boolean eliminarCliente(Cliente cliente) {
         return this.listaClientes.remove(cliente);
    }
    
    public ArrayList<Cliente> consultaClientes() {
        return this.listaClientes;
    }
    
    public boolean agregarPlatillo(Platillo platillo) {
         return !this.listaPlatillos.contains(platillo) && this.listaPlatillos.add(platillo);
    }
    
    public boolean actualizarPlatillo(Platillo platillo) {
          int index = this.listaPlatillos.indexOf(platillo);
        if (index>=0){
        this.listaPlatillos.set(index, platillo);
        return true;
                }
        return false;
    }
    
    public boolean eliminarPlatillo(Platillo platillo) {
         return this.listaPlatillos.remove(platillo);
    }
    
    public ArrayList<Platillo> consultaPlatillos() {
        return this.listaPlatillos;
    }

    public boolean agregarVentaCredito(VentaCredito ventaCredito) {
         return !this.listaVentasCredito.contains(ventaCredito) && this.listaVentasCredito.add(ventaCredito);
    }
    
    public boolean actualizarVentaCredito(VentaCredito ventaCredito) {
         int index = this.listaVentasCredito.indexOf(ventaCredito);
        if (index>=0){
        this.listaVentasCredito.set(index, ventaCredito);
        return true;
                }
        return false;
    }
    
    public boolean eliminarVentaCredito(VentaCredito ventaCredito) {
         return this.listaVentasCredito.remove(ventaCredito);
    }
    
    public ArrayList<VentaCredito> consultaVentasCredito() {
        return this.listaVentasCredito;
    }

    public boolean agregarVentaPlatillo(VentaPlatillos ventaPlatillo) {
         return !this.listaVentasPlatillos.contains(ventaPlatillo) && this.listaVentasPlatillos.add(ventaPlatillo);
    }
    
    public boolean actualizarVentaPlatillo(VentaPlatillos ventaPlatillo) {
          int index = this.listaVentasPlatillos.indexOf(ventaPlatillo);
        if (index>=0){
        this.listaVentasPlatillos.set(index, ventaPlatillo);
        return true;
                }
        return false;
    }
    
    public boolean eliminarVentaPlatillo(VentaPlatillos ventaPlatillo) {
         return this.listaVentasPlatillos.remove(ventaPlatillo);
    }
    
    public ArrayList<VentaPlatillos> consultaVentasPlatillos() {
        return this.listaVentasPlatillos;
    }
    
    public boolean agregarUsuario(Usuario usuario) {
        return !this.listaUsuarios.contains(usuario) && this.listaUsuarios.add(usuario);
    }
    
    public boolean actualizarUsuario(Usuario usuario) {
        int index = this.listaUsuarios.indexOf(usuario);
        if (index>=0){
        this.listaUsuarios.set(index, usuario);
        return true;
                }
        return false;
    }
    
    public boolean eliminarUsuario(Usuario usuario) {
         return this.listaUsuarios.remove(usuario);
    }
    
    public ArrayList<Usuario> consultaUsuarios() {
        return this.listaUsuarios;
    }
    

    public boolean agregarProducto(Producto producto) {
         return !this.listaProductos.contains(producto) && this.listaProductos.add(producto);
    }
    
    public boolean actualizarProducto(Producto producto) {
         int index = this.listaProductos.indexOf(producto);
        if (index>=0){
        this.listaProductos.set(index, producto);
        return true;
                }
        return false;
    }
    
    public boolean eliminarProducto(Producto producto) {
        return this.listaProductos.remove(producto);
    }
    
    public ArrayList<Producto> consultaProductos() {
        return this.listaProductos;
    }
}
