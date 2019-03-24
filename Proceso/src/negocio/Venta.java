package negocio;

import java.sql.Timestamp;

/**
 *
 */
public class Venta {    
    private int folioVenta;
    private Timestamp fechaHora;
    private Usuario usuario;
 public Venta() {
        this.folioVenta = -1;
        this.fechaHora = null;
        this.usuario = null;
    }
    public Venta(int folioVenta, Timestamp fechaHora, Usuario usuario) {
        this.folioVenta = folioVenta;
        this.fechaHora = fechaHora;
        this.usuario = usuario;
    }
    
    public int getFolioVenta() {
        return folioVenta;
    }

    public void setFolioVenta(int folioVenta) {
        this.folioVenta = folioVenta;
    }

    public Timestamp getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Timestamp fechaHora) {
        this.fechaHora = fechaHora;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setIdUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.folioVenta;
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
        final Venta other = (Venta) obj;
        if (this.folioVenta != other.folioVenta) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Venta{" + "folioVenta=" + folioVenta + ", fechaHora=" + fechaHora + ", usuario=" + usuario + '}';
    }

    
    
    }
    

