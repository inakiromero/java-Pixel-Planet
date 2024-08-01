package MetodoDePago;

import excepciones.ExcepcionGeneral;

public class debito extends Tarjeta {

    public debito(String nombreCompleto, String fechaExpiracion, String numeroTarjeta, String codigoSeguridad) {
        super(nombreCompleto, fechaExpiracion, numeroTarjeta, codigoSeguridad);
    }

    @Override
     public boolean metodoPago(double monto) throws ExcepcionGeneral {
        if (!validarTarjeta()) {
            throw new ExcepcionGeneral("La tarjeta de débito no es válida.");
        }
        System.out.println("Procesando pago con tarjeta de débito de " + monto);
        return true; // Simular éxito en el pago
    }

    
   
}
