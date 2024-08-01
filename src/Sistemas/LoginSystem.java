package Sistemas;

import Gestores.GestorDeJuego;
import Gestores.GestorDePublicacion;
import Gestores.GestorDeUsuarios;
import Usuarios.Usuario;
import excepciones.ExcepcionGeneral;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginSystem {
    private GestorDeUsuarios gestorUsuarios;
    private GestorDeJuego gestorDeJuego;
    private JFrame frame;
    private JTextField nombreUsuarioField;
    private JPasswordField contraseniaField;
    private JButton loginButton;
    private JButton registerButton;
    private GestorDePublicacion gestorDePublicacion;

    public LoginSystem(GestorDeUsuarios gestorUsuarios, GestorDeJuego gestorDeJuego, GestorDePublicacion gestorDePublicacion) throws IOException, ExcepcionGeneral {
        this.gestorUsuarios = gestorUsuarios;
        this.gestorDeJuego = gestorDeJuego;
        this.gestorDePublicacion = gestorDePublicacion;
        initialize();
    }

    private void initialize() throws IOException, ExcepcionGeneral {
        gestorUsuarios.cargarUsuarios("usuarios.txt", gestorDeJuego);
        frame = new JFrame("Sistema de Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(3, 2));

        JLabel nombreUsuarioLabel = new JLabel("Nombre de Usuario:");
        nombreUsuarioField = new JTextField();
        JLabel contraseniaLabel = new JLabel("Contraseña:");
        contraseniaField = new JPasswordField();

        loginButton = new JButton("Login");
        registerButton = new JButton("Registrarse");

        frame.add(nombreUsuarioLabel);
        frame.add(nombreUsuarioField);
        frame.add(contraseniaLabel);
        frame.add(contraseniaField);
        frame.add(loginButton);
        frame.add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    login();
                } catch (ExcepcionGeneral | IOException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    registrarUsuario();
                } catch (IOException | ExcepcionGeneral ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }

    private void login() throws ExcepcionGeneral, IOException {
        String nombreUsuario = nombreUsuarioField.getText();
        String contrasenia = new String(contraseniaField.getPassword());

        

        // Buscar al usuario por su nombre de usuario
        Usuario usuario = gestorUsuarios.buscarUsuarioPorNombreUsuario(nombreUsuario);

        // Verificar si el usuario existe y si la contraseña es correcta
        if (usuario != null && usuario.getContrasenia().equals(contrasenia)) {
            JOptionPane.showMessageDialog(frame, "Login exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Crear e iniciar MainMenu
            MainMenu menuPrincipal = new MainMenu(usuario, gestorUsuarios, gestorDePublicacion, gestorDeJuego);
            menuPrincipal.mostrar(); // Método para hacer visible el menú principal

            // Cerrar el frame de inicio de sesión
            frame.dispose();
        } else {
            throw new ExcepcionGeneral("Usuario y/o contraseña incorrectas.");
        }
    }

    private void registrarUsuario() throws IOException, ExcepcionGeneral {
        JTextField nombreField = new JTextField();
        JTextField apellidoField = new JTextField();
        JTextField correoField = new JTextField();
        JTextField nuevoNombreUsuarioField = new JTextField();
        JPasswordField nuevoContraseniaField = new JPasswordField();

        Object[] message = {
                "Nombre:", nombreField,
                "Apellido:", apellidoField,
                "Correo:", correoField,
                "Nombre de Usuario:", nuevoNombreUsuarioField,
                "Contraseña:", nuevoContraseniaField
        };

        int option = JOptionPane.showConfirmDialog(frame, message, "Registrar Usuario", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            Usuario nuevoUsuario = new Usuario(nombreField.getText(), apellidoField.getText(), correoField.getText(), nuevoNombreUsuarioField.getText(), new String(nuevoContraseniaField.getPassword()));
            gestorUsuarios.agregarUsuario(nuevoUsuario);

            // Guardar usuarios en el archivo después de registrar un nuevo usuario
            gestorUsuarios.guardarUsuarios("usuarios.txt");

            JOptionPane.showMessageDialog(frame, "Registro exitoso", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Actualizar campos de login con las nuevas credenciales
            nombreUsuarioField.setText(nuevoNombreUsuarioField.getText());
            contraseniaField.setText(new String(nuevoContraseniaField.getPassword()));
        }
    }
}