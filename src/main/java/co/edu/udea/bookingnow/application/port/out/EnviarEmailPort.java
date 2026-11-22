package co.edu.udea.bookingnow.application.port.out;

public interface EnviarEmailPort {
    void enviar(String destinatario, String asunto, String contenido);
}
