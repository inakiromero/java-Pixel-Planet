package MetodoDePago;

import excepciones.ExcepcionGeneral;

public interface TipoPago  {
    public boolean metodoPago(double valor)throws ExcepcionGeneral;
    
}
