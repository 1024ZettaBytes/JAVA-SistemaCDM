package PruebasControl;


import java.time.DayOfWeek;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import logica.Control;
import logica.ControlUsuarios;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eduardo Ramírez
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Calendar fechaLunes = Calendar.getInstance();
        Calendar fechaDomingo = Calendar.getInstance();
        
        GregorianCalendar hoy = new GregorianCalendar();
        System.out.println(hoy.get(Calendar.DAY_OF_WEEK));
        fechaLunes.add(Calendar.DAY_OF_YEAR, -(hoy.get(Calendar.DAY_OF_WEEK)-2));
        fechaDomingo.add(Calendar.DAY_OF_YEAR, 8-hoy.get(Calendar.DAY_OF_WEEK));
        
        System.out.println("Fecha actual "+hoy.getTime());
        System.out.println("Fecha Lunes "+fechaLunes.getTime());
        System.out.println("Fecha Domingo "+fechaDomingo.getTime());
      
        
    }
    
}
