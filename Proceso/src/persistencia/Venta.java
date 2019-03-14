package persistencia;

import java.sql.Timestamp;

/**
 *
 */
public class Venta {
    protected int folioVenta;
    protected Timestamp fechaHora;
    protected int idUsuario;
    protected float monto;
    
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

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
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
        return "Venta{" + "folioVenta=" + folioVenta + ", fechaHora=" + fechaHora + ", idUsuario=" + idUsuario + ", monto=" + monto + '}';
    }
    
    }
    

