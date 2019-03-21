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
public class SalidaProducto {
    private int idSalida;
    private int idProducto;
    private float unidades;
    public SalidaProducto(){
        this.idSalida = -1;
        this.idProducto = -1;
        this.unidades = -1;

    }

    public SalidaProducto(int idEntrada, int idProducto, float unidades) {
        this.idSalida = idEntrada;
        this.idProducto = idProducto;
        this.unidades = unidades;
        
    }

    public int getIdEntrada() {
        return idSalida;
    }

    public void setIdEntrada(int idEntrada) {
        this.idSalida = idEntrada;
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

    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.idSalida;
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
        final SalidaProducto other = (SalidaProducto) obj;
        if (this.idSalida != other.idSalida) {
            return false;
        }
        return true;
    }
}
