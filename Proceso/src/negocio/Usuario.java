package negocio;

/**
 *
 */
public class Usuario {
    private int idUsuario;
    private String pass;
    private String nombre;
    private boolean tipoAdmin;
    public Usuario(){
        this.idUsuario = -1;
        this.nombre = null;
        this.pass = null;
        this.tipoAdmin = false;
    }
     public Usuario(int id, String nombre, String pass, boolean tipoAdmin){
        this.idUsuario = id;
        this.nombre = nombre;
        this.pass = pass;
        this.tipoAdmin = tipoAdmin;
    }
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isTipoAdmin() {
        return tipoAdmin;
    }

    public void setTipoAdmin(boolean tipoAdmin) {
        this.tipoAdmin = tipoAdmin;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + this.idUsuario;
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
        final Usuario other = (Usuario) obj;
        if (this.idUsuario != other.idUsuario) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", pass=" + pass + ", nombre=" + nombre + ", tipoAdmin=" + tipoAdmin + '}';
    }
    
    
}
