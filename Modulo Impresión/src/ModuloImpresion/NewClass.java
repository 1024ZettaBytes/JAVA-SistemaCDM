/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModuloImpresion;

import Interfaces.IImpresion;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author ed000
 */
public class NewClass {

    public static void main(String args[]) {
        //Aca llenamos los articulos, sustituyelo por lo de tu eleccion
    String items = "2   Articulo Prueba   15.00\n"+
    "7   Articulo Tara tara   25.00\n"+
    "4   Super articulo   55.39";
    String store = "Picharras Ltd.";
    String venue = "Molas, Yuc.";
    String date = "01/enero/2012";
    String caissier = "Josue Camara";
    Ticket ticket = new Ticket(store, venue, "5", "99", caissier, date, items, "100.00", "16.00", "116.00", "150", "34");
    ticket.print();
    }
}
