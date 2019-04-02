/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import Interfaces.IConexion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import negocio.Cliente;
import negocio.ReservaPlatillo;

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
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        if (conexion.insertarCliente(nuevoCliente.getNombre(), nuevoCliente.getCreditoDesayuno(), nuevoCliente.getCreditoComida(), nuevoCliente.getCreditoCena())) {
           
            clienteSiguiente = conexion.obtenUltimoID()+1;
             nuevoCliente.setIdCliente(clienteSiguiente-1);
            return !listaClientes.contains(nuevoCliente) && listaClientes.add(nuevoCliente);
        } else {
            return false;
        }
    }

    public boolean actualizar(Cliente cliente) {
if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        int index = listaClientes.indexOf(cliente);
        if (index >= 0 && conexion.actualizarCliente(cliente.getIdCliente(), cliente.getNombre(), cliente.getCreditoDesayuno(), cliente.getCreditoComida(), cliente.getCreditoCena())) {
            listaClientes.set(index, cliente);
            return true;
        }
        return false;
    }

    public boolean eliminar(int idCliente) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
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
public ArrayList<Cliente> clientesConCredito() {
ArrayList<Cliente> lista = new ArrayList<>();
    for (Cliente cliente : listaClientes) {
        if(cliente.getCreditoDesayuno()+cliente.getCreditoComida()+cliente.getCreditoCena() >0)
            lista.add(cliente);
    }
        return lista;
    }
public ArrayList<Cliente> clientesParaModificar() {
    int indexMayor = 0;
    boolean agregarEntre=true;
ArrayList<Cliente> clientesModificables = new ArrayList<>();
    for (Cliente cliente : listaClientes) {
        
        if(cliente.getCreditoDesayuno()+cliente.getCreditoComida()+cliente.getCreditoCena() >0 || Control.reservas.consultarReservasClienteVigente(cliente, Calendar.getInstance().getTime()).size()>0){
            
            agregarEntre = clientesModificables.size()>0;
            if(agregarEntre)
            for (Cliente clienteModificable : clientesModificables) {
                if(cliente.getNombre().compareTo(clienteModificable.getNombre())<=0){
                    indexMayor = clientesModificables.indexOf(clienteModificable);
                    break;
                }
                else{
                    if(clientesModificables.indexOf(clienteModificable)==clientesModificables.size()-1)
                        agregarEntre = false;
                }
            }
            if(agregarEntre)
                clientesModificables.add(indexMayor, cliente);
            else
                clientesModificables.add(cliente);
        }
    }
    return clientesModificables;
}
    public ArrayList<Cliente> consultarLista() {

        return listaClientes;
    }
}
