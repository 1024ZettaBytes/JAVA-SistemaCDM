package persistencia;

import java.util.Objects;

/**
 *
 */
public class PlatilloMenu {
    private Platillo platillo;
    private int diaSemana;
    private int cantidad;

    public PlatilloMenu() {
        this.platillo = null;
        this.diaSemana = -1;
        this.cantidad = -1;
    }

    public PlatilloMenu(Platillo platillo, int diaSemana, int cantidad) {
        this.platillo = platillo;
        this.diaSemana = diaSemana;
        this.cantidad = cantidad;
    }

    public Platillo getPlatillo() {
        return platillo;
    }

    public void setPlatillo(Platillo platillo) {
        this.platillo = platillo;
    }

    public int getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.platillo);
        hash = 89 * hash + this.diaSemana;
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
        final PlatilloMenu other = (PlatilloMenu) obj;
        if (this.diaSemana != other.diaSemana) {
            return false;
        }
        if (!Objects.equals(this.platillo, other.platillo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PlatilloMenu{" + "platillo=" + platillo + ", diaSemana=" + diaSemana + ", cantidad=" + cantidad + '}';
    }
    
    
}
