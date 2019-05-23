/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import Interfaces.IConexion;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Eduardo Ramírez
 */
public class PruebaConexion {

    public static void main(String[] args) throws ParseException {
        IConexion c = new ConexionBD();
        c.conectar();
        ArrayList<Object[]> ven= c.consultarVentasCredito();
        for (Object[] objects : ven) {
            System.out.println(Arrays.toString(objects));
        }
        
}
}
