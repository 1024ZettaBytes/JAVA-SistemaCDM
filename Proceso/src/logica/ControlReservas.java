/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import Interfaces.IConexion;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import negocio.Cliente;
import negocio.Platillo;
import negocio.ReservaPlatillo;

/**
 *
 * @author Eduardo Ramírez
 */
public class ControlReservas {

    private ArrayList<ReservaPlatillo> listaReservasPlatillos;
    private int reservaSiguiente;
    private IConexion conexion;

    public ControlReservas(IConexion conexion) {
        this.conexion = conexion;
        this.listaReservasPlatillos = new ArrayList<>();
        if (this.conexion.conectar()) {
            ArrayList<Object[]> arreglosReservas = conexion.consultarReservasPlatillos();
            for (Object[] arregloReserva : arreglosReservas) {
                String pattern = "yyyy-MM-dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                Date fecha = null;
                try {
                    fecha = simpleDateFormat.parse((String) arregloReserva[4]);
                } catch (ParseException ex) {
                    Logger.getLogger(ControlReservas.class.getName()).log(Level.SEVERE, null, ex);
                }
                ReservaPlatillo reserva = new ReservaPlatillo((Integer) arregloReserva[0],
                        Control.clientes.consultarPorId((Integer) arregloReserva[1]),
                        Control.platillos.consultarPorId((Integer) arregloReserva[2]),
                        (Integer) arregloReserva[3], fecha, (Integer) arregloReserva[5]);
                listaReservasPlatillos.add(reserva);
            }

        }
    }

    public boolean agregar(ReservaPlatillo nuevaReserva) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        if (conexion.insertarReservaPlatillo(nuevaReserva.getCliente().getIdCliente(), nuevaReserva.getPlatillo().getIdPlatillo(), nuevaReserva.getCantidad(), nuevaReserva.getFecha(), nuevaReserva.getTipo())) {
            reservaSiguiente = conexion.obtenUltimoID() + 1;
            nuevaReserva.setIdReserva(reservaSiguiente - 1);
            return !listaReservasPlatillos.contains(nuevaReserva) && listaReservasPlatillos.add(nuevaReserva);
        } else {
            return false;
        }
    }

    public boolean actualizar(ReservaPlatillo reserva) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        int index = listaReservasPlatillos.indexOf(reserva);
        if (index >= 0 && conexion.actualizarReservaPlatillo(reserva.getIdReserva(), reserva.getCliente().getIdCliente(), reserva.getPlatillo().getIdPlatillo(), reserva.getCantidad(), reserva.getFecha(), reserva.getTipo())) {
            listaReservasPlatillos.set(index, reserva);
            return true;
        }
        return false;
    }

    public boolean eliminar(int id) {
        if (!conexion.hayConexion()) {
            if (conexion.conectar() == false) {
                return false;
            }
        }
        ReservaPlatillo r = new ReservaPlatillo(id, null, null, 0, null, 0);
        return conexion.eliminarReservaPlatillo(id) && listaReservasPlatillos.remove(r);
    }

    public ReservaPlatillo consultarPorId(int idReserva) {
        ReservaPlatillo reserva = new ReservaPlatillo(idReserva, null, null, 0, null, 0);
        if (listaReservasPlatillos.contains(reserva)) {
            return listaReservasPlatillos.get(listaReservasPlatillos.indexOf(reserva));
        }
        return null;
    }

    public ArrayList<ReservaPlatillo> consultarPorCliente(Cliente cliente) {
        ArrayList<ReservaPlatillo> lista = new ArrayList();
        listaReservasPlatillos.stream().filter((reservaPlatillo) -> (reservaPlatillo.getCliente().equals(cliente))).forEachOrdered((reservaPlatillo) -> {
            lista.add(reservaPlatillo);
        });
        return lista;
    }

    public ArrayList<ReservaPlatillo> consultarPorFecha(Date fecha) {
        ArrayList<ReservaPlatillo> lista = new ArrayList();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(fecha);
        Calendar c2 = Calendar.getInstance();
        for (ReservaPlatillo reserva : listaReservasPlatillos) {
            c2.setTime(reserva.getFecha());
            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                    && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                    && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)) {
                lista.add(reserva);
            }

        }
        return lista;
    }

    public ArrayList<ReservaPlatillo> consultarPorFechaPlatillo(Date fecha, Platillo platillo) {
        ArrayList<ReservaPlatillo> lista = new ArrayList();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(fecha);
        Calendar c2 = Calendar.getInstance();
        for (ReservaPlatillo reserva : listaReservasPlatillos) {
            c2.setTime(reserva.getFecha());
            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                    && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                    && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
                    && reserva.getPlatillo().equals(platillo)) {
                lista.add(reserva);
            }

        }
        return lista;
    }
    public ArrayList<ReservaPlatillo> consultarPorFechaPlatilloTipo(Date fecha, Platillo platillo, int tipo) {
        ArrayList<ReservaPlatillo> lista = new ArrayList();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(fecha);
        Calendar c2 = Calendar.getInstance();
        for (ReservaPlatillo reserva : listaReservasPlatillos) {
            c2.setTime(reserva.getFecha());
            if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                    && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)
                    && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
                    && reserva.getPlatillo().equals(platillo)
                    && reserva.getTipo()==tipo) {
                lista.add(reserva);
            }

        }
        return lista;
    }

    public ArrayList<ReservaPlatillo> consultarReservasClienteVigente(Cliente cliente, Date fecha) {
        ArrayList<ReservaPlatillo> lista = new ArrayList<>();
        Calendar fechaRecibida = Calendar.getInstance();
        fechaRecibida.setTime(fecha);

        Calendar fechaReserva = Calendar.getInstance();
        for (ReservaPlatillo reserva : listaReservasPlatillos) {
            fechaReserva.setTime(reserva.getFecha());
            if (reserva.getCliente().equals(cliente) && fechaReserva.get(Calendar.DAY_OF_YEAR) >= fechaRecibida.get(Calendar.DAY_OF_YEAR)) {
                lista.add(reserva);
            }
        }
        return lista;
    }

    public ArrayList<ReservaPlatillo> consultarLista() {
        return listaReservasPlatillos;
    }

}
