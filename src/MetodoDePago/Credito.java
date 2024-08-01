package MetodoDePago;


import excepciones.ExcepcionGeneral;

public class Credito extends Tarjeta{

    public Credito(String nombreCompleto, String fechaExpiracion, String numeroTarjeta, String codigoSeguridad) {
        super(nombreCompleto, fechaExpiracion, numeroTarjeta, codigoSeguridad);
    }

    @Override
    public boolean metodoPago(double monto) throws ExcepcionGeneral {
        if (!validarTarjeta()) {
            throw new ExcepcionGeneral("La tarjeta de crédito no es válida.");
        }
        System.out.println("Procesando pago con tarjeta de crédito de " + monto);
        return true; // Simular éxito en el pago
    }

   
}


