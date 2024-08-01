package Sistemas;

import Gestores.GestorDeCarrito;
import Gestores.GestorDeJuego;
import Gestores.GestorDePublicacion;
import Gestores.GestorDeUsuarios;
import Juegos.Juego;
import Usuarios.Usuario;
import excepciones.ExcepcionGeneral;
import Publicaciones.Resenia;

import javax.swing.*;

import Compras.CarritoCompra;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Tienda {
    private GestorDeJuego gestorJuegos;
    private GestorDeCarrito gestorDeCarrito;
    private GestorDePublicacion gestorDePublicacion;
    private Usuario usuario;
    private JFrame frame;
    private JPanel panel;
    private JButton volverMenuButton;
    private JButton irCarritoButton;
    private JButton buscarJuegoButton;
    private JButton refrescarJuegosButton;
    private GestorDeUsuarios gestorDeUsuarios;
    private CarritoCompra carrito;

    public Tienda(GestorDeJuego gestorJuegos, Usuario usuario, GestorDePublicacion gestorDePublicacion, GestorDeUsuarios gestorDeUsuarios) {
        this.gestorJuegos = gestorJuegos;
        this.usuario = usuario;
        this.gestorDeCarrito = new GestorDeCarrito(gestorJuegos);
        this.gestorDePublicacion = gestorDePublicacion;
        this.gestorDeUsuarios = gestorDeUsuarios;
        this.carrito = new CarritoCompra();
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Tienda");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        refrescarJuegos();

        buscarJuegoButton = new JButton("Buscar Juego por Título");
        buscarJuegoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarJuego();
            }
        });

        volverMenuButton = new JButton("Volver al Menú");
        volverMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverMenu();
                frame.dispose();
            }
        });

        irCarritoButton = new JButton("Ir al Carrito");
        irCarritoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                irAlCarrito();
            }
        });

        refrescarJuegosButton = new JButton("Refrescar Juegos");
        refrescarJuegosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refrescarJuegos();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 4));
        buttonPanel.add(buscarJuegoButton);
        buttonPanel.add(volverMenuButton);
        buttonPanel.add(irCarritoButton);
        buttonPanel.add(refrescarJuegosButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void refrescarJuegos() {
        panel.removeAll();
        List<Juego> juegosRandom = gestorJuegos.getJuegosRandom(5);

        for (Juego juego : juegosRandom) {
            JPanel juegoPanel = new JPanel();
            juegoPanel.setLayout(new GridLayout(1, 4));

            JLabel tituloLabel = new JLabel("Título: " + juego.getTitulo());
            JLabel creadorLabel = new JLabel("Creador: " + juego.getCreador());
            JLabel tipoLabel = new JLabel("Tipo: " + juego.getTipo());
            JLabel precioLabel = new JLabel("Precio: $" + juego.getPrecio());

            juegoPanel.add(tituloLabel);
            juegoPanel.add(creadorLabel);
            juegoPanel.add(tipoLabel);
            juegoPanel.add(precioLabel);

            JButton verReseniasButton = new JButton("Ver Reseñas");
            verReseniasButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    verResenias(juego);
                }
            });

            JButton agregarCarritoButton = new JButton("Agregar al Carrito");
            agregarCarritoButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        agregarAlCarrito(juego);
                    } catch (ExcepcionGeneral e1) {
                        e1.printStackTrace();
                    }
                }
            });

            juegoPanel.add(verReseniasButton);
            juegoPanel.add(agregarCarritoButton);

            panel.add(juegoPanel);
        }

        panel.revalidate();
        panel.repaint();
    }

    private void verResenias(Juego juego) {
        List<Resenia> resenias = juego.getValoracionesDeUsuarios();
        StringBuilder sb = new StringBuilder();
        sb.append("Reseñas para ").append(juego.getTitulo()).append(":\n");
        for (Resenia resenia : resenias) {
            sb.append("Usuario: ").append(resenia.getUsuario().getNombreUsuario()).append("\n");
            sb.append("Comentario: ").append(resenia.getComentario()).append("\n");
            sb.append("Valoración: ").append(resenia.isValoracion()).append("\n\n");
        }
        JOptionPane.showMessageDialog(frame, sb.toString(), "Reseñas", JOptionPane.PLAIN_MESSAGE);
    }

    private void agregarAlCarrito(Juego juego) throws ExcepcionGeneral {
        gestorDeCarrito.agregarJuegoAlCarrito(usuario, juego, carrito);
        JOptionPane.showMessageDialog(frame, "Juego agregado al carrito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void buscarJuego() {
        String titulo = JOptionPane.showInputDialog(frame, "Ingrese el título del juego a buscar:");
        Juego juegoEncontrado = gestorJuegos.buscarJuegoPorTitulo(titulo);
        if (juegoEncontrado != null) {
            JOptionPane.showMessageDialog(frame, "Juego encontrado:\n" +
                    "Título: " + juegoEncontrado.getTitulo() + "\n" +
                    "Creador: " + juegoEncontrado.getCreador() + "\n" +
                    "Tipo: " + juegoEncontrado.getTipo() + "\n" +
                    "Precio: $" + juegoEncontrado.getPrecio(), "Juego Encontrado", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Juego no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void volverMenu() {
        frame.dispose();
        new MainMenu(usuario, gestorDeUsuarios, gestorDePublicacion, gestorJuegos);
    }

    private void irAlCarrito() {
        frame.dispose();
        new SistemaCarrito(usuario, gestorDeCarrito, gestorDePublicacion, gestorDeUsuarios, gestorJuegos, carrito);
    }
}
