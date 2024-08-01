package Sistemas;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Gestores.GestorDeJuego;
import Gestores.GestorDePublicacion;
import Gestores.GestorDeUsuarios;
import Usuarios.Usuario;
import excepciones.ExcepcionGeneral;

public class PerfilUsuario {
    private GestorDeUsuarios gestorUsuarios;
    private GestorDeJuego gestorDeJuego;
    private Usuario usuario;
    private JFrame frame;
    private JButton agregarMetodoPagoButton;
    private JButton irBibliotecaButton;
    private JButton cambiarContraseniaButton;
    private JButton cambiarNombreUsuarioButton;
    private JButton volverMenuButton;
    private GestorDePublicacion gestorDePublicacion;

    public PerfilUsuario(GestorDeUsuarios gestorUsuarios, Usuario usuario,GestorDeJuego gestorDeJuego,GestorDePublicacion gestorDePublicacion) {
        this.gestorUsuarios = gestorUsuarios;
        this.gestorDeJuego= gestorDeJuego;
        this.usuario = usuario;
        this.gestorDePublicacion=gestorDePublicacion;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Perfil de Usuario");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 1));

        agregarMetodoPagoButton = new JButton("Agregar Método de Pago");
        irBibliotecaButton = new JButton("Ir a Biblioteca");
        cambiarContraseniaButton = new JButton("Cambiar Contraseña");
        cambiarNombreUsuarioButton = new JButton("Cambiar Nombre de Usuario");
        volverMenuButton = new JButton("Volver al Menú Principal");

        frame.add(agregarMetodoPagoButton);
        frame.add(irBibliotecaButton);
        frame.add(cambiarContraseniaButton);
        frame.add(cambiarNombreUsuarioButton);
        frame.add(volverMenuButton);

        agregarMetodoPagoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    agregarMetodoPago();
                } catch (ExcepcionGeneral e1) {
                    e1.printStackTrace();
                }
            }
        });

        irBibliotecaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                irBiblioteca();
                frame.dispose();
            }
        });

        cambiarContraseniaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cambiarContrasenia();

            }
        });

        cambiarNombreUsuarioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cambiarNombreUsuario();
            }
        });

        volverMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverMenu();
                frame.dispose();
            }
        });

        frame.setVisible(true);
    }

    private void agregarMetodoPago()  throws ExcepcionGeneral {
       new AgregarMetodoPago(usuario);
    }

        private void irBiblioteca() {
            new Biblioteca(usuario,gestorDeJuego,gestorUsuarios,gestorDePublicacion);
        }
    

    private void cambiarContrasenia() {
        JTextField nuevaContraseniaField = new JPasswordField();
        Object[] message = {
                "Nueva Contraseña:", nuevaContraseniaField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Cambiar Contraseña", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            usuario.setContrasenia(nuevaContraseniaField.getText());
            JOptionPane.showMessageDialog(frame, "Contraseña cambiada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    

    private void cambiarNombreUsuario() {
        JTextField nuevoNombreUsuarioField = new JTextField();
        Object[] message = {
                "Nuevo Nombre de Usuario:", nuevoNombreUsuarioField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Cambiar Nombre de Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            usuario.setNombreUsuario(nuevoNombreUsuarioField.getText());
            JOptionPane.showMessageDialog(frame, "Nombre de Usuario cambiado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void volverMenu() {
        frame.dispose();
        new MainMenu(usuario,gestorUsuarios, gestorDePublicacion,gestorDeJuego);  // Asegúrate de pasar el gestor de usuarios correcto si es necesario
    }
}
