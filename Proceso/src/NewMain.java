package PruebasControl;


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
      Control c = new Control();
        System.out.println(Control.clientes.consultarLista());
     
      
        
    }
    
}
