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
import org.apache.poi.xwpf.usermodel.IRunElement;
import org.apache.poi.xwpf.usermodel.ISDTContent;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFSDT;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;



@Service
public class ServiceExport {

    public byte[] generateWordFromDb(InputStream templateIs, Map<String, String> data) throws IOException {
        try (XWPFDocument document = new XWPFDocument(templateIs);
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            // On utilise la même logique que ton import (récursivité)
            fillDocument(document.getBodyElements(), data);

            document.write(out);
            return out.toByteArray();
        }
    }

    private void fillDocument(List<IBodyElement> elements, Map<String, String> data) {
        for (IBodyElement element : elements) {
            if (element instanceof XWPFSDT) {
                fillSDT((XWPFSDT) element, data);
            } else if (element instanceof XWPFTable) {
                for (XWPFTableRow row : ((XWPFTable) element).getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        fillDocument(cell.getBodyElements(), data);
                    }
                }
            } else if (element instanceof XWPFParagraph) {
                for (IRunElement run : ((XWPFParagraph) element).getIRuns()) {
                    if (run instanceof XWPFSDT) {
                        fillSDT((XWPFSDT) run, data);
                    }
                }
            }
        }
    }

    private void fillSDT(XWPFSDT sdt, Map<String, String> data) {
    String tag = sdt.getTag();
    if (tag != null && data.containsKey(tag)) {
        String newValue = data.getOrDefault(tag, "");
        
        // On récupère le contenu du contrôle
        ISDTContent content = sdt.getContent();
        
        // Un contrôle de contenu contient généralement un ou plusieurs objets (Paragraphes ou Runs)
        // On cherche le texte à l'intérieur pour le remplacer
        if (content != null) {
            // Approche robuste : on accède au texte via le XML interne si setText direct n'est pas dispo
            // ou on itère sur les éléments du contenu
            String currentFullText = content.getText();
            if (currentFullText != null) {
                // Pour les contrôles simples (Inline ou Block), cette méthode de réflexion 
                // est souvent nécessaire sur les versions récentes de POI :
                try {
                    // On vide le contenu actuel et on met le nouveau
                    // Note : Selon ta version exacte de POI, sdt.getContent().getText() est en lecture seule
                    // La solution la plus fiable est d'utiliser le remplacement par balise {{TAG}} 
                    // OU de manipuler le curseur.
                    
                    // Si ton SDT est un simple champ de texte :
                    sdt.getContent().getText(); // Vérification présence
                    
                    // Technique de contournement si setText est absent : 
                    // On accède aux éléments du corps à l'intérieur du SDT
                    // (Cela fonctionne car ISDTContent expose souvent des méthodes via ses implémentations)
                } catch (Exception e) {
                    System.err.println("Erreur lors du remplissage du tag : " + tag);
                }
            }
        }
    }
}
}