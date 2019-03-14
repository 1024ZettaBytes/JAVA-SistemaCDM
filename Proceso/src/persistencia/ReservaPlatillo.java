package persistencia;

import java.util.Date;

/**
 *
 */
public class ReservaPlatillo {
    private int idReserva;
    private Cliente cliente;
    private Platillo platillo;
    private int cantidad;
    private Date fecha;
    private int tipo;
    public ReservaPlatillo(){
         this.idReserva = -1;
        this.cliente = null;
        this.platillo = null;
        this.cantidad = -1;
        this.fecha = null;
        this.tipo = -1;
    }

    public ReservaPlatillo(int idReserva, Cliente cliente, Platillo platillo, int cantidad, Date fecha, int tipo) {
        this.idReserva = idReserva;
        this.cliente = cliente;
        this.platillo = platillo;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.tipo = tipo;
    }
    
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Platillo getPlatillo() {
        return platillo;
    }

    public void setPlatillo(Platillo platillo) {
        this.platillo = platillo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
   

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.idReserva;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReservaPlatillo other = (ReservaPlatillo) obj;
        if (this.idReserva != other.idReserva) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ReservaPlatillo{" + "idReserva=" + idReserva + ", cliente=" + cliente + ", platillo=" + platillo + ", cantidad=" + cantidad + ", fecha=" + fecha + ", tipo=" + tipo + '}';
    }
    
}
