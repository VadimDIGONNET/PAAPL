/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import fr.centrale.nantes.pappl.items.Annee;
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
    
    @RequestMapping(value="ImportProjetServlet.do", method = RequestMethod.POST)
    public ModelAndView HandleFileUpload(HttpServletRequest request) throws IOException, ServletException{
        
        Part filePart = request.getPart("fichierWord");

        // On utilise une Map pour stocker tout ce qu'on trouve
        Map<String, String> data = new HashMap<>();

        try (InputStream is = filePart.getInputStream();
             XWPFDocument document = new XWPFDocument(is)) {

            // --- 1. SCAN DU DOCUMENT (Récursif) ---
            // Cette méthode va chercher partout : racine, tableaux, paragraphes...
            scanDocument(document.getBodyElements(), data);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // --- 2. RÉCUPÉRATION DES VARIABLES DEPUIS LA MAP ---
        // Attention : Les clés ("entreprise", "titre"...) doivent être IDENTIQUES 
        // aux Tags dans les propriétés Word (majuscules/minuscules comprises).
        
        String entreprisestr = data.getOrDefault("entreprise", "");
        String titre = data.getOrDefault("titre", "");
        String contexte = data.getOrDefault("contexte", "");
        String travail = data.getOrDefault("travail", "");
        String livrable = data.getOrDefault("livrable", "");
        String nom = data.getOrDefault("nom", "");
        String prenom = data.getOrDefault("prenom", "");
        String tel = data.getOrDefault("telephone", "");
        String mail = data.getOrDefault("email", "");
        String anneestr = data.getOrDefault("annee", "");
        String description = data.getOrDefault("description", "");

        // Debug console
        System.out.println("=== DONNÉES EXTRAITES ===");
        System.out.println("Map complète : " + data);

        // --- 3. LOGIQUE BASE DE DONNÉES ---

        // A. Gestion de l'année
        int anneeint = Tools.getIntFromString(anneestr);
        Annee annee = null;
        if (anneeint > 0) {
            try {
                // Utilisation de findById si possible, ou gestion d'erreur avec getReference
                 annee = anneeRepository.getReferenceById(anneeint);
            } catch(Exception ex) {
                System.out.println("Année introuvable en base : " + anneeint);
            }
        }

        // B. Gestion de l'Entreprise
        Entreprise entreprise = null;
        if (entreprisestr != null && !entreprisestr.isEmpty()) {
            try {
                entreprise = entrepriseRepository.getReferenceById(entreprisestr);
            } catch(Exception ex) {
                // Pas trouvée
            }
            
            // Si elle n'existe pas, ON LA CRÉE
            if (entreprise == null) {
                System.out.println("Création de l'entreprise : " + entreprisestr);
                try {
                    // On n'oublie pas le "entreprise =" pour récupérer l'objet créé
                    entreprise = entrepriseRepository.create(entreprisestr, null, null, null, null, description);
                } catch (Exception e) {
                    System.err.println("Erreur création entreprise : " + e.getMessage());
                }
            }
        }
        
        // C. Création du Sujet
        if (titre != null && !titre.isEmpty() && annee != null) {
            try {
                Sujet sujet = sujetRepository.create(null, titre, annee);
                
                if (sujet != null) {
                    int id = sujet.getNumero();
                    descriptionSujetRepository.create(id, contexte, travail, livrable, entreprise);
                    System.out.println(">>> SUCCÈS : Sujet " + id + " enregistré.");
                } else {
                    System.err.println("ERREUR : sujetRepository.create a renvoyé null.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.err.println("ECHEC IMPORT : Titre vide ou Année ("+anneeint+") introuvable.");
        }
        if (nom != null && !nom.isEmpty() && entreprise != null) {
            System.out.println("Enregistrement du correspondant : " + nom);
            try {
                // 1. On essaie de voir s'il existe déjà (pour éviter les doublons)
                // (Adaptez cette ligne selon les méthodes disponibles dans votre Repository)
                // Ex: findByNomAndEntreprise(nom, entreprise) ou juste create
                
                // 2. Création directe (Adaptez les arguments selon votre méthode create)
                int tele = Tools.getIntFromString(tel);
                correspondantRepository.create(nom, prenom, tele, mail, entreprise);
                
                System.out.println(">>> Correspondant enregistré avec succès.");
                
            } catch (Exception e) {
                System.err.println("Erreur enregistrement Correspondant : " + e.getMessage());
            }
        }
        
        List<Object> listSujet = new ArrayList<Object>(descriptionSujetRepository.findAllWithSujet());

        ModelAndView returned = new ModelAndView("liste_sujet");
        returned.addObject("sujetList", listSujet);
        return returned;
    }

    // =========================================================================
    // LA MÉTHODE MAGIQUE (À placer à l'intérieur de la classe)
    // =========================================================================
    private void scanDocument(List<IBodyElement> elements, Map<String, String> data) {
        for (IBodyElement element : elements) {
            
            // CAS 1 : C'est une grosse zone de texte (SDT)
            if (element instanceof XWPFSDT) {
                extractDataFromSDT((XWPFSDT) element, data);
            } 
            
            // CAS 2 : C'est un Tableau -> On doit descendre dedans
            else if (element instanceof XWPFTable) {
                XWPFTable table = (XWPFTable) element;
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        // Récursivité : on scanne le contenu de la cellule
                        scanDocument(cell.getBodyElements(), data);
                    }
                }
            }
            
            // CAS 3 : C'est un Paragraphe -> On cherche les petites zones (Inline SDT)
            // C'est ce qui manquait pour "Entreprise" et "Année"
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

    // Petite méthode utilitaire pour éviter de répéter le code d'extraction
    private void extractDataFromSDT(XWPFSDT controle, Map<String, String> data) {
        String tag = controle.getTag();
        if (tag != null && !tag.isEmpty()) {
            String value = controle.getContent().getText().trim();
            // On ajoute dans la Map (ex: clé="entreprise", valeur="Total")
            data.put(tag, value);
        }
    }
}