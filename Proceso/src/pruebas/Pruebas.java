/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import Interfaces.IControl;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import logica.Control;
import logica.ControlUsuarios;
import negocio.Cliente;
import negocio.Platillo;
import negocio.PlatilloMenu;

/**
 *
 * @author Eduardo Ramírez
 */
public class Pruebas {
    public static void main(String args[]) throws InterruptedException{
        
        try {
            
            IControl c = new Control();
           Control.menu.agregar(new PlatilloMenu(0, Control.platillos.consultarPorId(38), 3, 20, "CENA"));
            Control.menu.agregar(new PlatilloMenu(0, Control.platillos.consultarPorId(39), 3, 20, "CENA"));
            Control.menu.agregar(new PlatilloMenu(0, Control.platillos.consultarPorId(40), 3, 20, "CENA"));
        
        } catch (ExceptionInInitializerError e) {
            System.out.println("ERROR: NO se pudo conectar a la base de datos.");
        }
        
        
        
    }
}
