package co.edu.udea.bookingnow.application.port.out;

public interface PublicarEmailPort {
    void publicar(String destinatario, String asunto, String contenido);
}
