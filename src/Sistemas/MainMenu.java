package Sistemas;

import Gestores.GestorDeUsuarios;
import Gestores.GestorDeJuego;
import Gestores.GestorDePublicacion;
import Usuarios.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu {
    private Usuario usuario;
    private GestorDeUsuarios gestorDeUsuarios;
    private GestorDeJuego gestorDeJuego;
    private GestorDeUsuarios gestorUsuarios;
    private GestorDePublicacion gestorPublicaciones;
    private JFrame frame;
    private JButton tiendaButton;
    private JButton usuarioButton;
    private JButton bibliotecaButton;
    private JButton publicacionesButton;
    private JButton adminButton;

    public MainMenu( Usuario usuario,GestorDeUsuarios gestorDeUsuarios, GestorDePublicacion gestorPublicaciones, GestorDeJuego gestorDeJuego) {
        this.usuario = usuario;
        this.gestorDeUsuarios=gestorDeUsuarios;
        this.gestorPublicaciones = gestorPublicaciones;
        this.gestorDeJuego= gestorDeJuego;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Menú Principal");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 1));

        tiendaButton = new JButton("Ir a la Tienda");
        usuarioButton = new JButton("Ir al Usuario");
        bibliotecaButton = new JButton("Ir a la Biblioteca");
        publicacionesButton = new JButton("Ir a Publicaciones");
         adminButton = new JButton("Menú de Administrador");

        frame.add(tiendaButton);
        frame.add(usuarioButton);
        frame.add(bibliotecaButton);
        frame.add(publicacionesButton);
        frame.add(adminButton);


        tiendaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirTienda();
                frame.dispose();
                JOptionPane.showMessageDialog(frame, "Ir a la Tienda...");
            }
        });

        usuarioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirUsuario();
                frame.dispose();
            }
        });

        bibliotecaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirBiblioteca();
                frame.dispose();
            }
        });

        publicacionesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirPublicaciones();
                frame.dispose();

            }
        });

        
        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 abrirMenuAdministrador();
                 frame.dispose();

                }
        });
        

        frame.setVisible(true);
    }

    private void abrirUsuario() {
        if (usuario != null) {
            new PerfilUsuario(gestorDeUsuarios, usuario,gestorDeJuego,gestorPublicaciones);
        } else {
            JOptionPane.showMessageDialog(frame, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirBiblioteca() {
        if (usuario != null) {
            new Biblioteca(usuario,gestorDeJuego,gestorUsuarios,gestorPublicaciones);
        } else {
            JOptionPane.showMessageDialog(frame, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void abrirTienda()
    {
        if (usuario!=null) {
            new Tienda(gestorDeJuego, usuario,gestorPublicaciones,gestorDeUsuarios);
        }else
        
            {
                JOptionPane.showMessageDialog(frame, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        

    }
    private void abrirMenuAdministrador() {

        if (usuario.autenticar(usuario.getContrasenia())) {
        new MenuAdministrador(usuario, gestorDeUsuarios, gestorDeJuego, gestorPublicaciones);
           
            frame.dispose(); // Cerrar el MainMenu después de abrir MenuAdministrador
        } else {
            JOptionPane.showMessageDialog(frame, "Usuario no es administrador o la contraseña es incorrecta.", "Error", JOptionPane.ERROR_MESSAGE);
        }
}

    private void abrirPublicaciones() {
        if (usuario!=null) {
        new MenuPublicaciones(usuario,gestorPublicaciones,gestorDeJuego,gestorDeUsuarios);
        }else{
            JOptionPane.showMessageDialog(frame, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void mostrar() {
        frame.setVisible(true);
    }
}