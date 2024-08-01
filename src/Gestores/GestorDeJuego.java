package Gestores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Juegos.Juego;
import Juegos.TipoJuego;
import Publicaciones.Resenia;
import excepciones.ExcepcionGeneral;

public class GestorDeJuego {
    private Map<String, Juego> juegos;

    public GestorDeJuego(Map<String, Juego> juegos) {
        this.juegos = juegos;
    }

    public void agregarJuego(Juego juego) throws ExcepcionGeneral {
        if (juegos.containsKey(juego.getCodigoUnico())) {
            throw new ExcepcionGeneral("El juego ya existe.");
        }
        juegos.put(juego.getTitulo(), juego);
        guardarJuegos();
    }

    public Juego obtenerJuego(String codigoUnico) {
        return juegos.get(codigoUnico);
    }

    public void eliminarJuego(String codigoUnico) {
        juegos.remove(codigoUnico);
        guardarJuegos();
    }

    public List<Juego> getJuegosRandom(int cantidad) {
        List<Juego> juegosRandom = new ArrayList<>();
        Random random = new Random();
        List<String> titulos = new ArrayList<>(juegos.keySet());

        if (cantidad > juegos.size()) {
            cantidad = juegos.size();
        }

        while (juegosRandom.size() < cantidad) {
            String titulo = titulos.get(random.nextInt(titulos.size()));
            Juego juego = juegos.get(titulo);
            if (!juegosRandom.contains(juego)) {
                juegosRandom.add(juego);
            }
        }

        return juegosRandom;
    }

    public Juego buscarJuegoPorCodigo(String codigo) {
        return juegos.get(codigo);
    }

    public Juego buscarJuegoPorTitulo(String titulo) {
        for (Juego juego : juegos.values()) {
            if (juego.getTitulo().equalsIgnoreCase(titulo)) {
                return juego;
            }
        }
        return null;
    }

    public void agregarResena(String codigoJuego, Resenia resena) {
        Juego juego = juegos.get(codigoJuego);
        if (juego != null) {
            juego.agregarValoracionDeUsuario(resena);
        }
    }

    public void mostrarValoracionDeUsuario(String codigoJuego) {
        Juego juego = juegos.get(codigoJuego);
        if (juego != null) {
            System.out.println("ValoraciÃ³n de usuario del juego " + juego.getTitulo() + ": " + juego.getValoracionDeUsuario() + "% positiva");
        }
    }

    public void mostrarResenas(String codigoJuego) {
        Juego juego = juegos.get(codigoJuego);
        if (juego != null) {
            for (Resenia resena : juego.getValoracionesDeUsuarios()) {
                System.out.println("Comentario: " + resena.getComentario() + ", ValoraciÃ³n: " + (resena.isValoracion() ? "Positiva" : "Negativa"));
            }
        }
    }

    public void guardarJuegos() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("juegos.txt"))) {
            for (Juego juego : juegos.values()) {
                writer.println(juego.getCodigoUnico() + "," + juego.getTitulo() + "," + juego.getCreador() + "," + juego.getTipo() + "," + juego.getPrecio() + "," + juego.getValoracion());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarJuegosDesdeArchivo(String rutaArchivo) throws ExcepcionGeneral {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 6) {
                    String codigo = datos[0];
                    String titulo = datos[1];
                    String creador = datos[2];
                    TipoJuego tipo = TipoJuego.valueOf(datos[3].toUpperCase());
                    double precio = Double.parseDouble(datos[4]);
                    double valoracion = Double.parseDouble(datos[5]);

                    Juego juego = new Juego(codigo, titulo, valoracion, precio, tipo, creador);
                    agregarJuego(juego);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Juego> getTodosLosJuegos() {
        return new ArrayList<>(juegos.values());
    }

    public Map<String, Juego> getJuegos() {
        return juegos;
    }
}
