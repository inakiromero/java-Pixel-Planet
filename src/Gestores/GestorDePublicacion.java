package Gestores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import Juegos.Juego;
import Publicaciones.Publicacion;
import Publicaciones.Resenia;
import Usuarios.Usuario;
import excepciones.ExcepcionGeneral;

public class GestorDePublicacion {
    private List<Publicacion> publicaciones;
    private GestorDeJuego gestorJuego;

    public GestorDePublicacion(GestorDeJuego gestorJuego) {
        this.publicaciones = new ArrayList<>();
        this.gestorJuego = gestorJuego;
    }

    public void agregarPublicacion(Publicacion publicacion) throws ExcepcionGeneral, IOException {
        for (Publicacion p : publicaciones) {
            if (p.getTitulo().equals(publicacion.getTitulo()) && p.getUsuario().equals(publicacion.getUsuario())) {
                throw new ExcepcionGeneral("El usuario ya ha creado una publicación con este título.");
            }
        }
        publicaciones.add(publicacion);
        guardarPublicaciones("publicaciones.txt");
    }

    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    public List<Publicacion> buscarPublicacionesPorUsuario(Usuario usuario) {
        List<Publicacion> publicacionesUsuario = new ArrayList<>();
        for (Publicacion publicacion : publicaciones) {
            if (publicacion.getUsuario().equals(usuario)) {
                publicacionesUsuario.add(publicacion);
            }
        }
        return publicacionesUsuario;
    }

    public List<Publicacion> getPublicacionesRandom(int cantidad) {
        Random rand = new Random();
        return IntStream.generate(() -> rand.nextInt(publicaciones.size()))
                .distinct()
                .limit(cantidad)
                .mapToObj(publicaciones::get)
                .collect(Collectors.toList());
    }

    public void agregarResena(String titulo, Usuario usuario, String comentario, boolean valoracion) {
        Juego juego = gestorJuego.buscarJuegoPorTitulo(titulo);
        if (juego != null) {
            Resenia resena = new Resenia(usuario, comentario, valoracion, juego);
            gestorJuego.agregarResena(juego.getCodigoUnico(), resena);
        }
    }

    public List<Resenia> obtenerResenas(String codigoJuego) {
        Juego juego = gestorJuego.buscarJuegoPorCodigo(codigoJuego);
        if (juego != null) {
            return juego.getValoracionesDeUsuarios();
        }
        return new ArrayList<>();
    }

    public void guardarPublicaciones(String archivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(archivo, true)))) {
            for (Publicacion publicacion : publicaciones) {
                String linea = publicacion.getUsuario().getNombreUsuario() + ","
                        + publicacion.getTitulo() + ","
                        + publicacion.getContenido() + ","
                        + publicacion.getJuego().getTitulo();
                writer.println(linea);
            }
        }
    }

    public void cargarPublicacionesDesdeArchivo(String rutaArchivo, GestorDeUsuarios gestorUsuarios) throws IOException, ExcepcionGeneral {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 4) {
                    String nombreUsuario = datos[0];
                    String titulo = datos[1];
                    String contenido = datos[2];
                    String tituloJuego = datos[3];
                    
                    Usuario usuario = gestorUsuarios.buscarUsuario(nombreUsuario);
                    Juego juego = gestorJuego.buscarJuegoPorTitulo(tituloJuego);

                    if (usuario != null && juego != null) {
                        Publicacion publicacion = new Publicacion(usuario, titulo, contenido, juego);
                        publicaciones.add(publicacion);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}