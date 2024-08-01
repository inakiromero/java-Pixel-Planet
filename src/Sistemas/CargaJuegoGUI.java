package Sistemas;

import Juegos.Juego;
import Juegos.TipoJuego;
import excepciones.ExcepcionGeneral;

import javax.swing.*;

import Gestores.GestorDeJuego;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CargaJuegoGUI {
    private JFrame frame;
    private JPanel panel;
    private GestorDeJuego gestorDeJuego;
    private JTextField txtTitulo;
    private JTextField txtCreador;
    private JTextField txtPrecio;
    private JTextField txtValoracion;
    private JComboBox<TipoJuego> cbTipo;
    private JButton btnCargar;

    public CargaJuegoGUI(GestorDeJuego gestorDeJuego) {
        this.gestorDeJuego=gestorDeJuego;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Cargar Juego");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel lblTitulo = new JLabel("Título:");
        JLabel lblCreador = new JLabel("Creador:");
        JLabel lblPrecio = new JLabel("Precio:");
        JLabel lblValoracion = new JLabel("Valoración (1-100):");
        JLabel lblTipo = new JLabel("Tipo:");

        txtTitulo = new JTextField();
        txtCreador = new JTextField();
        txtPrecio = new JTextField();
        txtValoracion = new JTextField();
        cbTipo = new JComboBox<>(TipoJuego.values());

        btnCargar = new JButton("Cargar");

        panel.add(lblTitulo);
        panel.add(txtTitulo);
        panel.add(lblCreador);
        panel.add(txtCreador);
        panel.add(lblPrecio);
        panel.add(txtPrecio);
        panel.add(lblValoracion);
        panel.add(txtValoracion);
        panel.add(lblTipo);
        panel.add(cbTipo);
        panel.add(new JLabel()); // Espacio en blanco para alinear
        panel.add(btnCargar);

        btnCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cargarJuego();
                } catch (ExcepcionGeneral e1) {
                    e1.printStackTrace();
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private void cargarJuego() throws ExcepcionGeneral {
        String titulo = txtTitulo.getText();
        String creador = txtCreador.getText();
        double precio = Double.parseDouble(txtPrecio.getText());
        int valoracion = Integer.parseInt(txtValoracion.getText());
        TipoJuego tipo = (TipoJuego) cbTipo.getSelectedItem();

        // Validar que la valoración esté en el rango permitido (1-100)
        if (valoracion < 1 || valoracion > 100) {
            JOptionPane.showMessageDialog(frame, "La valoración debe estar entre 1 y 100.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Aquí podrías hacer la lógica para guardar el juego en tu sistema
        Juego juego = new Juego(generarCodigoUnico(), titulo, valoracion, precio, tipo, creador);

        // Por ahora, solo mostraremos un mensaje indicando que se cargó el 
        gestorDeJuego.agregarJuego(juego);
        
        JOptionPane.showMessageDialog(frame, "Juego cargado:\n\n" + juego.toString(), "Juego Cargado", JOptionPane.INFORMATION_MESSAGE);

        // Cierra la ventana después de cargar el juego
        frame.dispose();
    }

    // Método simple para generar un código único (puedes implementarlo mejor según tus necesidades)
    private String generarCodigoUnico() {
        return "JUEGO_" + System.currentTimeMillis(); // Ejemplo básico
    }

    
}