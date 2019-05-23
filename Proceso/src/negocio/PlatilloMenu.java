package negocio;

import java.util.Objects;

/**
 *
 */
public class PlatilloMenu {
    private int idPlatilloMenu;
    private Platillo platillo;
    private int diaSemana;
    private int cantidad;
    private int reservados;
    private String categoria;

    public PlatilloMenu() {
        this.idPlatilloMenu = 0;
        this.platillo = null;
        this.diaSemana = -1;
        this.cantidad = -1;
        this.reservados = -1;
        this.categoria = null;
    }

    public PlatilloMenu(int idPlatilloMenu, Platillo platillo, int diaSemana, int cantidad, int reservados, String categoria) {
        this.idPlatilloMenu = idPlatilloMenu;
        this.platillo = platillo;
        this.diaSemana = diaSemana;
        this.cantidad = cantidad;
        this.reservados = reservados;
        this.categoria = categoria;
    }

    public int getIdPlatilloMenu() {
        return idPlatilloMenu;
    }

    public void setIdPlatilloMenu(int idPlatilloMenu) {
        this.idPlatilloMenu = idPlatilloMenu;
    }

    public Platillo getPlatillo() {
        return platillo;
    }

    public int getReservados() {
        return reservados;
    }

    public void setReservados(int reservados) {
        this.reservados = reservados;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + this.idPlatilloMenu;
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
        if (this.idPlatilloMenu != other.idPlatilloMenu) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return platillo.getNombre();
    }
    
    
}
