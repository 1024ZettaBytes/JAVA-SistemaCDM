package negocio;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 */
public class VentaPlatillo{
    private int idVentaPlatillo;
    
   private Venta ventaGeneral;
   private String nombreCliente;
   private Platillo platillo;
   private float costo;
    public VentaPlatillo(){
        this.idVentaPlatillo = -1;
        this.ventaGeneral = null;
        this.nombreCliente = null;
        this.platillo = null;
        this.costo = -1;
    }

    public VentaPlatillo(int idVentaPlatillo, Venta ventaGeneral, String nombreCliente, Platillo platillo, float costo) {
        this.idVentaPlatillo = idVentaPlatillo;
        this.ventaGeneral = ventaGeneral;
        this.nombreCliente = nombreCliente;
        this.platillo = platillo;
        this.costo = costo;
    }
    
    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        
        this.nombreCliente = nombreCliente;
    }

    public int getIdVentaPlatillo() {
        return idVentaPlatillo;
    }

    public void setIdVentaPlatillo(int idVentaPlatillo) {
        this.idVentaPlatillo = idVentaPlatillo;
    }

    public Venta getVentaGeneral() {
        return ventaGeneral;
    }

    public void setVentaGeneral(Venta ventaGeneral) {
        this.ventaGeneral = ventaGeneral;
    }

    public Platillo getPlatillo() {
        return platillo;
    }

    public void setPlatillo(Platillo platillo) {
        this.platillo = platillo;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.idVentaPlatillo;
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
        final VentaPlatillo other = (VentaPlatillo) obj;
        if (this.idVentaPlatillo != other.idVentaPlatillo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "VentaPlatillos{" + "idVentaPlatillo=" + idVentaPlatillo + ", ventaGeneral=" + ventaGeneral + ", nombreCliente=" + nombreCliente + ", platillo=" + platillo + ", costo=" + costo + '}';
    }

  
    
    
}
