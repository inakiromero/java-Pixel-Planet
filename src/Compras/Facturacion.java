package Compras;
import Juegos.Juego;

public class Facturacion {
    private CarritoCompra carrito;

    public Facturacion(CarritoCompra carrito) {
        this.carrito = carrito;
    }

    public double getSubtotal() {
        double subtotal = 0.0;
        for (Juego juego : carrito.getJuegos()) {
            subtotal += juego.getPrecio();
        }
        return subtotal;
    }
    
    public double getTotalConImpuestos(double impuesto) {
        double subtotal = getSubtotal();
        return subtotal * (1 + impuesto);
    }

    public String generarTicket() {
        StringBuilder ticket = new StringBuilder();
        ticket.append("----- Ticket de Compra -----\n");
        for (Juego juego : carrito.getJuegos()) {
            ticket.append(juego.getTitulo()).append(" - $").append(String.format("%.2f", juego.getPrecio())).append("\n");
        }
        double subtotal = getSubtotal();
        double totalConImpuestos = getTotalConImpuestos(0.60);
        ticket.append("Subtotal: $").append(String.format("%.2f", subtotal)).append("\n");
        ticket.append("Total con impuestos: $").append(String.format("%.2f", totalConImpuestos)).append("\n");
        ticket.append("----------------------------\n");
        return ticket.toString();
    }
}