/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import Interfaces.IControl;
import logica.Control;
import logica.ControlUsuarios;
import negocio.Platillo;

/**
 *
 * @author Eduardo Ramírez
 */
public class Pruebas {
    public static void main(String args[]) throws InterruptedException{
        try {
            IControl c = new Control();
        
        System.out.println(Control.platillos.consultarLista());
            for (int i = 1; i <= 20; i++) {
                Thread.sleep(1000);
                System.out.println("Segundos transcurridos: "+i);
            }
            System.out.println("Tratando de insertar platillo nuevo: ");
            System.out.println(Control.platillos.agregar(new Platillo(0, "Gallina pinta")));
            for (int i = 1; i <= 20; i++) {
                Thread.sleep(1000);
                System.out.println("Segundos transcurridos: "+i);
            }
             System.out.println("Tratando de insertar platillo nuevo: ");
            System.out.println(Control.platillos.agregar(new Platillo(0, "Gallina pinta")));
        } catch (ExceptionInInitializerError e) {
            System.out.println("ERROR: NO se pudo conectar a la base de datos.");
        }
        
        
        
    }
}
