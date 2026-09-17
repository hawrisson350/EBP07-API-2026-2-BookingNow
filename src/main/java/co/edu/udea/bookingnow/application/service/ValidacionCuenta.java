package co.edu.udea.bookingnow.application.service;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

final class ValidacionCuenta {
    private ValidacionCuenta() {}

    static String obligatorio(String valor, String campo) {
        if (valor == null || valor.isBlank() || valor.strip().length() > 255) {
            throw new IllegalArgumentException(campo + " es obligatorio y admite hasta 255 caracteres");
        }
        return valor.strip();
    }

    static String nombreUsuario(String valor) {
        String nombre = obligatorio(valor, "El nombre de usuario").toLowerCase(Locale.ROOT);
        if (!nombre.matches("[a-z0-9._-]{3,50}")) {
            throw new IllegalArgumentException("El usuario debe tener entre 3 y 50 letras, numeros, puntos, guiones o guiones bajos");
        }
        return nombre;
    }

    static String correo(String valor) {
        String correo = obligatorio(valor, "El correo").toLowerCase(Locale.ROOT);
        if (!correo.matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+")) {
            throw new IllegalArgumentException("El formato del correo no es valido");
        }
        return correo;
    }

    static void contrasena(String valor) {
        if (valor == null || valor.isBlank() || valor.length() < 8
                || valor.getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new IllegalArgumentException("La contrasena debe tener al menos 8 caracteres y hasta 72 bytes UTF-8");
        }
    }
}
