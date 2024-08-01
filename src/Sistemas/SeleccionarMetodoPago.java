package Sistemas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import MetodoDePago.*;
import Usuarios.Usuario;

public class SeleccionarMetodoPago extends JFrame {
    private Usuario usuario;
    private JComboBox<String> metodoPagoComboBox;
    private JFrame parentFrame;
    private JComboBox<FormaDePago> parentComboBox;

    public SeleccionarMetodoPago(Usuario usuario, JComboBox<FormaDePago> comboBox, JFrame parentFrame) {
        this.usuario = usuario;
        this.parentFrame = parentFrame;
        this.parentComboBox = comboBox;
        this.metodoPagoComboBox = new JComboBox<>();
        initComponents(comboBox);
    }

    private void initComponents(JComboBox<FormaDePago> comboBox) {
        setTitle("Seleccionar o Agregar Método de Pago");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        for (FormaDePago tipoPago : usuario.getTipoPago()) {
            metodoPagoComboBox.addItem(tipoPago.toString());
        }

        JButton agregarNuevoButton = new JButton("Agregar Nuevo Método de Pago");
        agregarNuevoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirAgregarMetodoPago();
            }
        });

        JButton seleccionarExistenteButton = new JButton("Seleccionar Método Existente");
        seleccionarExistenteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarMetodoExistente();
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.add(new JLabel("Seleccione o agregue un método de pago:"));
        panel.add(metodoPagoComboBox);
        panel.add(agregarNuevoButton);
        panel.add(seleccionarExistenteButton);

        add(panel);
        setVisible(true);
    }

    private void seleccionarMetodoExistente() {
        int selectedIndex = metodoPagoComboBox.getSelectedIndex();
        if (selectedIndex != -1) {
            FormaDePago metodoSeleccionado = usuario.getTipoPago().get(selectedIndex);
            parentComboBox.setSelectedItem(metodoSeleccionado);
            parentFrame.repaint();
        }
        dispose();
    }

    private void actualizarComboBox() {
        metodoPagoComboBox.removeAllItems();
        for (FormaDePago tipoPago : usuario.getTipoPago()) {
            metodoPagoComboBox.addItem(tipoPago.toString());
        }
    }
    private void abrirAgregarMetodoPago() {
        // Supongamos que AgregarMetodoPago actualiza el objeto Usuario correctamente
        new AgregarMetodoPago(usuario);
        actualizarComboBox();

        // También actualiza el JComboBox en la clase padre (SistemaCarrito)
        actualizarParentComboBox();
    }
    private void actualizarParentComboBox() {
        parentComboBox.removeAllItems();
        for (FormaDePago tipoPago : usuario.getTipoPago()) {
            parentComboBox.addItem(tipoPago);
        }
    }
}