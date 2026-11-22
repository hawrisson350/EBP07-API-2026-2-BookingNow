package co.edu.udea.bookingnow.infrastructure.adapter.out.notification;

public record EmailEvent(String destinatario, String asunto, String contenido) {
}
