package Sistemas;

import Gestores.GestorDeJuego;
import Gestores.GestorDePublicacion;
import Gestores.GestorDeUsuarios;
import Juegos.Juego;
import Publicaciones.Publicacion;
import Usuarios.Usuario;
import excepciones.ExcepcionGeneral;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class MenuPublicaciones {
    private Usuario usuario;
    private GestorDePublicacion gestorPublicaciones;
    private GestorDeJuego gestorDeJuego;
    private GestorDeUsuarios gestorDeUsuarios;
    private JFrame frame;
    private JPanel panel;
    private JButton volverMenuButton;
    private JButton crearPublicacionButton;
    private JButton buscarPublicacionButton;

    public MenuPublicaciones(Usuario usuario, GestorDePublicacion gestorPublicaciones, GestorDeJuego gestorDeJuego, GestorDeUsuarios gestorDeUsuarios) {
        this.usuario = usuario;
        this.gestorPublicaciones = gestorPublicaciones;
        this.gestorDeJuego = gestorDeJuego;
        this.gestorDeUsuarios = gestorDeUsuarios;
        initialize();
        mostrarPublicaciones(); // Mostrar publicaciones cargadas
    }

    private void initialize() {
        frame = new JFrame("Publicaciones");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        volverMenuButton = new JButton("Volver al Menú");
        crearPublicacionButton = new JButton("Crear Publicación");
        buscarPublicacionButton = new JButton("Buscar Publicación");

        volverMenuButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                volverMenu();
                frame.dispose();
            }
        });

        crearPublicacionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                crearPublicacion();
            }
        });

        buscarPublicacionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buscarPublicacion();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(crearPublicacionButton);
        buttonPanel.add(buscarPublicacionButton);
        buttonPanel.add(volverMenuButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void volverMenu() {
        frame.dispose();
        new MainMenu(usuario, gestorDeUsuarios, gestorPublicaciones, gestorDeJuego);
    }

    private void crearPublicacion() {
        JFrame crearPublicacionFrame = new JFrame("Crear Publicación");
        crearPublicacionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        crearPublicacionFrame.setSize(400, 300);
        crearPublicacionFrame.setLayout(new GridLayout(5, 2));

        JLabel tituloLabel = new JLabel("Título:");
        JTextField tituloField = new JTextField();
        JLabel contenidoLabel = new JLabel("Contenido:");
        JTextArea contenidoArea = new JTextArea();
        JLabel juegoLabel = new JLabel("Juego:");
        JTextField juegoField = new JTextField();
        JButton crearButton = new JButton("Crear");

        crearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String titulo = tituloField.getText();
                String contenido = contenidoArea.getText();
                String juegoTitulo = juegoField.getText();
                Juego juego = gestorDeJuego.buscarJuegoPorTitulo(juegoTitulo);

                if (juego != null) {
                    try {
                        Publicacion publicacion = new Publicacion(usuario, titulo, contenido, juego);
                        gestorPublicaciones.agregarPublicacion(publicacion);
                        JOptionPane.showMessageDialog(crearPublicacionFrame, "Publicación creada exitosamente.");
                        crearPublicacionFrame.dispose();
                        mostrarPublicaciones(); // Actualiza la lista de publicaciones en el panel principal
                    } catch (ExcepcionGeneral excepcionGeneral) {
                        JOptionPane.showMessageDialog(crearPublicacionFrame, excepcionGeneral.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(crearPublicacionFrame, "Juego no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        crearPublicacionFrame.add(tituloLabel);
        crearPublicacionFrame.add(tituloField);
        crearPublicacionFrame.add(contenidoLabel);
        crearPublicacionFrame.add(contenidoArea);
        crearPublicacionFrame.add(juegoLabel);
        crearPublicacionFrame.add(juegoField);
        crearPublicacionFrame.add(new JLabel());
        crearPublicacionFrame.add(crearButton);

        crearPublicacionFrame.setVisible(true);
    }

    private void buscarPublicacion() {
        String titulo = JOptionPane.showInputDialog(frame, "Ingrese el título de la publicación:");
        if (titulo != null && !titulo.isEmpty()) {
            List<Publicacion> publicaciones = gestorPublicaciones.getPublicaciones();
            panel.removeAll();

            for (Publicacion publicacion : publicaciones) {
                if (publicacion.getTitulo().equalsIgnoreCase(titulo)) {
                    JPanel publicacionPanel = new JPanel();
                    publicacionPanel.setLayout(new GridLayout(1, 4));

                    JLabel tituloJuegoLabel = new JLabel("Juego: " + publicacion.getJuego().getTitulo());
                    JLabel tituloLabel = new JLabel("Título: " + publicacion.getTitulo());
                    JLabel publicacionJLabel = new JLabel("Contenido: " + publicacion.getContenido());
                    JLabel usuarioLabel = new JLabel("Usuario: " + publicacion.getUsuario().getNombreUsuario());

                    publicacionPanel.add(tituloJuegoLabel);
                    publicacionPanel.add(tituloLabel);
                    publicacionPanel.add(publicacionJLabel);
                    publicacionPanel.add(usuarioLabel);

                    panel.add(publicacionPanel);
                }
            }
            panel.revalidate();
            panel.repaint();
        }
    }

    private void mostrarPublicaciones() {
        panel.removeAll();
        List<Publicacion> publicaciones = gestorPublicaciones.getPublicaciones();

        for (Publicacion publicacion : publicaciones) {
            JPanel publicacionPanel = new JPanel();
            publicacionPanel.setLayout(new GridLayout(1, 4));

            JLabel tituloLabel = new JLabel("Título: " + publicacion.getTitulo());
            JLabel tituloJuegoLabel = new JLabel("Juego: " + publicacion.getJuego().getTitulo());
            JLabel publicacionJLabel = new JLabel("Contenido: " + publicacion.getContenido());
            JLabel usuarioLabel = new JLabel("Usuario: " + publicacion.getUsuario().getNombreUsuario());

            publicacionPanel.add(tituloLabel);
            publicacionPanel.add(tituloJuegoLabel);
            publicacionPanel.add(publicacionJLabel);
            publicacionPanel.add(usuarioLabel);

            panel.add(publicacionPanel);
        }
        panel.revalidate();
        panel.repaint();
    }
}