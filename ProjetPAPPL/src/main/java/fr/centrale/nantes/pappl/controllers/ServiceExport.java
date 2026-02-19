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