package Sistemas;

import Usuarios.Usuario;
import Juegos.Juego;
import Publicaciones.Resenia;

import javax.swing.*;

import Gestores.GestorDeJuego;
import Gestores.GestorDePublicacion;
import Gestores.GestorDeUsuarios;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Biblioteca {
    private Usuario usuario;
    private GestorDeJuego gestorDeJuego;
    private GestorDeUsuarios gestorDeUsuarios;
    private GestorDePublicacion gestorDePublicacion;
    private JFrame frame;
    private JPanel panel;
    private JButton volverPerfilButton;
    private JButton irTiendaButton;

    public Biblioteca(Usuario usuario,GestorDeJuego gestorDeJuego,GestorDeUsuarios gestorUsuarios,GestorDePublicacion gestorDePublicacion) {
        this.usuario = usuario;
        this.gestorDeJuego= gestorDeJuego;
        this.gestorDeUsuarios= gestorUsuarios;
        this.gestorDePublicacion=gestorDePublicacion;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Biblioteca");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        for (Juego juego : usuario.getBiblioteca()) {
            JPanel juegoPanel = new JPanel();
            juegoPanel.setLayout(new GridLayout(1, 4));

            JLabel tituloLabel = new JLabel("Título: " + juego.getTitulo());
            JLabel creadorLabel = new JLabel("Creador: " + juego.getCreador());
            JButton jugarButton = new JButton("Jugar");
            JButton reseniaButton = new JButton("Hacer Reseña");

            jugarButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    iniciarJuego(juego);
                }
            });

            reseniaButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    hacerResenia(juego);
                }
            });

            juegoPanel.add(tituloLabel);
            juegoPanel.add(creadorLabel);
            juegoPanel.add(jugarButton);
            juegoPanel.add(reseniaButton);

            panel.add(juegoPanel);
        }

        volverPerfilButton = new JButton("Volver al Perfil");
        irTiendaButton = new JButton("Ir a la Tienda");

        volverPerfilButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverPerfil();
                frame.dispose();
            }
        });

        irTiendaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                irTienda();
                frame.dispose();
            }
        });

        frame.add(panel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(volverPerfilButton);
        buttonPanel.add(irTiendaButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void iniciarJuego(Juego juego) {
        JOptionPane.showMessageDialog(frame, "Iniciando el juego: " + juego.getTitulo());
    }

    private void hacerResenia(Juego juego ) {
        // Aquí va el código para hacer una reseña
        JTextArea reseniaField = new JTextArea(5, 30);
        JScrollPane scrollPane = new JScrollPane(reseniaField);
        Object[] message = {
            "Escribe tu reseña:", scrollPane
        };
        
        int option = JOptionPane.showConfirmDialog(frame, message, "Hacer Reseña", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String resenia = reseniaField.getText();
            boolean valoracion = JOptionPane.showConfirmDialog(frame, "¿Es una valoración positiva?", "Valoración", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
            
            // Agregar la reseña al juego
             gestorDeJuego.agregarResena(juego.getCodigoUnico(),new Resenia(usuario, resenia, valoracion, juego));
            JOptionPane.showMessageDialog(frame, "Reseña añadida.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void volverPerfil() {
        frame.dispose();
        // Aquí se puede abrir el perfil del usuario nuevamente
         new PerfilUsuario(gestorDeUsuarios, usuario,gestorDeJuego,gestorDePublicacion);
    }

    private void irTienda() {
        frame.dispose();
        // Aquí se puede abrir la tienda
         new Tienda(gestorDeJuego, usuario,gestorDePublicacion,gestorDeUsuarios);
    }
}