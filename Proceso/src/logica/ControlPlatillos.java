/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import Interfaces.IConexion;
import java.util.ArrayList;

import negocio.Platillo;

/**
 *
 * @author Eduardo Ramírez
 */
public class ControlPlatillos {
    ArrayList<Platillo> listaPlatillos;
    private int platilloSiguiente = 1;
    private IConexion conexion;

    public ControlPlatillos(IConexion conexion) {
        listaPlatillos = new ArrayList<>();
        this.conexion = conexion;
        if (this.conexion.conectar()) {
            ArrayList<Object[]> arreglosPlatillos = conexion.consultarPlatillos();
            for (Object[] arregloPlatillo : arreglosPlatillos) {
                Platillo p = new Platillo((Integer)arregloPlatillo[0], (String)arregloPlatillo[1]);
                listaPlatillos.add(p);
            }
            
            
        }
    }

    public boolean agregar(Platillo nuevoPlatillo) {
        
        if (conexion.insertarPlatillo(nuevoPlatillo.getNombre())) {
            platilloSiguiente= conexion.obtenUltimoID()+1;
            nuevoPlatillo.setIdPlatillo(platilloSiguiente-1);
            return !listaPlatillos.contains(nuevoPlatillo) && listaPlatillos.add(nuevoPlatillo);
        } else {
            return false;
        }
    }

    public boolean actualizar(Platillo platillo) {

        int index = listaPlatillos.indexOf(platillo);
        if (index >= 0 && conexion.actualizarPlatillo(platillo.getIdPlatillo(), platillo.getNombre())) {
            listaPlatillos.set(index, platillo);
            return true;
        }
        return false;
    }

    public boolean eliminar(int idPlatillo) {
        Platillo p = new Platillo(idPlatillo, "");
        return conexion.eliminarPlatillo(idPlatillo) && listaPlatillos.remove(p);
    }
public Platillo consultarPorId(int idPlatillo){
    Platillo platillo =new Platillo(idPlatillo,  null);
   if(listaPlatillos.contains(platillo)){
       return listaPlatillos.get(listaPlatillos.indexOf(platillo));
   }
   return null;
}
    public ArrayList<Platillo> consultarLista() {

        return listaPlatillos;
    }
}
