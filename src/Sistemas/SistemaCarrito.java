package Sistemas;

import javax.swing.*;

import Compras.CarritoCompra;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import Usuarios.Usuario;
import excepciones.ExcepcionGeneral;
import Gestores.GestorDeCarrito;
import Gestores.GestorDeJuego;
import Gestores.GestorDePublicacion;
import Gestores.GestorDeUsuarios;
import Juegos.Juego;
import MetodoDePago.*;

public class SistemaCarrito extends JFrame {
    private Usuario usuario;
    private GestorDeCarrito carrito;
    
    private DefaultListModel<Juego> juegoListModel;
    private JList<Juego> juegoList;
    private JLabel totalLabel;
    private JComboBox<FormaDePago> metodoPagoComboBox;
    private JFrame frame;
    private GestorDePublicacion gestorDePublicacion;
    private GestorDeUsuarios gestorDeUsuarios;
    private GestorDeJuego gestorDeJuego;
    private CarritoCompra carritoCompra;

    public SistemaCarrito(Usuario usuario, GestorDeCarrito carrito, GestorDePublicacion gestorDePublicacion,GestorDeUsuarios gestorDeUsuarios,GestorDeJuego gestorDeJuego,CarritoCompra carritoCompra) {
        this.usuario = usuario;
        this.carrito = carrito;
        this.gestorDePublicacion = gestorDePublicacion;
        this.gestorDeUsuarios=gestorDeUsuarios;
        this.gestorDeJuego=gestorDeJuego;
        this.carritoCompra=carritoCompra;
        initComponents();
    }

    private void initComponents() {
        setTitle("Carrito de Compras");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        juegoListModel = new DefaultListModel<>();
        for (Juego juego : carrito.obtenerCarrito(usuario).getJuegos()) {
            juegoListModel.addElement(juego);
        }
        juegoList = new JList<>(juegoListModel);
        JScrollPane scrollPane = new JScrollPane(juegoList);

        totalLabel = new JLabel("Total: $" + carrito.calcularTotal(usuario));

        metodoPagoComboBox = new JComboBox<>();
        for (FormaDePago tipoPago : usuario.getTipoPago()) {
            metodoPagoComboBox.addItem(tipoPago);
        }

        JButton agregarMetodoPagoButton = new JButton("Agregar Método de Pago");
        agregarMetodoPagoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirSeleccionarMetodoPago();
            }
        });

        JButton volverTiendaButton = new JButton("Volver a la Tienda");
        volverTiendaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverTienda();
                frame.dispose();
            }
        });

        JButton realizarCompraButton = new JButton("Realizar Compra");
        realizarCompraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    realizarCompra();
                } catch (ExcepcionGeneral e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(totalLabel, BorderLayout.WEST);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 3, 5, 5));
        bottomPanel.add(agregarMetodoPagoButton);
        bottomPanel.add(volverTiendaButton);
        bottomPanel.add(realizarCompraButton);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

        juegoList.setComponentPopupMenu(createPopupMenu());

        setVisible(true);
    }

    private JPopupMenu createPopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem eliminarItem = new JMenuItem("Eliminar");
        eliminarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = juegoList.getSelectedIndex();
                if (selectedIndex != -1) {
                    Juego selectedJuego = juegoListModel.getElementAt(selectedIndex);
                    carrito.eliminarJuegoDelCarrito(usuario, selectedJuego);
                    juegoListModel.remove(selectedIndex);
                    actualizarTotal();
                }
            }
        });
        menu.add(eliminarItem);
        return menu;
    }

    private void actualizarTotal() {
        totalLabel.setText("Total: $" + carrito.calcularTotal(usuario));
    }

    private void abrirSeleccionarMetodoPago() {
        new SeleccionarMetodoPago(usuario, metodoPagoComboBox, this);
    }

    private void volverTienda() {
        new Tienda(gestorDeJuego, usuario, gestorDePublicacion,gestorDeUsuarios);
        dispose();
    }

    private void realizarCompra() throws ExcepcionGeneral, IOException {
        
        FormaDePago metodoSeleccionado = (FormaDePago) metodoPagoComboBox.getSelectedItem();
        if (metodoSeleccionado == null)   {
            JOptionPane.showMessageDialog(this, "Seleccione un método de pago", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }else if (carritoCompra.getJuegos() == null) {
            JOptionPane.showMessageDialog(this, "el carrito esta vacio", "Error", JOptionPane.ERROR_MESSAGE);
            return;
            
        }
        carrito.realizarCompra(usuario, metodoSeleccionado);
        gestorDeUsuarios.reemplazarUsuario(usuario);
        gestorDeUsuarios.guardarUsuarios("usuarios.txt");
        // Puedes agregar aquí la lógica adicional después de realizar la compra
        new MainMenu(usuario,gestorDeUsuarios, gestorDePublicacion,carrito.getGestorDeJuego());
        dispose();

    }
}