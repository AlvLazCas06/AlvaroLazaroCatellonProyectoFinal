package com.salesianostriana.dam.alvarolazarocastellon.util;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
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
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@EnableAsync
public class ModeloExportPDF {

    private List<Modelo> listaModelos;

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

        cell.setPhrase(new Phrase("Fabricante", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Precio", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Consola", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Cantidad", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Venta", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Fecha de Lanzamiento", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Llegada al mercado", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Carátula", font));

        table.addCell(cell).setHorizontalAlignment(Element.ALIGN_CENTER);
    }

    public void writeDataOfTable(PdfPTable table) {
        listaModelos.forEach(modelo -> {
            table.addCell(String.valueOf(modelo.getId())).setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(modelo.getNombre());
            table.addCell(modelo.getFabricante().getNombre());
            table.addCell(String.valueOf(modelo.getPrecio()));
            table.addCell(modelo.getConsola() != null ? modelo.getConsola().getNombre() : "N/A");
            table.addCell(String.valueOf(modelo.getCantidad()));
            table.addCell(String.valueOf(modelo.getVentas()));
            table.addCell(modelo.getFechaLanzamiento().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            table.addCell(modelo.getLlegadaAlMercado().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            try {
                if (modelo.getImagen() != null) {
                    com.lowagie.text.Image img = com.lowagie.text.Image.getInstance(modelo.getImagen());
                    img.scaleToFit(50, 50); // Ajusta el tamaño de la imagen
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
        Paragraph title = new Paragraph("Lista de modelos", font);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{0.4f, 0.9f, 1.05f, 0.7f, 1f, 0.625f, 0.5f, 1.2f, 1.2f, 1f});
        table.setSpacingBefore(15);

        writeHeaderOfTheTable(table);
        writeDataOfTable(table);
        document.add(table);
        document.close();
    }

}
