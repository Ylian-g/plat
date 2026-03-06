package Foreach.cda.Plat.service;

import java.io.ByteArrayOutputStream;
import org.springframework.stereotype.Service;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import Foreach.cda.Plat.entity.Recette;
import Foreach.cda.Plat.entity.RecetteIngredient;
import Foreach.cda.Plat.repository.RecetteRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PdfGeneratorService {

    private final RecetteRepository recetteRepository;

    public byte[] generateRecettePdf(Long recetteId) {
        Recette recette = recetteRepository.findById(recetteId).orElse(null);

        if (recette == null) {
            return null;
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            Paragraph title = new Paragraph(recette.getNomPlat())
                    .setFontSize(20)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            document.add(new Paragraph("Cree par: " + recette.getCreateur().getPrenom() + " " + recette.getCreateur().getNom()));
            document.add(new Paragraph("Duree de preparation: " + recette.getDureePreparation() + " minutes"));
            document.add(new Paragraph("Duree de cuisson: " + recette.getDureeCuisson() + " minutes"));
            document.add(new Paragraph("Calories totales: " + recette.getNombreCalorique() + " kcal"));
            document.add(new Paragraph("Partagee: " + (recette.getPartage() ? "Oui" : "Non")));

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Ingredients:").setBold());

            if (recette.getRecetteIngredients() != null && !recette.getRecetteIngredients().isEmpty()) {
                Table table = new Table(4);

                table.addHeaderCell("Ingredient");
                table.addHeaderCell("Type");
                table.addHeaderCell("Quantite");
                table.addHeaderCell("Calories");

                for (RecetteIngredient ri : recette.getRecetteIngredients()) {
                    table.addCell(ri.getIngredient().getLibelle());
                    table.addCell(ri.getIngredient().getType().toString());
                    table.addCell(ri.getQuantite() + " " + ri.getUnite());
                    table.addCell(ri.getIngredient().getNombreCalorie() + " kcal");
                }

                document.add(table);
            } else {
                document.add(new Paragraph("Aucun ingredient ajoute."));
            }

            document.close();

        } catch (Exception e) {
            return null;
        }

        return baos.toByteArray();
    }
}
