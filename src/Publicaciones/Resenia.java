package Publicaciones;
import Juegos.Juego;
import Usuarios.Usuario;
public class Resenia {
    private Juego juego;
    private Usuario usuario;
    private String comentario;
    private Boolean valoracion;

    public Resenia(Usuario usuario, String comentario, Boolean valoracion, Juego juego) {
        this.juego = juego;
        this.usuario = usuario;
        this.comentario = comentario;
        this.valoracion = valoracion;
    }

    

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isValoracion() {
        return valoracion;
    }

    public void setValoracion(boolean valoracion) {
        this.valoracion = valoracion;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((juego == null) ? 0 : juego.hashCode());
        result = prime * result + ((usuario == null) ? 0 : usuario.hashCode());
        result = prime * result + ((comentario == null) ? 0 : comentario.hashCode());
        result = prime * result + ((valoracion == null) ? 0 : valoracion.hashCode());
        return result;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Resenia other = (Resenia) obj;
        if (juego == null) {
            if (other.juego != null)
                return false;
        } else if (!juego.equals(other.juego))
            return false;
        if (usuario == null) {
            if (other.usuario != null)
                return false;
        } else if (!usuario.equals(other.usuario))
            return false;
        if (comentario == null) {
            if (other.comentario != null)
                return false;
        } else if (!comentario.equals(other.comentario))
            return false;
        if (valoracion == null) {
            if (other.valoracion != null)
                return false;
        } else if (!valoracion.equals(other.valoracion))
            return false;
        return true;
    }



    public Usuario getUsuario() {
        return usuario;
        }

}
