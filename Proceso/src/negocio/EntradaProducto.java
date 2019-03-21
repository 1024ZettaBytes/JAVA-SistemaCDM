/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

/**
 *
 * @author Eduardo Ramírez
 */
public class EntradaProducto {
    private int idEntrada;
    private int idProducto;
    private float unidades;
    private float gasto;
    public EntradaProducto(){
        this.idEntrada = -1;
        this.idProducto = -1;
        this.unidades = -1;
        this.gasto=-1;
    }

    public EntradaProducto(int idEntrada, int idProducto, float unidades, float gasto) {
        this.idEntrada = idEntrada;
        this.idProducto = idProducto;
        this.unidades = unidades;
        this.gasto = gasto;
    }

    public int getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(int idEntrada) {
        this.idEntrada = idEntrada;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public float getUnidades() {
        return unidades;
    }

    public void setUnidades(float unidades) {
        this.unidades = unidades;
    }

    public float getGasto() {
        return gasto;
    }

    public void setGasto(float gasto) {
        this.gasto = gasto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.idEntrada;
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
        final EntradaProducto other = (EntradaProducto) obj;
        if (this.idEntrada != other.idEntrada) {
            return false;
        }
        return true;
    }
    
}
