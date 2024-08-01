package MetodoDePago;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import excepciones.ExcepcionGeneral;

public abstract class Tarjeta extends FormaDePago {

    private String fechaExpiracion;
    private String numeroTarjeta;
    private String codigoSeguridad;

    


    public Tarjeta(String nombreComleto, String fechaExpiracion, String numeroTarjeta, String codigoSeguridad) {
        super(nombreComleto);
        this.fechaExpiracion = fechaExpiracion;
        this.numeroTarjeta = numeroTarjeta;
        this.codigoSeguridad = codigoSeguridad;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public void setFechaExpiracion(String fechaExpiracion) {
        this.fechaExpiracion = fechaExpiracion;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(String codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

   

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((fechaExpiracion == null) ? 0 : fechaExpiracion.hashCode());
        result = prime * result + ((numeroTarjeta == null) ? 0 : numeroTarjeta.hashCode());
        result = prime * result + ((codigoSeguridad == null) ? 0 : codigoSeguridad.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tarjeta other = (Tarjeta) obj;
        if (fechaExpiracion == null) {
            if (other.fechaExpiracion != null)
                return false;
        } else if (!fechaExpiracion.equals(other.fechaExpiracion))
            return false;
        if (numeroTarjeta == null) {
            if (other.numeroTarjeta != null)
                return false;
        } else if (!numeroTarjeta.equals(other.numeroTarjeta))
            return false;
        if (codigoSeguridad == null) {
            if (other.codigoSeguridad != null)
                return false;
        } else if (!codigoSeguridad.equals(other.codigoSeguridad))
            return false;
        return true;
    }

    public boolean validarTarjeta() {
        return numeroTarjeta.matches("\\d{16}") && 
               validarFechaExpiracion() && 
               codigoSeguridad.matches("\\d{3}");
    }

    private boolean validarFechaExpiracion() {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            LocalDate fecha = LocalDate.parse("01/" + fechaExpiracion, formatter);
            return fecha.isAfter(LocalDate.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }


    @Override
    public boolean metodoPago(double valor)throws ExcepcionGeneral {
        if (!validarTarjeta()) {
            throw new ExcepcionGeneral("La tarjeta no es válida.");
        }
        return false; 
    }

    @Override
    public String toString() {
        return "Tarjeta{" +
                ", fechaExpiracion='" + fechaExpiracion + '\'' +
                ", numeroTarjeta='" + numeroTarjeta + '\'' +
                ", codigoSeguridad=" + codigoSeguridad +
                '}';
    }

   
}
   

