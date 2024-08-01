package MetodoDePago;

import excepciones.ExcepcionGeneral;

public class FormaDePago implements TipoPago {
    private String nombreComleto;
    

    public FormaDePago(String nombreComleto) {
        this.nombreComleto = nombreComleto;
    }

    @Override
    public boolean metodoPago(double valor) throws ExcepcionGeneral {
        return false;
    }

    

    public String getNombreComleto() {
        return nombreComleto;
    }

    public void setNombreComleto(String nombreComleto) {
        this.nombreComleto = nombreComleto;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nombreComleto == null) ? 0 : nombreComleto.hashCode());
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
        FormaDePago other = (FormaDePago) obj;
        if (nombreComleto == null) {
            if (other.nombreComleto != null)
                return false;
        } else if (!nombreComleto.equals(other.nombreComleto))
            return false;
        return true;
    }
    

}
