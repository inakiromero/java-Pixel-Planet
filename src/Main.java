import Gestores.GestorDeJuego;
import Gestores.GestorDePublicacion;
import Gestores.GestorDeUsuarios;
import Juegos.Juego;
import Sistemas.LoginSystem;
import excepciones.ExcepcionGeneral;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GestorDeUsuarios gestorUsuarios = new GestorDeUsuarios();
                Map<String, Juego> juegos=new HashMap<>();
                
                GestorDeJuego gestorDeJuego = new GestorDeJuego(juegos);
                try {
                    gestorDeJuego.cargarJuegosDesdeArchivo("Juegos.txt");
                } catch (ExcepcionGeneral e) {
                    e.printStackTrace();
                }
                GestorDePublicacion gestorDePublicacion = new GestorDePublicacion(gestorDeJuego);
                
                try {
                    gestorDePublicacion.cargarPublicacionesDesdeArchivo("publicaciones.txt", gestorUsuarios);
                } catch (IOException | ExcepcionGeneral e) {
                    e.printStackTrace();
                }
                

                try {
                    new LoginSystem(gestorUsuarios,gestorDeJuego,gestorDePublicacion);
                } catch (IOException | ExcepcionGeneral e) {
                    e.printStackTrace();
                }
            }
        });
    }
}