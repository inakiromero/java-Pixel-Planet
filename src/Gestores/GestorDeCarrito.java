package Gestores;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import Compras.CarritoCompra;
import Compras.Facturacion;
import Juegos.Juego;
import MetodoDePago.FormaDePago;
import Usuarios.Usuario;
import excepciones.ExcepcionGeneral;

public class GestorDeCarrito {
private Map<Usuario, CarritoCompra> carritos;
private GestorDeJuego gestorDeJuego;

    public GestorDeCarrito(GestorDeJuego gestorDeJuego) {
        this.carritos = new HashMap<>();
        this.gestorDeJuego=gestorDeJuego;
    }

    public Map<Usuario, CarritoCompra> getCarritos() {
        return carritos;
    }

    public void setCarritos(Map<Usuario, CarritoCompra> carritos) {
        this.carritos = carritos;
    }

    public GestorDeJuego getGestorDeJuego() {
        return gestorDeJuego;
    }

    public void setGestorDeJuego(GestorDeJuego gestorDeJuego) {
        this.gestorDeJuego = gestorDeJuego;
    }

    public CarritoCompra obtenerCarrito(Usuario usuario) {
        return carritos.computeIfAbsent(usuario, k -> new CarritoCompra());
    }

   public void agregarJuegoAlCarrito(Usuario usuario, Juego juego,CarritoCompra carrito) throws ExcepcionGeneral {
        if (carrito.getJuegos().contains(juego)) {
            throw new ExcepcionGeneral("El juego ya está en el carrito.");
        }
        carrito.agregarJuego(juego);
        carritos.put(usuario, carrito);
    }

    public void eliminarJuegoDelCarrito(Usuario usuario, Juego juego) {
        CarritoCompra carrito = obtenerCarrito(usuario);
        carrito.eliminarJuego(juego);
    }

    public double calcularTotal(Usuario usuario) {
        CarritoCompra carrito = obtenerCarrito(usuario);
        return carrito.calcularTotal();
    }


    public void mostrarCarrito(Usuario usuario) {
        CarritoCompra carrito = obtenerCarrito(usuario);
        System.out.println("Carrito de " + usuario.getNombreUsuario() + ":");
        for (Juego juego : carrito.getJuegos()) {
            System.out.println(" - " + juego.getTitulo() + " - " + juego.getPrecio());
        }
        System.out.println("Total: " + carrito.calcularTotal());
    }

    public void realizarCompra(Usuario usuario, FormaDePago tipoPago) throws ExcepcionGeneral {
        CarritoCompra carrito = obtenerCarrito(usuario);
        if (carrito.getJuegos().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El carrito de compras está vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tipoPago == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un método de pago antes de realizar la compra.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double total = carrito.calcularTotal();
        double totalConImpuesto = total * 1.60; // Incluyendo el 60% de impuestos

        boolean pagoExitoso = tipoPago.metodoPago(totalConImpuesto);
        if (pagoExitoso) {
            List<Juego> juegosComprados = carrito.getJuegos();
            for (Juego juego : juegosComprados) {
                usuario.getBiblioteca().add(juego);
            }
            JOptionPane.showMessageDialog(null, "Compra realizada con éxito por " + usuario.getNombreUsuario() + ".", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Generar ticket de compra y mostrarlo en una ventana emergente
            Facturacion facturacion = new Facturacion(carrito);
            String ticket = facturacion.generarTicket();
            JOptionPane.showMessageDialog(null, "Ticket de compra:\n" + ticket, "Ticket de Compra", JOptionPane.INFORMATION_MESSAGE);
                 carrito.getJuegos().clear();  

        } else {
            throw new ExcepcionGeneral("El pago no se pudo procesar.");
        }
    }
}

