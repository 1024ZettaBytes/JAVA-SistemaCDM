package negocio;

/**
 *
 */
public class Cliente {
    private int idCliente;
    private String nombre;
    private int creditoDesayuno;
    private int creditoComida;
    private int creditoCena;
    public Cliente(){
        this.idCliente = -1;
        this.nombre = null;
        this.creditoDesayuno = -1;
        this.creditoComida = -1;
        this.creditoCena = -1;
    }

    public Cliente(int idCliente, String nombre, int creditoDesayuno, int creditoComida, int creditoCena) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.creditoDesayuno = creditoDesayuno;
        this.creditoComida = creditoComida;
        this.creditoCena = creditoCena;
    }
    
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCreditoDesayuno() {
        return creditoDesayuno;
    }

    public void setCreditoDesayuno(int creditoDesayuno) {
        this.creditoDesayuno = creditoDesayuno;
    }

    public int getCreditoComida() {
        return creditoComida;
    }

    public void setCreditoComida(int creditoComida) {
        this.creditoComida = creditoComida;
    }

    public int getCreditoCena() {
        return creditoCena;
    }

    public void setCreditoCena(int creditoCena) {
        this.creditoCena = creditoCena;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + this.idCliente;
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
        final Cliente other = (Cliente) obj;
        if (this.idCliente != other.idCliente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    
}
