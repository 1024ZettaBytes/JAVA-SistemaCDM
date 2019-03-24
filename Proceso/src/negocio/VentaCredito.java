package negocio;

import java.sql.Timestamp;

/**
 *
 */
public class VentaCredito{
    private int idVentaCredito;
    private Venta ventaGeneral;
    private Cliente cliente;
    private int cantidadDesayunos;
    private int cantidadComidas;
    private int cantidadCenas;
 public VentaCredito() {
        this.idVentaCredito = -1;
        this.ventaGeneral = null;
        this.cliente = null;
        this.cantidadDesayunos = -1;
        this.cantidadComidas = -1;
        this.cantidadCenas = -1;
    }
    public VentaCredito(int idVentaCredito, Venta ventaGeneral, Cliente cliente, int cantidadDesayunos, int cantidadComidas, int cantidadCenas) {
        this.idVentaCredito = idVentaCredito;
        this.ventaGeneral = ventaGeneral;
        this.cliente = cliente;
        this.cantidadDesayunos = cantidadDesayunos;
        this.cantidadComidas = cantidadComidas;
        this.cantidadCenas = cantidadCenas;
    }

    public int getIdVentaCredito() {
        return idVentaCredito;
    }

    public void setIdVentaCredito(int idVentaCredito) {
        this.idVentaCredito = idVentaCredito;
    }

    public Venta getVentaGeneral() {
        return ventaGeneral;
    }

    public void setVentaGeneral(Venta ventaGeneral) {
        this.ventaGeneral = ventaGeneral;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getCantidadDesayunos() {
        return cantidadDesayunos;
    }

    public void setCantidadDesayunos(int cantidadDesayunos) {
        this.cantidadDesayunos = cantidadDesayunos;
    }

    public int getCantidadComidas() {
        return cantidadComidas;
    }

    public void setCantidadComidas(int cantidadComidas) {
        this.cantidadComidas = cantidadComidas;
    }

    public int getCantidadCenas() {
        return cantidadCenas;
    }

    public void setCantidadCenas(int cantidadCenas) {
        this.cantidadCenas = cantidadCenas;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.idVentaCredito;
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
        final VentaCredito other = (VentaCredito) obj;
        if (this.idVentaCredito != other.idVentaCredito) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VentaCredito{" + "idVentaCredito=" + idVentaCredito + ", ventaGeneral=" + ventaGeneral + ", cliente=" + cliente + ", cantidadDesayunos=" + cantidadDesayunos + ", cantidadComidas=" + cantidadComidas + ", cantidadCenas=" + cantidadCenas + '}';
    }
    

}
