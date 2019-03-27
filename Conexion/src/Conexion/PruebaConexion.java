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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Eduardo Ramírez
 */
public class PruebaConexion {

    public static void main(String[] args) throws ParseException {
        String pattern = "yyyy-MM-dd";
SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

Date date = simpleDateFormat.parse("2019-03-27");
        System.out.println(date);
        GregorianCalendar g= new GregorianCalendar();
        System.out.println(g.get(0));
    }
}
