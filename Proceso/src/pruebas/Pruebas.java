/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import Interfaces.IControl;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
           Calendar ca = Calendar.getInstance();
           
        
        } catch (ExceptionInInitializerError e) {
            System.out.println("ERROR: NO se pudo conectar a la base de datos.");
        }
        
        
        
    }
}
