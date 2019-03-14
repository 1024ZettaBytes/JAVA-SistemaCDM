package persistencia;

/**
 *
 */
public class Producto {
    private int idProducto;
    private String nombre;
    private int stock;
public Producto(){
    this.idProducto=-1;
    this.nombre=null;
    this.stock = -1;
}
public Producto(int id, String nombre, int stock){
    this.idProducto=id;
    this.nombre=nombre;
    this.stock = stock;
}
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + this.idProducto;
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
        final Producto other = (Producto) obj;
        if (this.idProducto != other.idProducto) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Producto{" + "idProducto=" + idProducto + ", nombre=" + nombre + ", stock=" + stock + '}';
    }
    
}
