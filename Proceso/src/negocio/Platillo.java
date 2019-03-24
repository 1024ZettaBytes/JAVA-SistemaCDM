package negocio;

/**
 *
 */
public class Platillo {
    
    private int idPlatillo;
    private String nombre;
    
    
    public Platillo(){
         this.idPlatillo = -1;
        this.nombre = null;
        
    }
    public Platillo(int id, String nombre){
        this.idPlatillo = id;
        this.nombre = nombre;
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
        return "Platillo{" + "idPlatillo=" + idPlatillo + ", nombre=" + nombre +'}';
    }
    
    
}
