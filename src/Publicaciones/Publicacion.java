package Publicaciones;
import Juegos.Juego;
import Usuarios.Usuario;


public class Publicacion {
    private Usuario usuario;
    private String titulo;
    private String contenido;
    private Juego juego;

    public Publicacion(Usuario usuario, String titulo, String contenido, Juego juego) {
        this.usuario = usuario;
        this.titulo = titulo;
        this.contenido = contenido;
        this.juego=juego;    
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Juego getJuego() {
        return juego;
    }

    public void setJuego(Juego juego) {
        this.juego = juego;
    }

}
