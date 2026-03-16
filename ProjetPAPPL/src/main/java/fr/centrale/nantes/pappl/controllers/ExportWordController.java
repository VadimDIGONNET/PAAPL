/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import java.io.IOException;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import fr.centrale.nantes.pappl.controllers.ServiceExport;
import fr.centrale.nantes.pappl.items.Correspondant;
import fr.centrale.nantes.pappl.items.DescriptionSujet;
import fr.centrale.nantes.pappl.items.Entreprise;
import fr.centrale.nantes.pappl.items.Sujet;
import fr.centrale.nantes.pappl.repositories.DescriptionSujetRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
/**
 *
 * @author agach
 */
@Controller
public class ExportWordController {
    @Autowired
    private ServiceExport exportService;
    
    @Autowired
    private DescriptionSujetRepository descriptionSujetRepository;
    
    

    @RequestMapping(value="ExportProjet.do", method = RequestMethod.POST)
    public ResponseEntity<byte[]> handleFileExport(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            DescriptionSujet ds = descriptionSujetRepository.getReferenceById(id);
            Sujet s = ds.getSujet();
            Entreprise e = ds.getNomEntreprise();
        
            Correspondant c = s.getIdCorrespondant();
            Map<String, String> data = new HashMap<>();
            data.put("entreprise", e.getNom() != null ? e.getNom() : "");
            data.put("titre", s.getIntitule() != null ? s.getIntitule() : "");
            data.put("contexte", ds.getContexte() != null ? ds.getContexte() : "");
            data.put("travail", ds.getTravailRealiser() != null ? ds.getTravailRealiser() : "");
            data.put("livrable", ds.getLivrable() != null ? ds.getLivrable() : "");
            data.put("confidentiel", ds.getConfidentiel() != null ? ds.getConfidentiel() : "");
            data.put("annee", s.getAnnee() != null ? String.valueOf(s.getAnnee().getAnnee()) : "");
            data.put("description", e.getDescription() != null ? e.getDescription() : "");
            data.put("adresse", e.getAdresse() !=null ? e.getAdresse() : "");
            data.put("code_postal", e.getCodePostal() !=null ? String.valueOf(e.getCodePostal()) : "");
            data.put("telentre", e.getTel()!=null ? String.valueOf(e.getTel()) :"");
            if (c != null) {
                data.put("nom", c.getNom());
                data.put("prenom", c.getPrenom());
                data.put("telephone", c.getTel() != null ? String.valueOf(c.getTel()) : "");
                data.put("email", c.getEmail());
            }
            
            InputStream is = getClass().getResourceAsStream("/templates/modele_export.docx");
            if (is == null) throw new FileNotFoundException("Modèle Word introuvable");

            byte[] docBytes = exportService.generateWordFromDb(is, data);

            String filename = "Projet_" + e.getNom().replace(" ", "_") + ".docx";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(docBytes);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @RequestMapping(value = "Exportsujetgeneral.do", method = RequestMethod.POST)
    public ResponseEntity<byte[]> handleExportAll(HttpServletRequest request) {
        try {
            Object sessionAnnee = request.getSession().getAttribute("anneeCourante");
            List<Map<String, String>> allData = new ArrayList<>();

            if (sessionAnnee == null) {
                // Cas 1 : Récupération classique (renvoie List<DescriptionSujet>)
                List<DescriptionSujet> listFull = descriptionSujetRepository.findAll();
                for (DescriptionSujet ds : listFull) {
                    allData.add(prepareDataMap(ds));
                }
            } else {
                // Cas 2 : Filtrage par année (renvoie List<Object[]>)
                int anneeVar = (Integer) sessionAnnee;
                List<Object[]> results = descriptionSujetRepository.findAllWithSujetWithAnnee(anneeVar);
            
                for (Object[] row : results) {
                    // row[0] est le Sujet, row[1] est la DescriptionSujet (selon votre SELECT ds.sujet, ds)
                    DescriptionSujet ds = (DescriptionSujet) row[1]; 
                    if (ds != null) {
                        allData.add(prepareDataMap(ds));
                    }
                }
            }

            // Appel au service (utilise le chemin String vers le template)
            byte[] docBytes = exportService.generateAllWordFromDb("/templates/modele_export.docx", allData);
        
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Catalogue_Sujets.docx\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(docBytes);

        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    private Map<String, String> prepareDataMap(DescriptionSujet ds) {
        Map<String, String> data = new HashMap<>();
        Sujet s = ds.getSujet();
        Entreprise e = ds.getNomEntreprise();
        Correspondant c = (s != null) ? s.getIdCorrespondant() : null;

        data.put("titre", (s != null) ? s.getIntitule() : "");
        data.put("confidentiel", ds.getConfidentiel() != null ? ds.getConfidentiel() : "");

        data.put("contexte", ds.getContexte() != null ? ds.getContexte() : "");
        data.put("travail", ds.getTravailRealiser() != null ? ds.getTravailRealiser() : "");
        data.put("livrable", ds.getLivrable() != null ? ds.getLivrable() : "");
        data.put("annee", (s != null && s.getAnnee() != null) ? String.valueOf(s.getAnnee().getAnnee()) : "");
    
        if (e != null) {
            data.put("entreprise", e.getNom() != null ? e.getNom() : "");
            data.put("description", e.getDescription() != null ? e.getDescription() : "");
            data.put("adresse", e.getAdresse() != null ? e.getAdresse() : "");
            data.put("code_postal", e.getCodePostal() != null ? String.valueOf(e.getCodePostal()) : "");
            data.put("telentre", e.getTel() != null ? String.valueOf(e.getTel()) : "");
        }

        if (c != null) {
            data.put("nom", c.getNom());
            data.put("prenom", c.getPrenom());
            data.put("telephone", c.getTel() != null ? String.valueOf(c.getTel()) : "");
            data.put("email", c.getEmail());
        }
        return data;
    }
    
    @RequestMapping(value="Exportmodelevide.do", method=RequestMethod.POST)
    public ResponseEntity<byte[]> handleexportmodel(HttpServletRequest request) throws FileNotFoundException, IOException {
        Map<String, String> data = new HashMap<>();
        InputStream is = getClass().getResourceAsStream("/templates/mon_modele.docx");
        if (is == null) throw new FileNotFoundException("Modèle Word introuvable");
        byte[] docBytes = exportService.generateWordFromDb(is, data);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"Modele_sujet.docx\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(docBytes);
    }

}
