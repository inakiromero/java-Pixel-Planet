package Compras;
import java.util.ArrayList;
import java.util.List;
import Juegos.Juego;

public class CarritoCompra {
    private List<Juego> juegos;

    public CarritoCompra() {
        this.juegos = new ArrayList<>();
    }

    public void agregarJuego(Juego juego) {
        this.juegos.add(juego);
    }

    public void eliminarJuego(Juego juego) {
        this.juegos.remove(juego);
    }

    public double calcularTotal() {
        double total = 0;
        for (Juego juego : juegos) {
            total += juego.getPrecio();
        }
        return total;
    }

    public List<Juego> getJuegos() {
        return juegos;
    }

    public void setJuegos(List<Juego> juegos) {
        this.juegos = juegos;
    }

    public CarritoCompra(List<Juego> juegos) {
        this.juegos = juegos;
    }
}