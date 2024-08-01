package Sistemas;

import Usuarios.Usuario;

import javax.swing.*;

import Gestores.GestorDeJuego;
import Gestores.GestorDePublicacion;
import Gestores.GestorDeUsuarios;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuAdministrador {
    private Usuario administrador;
    private GestorDeUsuarios gestorDeUsuarios;
    private GestorDeJuego gestorDeJuego;
    private GestorDePublicacion gestorDePublicacion;
    private JFrame frame;
    private JButton eliminarJuegoButton;
    private JButton volverMenuButton;
    private JButton cargarJuegoButton; // Nuevo botón para cargar juego

    public MenuAdministrador(Usuario administrador, GestorDeUsuarios gestorDeUsuarios, GestorDeJuego gestorDeJuego, GestorDePublicacion gestorDePublicacion) {
        this.administrador = administrador;
        this.gestorDeJuego=gestorDeJuego;
        this.gestorDeUsuarios=gestorDeUsuarios;
        this.gestorDePublicacion= gestorDePublicacion;

        initialize();
    }

    private void initialize() {
        frame = new JFrame("Menú de Administrador");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 1));

       
        eliminarJuegoButton = new JButton("Eliminar Juego del Sistema");
        volverMenuButton = new JButton("Volver al Menú Principal");
        cargarJuegoButton = new JButton("Cargar Juego"); // Nuevo botón para cargar juego

       
        frame.add(eliminarJuegoButton);
        frame.add(volverMenuButton);
        frame.add(cargarJuegoButton); // Agregar botón cargar juego

       

        eliminarJuegoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               eliminarJuego();
            }
        });
        volverMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverMenu();
                frame.dispose();
            }
        });


        cargarJuegoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirCargaJuego();
            }
        });

        frame.setVisible(true);
    }

    private void volverMenu() {
        frame.dispose();
        new MainMenu(administrador, gestorDeUsuarios, gestorDePublicacion, gestorDeJuego);
    }

    private void abrirCargaJuego() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CargaJuegoGUI(gestorDeJuego); // Abre la interfaz de carga de juego
            }
        });
    }private void eliminarJuego() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ListaJuegosGUI(gestorDeJuego); // Abre la interfaz de carga de juego
            }
        });
    }
}