/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.springframework.stereotype.Service;

@Service
public class ServiceExport {

    public byte[] generateWordFromDb(InputStream templateIs, Map<String, String> data) throws IOException {
        try (XWPFDocument document = new XWPFDocument(templateIs);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            fillElements(document.getBodyElements(), data);
            document.write(out);
            return out.toByteArray();
        }
    }
    


    public byte[] generateAllWordFromDb(String templatePath, List<Map<String, String>> allData) throws IOException {
        try (InputStream is = getClass().getResourceAsStream(templatePath);
         XWPFDocument finalDoc = new XWPFDocument(is); // On charge le premier avec l'image
         ByteArrayOutputStream out = new ByteArrayOutputStream()) {

        // On remplit le premier sujet directement dans le document qui a déjà l'image
        if (!allData.isEmpty()) {
            fillElements(finalDoc.getBodyElements(), allData.get(0));
        }

        // Pour les sujets suivants (à partir de l'index 1)
        for (int i = 1; i < allData.size(); i++) {
            // Saut de page avant le nouveau sujet
            finalDoc.createParagraph().setPageBreak(true);

            try (InputStream tempIs = getClass().getResourceAsStream(templatePath);
                 XWPFDocument tempDoc = new XWPFDocument(tempIs)) {
                
                // Remplir le template temporaire
                fillElements(tempDoc.getBodyElements(), allData.get(i));

                // Fusionner le contenu (le texte et les structures)
                appendBody(finalDoc, tempDoc);
            }
        }

            finalDoc.write(out);
            return out.toByteArray();
        }
    }

    // Méthode pour fusionner les corps de documents (préserve mieux les structures complexes)
    private void appendBody(XWPFDocument src, XWPFDocument append) {
        CTBody srcBody = src.getDocument().getBody();
        CTBody appendBody = append.getDocument().getBody();

        for (IBodyElement element : append.getBodyElements()) {
            if (element instanceof XWPFParagraph) {
                src.createParagraph().getCTP().set(((XWPFParagraph) element).getCTP().copy());
            } else if (element instanceof XWPFTable) {
                src.createTable().getCTTbl().set(((XWPFTable) element).getCTTbl().copy());
            }
        }
    }

    // Méthode pour copier un paragraphe avec TOUS ses styles (gras, taille, police)
    private void copyParagraph(XWPFParagraph source, XWPFParagraph target) {
        target.getCTP().set(source.getCTP().copy());
    }

    // Méthode pour copier un tableau avec ses bordures, couleurs et contenus
    private void copyTable(XWPFTable source, XWPFTable target) {
        target.getCTTbl().set(source.getCTTbl().copy());
    }

    private void fillElements(List<IBodyElement> elements, Map<String, String> data) {
        for (IBodyElement element : elements) {
            if (element instanceof XWPFParagraph) {
                replaceInParagraph((XWPFParagraph) element, data);
            } else if (element instanceof XWPFTable) {
                replaceInTable((XWPFTable) element, data);
            }
        }
    }

    private void replaceInParagraph(XWPFParagraph paragraph, Map<String, String> data) {
        List<XWPFRun> runs = paragraph.getRuns();
        if (runs == null || runs.isEmpty()) return;

        for (XWPFRun run : runs) {
            String text = run.getText(0);
            if (text != null) {
                boolean modified = false;
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    String placeholder = "{{" + entry.getKey() + "}}";
                    if (text.contains(placeholder)) {
                        // On remplace la balise par la valeur (ou vide si null)
                        String value = (entry.getValue() != null) ? entry.getValue() : "";
                        text = text.replace(placeholder, value);
                        modified = true;
                    }
                }
                if (modified) {
                    run.setText(text, 0);
                }
            }
        }
    }

    private void replaceInTable(XWPFTable table, Map<String, String> data) {
        for (XWPFTableRow row : table.getRows()) {
            for (XWPFTableCell cell : row.getTableCells()) {
                // Une cellule peut contenir des paragraphes ou d'autres tableaux
                fillElements(cell.getBodyElements(), data);
            }
        }
    }
}