package co.edu.udea.bookingnow.infrastructure.adapter.out.report;

import co.edu.udea.bookingnow.application.port.out.GeneradorReportePort;
import co.edu.udea.bookingnow.domain.model.ReporteArchivo;
import co.edu.udea.bookingnow.domain.model.ReporteReservaFila;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class ArchivoReporteAdapter implements GeneradorReportePort {
    private static final String PDF = "application/pdf";
    private static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    @Override
    public ReporteArchivo generar(String tipoReporte, String formato, LocalDate desde, LocalDate hasta,
            List<ReporteReservaFila> filas) {
        try {
            boolean uso = tipoReporte.equals("USO_SERVICIOS_RECURSOS");
            String nombre = "bookingnow-" + (uso ? "uso-servicios-recursos" : "reservas") + "-"
                    + (desde == null ? "todo" : desde) + "-" + (hasta == null ? "hoy" : hasta);
            if (formato.equals("PDF")) return new ReporteArchivo(pdf(tipoReporte, desde, hasta, filas), nombre + ".pdf", PDF);
            return new ReporteArchivo(excel(tipoReporte, desde, hasta, filas), nombre + ".xlsx", XLSX);
        } catch (IOException e) {
            throw new IllegalStateException("No fue posible generar el archivo del reporte", e);
        }
    }

    private byte[] excel(String tipo, LocalDate desde, LocalDate hasta, List<ReporteReservaFila> filas) throws IOException {
        try (Workbook wb = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = wb.createSheet("Reporte");
            Row title = sheet.createRow(0); title.createCell(0).setCellValue(tipo.equals("RESERVAS") ? "Reporte de reservas" : "Uso de servicios y recursos");
            Row period = sheet.createRow(1); period.createCell(0).setCellValue("Periodo: " + (desde == null ? "sin inicio" : desde) + " a " + (hasta == null ? "sin fin" : hasta));
            boolean uso = tipo.equals("USO_SERVICIOS_RECURSOS");
            String[] headers = uso ? new String[]{"Servicio", "Recurso", "Cantidad de reservas"}
                    : new String[]{"Reserva", "Fecha", "Hora", "Cliente", "Servicio", "Recurso", "Negocio", "Estado"};
            Row h = sheet.createRow(3); CellStyle style = wb.createCellStyle(); Font font = wb.createFont(); font.setBold(true); style.setFont(font);
            for (int i=0;i<headers.length;i++){Cell c=h.createCell(i);c.setCellValue(headers[i]);c.setCellStyle(style);}
            if (uso) {
                for (var e : agrupar(filas).entrySet()) { Row row=sheet.createRow(sheet.getLastRowNum()+1);row.createCell(0).setCellValue(e.getKey().split(":::",-1)[0]);row.createCell(1).setCellValue(e.getKey().split(":::",-1)[1]);row.createCell(2).setCellValue(e.getValue()); }
            } else {
                for (var f:filas){Row r=sheet.createRow(sheet.getLastRowNum()+1);r.createCell(0).setCellValue(f.idReserva());r.createCell(1).setCellValue(f.fecha().toString());r.createCell(2).setCellValue(f.hora().toString());r.createCell(3).setCellValue(f.nombreCliente());r.createCell(4).setCellValue(f.nombreServicio());r.createCell(5).setCellValue(f.nombreRecurso());r.createCell(6).setCellValue(f.nombreNegocio());r.createCell(7).setCellValue(f.estado());}
            }
            if(filas.isEmpty())sheet.createRow(4).createCell(0).setCellValue("No hay resultados para los filtros seleccionados.");
            for(int i=0;i<headers.length;i++)sheet.autoSizeColumn(i);
            wb.write(out); return out.toByteArray();
        }
    }

    private byte[] pdf(String tipo, LocalDate desde, LocalDate hasta, List<ReporteReservaFila> filas) throws IOException {
        try (PDDocument doc = new PDDocument(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PDType1Font font = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
            List<String> lines = new java.util.ArrayList<>();
            lines.add(tipo.equals("RESERVAS") ? "Reporte de reservas" : "Uso de servicios y recursos");
            lines.add("Periodo: " + (desde == null ? "sin inicio" : desde) + " a " + (hasta == null ? "sin fin" : hasta));
            lines.add("");
            if (filas.isEmpty()) lines.add("No hay resultados para los filtros seleccionados.");
            else if (tipo.equals("USO_SERVICIOS_RECURSOS")) {
                lines.add("Servicio | Recurso | Cantidad de reservas");
                agrupar(filas).forEach((k,v)->lines.add(k.replace(":::" , " | ") + " | " + v));
            } else {
                for(var f:filas)lines.add("#"+f.idReserva()+" | "+f.fecha()+" "+f.hora()+" | "+f.nombreServicio()+" | "+f.nombreNegocio()+" | "+f.estado());
            }
            int index=0;
            while(index<lines.size()){
                PDPage page=new PDPage(PDRectangle.LETTER);doc.addPage(page);
                try(PDPageContentStream stream=new PDPageContentStream(doc,page)){
                    stream.beginText();stream.setFont(font,10);stream.setLeading(14);stream.newLineAtOffset(45,750);
                    for(int n=0;n<48&&index<lines.size();n++,index++){stream.showText(pdfSafe(lines.get(index)));stream.newLine();}
                    stream.endText();
                }
            }
            doc.save(out);return out.toByteArray();
        }
    }

    private Map<String,Integer> agrupar(List<ReporteReservaFila> filas){Map<String,Integer> uso=new TreeMap<>();for(var f:filas){String key=f.nombreServicio()+":::"+f.nombreRecurso();uso.merge(key,1,Integer::sum);}return uso;}
    private String pdfSafe(String text){StringBuilder b=new StringBuilder();for(char c:text.toCharArray()){if(c=='\u2013'||c=='\u2014')b.append('-');else if(c=='\u2019'||c=='\u2018')b.append('\'');else if(c=='\u201c'||c=='\u201d')b.append('"');else if(c<32||c>255)b.append('?');else b.append(c);}return b.toString();}
}
