/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import fr.centrale.nantes.pappl.items.Annee;
import fr.centrale.nantes.pappl.items.Correspondant;
import fr.centrale.nantes.pappl.items.Entreprise;
import fr.centrale.nantes.pappl.items.Sujet;
import fr.centrale.nantes.pappl.repositories.AnneeRepository;
import fr.centrale.nantes.pappl.repositories.CorrespondantRepository;
import fr.centrale.nantes.pappl.repositories.DescriptionSujetRepository;
import fr.centrale.nantes.pappl.repositories.EntrepriseRepository;
import fr.centrale.nantes.pappl.repositories.SujetRepository;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFSDT;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.xwpf.usermodel.IRunElement;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@MultipartConfig
public class ImportController extends HttpServlet {
    
    @Autowired
    private EntrepriseRepository entrepriseRepository;
    
    @Autowired
    private SujetRepository sujetRepository;
    
    @Autowired
    private DescriptionSujetRepository descriptionSujetRepository;
    
    @Autowired 
    private AnneeRepository anneeRepository;
    
    @Autowired
    private CorrespondantRepository correspondantRepository;
    
    @RequestMapping(value="/ImportProjetServlet.do", method = RequestMethod.POST)
    public ModelAndView handleFileUpload(
        @RequestParam("file") MultipartFile file, 
        HttpServletRequest request) {

        Map<String, String> data = new HashMap<>();

        try (InputStream is = file.getInputStream();
             XWPFDocument document = new XWPFDocument(is)) {

            // Ton code de scan Word
            scanDocument(document.getBodyElements(), data);

        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
            e.printStackTrace();
        }
        
        String entreprisestr = data.getOrDefault("entreprise", "");
        String titre = data.getOrDefault("titre", "");
        String contexte = data.getOrDefault("contexte", "");
        String travail = data.getOrDefault("travail", "");
        String livrable = data.getOrDefault("livrable", "");
        String confidentiel=data.getOrDefault("confidentiel","");
        String nom = data.getOrDefault("nom", "");
        String prenom = data.getOrDefault("prenom", "");
        String tel = data.getOrDefault("telephone", "");
        String mail = data.getOrDefault("email", "");
        String anneestr = data.getOrDefault("annee", "");
        String description = data.getOrDefault("description", "");
        String telEntre =data.getOrDefault("telEntreprise","");
        String codePostal = data.getOrDefault("codePostal","");
        String adresse =data.getOrDefault("adresse", "");

        System.out.println("=== DONNÉES EXTRAITES ===");
        System.out.println("Map complète : " + data);
        int codePostalint = Tools.getIntFromString(codePostal);
        int telEntreint = Tools.getIntFromString(telEntre);
        int anneeint = Tools.getIntFromString(anneestr);
        Annee annee = null;
        if (anneeint > 0) {
            try {
                 annee = anneeRepository.getReferenceById(anneeint);
            } catch(Exception ex) {
                System.out.println("Année introuvable en base : " + anneeint);
            }
        }

        Entreprise entreprise = null;
        if (entreprisestr != null && !entreprisestr.isEmpty()) {
            try {
                entreprise = entrepriseRepository.getReferenceById(entreprisestr);
            } catch(Exception ex) {
            }
            
            if (entreprise == null) {
                System.out.println("Création de l'entreprise : " + entreprisestr);
                try {
                    entreprise = entrepriseRepository.create(entreprisestr, adresse, codePostalint, telEntreint, null, description);
                } catch (Exception e) {
                    System.err.println("Erreur création entreprise : " + e.getMessage());
                }
            }
        }
        Correspondant correspondant = null;
            if (nom != null && !nom.isEmpty() && entreprise != null) {
                try {
                    int tele = Tools.getIntFromString(tel);
                    correspondant = correspondantRepository.create(nom, prenom, tele, mail, entreprise);
                     System.out.println(">>> Correspondant enregistré avec succès.");
                } catch (Exception e) {
                    System.err.println("Erreur enregistrement Correspondant : " + e.getMessage());
                }
                if (titre != null && !titre.isEmpty() && annee != null) {
                    try {
                        Sujet sujet = sujetRepository.create(null, titre, annee,correspondant);
                        if (sujet != null) {
                            if (correspondant != null) {
                            sujet.setIdCorrespondant(correspondant);
                            sujetRepository.save(sujet);
                            }
                        int id = sujet.getNumero();
                        descriptionSujetRepository.create(id, contexte, travail, livrable, entreprise, confidentiel);
                        System.out.println(">>> SUCCÈS : Sujet " + id + " enregistré avec correspondant lié.");
                        }
                    } catch (Exception e) {
                    e.printStackTrace();
                } 
            }
        }
        
        List<Object> listSujet = new ArrayList<Object>(descriptionSujetRepository.findAllWithSujet());

        ModelAndView returned = new ModelAndView("liste_sujet");
        returned.addObject("sujetList", listSujet);
        return returned;
    }


    private void scanDocument(List<IBodyElement> elements, Map<String, String> data) {
        for (IBodyElement element : elements) {
            
            if (element instanceof XWPFSDT) {
                extractDataFromSDT((XWPFSDT) element, data);
            } 
            
            else if (element instanceof XWPFTable) {
                XWPFTable table = (XWPFTable) element;
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        scanDocument(cell.getBodyElements(), data);
                    }
                }
            }
            

            else if (element instanceof XWPFParagraph) {
                XWPFParagraph p = (XWPFParagraph) element;
                for (IRunElement run : p.getIRuns()) {
                    if (run instanceof XWPFSDT) {
                        extractDataFromSDT((XWPFSDT) run, data);
                    }
                }
            }
        }
    }

    private void extractDataFromSDT(XWPFSDT controle, Map<String, String> data) {
        String tag = controle.getTag();
        if (tag != null && !tag.isEmpty()) {
            String value = controle.getContent().getText().trim();
            data.put(tag, value);
        }
    }
}