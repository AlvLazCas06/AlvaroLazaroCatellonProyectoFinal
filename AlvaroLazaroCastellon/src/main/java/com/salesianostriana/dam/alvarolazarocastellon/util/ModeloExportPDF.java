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

import java.awt.*;
import java.io.IOException;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModeloExportPDF {

    private List<Modelo> listaJuegos;

    public void writeHeaderOfTheTable(PdfPTable table) {
        PdfPCell cell = new PdfPCell();

        cell.setBackgroundColor(Color.DARK_GRAY);

        cell.setPadding(5);

        com.lowagie.text.Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("ID", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Nombre", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Descripción", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Fabricante", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Precio", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Consola", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Cantidad", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Venta", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Fecha de Lanzamiento", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Llegada al mercado", font));

        table.addCell(cell);

        cell.setPhrase(new Phrase("Carátula", font));

        table.addCell(cell);
    }

    public void writeDataOfTable(PdfPTable table) {
        listaJuegos.forEach(juego -> {
            table.addCell(String.valueOf(juego.getId()));
            table.addCell(juego.getNombre());
            table.addCell(juego.getDescription().length() > 75 ? juego.getDescription().substring(0, 75) + "..." : juego.getDescription());
            table.addCell(juego.getFabricante());
            table.addCell(String.valueOf(juego.getPrecio()));
            table.addCell(juego.getConsola() != null ? juego.getConsola().getNombre() : "N/A");
            table.addCell(String.valueOf(juego.getCantidad()));
            table.addCell(String.valueOf(juego.getVentas()));
            table.addCell(juego.getFechaLanzamiento().toString());
            table.addCell(juego.getLlegadaAlMercado().toString());
            try {
                if (juego.getImagen() != null) {
                    com.lowagie.text.Image img = com.lowagie.text.Image.getInstance(juego.getImagen());
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

    public void exportDocument(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES);
        font.setColor(Color.WHITE);
        font.setSize(18);
        Paragraph title = new Paragraph("Lista de juegos", font);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(title);

        PdfPTable table = new PdfPTable(11);
        table.setWidthPercentage(100);
        table.setSpacingBefore(15);

        writeHeaderOfTheTable(table);
        writeDataOfTable(table);
        document.add(table);
        document.close();
    }

}
