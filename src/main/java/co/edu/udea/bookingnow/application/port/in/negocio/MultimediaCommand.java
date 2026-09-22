package co.edu.udea.bookingnow.application.port.in.negocio;

/** Contenido Base64 en formato data URL, por ejemplo data:image/png;base64,... */
public record MultimediaCommand(String base64, String tipo) {}
