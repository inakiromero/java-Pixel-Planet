package Sistemas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Usuarios.Usuario;
import excepciones.ExcepcionGeneral;
import MetodoDePago.*;


public class AgregarMetodoPago {
    private Usuario usuario;
    private JFrame frame;

    public AgregarMetodoPago(Usuario usuario) {
        this.usuario = usuario;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Agregar Método de Pago");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 1));

        JLabel tipoPagoLabel = new JLabel("Seleccione el tipo de forma de pago:");
        JComboBox<String> tipoPagoComboBox = new JComboBox<>(new String[]{"Tarjeta de Crédito", "Tarjeta de Débito", "PayPal"});

        JButton agregarButton = new JButton("Agregar");
        agregarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    agregarMetodoPago(tipoPagoComboBox.getSelectedIndex());
                } catch (ExcepcionGeneral ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.add(tipoPagoLabel);
        frame.add(tipoPagoComboBox);
        frame.add(agregarButton);
        frame.setVisible(true);
    }

    private void agregarMetodoPago(int opcion) throws ExcepcionGeneral {
        FormaDePago formaDePago;
        switch (opcion) {
            case 0:
                formaDePago = crearTarjetaCredito();
                usuario.agregarTipoPago(formaDePago);
                break;
            case 1:
                formaDePago = crearTarjetaDebito();
                usuario.agregarTipoPago(formaDePago);

                break;
            case 2:
                formaDePago = crearPayPal();
                usuario.agregarTipoPago(formaDePago);

                break;
            default:
                throw new ExcepcionGeneral("Opción no válida.");
        }

        if (formaDePago != null) {
            usuario.agregarTipoPago(formaDePago);
            JOptionPane.showMessageDialog(frame, "Método de pago agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private FormaDePago crearTarjetaCredito() throws ExcepcionGeneral {
        JTextField nombreCompletoField = new JTextField();
        JTextField fechaExpiracionField = new JTextField();
        JTextField numeroTarjetaField = new JTextField();
        JTextField codigoSeguridadField = new JTextField();

        Object[] message = {
                "Nombre Completo:", nombreCompletoField,
                "Fecha de Expiración (MM/yy):", fechaExpiracionField,
                "Número de Tarjeta:", numeroTarjetaField,
                "Código de Seguridad:", codigoSeguridadField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Tarjeta de Crédito", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Credito credito = new Credito(nombreCompletoField.getText(), fechaExpiracionField.getText(), numeroTarjetaField.getText(), codigoSeguridadField.getText());
            if (credito.validarTarjeta()) {
                return credito;
            } else {
                throw new ExcepcionGeneral("Tarjeta de Crédito no válida.");
            }
        }
        return null;
    }

    private FormaDePago crearTarjetaDebito() throws ExcepcionGeneral {
        JTextField nombreCompletoField = new JTextField();
        JTextField fechaExpiracionField = new JTextField();
        JTextField numeroTarjetaField = new JTextField();
        JTextField codigoSeguridadField = new JTextField();

        Object[] message = {
                "Nombre Completo:", nombreCompletoField,
                "Fecha de Expiración (MM/yy):", fechaExpiracionField,
                "Número de Tarjeta:", numeroTarjetaField,
                "Código de Seguridad:", codigoSeguridadField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Tarjeta de Débito", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            debito debito = new debito(nombreCompletoField.getText(), fechaExpiracionField.getText(), numeroTarjetaField.getText(), codigoSeguridadField.getText());
            if (debito.validarTarjeta()) {
                return debito;
            } else {
                throw new ExcepcionGeneral("Tarjeta de Débito no válida.");
            }
        }
        return null;
    }

    private FormaDePago crearPayPal() throws ExcepcionGeneral {
        JTextField correoField = new JTextField();
        JPasswordField contraseniaField = new JPasswordField();

        Object[] message = {
                "Correo Electrónico:", correoField,
                "Contraseña:", contraseniaField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "PayPal", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            PayPal payPal = new PayPal(usuario.getNombre(), correoField.getText(), new String(contraseniaField.getPassword()));
            if (payPal.validarCuenta()) {
                return payPal;
            } else {
                throw new ExcepcionGeneral("Cuenta de PayPal no válida.");
            }
        }
        return null;
    }
}