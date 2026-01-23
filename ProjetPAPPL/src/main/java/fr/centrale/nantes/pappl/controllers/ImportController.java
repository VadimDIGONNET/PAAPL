/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import fr.centrale.nantes.pappl.items.Annee;
import fr.centrale.nantes.pappl.items.Entreprise;
import fr.centrale.nantes.pappl.items.Sujet;
import fr.centrale.nantes.pappl.repositories.AnneeRepository;
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
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@MultipartConfig // Indispensable pour recevoir le fichier
public class ImportController extends HttpServlet {
    @Autowired
    private EntrepriseRepository entrepriseRepository;
    
    @Autowired
    private SujetRepository sujetRepository;
    
    @Autowired
    private DescriptionSujetRepository descriptionSujetRepository;
    
    @Autowired 
    private AnneeRepository anneeRepository;
    
    @RequestMapping(value="ImportProjetServlet.do", method = RequestMethod.POST)
    public ModelAndView HandleFileUpload(HttpServletRequest request) throws IOException, ServletException{
        
        Part filePart = request.getPart("fichierWord"); // Assurez-vous que <input name="fichierWord">

        

        String entreprisestr = "";
        String titre = "";
        String contexte = "";
        String travail = "";
        String livrable = "";
        String nom="";
        String prenom="";
        String tel="";
        String mail="";
        String anneestr="";
        String description="";

        try (InputStream is = filePart.getInputStream();
             XWPFDocument document = new XWPFDocument(is)) {

            // 2. Parcourir tous les éléments "racine" du document
            // (Un élément peut être un Paragraphe, un Tableau, ou un SDT/Contrôle de contenu)
            for (IBodyElement element : document.getBodyElements()) {
                
                // On s'intéresse uniquement aux contrôles de contenu (SDT)
                if (element instanceof XWPFSDT) {
                    XWPFSDT controle = (XWPFSDT) element;
                    
                    // On récupère le TAG que vous avez mis dans les propriétés Word
                    String tag = controle.getTag(); 

                    if (tag != null) {
                        // On nettoie le texte (supprime les espaces inutiles au début/fin)
                        String contenu = controle.getContent().getText().trim();

                        // 3. Ranger les données dans la bonne variable
                        switch (tag) {
                            case "entreprise":
                                entreprisestr = contenu;
                                break;
                            case "description":
                                description = contenu;
                                break;
                            case "titre":
                                titre = contenu;
                                break;
                            case "contexte":
                                contexte = contenu;
                                break;
                            case "travail":
                                travail = contenu;
                                break;
                            case "livrable":
                                livrable = contenu;
                                break;
                            case "nom":
                                nom=contenu;
                                break;
                            case "prenom":
                                prenom=contenu;
                                break;
                            case "telephone":
                                tel=contenu;
                                break;
                            case "email":
                                mail=contenu;
                                break;    
                            case "annee":
                                anneestr=contenu;
                                break;
                            default:
                                System.out.println("Tag inconnu trouvé : " + tag);
                                break;
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Gestion d'erreur (ex: format de fichier invalide)
        }

      
        System.out.println("=== RAPPORT D'IMPORTATION ===");
        System.out.println("Structure : " + entreprisestr);
        System.out.println("Titre : " + titre);
        System.out.println("Contexte (aperçu) : " + (contexte.length() > 50 ? contexte.substring(0, 50) + "..." : contexte));
        
        int anneeint=Tools.getIntFromString(anneestr);
        Annee annee = null;
        if (anneestr != null && !anneestr.isEmpty()) {
            try {
                annee = anneeRepository.getReferenceById(anneeint);
            } catch(Exception ex) {
                // L'entreprise n'existe pas
            }
        }
        Entreprise entreprise = null;
        if (entreprisestr != null && !entreprisestr.isEmpty()) {
            try {
                entreprise = entrepriseRepository.getReferenceById(entreprisestr);
            } catch(Exception ex) {
            }
        }
        if (entreprise==null){
            entrepriseRepository.create(entreprisestr,null,null,null,null,description);
        }
        
        Sujet sujet =sujetRepository.create(null,titre,annee);
        int id=sujet.getNumero();
        descriptionSujetRepository.create(id, contexte, travail, livrable, entreprise);
        
        List<Object> listSujet = new ArrayList<Object>(descriptionSujetRepository.findAllWithSujet());
        ModelAndView returned = new ModelAndView("liste_sujet");
        returned.addObject("sujetList", listSujet);
        return returned;
    }
}