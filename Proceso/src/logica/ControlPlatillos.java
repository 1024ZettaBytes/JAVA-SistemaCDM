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
                Platillo p = new Platillo((Integer) arregloPlatillo[0], (String) arregloPlatillo[1]);
                listaPlatillos.add(p);
            }

        }

    }

    public boolean agregar(Platillo nuevoPlatillo) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        if (conexion.insertarPlatillo(nuevoPlatillo.getNombre())) {
            platilloSiguiente = conexion.obtenUltimoID() + 1;
            nuevoPlatillo.setIdPlatillo(platilloSiguiente - 1);
            return !listaPlatillos.contains(nuevoPlatillo) && listaPlatillos.add(nuevoPlatillo);
        } else {
            return false;
        }
    }

    public boolean actualizar(Platillo platillo) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        int index = listaPlatillos.indexOf(platillo);
        if (index >= 0 && conexion.actualizarPlatillo(platillo.getIdPlatillo(), platillo.getNombre())) {
            listaPlatillos.set(index, platillo);
            return true;
        }
        return false;
    }

    public boolean eliminar(int idPlatillo) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        Platillo p = new Platillo(idPlatillo, "");
        return conexion.eliminarPlatillo(idPlatillo) && listaPlatillos.remove(p);
    }

    public Platillo consultarPorId(int idPlatillo) {
        Platillo platillo = new Platillo(idPlatillo, null);
        if (listaPlatillos.contains(platillo)) {
            return listaPlatillos.get(listaPlatillos.indexOf(platillo));
        }
        return null;
    }

    public ArrayList<Platillo> consultarLista() {

        return listaPlatillos;
    }
    public ArrayList<Platillo> consultarListaOrdenada() {

        int indexMayor = 0;
        boolean agregarEntre = true;
        ArrayList<Platillo> platillos = new ArrayList<>();
        for (Platillo platillo : listaPlatillos) {
            agregarEntre = platillos.size() > 0;
            if (agregarEntre) {
                for (Platillo p : platillos) {
                    if (platillo.getNombre().compareTo(p.getNombre()) <= 0) {
                        indexMayor = platillos.indexOf(p);
                        break;
                    } else {
                        if (platillos.indexOf(p) == platillos.size() - 1) {
                            agregarEntre = false;
                        }
                    }
                }
            }
            if (agregarEntre) {
                platillos.add(indexMayor, platillo);
            } else {
                platillos.add(platillo);
            }

        }
        return platillos;
    }
    public ArrayList<Platillo> consultarSopas() {
        ArrayList<Platillo> sopas = new ArrayList<>();
         for(Platillo platillo:listaPlatillos){
             if(platillo.getNombre().length()>7){
             if(platillo.getNombre().substring(0, 7).toLowerCase().equals("sopa de"))
                 sopas.add(platillo);
             }
         }
         return sopas;
    }
}
