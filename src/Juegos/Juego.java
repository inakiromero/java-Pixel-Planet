package Juegos;


import java.util.ArrayList;
import java.util.List;

import Publicaciones.Resenia;

public class Juego {
    private final String codigoUnico;
    private String titulo;
    private String creador;
    private TipoJuego tipo;
    private double precio;
    private double valoracion;
    private double valoracionDeUsuario;
    private List<Resenia> valoracionesDeUsuarios;


    public Juego(String codigoUnico,String titulo, double valoracion, double precio, TipoJuego tipo, String creador) {
        this.codigoUnico= codigoUnico;
        this.titulo = titulo;
        this.valoracion = valoracion;
        this.precio = precio;
        this.tipo = tipo;
        this.creador = creador;
        this.valoracionDeUsuario= 0.0;
        this.valoracionesDeUsuarios = new ArrayList<>();
    }

    public String getCodigoUnico() {
        return codigoUnico;
    }

    public void setValoracionDeUsuario(double valoracionDeUsuario) {
        this.valoracionDeUsuario = valoracionDeUsuario;
    }

    public List<Resenia> getValoracionesDeUsuarios() {
        return valoracionesDeUsuarios;
    }

    public void setValoracionesDeUsuarios(List<Resenia> valoracionesDeUsuarios) {
        this.valoracionesDeUsuarios = valoracionesDeUsuarios;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getValoracion() {
        return valoracion;
    }

    public void setValoracion(double valoracion) {
        this.valoracion = valoracion;
    }

    public TipoJuego getTipo() {
        return tipo;
    }

    public void setTipo(TipoJuego tipo) {
        this.tipo = tipo;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public double getValoracionDeUsuario() {
        return valoracionDeUsuario;
    }

    public void agregarValoracionDeUsuario(Resenia valoracion) {
        this.valoracionesDeUsuarios.add(valoracion);
        actualizarValoracionDeUsuario();
    }

   private void actualizarValoracionDeUsuario() {
        int totalValoraciones = valoracionesDeUsuarios.size();
        if (totalValoraciones == 0) {
            this.valoracionDeUsuario = 0;
            return;
        }

        int valoracionesPositivas = 0;
        for (Resenia val : valoracionesDeUsuarios) {
            if (val.isValoracion()==true) {
                valoracionesPositivas++;
            }
        }
        this.valoracionDeUsuario = ((double) valoracionesPositivas / totalValoraciones) * 100;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
        result = prime * result + ((creador == null) ? 0 : creador.hashCode());
        result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
        long temp;
        temp = Double.doubleToLongBits(precio);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(valoracion);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(valoracionDeUsuario);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        Juego other = (Juego) obj;
        if (titulo == null) {
            if (other.titulo != null)
                return false;
        } else if (!titulo.equals(other.titulo))
            return false;
        if (creador == null) {
            if (other.creador != null)
                return false;
        } else if (!creador.equals(other.creador))
            return false;
        if (tipo != other.tipo)
            return false;
        if (Double.doubleToLongBits(precio) != Double.doubleToLongBits(other.precio))
            return false;
        if (Double.doubleToLongBits(valoracion) != Double.doubleToLongBits(other.valoracion))
            return false;
        if (Double.doubleToLongBits(valoracionDeUsuario) != Double.doubleToLongBits(other.valoracionDeUsuario))
            return false;
        return true;
    }

}
