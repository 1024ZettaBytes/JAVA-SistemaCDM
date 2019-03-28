/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import Interfaces.IConexion;
import java.util.ArrayList;
import negocio.PlatilloMenu;

/**
 *
 * @author itson
 */
public class ControlMenu {

    private ArrayList<PlatilloMenu> listaPlatillosMenu;
    private int platilloMenuSiguiente;
    private IConexion conexion;

    public ControlMenu(IConexion conexion) {
        listaPlatillosMenu = new ArrayList<>();
        this.conexion = conexion;
        if (this.conexion.conectar()) {
            ArrayList<Object[]> arreglosPlatillosMenu = conexion.consultarPlatillosMenu();
            for (Object[] arregloPlatilloMenu : arreglosPlatillosMenu) {
                PlatilloMenu pm = new PlatilloMenu((Integer) arregloPlatilloMenu[0], Control.platillos.consultarPorId((Integer) arregloPlatilloMenu[1]), (Integer) arregloPlatilloMenu[2], (Integer) arregloPlatilloMenu[3], (String) arregloPlatilloMenu[4]);
                listaPlatillosMenu.add(pm);
            }

        }
    }

    public boolean agregar(PlatilloMenu nuevoPlatilloMenu) {
if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        if (conexion.insertarPlatilloMenu(nuevoPlatilloMenu.getPlatillo().getIdPlatillo(), nuevoPlatilloMenu.getDiaSemana(), nuevoPlatilloMenu.getCantidad(), nuevoPlatilloMenu.getCategoria())) {

            platilloMenuSiguiente = conexion.obtenUltimoID() + 1;
            nuevoPlatilloMenu.setIdPlatilloMenu(platilloMenuSiguiente - 1);
            return !listaPlatillosMenu.contains(nuevoPlatilloMenu) && listaPlatillosMenu.add(nuevoPlatilloMenu);
        } else {
            return false;
        }
    }

    public boolean actualizar(PlatilloMenu platilloMenu) {
if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        int index = listaPlatillosMenu.indexOf(platilloMenu);
        if (index >= 0 && conexion.actualizarPlatilloMenu(platilloMenu.getIdPlatilloMenu(), platilloMenu.getPlatillo().getIdPlatillo(), platilloMenu.getDiaSemana(), platilloMenu.getCantidad(), platilloMenu.getCategoria())) {
            listaPlatillosMenu.set(index, platilloMenu);
            return true;
        }
        return false;
    }

    public boolean eliminar(int idPlatilloMenu) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        PlatilloMenu pm = new PlatilloMenu(idPlatilloMenu, null, 0, 0, null);
        return conexion.eliminarPlatilloMenu(idPlatilloMenu) && listaPlatillosMenu.remove(pm);
    }

    public ArrayList<PlatilloMenu> consultarMenuDia(int dia) {
        ArrayList<PlatilloMenu> menuDia = new ArrayList<>();
        listaPlatillosMenu.stream().filter((platilloMenu) -> (platilloMenu.getDiaSemana() == dia)).forEachOrdered((platilloMenu) -> {
            menuDia.add(platilloMenu);
        });
        return menuDia;
    }

    public ArrayList<PlatilloMenu> consultarMenuCategoria(String categoria) {
        ArrayList<PlatilloMenu> menuCategoria = new ArrayList<>();
        listaPlatillosMenu.stream().filter((platilloMenu) -> (platilloMenu.getCategoria().equals(categoria))).forEachOrdered((platilloMenu) -> {
            menuCategoria.add(platilloMenu);
        });
        return menuCategoria;
    }

    public ArrayList<PlatilloMenu> consultarMenuDiaCategoria(int dia, String categoria) {
        ArrayList<PlatilloMenu> menuDiaCategoria = new ArrayList<>();
        listaPlatillosMenu.stream().filter((platilloMenu) -> (platilloMenu.getCategoria().equals(categoria) && platilloMenu.getDiaSemana() == dia)).forEachOrdered((platilloMenu) -> {
            menuDiaCategoria.add(platilloMenu);
        });
        return menuDiaCategoria;
    }

    public ArrayList<PlatilloMenu> consultarMenu() {

        return listaPlatillosMenu;
    }
}
