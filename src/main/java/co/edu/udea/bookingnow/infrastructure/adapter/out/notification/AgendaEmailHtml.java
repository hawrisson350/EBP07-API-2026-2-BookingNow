package co.edu.udea.bookingnow.infrastructure.adapter.out.notification;

/** Builds a simple, email-client-friendly HTML layout for agenda notifications. */
final class AgendaEmailHtml {
    private AgendaEmailHtml() {}

    static String render(String subject, String content) {
        StringBuilder body = new StringBuilder();
        for (String rawLine : content.split("\\R")) {
            String line = rawLine.strip();
            if (line.isEmpty()) continue;

            int separator = line.indexOf(':');
            if (separator > 0 && separator < 24) {
                body.append("<tr><td style=\"padding:12px 14px;border-bottom:1px solid #e8e8e8;"
                        + "color:#666;font-size:13px;width:105px;vertical-align:top;\">")
                    .append(escape(line.substring(0, separator)))
                    .append("</td><td style=\"padding:12px 14px;border-bottom:1px solid #e8e8e8;"
                        + "color:#171717;font-size:14px;font-weight:600;\">")
                    .append(escape(line.substring(separator + 1).strip()))
                    .append("</td></tr>");
            } else {
                body.append("<tr><td colspan=\"2\" style=\"padding:10px 14px;color:#333;"
                        + "font-size:15px;line-height:1.6;\">")
                    .append(escape(line))
                    .append("</td></tr>");
            }
        }

        return "<!doctype html><html lang=\"es\"><head><meta charset=\"UTF-8\">"
                + "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"></head>"
                + "<body style=\"margin:0;padding:28px 12px;background:#f2f3f5;"
                + "font-family:Arial,Helvetica,sans-serif;color:#171717;\">"
                + "<div style=\"display:none;max-height:0;overflow:hidden;opacity:0;\">"
                + escape(content.replaceAll("\\s+", " ")) + "</div>"
                + "<table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" "
                + "style=\"width:100%;max-width:600px;margin:0 auto;background:#fff;"
                + "border:1px solid #e5e5e5;border-radius:12px;overflow:hidden;\">"
                + "<tr><td style=\"padding:20px 28px;background:#ff8a18;color:#171717;\">"
                + "<div style=\"font-size:20px;font-weight:800;letter-spacing:.2px;\">BookingNow</div>"
                + "<div style=\"margin-top:4px;font-size:11px;font-weight:700;letter-spacing:1.4px;\">"
                + "AGENDA DE SERVICIOS</div></td></tr>"
                + "<tr><td style=\"padding:30px 28px 12px;\">"
                + "<div style=\"margin-bottom:10px;color:#e87508;font-size:11px;font-weight:700;"
                + "letter-spacing:1.2px;text-transform:uppercase;\">Notificación de agenda</div>"
                + "<h1 style=\"margin:0 0 20px;font-size:24px;line-height:1.3;color:#171717;\">"
                + escape(subject) + "</h1>"
                + "<table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" "
                + "style=\"width:100%;border:1px solid #e8e8e8;border-radius:8px;\"><tbody>"
                + body + "</tbody></table>"
                + "<p style=\"margin:22px 0 8px;color:#555;font-size:14px;line-height:1.6;\">"
                + "Gracias por usar BookingNow. ¡Te esperamos!</p></td></tr>"
                + "<tr><td style=\"padding:16px 28px;background:#fafafa;border-top:1px solid #eee;"
                + "color:#777;font-size:11px;line-height:1.5;\">Este es un mensaje automático de BookingNow."
                + " Por favor, no respondas a este correo.</td></tr></table></body></html>";
    }

    private static String escape(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}
