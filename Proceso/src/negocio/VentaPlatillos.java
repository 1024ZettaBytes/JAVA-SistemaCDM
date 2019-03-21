package negocio;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 */
public class VentaPlatillos extends Venta {
    private String nombreCliente;
    private List<Platillo> listaPlatillos;
    public VentaPlatillos(){
        this.folioVenta = -1;
        this.nombreCliente = null;
        this.fechaHora = null;
        this.idUsuario = -1;
        this.listaPlatillos = null;
        this.monto = -1;
    }
    public VentaPlatillos(int folio, String nombreCliente, Timestamp fechaHora, int idUsuario, List<Platillo> listaPlatillos, float monto){
        this.folioVenta = folio;
        this.nombreCliente = nombreCliente;
        this.fechaHora = fechaHora;
        this.idUsuario = idUsuario;
        this.listaPlatillos = listaPlatillos;
        this.monto = monto;
    }
    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        
        this.nombreCliente = nombreCliente;
    }

  
    public List<Platillo> getListaPlatillos() {
        return listaPlatillos;
    }

    public void setListaPlatillos(List<Platillo> listaPlatillos) {
        this.listaPlatillos = listaPlatillos;
    }

    @Override
    public String toString() {
        return super.toString()+"VentaPlatillos{" + "nombreCliente=" + nombreCliente + ", listaPlatillos=" + listaPlatillos + '}';
    }
    
    
}
