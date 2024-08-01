package Gestores;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import Juegos.Juego;
import MetodoDePago.*;
import Usuarios.Usuario;
import excepciones.ExcepcionGeneral;

public class GestorDeUsuarios {
    private List<Usuario> usuarios;
    private static int ultimoId = 0;

    public GestorDeUsuarios() {
        this.usuarios = new ArrayList<>();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void agregarUsuario(Usuario usuario) throws ExcepcionGeneral {
        for (Usuario u : usuarios) {
            if (u.getCorreo().equals(usuario.getCorreo()) || u.getNombreUsuario().equals(usuario.getNombreUsuario())) {
                throw new ExcepcionGeneral("El usuario ya existe.");
            }
        }
        usuarios.add(usuario);
    }

    public void guardarUsuarios(String archivo) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (Usuario usuario : usuarios) {
                bw.write(usuario.getId() + "," +
                        usuario.getNombre() + "," +
                        usuario.getApellido() + "," +
                        usuario.getCorreo() + "," +
                        usuario.getNombreUsuario() + "," +
                        usuario.getContrasenia() + "," +
                        tipoPagoToString(usuario.getTipoPago()) + "," +
                        bibliotecaToString(usuario.getBiblioteca()));
                bw.newLine();
            }
        }
    }
    public void cargarUsuarios(String archivo, GestorDeJuego gestorDeJuego) throws IOException, ExcepcionGeneral {
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",", 8);
                Usuario usuario = new Usuario(partes[1], partes[2], partes[3], partes[4], partes[5]);
                tipoPagoFromString(partes[6], usuario);
                bibliotecaFromString(partes[7], gestorDeJuego, usuario);
                usuarios.add(usuario);

                // Actualizar el ultimoId basado en el ID del usuario
                int id = Integer.parseInt(usuario.getId());
                if (id > ultimoId) {
                    ultimoId = id;
                }
            }
        }
    }

    private String tipoPagoToString(List<FormaDePago> tipoPagoList) {
        StringBuilder sb = new StringBuilder();
        for (FormaDePago tipoPago : tipoPagoList) {
            if (tipoPago instanceof PayPal) {
                PayPal payPal = (PayPal) tipoPago;
                sb.append("PayPal(")
                  .append(payPal.getNombreComleto()).append(",")
                  .append(payPal.getCorreoElectronico()).append(",")
                  .append(payPal.getContrasenia()).append(");");
            } else if (tipoPago instanceof Credito) {
                Credito credito = (Credito) tipoPago;
                sb.append("Credito(")
                  .append(credito.getNombreComleto()).append(",")
                  .append(credito.getFechaExpiracion()).append(",")
                  .append(credito.getNumeroTarjeta()).append(",")
                  .append(credito.getCodigoSeguridad()).append(");");
            } else if (tipoPago instanceof debito) {
                debito debito = (debito) tipoPago;
                sb.append("Debito(")
                  .append(debito.getNombreComleto()).append(",")
                  .append(debito.getFechaExpiracion()).append(",")
                  .append(debito.getNumeroTarjeta()).append(",")
                  .append(debito.getCodigoSeguridad()).append(");");
            }
        }
        return sb.toString();
    }
    
    private void tipoPagoFromString(String tipoPagoStr, Usuario usuario) {
        String[] tiposPago = tipoPagoStr.split(";");
        for (String tipo : tiposPago) {
            if (tipo.startsWith("PayPal(")) {
                String[] partes = tipo.substring(7, tipo.length() - 1).split(",");
                usuario.agregarTipoPago(new PayPal(partes[0], partes[1], partes[2]));
            } else if (tipo.startsWith("Credito(")) {
                String[] partes = tipo.substring(8, tipo.length() - 1).split(",");
                usuario.agregarTipoPago(new Credito(partes[0], partes[1], partes[2], partes[3]));
            } else if (tipo.startsWith("Debito(")) {
                String[] partes = tipo.substring(7, tipo.length() - 1).split(",");
                usuario.agregarTipoPago(new debito(partes[0], partes[1], partes[2], partes[3]));
            }
        }
    }
    
    private String bibliotecaToString(List<Juego> biblioteca) {
        StringBuilder sb = new StringBuilder();
        for (Juego juego : biblioteca) {
            sb.append(juego.getCodigoUnico()).append(";");
        }
        return sb.toString();
    }
    
    private void bibliotecaFromString(String bibliotecaStr, GestorDeJuego gestorDeJuego, Usuario usuario) {
        String[] juegos = bibliotecaStr.split(";");
        for (String codigoJuego : juegos) {
            if (!codigoJuego.trim().isEmpty()) {
                Juego juego = gestorDeJuego.buscarJuegoPorCodigo(codigoJuego);
                if (juego != null) {
                    usuario.agregarJuegoABiblioteca(juego);
                }
            }
        }
    }
    
    public void listarUsuarios() {
        for (Usuario usuario : usuarios) {
            System.out.println(usuario);
        }
    }
    public Usuario buscarUsuario(String correo) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCorreo().equalsIgnoreCase(correo)) {
                return usuario;
            }
        }
        return null;
    }
    

    public Usuario buscarUsuarioPorNombreUsuario(String nombreUsuario) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return usuario;
            }
        }
        return null;
    }
    public void cambiarContrasenia(String contrasenia) {
        for (Usuario usuario : usuarios) {
            if (usuario.getContrasenia().equals(contrasenia)) {
                usuario.setContrasenia(contrasenia);
            }
        }
    }

    public void reemplazarUsuario(Usuario nuevoUsuario) {
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId().equals(nuevoUsuario.getId())) {
                usuarios.set(i, nuevoUsuario);
                return;
            }
        }
    }

       

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GestorDeUsuarios that = (GestorDeUsuarios) o;
        return Objects.equals(usuarios, that.usuarios);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(usuarios);
    }


}


