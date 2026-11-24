package co.edu.udea.bookingnow.application.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

final class FormatoAgendaCorreo {
    private static final Locale ESPANOL = Locale.forLanguageTag("es-CO");
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", ESPANOL);

    private FormatoAgendaCorreo() {}

    static String fecha(LocalDate fecha) {
        String texto = FECHA.format(fecha);
        return texto.substring(0, 1).toUpperCase(ESPANOL) + texto.substring(1);
    }

    static String hora(LocalTime hora) {
        int hora12 = hora.getHour() % 12;
        if (hora12 == 0) hora12 = 12;
        String periodo = hora.getHour() < 12 ? "a. m." : "p. m.";
        return String.format(Locale.ROOT, "%d:%02d %s", hora12, hora.getMinute(), periodo);
    }
}
