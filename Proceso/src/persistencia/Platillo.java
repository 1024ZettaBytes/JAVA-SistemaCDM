package persistencia;

/**
 *
 */
public class Platillo {
    private int idPlatillo;
    private String nombre;
    private String categoria;
    private float precio;
    public Platillo(){
         this.idPlatillo = -1;
        this.nombre = null;
        this.categoria = null;
        this.precio = -1;
    }
    public Platillo(int id, String nombre, String categoria, float precio){
        this.idPlatillo = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio = precio;
    }
    public int getIdPlatillo() {
        return idPlatillo;
    }

    public void setIdPlatillo(int idPlatillo) {
        this.idPlatillo = idPlatillo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + this.idPlatillo;
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
        final Platillo other = (Platillo) obj;
        if (this.idPlatillo != other.idPlatillo) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Platillo{" + "idPlatillo=" + idPlatillo + ", nombre=" + nombre + ", categoria=" + categoria + ", precio=" + precio + '}';
    }
    
    
}
