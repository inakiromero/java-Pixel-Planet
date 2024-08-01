package Usuarios;

import java.util.ArrayList;
import java.util.List;

import Gestores.GestorDeJuego;
import Juegos.Juego;
import MetodoDePago.FormaDePago;
import MetodoDePago.TipoPago;

public class Usuario {
    private static final String ADMIN_PASSWORD = "superSecreta123";
    private static int ultimoId = 0; // Variable estática para mantener el último id utilizado

    private final String id;
    private String nombre;
    private String apellido;
    private String correo;
    private String nombreUsuario;
    private String contrasenia;
    private List<FormaDePago> tipoPago;
    private ArrayList<Juego> biblioteca;

    public Usuario(String nombre, String apellido, String correo, String nombreUsuario, String contrasenia) {
        this.id = generarNuevoId();
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.tipoPago = new ArrayList<>();
        this.biblioteca = new ArrayList<>();
    }

    private static synchronized String generarNuevoId() {
        return String.valueOf(++ultimoId); // Genera un nuevo id en formato String
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public List<FormaDePago> getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(List<FormaDePago> tipoPago) {
        this.tipoPago = tipoPago;
    }

    public ArrayList<Juego> getBiblioteca() {
        return biblioteca;
    }

    public void setBiblioteca(ArrayList<Juego> biblioteca) {
        this.biblioteca = biblioteca;
    }

    public void agregarTipoPago(FormaDePago tipoPago) {
        this.tipoPago.add(tipoPago);
    }

    public void eliminarTipoPago(TipoPago tipoPago) {
        this.tipoPago.remove(tipoPago);
    }

    public void agregarJuegoABiblioteca(Juego juego) {
        this.biblioteca.add(juego);
    }

    public boolean autenticar(String contrasenia) {
        return ADMIN_PASSWORD.equals(contrasenia);
    }

    public void bibliotecaFromString(String bibliotecaStr, GestorDeJuego gestorDeJuego) {
        String[] juegos = bibliotecaStr.split(";");
        for (String codigoJuego : juegos) {
            if (!codigoJuego.trim().isEmpty()) {
                Juego juego = gestorDeJuego.buscarJuegoPorCodigo(codigoJuego);
                if (juego != null) {
                    this.biblioteca.add(juego);
                }
            }
        }
    }

    public boolean isAdmin() {
        return false; // Por defecto, un usuario no es administrador
    }
}
