package com.salesianostriana.dam.alvarolazarocastellon.util;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.salesianostriana.dam.alvarolazarocastellon.model.Juego;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.awt.*;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@EnableAsync
public class JuegoExportPDF {

    private List<Juego> listaJuegos;

    public void writeHeaderOfTheTable(PdfPTable table) {
        PdfPCell cell = new PdfPCell();

        cell.setBackgroundColor(Color.DARK_GRAY);

        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Nombre", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Precio", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Consola", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Cantidad", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Venta", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Nº de Jug.", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Género", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Fecha de Lanzamiento", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Llegada al mercado", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Carátula", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);
    }

    public void writeDataOfTable(PdfPTable table) {
        listaJuegos.forEach(juego -> {
            table.addCell(String.valueOf(juego.getId())).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(juego.getNombre()).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(String.valueOf(juego.getPrecio())).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(juego.getConsola() != null ? juego.getConsola().getNombre() : "N/A").setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(String.valueOf(juego.getCantidad())).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(String.valueOf(juego.getVentas())).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(String.valueOf(juego.getNumJugadores())).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(juego.getGenero()).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(juego.getFechaLanzamiento().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(juego.getLlegadaAlMercado().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))).setHorizontalAlignment(Element.ALIGN_CENTER);
            try {
                if (juego.getRutaImagen() != null) {
                    Image img = Image.getInstance(juego.getRutaImagen());
                    img.scaleToFit(50, 50);
                    PdfPCell cell = new PdfPCell(img);
                    table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);
                } else {
                    table.addCell("N/A").setHorizontalAlignment(Element.ALIGN_CENTER);
                }
            } catch (IOException e) {
                table.addCell("Error al cargar imagen");
            }
        });
    }

    @Async
    public void exportDocument(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A3);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setColor(Color.BLACK);
        font.setSize(18);
        Paragraph title = new Paragraph("Lista de juegos", font);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        PdfPTable table = new PdfPTable(11);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{0.375f, 3f, 0.65f, 1.2f, 0.825f, 0.625f, 0.5f, 1f, 1.2f, 1.2f, 1f});
        table.setSpacingBefore(15);

        writeHeaderOfTheTable(table);
        writeDataOfTable(table);
        document.add(table);
        document.close();
    }

}
