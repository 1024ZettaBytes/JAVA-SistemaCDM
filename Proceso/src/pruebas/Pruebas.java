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
    public static void main(String args[]){
        IControl c = new Control();
        System.out.println(Control.platillos.consultarLista());
        System.out.println(Control.platillos.agregarPlatillo(new Platillo(2, "Papas con carne molida y frijoles")));
        
    }
}
