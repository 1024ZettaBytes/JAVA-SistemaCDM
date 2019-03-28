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
            for (Platillo platillo : Control.platillos.consultarLista()) {
                System.out.println(platillo.getIdPlatillo()+", "+platillo.getNombre());
            }
       
        
        } catch (ExceptionInInitializerError e) {
            System.out.println("ERROR: NO se pudo conectar a la base de datos.");
        }
        
        
        
    }
}
