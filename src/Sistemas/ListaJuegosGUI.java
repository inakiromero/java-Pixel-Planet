package Sistemas;

import Juegos.Juego;

import javax.swing.*;

import Gestores.GestorDeJuego;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;


    public class ListaJuegosGUI {
        private GestorDeJuego gestorDeJuego;
        private JFrame frame;
        private JPanel panel;
    
        public ListaJuegosGUI(GestorDeJuego gestorDeJuego) {
            this.gestorDeJuego = gestorDeJuego;
            SwingUtilities.invokeLater(this::initialize);
        }
    
        private void initialize() {
            frame = new JFrame("Lista de Juegos");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 400);
    
            panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    
            // Agregar los juegos a la lista
            for (Map.Entry<String, Juego> entry : gestorDeJuego.getJuegos().entrySet()) {
                String codigo = entry.getKey();
                Juego juego = entry.getValue();
    
                JPanel juegoPanel = new JPanel();
                juegoPanel.setLayout(new BorderLayout());
    
                JLabel tituloLabel = new JLabel("Título: " + juego.getTitulo());
                JButton eliminarButton = new JButton("Eliminar");
    
                eliminarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        eliminarJuego(codigo);
                    }
                });
    
                juegoPanel.add(tituloLabel, BorderLayout.CENTER);
                juegoPanel.add(eliminarButton, BorderLayout.EAST);
    
                panel.add(juegoPanel);
            }
    
            JScrollPane scrollPane = new JScrollPane(panel);
            frame.add(scrollPane);
    
            frame.setVisible(true);
        }
    
        private void eliminarJuego(String codigo) {
            // Lógica para eliminar el juego del Map
            gestorDeJuego.eliminarJuego(codigo);
    
            // Actualizar la interfaz
            SwingUtilities.invokeLater(() -> {
                panel.removeAll();
                for (Map.Entry<String, Juego> entry : gestorDeJuego.getJuegos().entrySet()) {
                    String codigoJuego = entry.getKey();
                    Juego juego = entry.getValue();
    
                    JPanel juegoPanel = new JPanel();
                    juegoPanel.setLayout(new BorderLayout());
    
                    JLabel tituloLabel = new JLabel("Título: " + juego.getTitulo());
                    JButton eliminarButton = new JButton("Eliminar");
    
                    eliminarButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            eliminarJuego(codigoJuego);
                        }
                    });
    
                    juegoPanel.add(tituloLabel, BorderLayout.CENTER);
                    juegoPanel.add(eliminarButton, BorderLayout.EAST);
    
                    panel.add(juegoPanel);
                }
                panel.revalidate();
                panel.repaint();
            });
        }
    }