/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import Interfaces.IConexion;
import java.util.Date;

/**
 *
 * @author Eduardo Ramírez
 */
public class PruebaConexion {

    public static void main(String[] args) {
        IConexion co = new ConexionBD();
        System.out.println(co.conectar());
        System.out.println(co.insertarReservaPlatillo(2, 2, 1, new Date(), 2));
        
         co.consultarReservasPlatillos();
        System.out.println(co.desconectar());
    }
}
