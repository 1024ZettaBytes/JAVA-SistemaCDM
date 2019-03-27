/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import Interfaces.IConexion;
import java.util.ArrayList;

import negocio.Cliente;

/**
 *
 * @author Eduardo Ramírez
 */
public class ControlClientes {

    private ArrayList<Cliente> listaClientes;
    private int clienteSiguiente;
    private IConexion conexion;

    public ControlClientes(IConexion conexion) {
        listaClientes = new ArrayList<>();
        this.conexion = conexion;
        if (this.conexion.conectar()) {
            ArrayList<Object[]> arreglosClientes = conexion.consultarClientes();
            for (Object[] arregloCliente : arreglosClientes) {
                Cliente c = new Cliente((Integer) arregloCliente[0], (String) arregloCliente[1], (Integer) arregloCliente[2], (Integer) arregloCliente[3], (Integer) arregloCliente[4]);
                listaClientes.add(c);
            }
            
            
        }
    }

    public boolean agregar(Cliente nuevoCliente) {
        
        if (conexion.insertarCliente(nuevoCliente.getNombre(), nuevoCliente.getCreditoDesayuno(), nuevoCliente.getCreditoComida(), nuevoCliente.getCreditoCena())) {
           
            clienteSiguiente = conexion.obtenUltimoID()+1;
             nuevoCliente.setIdCliente(clienteSiguiente-1);
            return !listaClientes.contains(nuevoCliente) && listaClientes.add(nuevoCliente);
        } else {
            return false;
        }
    }

    public boolean actualizar(Cliente cliente) {

        int index = listaClientes.indexOf(cliente);
        if (index >= 0 && conexion.actualizarCliente(cliente.getIdCliente(), cliente.getNombre(), cliente.getCreditoDesayuno(), cliente.getCreditoComida(), cliente.getCreditoCena())) {
            listaClientes.set(index, cliente);
            return true;
        }
        return false;
    }

    public boolean eliminar(int idCliente) {
        Cliente c = new Cliente(idCliente, "", 0, 0, 0);
        return conexion.eliminarCliente(idCliente) && listaClientes.remove(c);
    }
public Cliente consultarPorId(int id){
    Cliente c = new Cliente(id, null, 0, 0, 0);
    if(listaClientes.contains(c)){
        return listaClientes.get(listaClientes.indexOf(c));
    }
    return null;
}
    public ArrayList<Cliente> consultarLista() {

        return listaClientes;
    }
}
