package MetodoDePago;

import excepciones.ExcepcionGeneral;

public class PayPal extends FormaDePago {
    private String correoElectronico;
    private String contrasenia;

    public PayPal(String nombreComleto, String correoElectronico, String contrasenia) {
        super(nombreComleto);
        this.correoElectronico = correoElectronico;
        this.contrasenia = contrasenia;
    }

    
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((correoElectronico == null) ? 0 : correoElectronico.hashCode());
        result = prime * result + ((contrasenia == null) ? 0 : contrasenia.hashCode());
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
        PayPal other = (PayPal) obj;
        if (correoElectronico == null) {
            if (other.correoElectronico != null)
                return false;
        } else if (!correoElectronico.equals(other.correoElectronico))
            return false;
        if (contrasenia == null) {
            if (other.contrasenia != null)
                return false;
        } else if (!contrasenia.equals(other.contrasenia))
            return false;
        return true;
    }

    public boolean metodoPago(double monto) throws ExcepcionGeneral {
        if (!validarCuenta()) {
            throw new ExcepcionGeneral("La cuenta de PayPal no es válida.");
        }
        System.out.println("Procesando pago con PayPal de " + monto);
        return true; // Simular éxito en el pago
    }

    public boolean validarCuenta() {
        // Implementación básica de la validación de la cuenta de PayPal
        return correoElectronico.matches("^[A-Za-z0-9+_.-]+@(.+)$") && contrasenia.length() >= 6;
    }
    
}
