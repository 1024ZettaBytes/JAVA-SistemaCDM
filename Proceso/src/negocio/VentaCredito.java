package negocio;

import java.sql.Timestamp;

/**
 *
 */
public class VentaCredito extends Venta {

    private int idCliente;
    private int cantidadDesayunos;
    private int cantidadComidas;
    private int cantidadCenas;

    public VentaCredito() {
        this.folioVenta = -1;
        this.idCliente = -1;
        this.fechaHora = null;
        this.idUsuario = -1;
        this.cantidadDesayunos = -1;
        this.cantidadComidas = -1;
        this.cantidadCenas = -1;
        this.monto = -1;
    }

    public VentaCredito(int folio, int idCliente, Timestamp fechaHora, int idUsuario, int cantidadDesayunos, int cantidadComidas, int cantidadCenas, float monto) {
        this.folioVenta = folio;
        this.idCliente = idCliente;
        this.fechaHora = fechaHora;
        this.idUsuario = idUsuario;
        this.cantidadDesayunos = cantidadDesayunos;
        this.cantidadComidas = cantidadComidas;
        this.cantidadCenas = cantidadCenas;
        this.monto = monto;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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
    public String toString() {
        return super.toString()+"VentaCredito{" + "idCliente=" + idCliente + ", cantidadDesayunos=" + cantidadDesayunos + ", cantidadComidas=" + cantidadComidas + ", cantidadCenas=" + cantidadCenas + '}';
    }

}
