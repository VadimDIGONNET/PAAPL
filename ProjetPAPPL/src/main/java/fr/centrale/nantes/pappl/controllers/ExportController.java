package fr.centrale.nantes.pappl.controllers;

import fr.centrale.nantes.pappl.repositories.ExportRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
public class ExportController {

    @Autowired
    private ExportRepository repository;

    @RequestMapping(value = "downloadExcel.do", method = RequestMethod.GET)
    public ResponseEntity<byte[]> downloadExcel() throws IOException {
        List<Object[]> results = repository.fetchDataForExcel();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Export P2T");
            
            String[] headers = {
                "P2T_Num", "P2T_Num2", "P2T_Conf", "ENT_Nom", "P2T_Amphi", "P2T_Fiche", "P2T_Convention", 
                "Qui suit ?", "P2T_Sujet", "ENT_Adr1", "ENT_CP",
                "COR_Nom", "COR_Tel","COR_Email", "COR2_Email", "ENS_Nom", "ENS_Email", "Ele1_Titre", "Ele1_Nom", "Ele2_Nom", "Ele3_Nom", "Ele4_Nom", "Ele5_Nom", "Ele6_Nom", "Ele7_Nom",
                "Ele1_Email", "Ele2_Email", "Ele3_Email", "Ele4_Email", "Ele5_Email", "Ele6_Email", "Ele7_Email", "Ele7_Matric",
                "Ele8_Titre", "Ele8_Nom", "Ele8_Email", "Ele8_Matric", "Ele9_Titre", "Ele9_Nom", "Ele9_Email", "Ele9_Matric",
                "DATE_RENCONTRE", "ADRESSE", "COMPLEMENT", "Convention", "Evaluation", 
                "Date_1", "Date_2", "Date_3", "Date_4", "Date_5", "Date_6", "Date_7", "Date_8", 
                "Date_9", "Date_10", "Date_11", "Date_12", "Date_13", "Date_14", "Date_15", "P2T l'an prochain"
            };

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            int rowNum = 1;
            for (Object[] rowData : results) {
                Row row = sheet.createRow(rowNum++);
                int col = 0;
                
                // Infos P2T & Entreprise
                row.createCell(col++).setCellValue(rowData[0] != null ? rowData[0].toString() : ""); // P2T_Num
                row.createCell(col++).setCellValue("P2T__"); 
                row.createCell(col++).setCellValue(""); //P2T_Conf
                row.createCell(col++).setCellValue(rowData[1] != null ? rowData[1].toString() : ""); // ENT_Nom
                row.createCell(col++).setCellValue("");
                row.createCell(col++).setCellValue("P2T1_Fiche_Synthétique.pdf");
                row.createCell(col++).setCellValue("P2T1_Convention.pdf");
                row.createCell(col++).setCellValue(""); 
                row.createCell(col++).setCellValue(rowData[2] != null ? rowData[2].toString() : ""); // Sujet
                row.createCell(col++).setCellValue(rowData[3] != null ? rowData[3].toString() : ""); // Adr1
                row.createCell(col++).setCellValue(rowData[4] != null ? rowData[4].toString() : ""); // CP

                // Correspondant
                row.createCell(col++).setCellValue(rowData[5] != null ? rowData[5].toString() : ""); // Nom
                row.createCell(col++).setCellValue(rowData[6] != null ? rowData[6].toString() : ""); // Tel
                row.createCell(col++).setCellValue(rowData[7] != null ? rowData[7].toString() : ""); // Email
                row.createCell(col++).setCellValue(""); // Email2

                // Enseignants (Colonnes vides pour respecter le format)
                row.createCell(col++).setCellValue(""); row.createCell(col++).setCellValue(""); 


                // BLOC NOMS (Ele1 à Ele7) - Index 8 à 14
                row.createCell(col++).setCellValue("M./Mme"); // Titre Ele1
                for (int i = 8; i <= 14; i++) {
                    row.createCell(col++).setCellValue(rowData[i] != null ? rowData[i].toString() : "");
                }

                // BLOC EMAILS (Ele1 à Ele7) - Index 15 à 21
                for (int i = 15; i <= 21; i++) {
                    row.createCell(col++).setCellValue(rowData[i] != null ? rowData[i].toString() : "");
                }
                row.createCell(col++).setCellValue(""); // Ele7 Matricule

                // Ele8 et Ele9 (Vides ou à mapper si besoin)
                for (int i = 0; i < 8; i++) row.createCell(col++).setCellValue("");

                // Infos Finales
                row.createCell(col++).setCellValue(""); // Rencontre
                row.createCell(col++).setCellValue(""); // Adresse
                row.createCell(col++).setCellValue(""); // Complement
                row.createCell(col++).setCellValue(""); // Convention
                row.createCell(col++).setCellValue(""); // Evaluation

                for (int i = 0;i < 16; i++) row.createCell(col++).setCellValue("");

                row.createCell(col++).setCellValue(""); // P2T an prochain

                ByteArrayOutputStream out = new ByteArrayOutputStream();
                workbook.write(out);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export_pappl.xlsx")
                        .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                        .body(out.toByteArray());
            }
        }
        return null;
    }
}