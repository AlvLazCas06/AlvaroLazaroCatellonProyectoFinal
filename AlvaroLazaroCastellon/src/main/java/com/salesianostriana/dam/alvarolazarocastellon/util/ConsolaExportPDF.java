package com.salesianostriana.dam.alvarolazarocastellon.util;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.salesianostriana.dam.alvarolazarocastellon.model.Consola;
import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import com.salesianostriana.dam.alvarolazarocastellon.model.Modelo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@EnableAsync
public class ConsolaExportPDF {

    private List<Consola> listaConsolas;

    public void writeHeaderOfTheTable(PdfPTable table) {
        PdfPCell cell = new PdfPCell();

        cell.setBackgroundColor(Color.DARK_GRAY);

        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Nombre", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Descripción", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Juegos", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Modelos", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Imagén", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);
    }

    public void writeDataOfTable(PdfPTable table) {
        listaConsolas.forEach(consola -> {
            table.addCell(String.valueOf(consola.getId())).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(consola.getNombre());
            table.addCell(consola.getDescription().length() > 75 ? consola.getDescription().substring(0, 75) + "..." : consola.getDescription());
            table.addCell(consola.getJuegos()
                    .stream()
                    .map(Juego::getNombre)
                    .collect(Collectors.joining(", ")));
            table.addCell(consola.getModelos()
                    .stream()
                    .map(Modelo::getNombre)
                    .collect(Collectors.joining(", ")));
            try {
                if (consola.getImagen() != null) {
                    com.lowagie.text.Image img = com.lowagie.text.Image.getInstance(consola.getImagen());
                    img.scaleToFit(50, 50);
                    PdfPCell cell = new PdfPCell(img);
                    table.addCell(cell);
                } else {
                    table.addCell("N/A");
                }
            } catch (IOException e) {
                table.addCell("Error al cargar imagen");
            }
        });
    }

    @Async
    public void exportDocument(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setColor(Color.BLACK);
        font.setSize(18);
        Paragraph title = new Paragraph("Lista de consolas", font);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{0.6f, 1.8f, 3f, 3f, 1.25f, 1.5f});
        table.setSpacingBefore(15);

        writeHeaderOfTheTable(table);
        writeDataOfTable(table);
        document.add(table);
        document.close();
    }

}
