/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;
import fr.centrale.nantes.pappl.items.Correspondant;
import fr.centrale.nantes.pappl.items.Entreprise;
import fr.centrale.nantes.pappl.repositories.CorrespondantRepository;
import fr.centrale.nantes.pappl.repositories.EntrepriseRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Safae
 */
@Controller
public class CorrespondantController {
    
    @Autowired
    private CorrespondantRepository correspondantRepository;
    
    @Autowired
    private EntrepriseRepository entrepriseRepository;
    
    @RequestMapping(value="savecorrespondant.do", method=RequestMethod.POST)
    public ModelAndView handlePostSaveCorrespondant(HttpServletRequest request) {
        // Récupération des paramètres
        String idStr = request.getParameter("id");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String telStr = request.getParameter("tel");
        String email = request.getParameter("email");
        String entrepriseNom = request.getParameter("entreprise");
        
        int id = Tools.getIntFromString(idStr);
        int tel = Tools.getIntFromString(telStr);
        
        Entreprise entreprise = null;
        if (entrepriseNom != null && !entrepriseNom.isEmpty()) {
            try {
                entreprise = entrepriseRepository.getReferenceById(entrepriseNom);
            } catch(Exception ex) {
                // L'entreprise n'existe pas
            }
        }
        
        if (id != -1) {
            // Mise à jour d'un correspondant existant
            try {
                Correspondant correspondant = correspondantRepository.getReferenceById(id);
                correspondantRepository.update(correspondant, nom, prenom, tel, email, entreprise);
            } catch(Exception ex) {
                // Le correspondant n'existe pas, on le crée
                correspondantRepository.create(nom, prenom, tel, email, entreprise);
            }
        } else {
            // Création d'un nouveau correspondant
            correspondantRepository.create(nom, prenom, tel, email, entreprise);
        }
        
        ModelAndView returned = new ModelAndView("correspondant");
        returned.addObject("correspondantList", correspondantRepository.findAll());
        return returned;
    }
    
    @RequestMapping(value="ajoutcorrespondant.do", method=RequestMethod.POST)
    public ModelAndView handlePostCreateCorrespondant() {
        ModelAndView returned;
        Correspondant nouveau = new Correspondant();
        returned = new ModelAndView("ajout-correspondant");
        returned.addObject("correspondant", nouveau);
        // Ajouter la liste des entreprises pour le dropdown
        returned.addObject("entrepriseList", entrepriseRepository.findAll());
        return returned;
    }
    
    @RequestMapping(value="correspondant.do", method=RequestMethod.POST)
    public ModelAndView handlePostCorrespondants(HttpServletRequest request) {
        List<Correspondant> listCorrespondant = new ArrayList<Correspondant>(correspondantRepository.findAll());
        ModelAndView returned = new ModelAndView("correspondant");
        returned.addObject("correspondantList", listCorrespondant);
        return returned;
    }
    
    @RequestMapping(value="editcorrespondant.do", method=RequestMethod.POST)
    public ModelAndView handleEditPost(HttpServletRequest request) {
        ModelAndView returned;
        
        String idStr = request.getParameter("id");
        int id = Tools.getIntFromString(idStr);
        
        if (id != -1) {
            try {
                Correspondant correspondant = correspondantRepository.getReferenceById(id);
                returned = new ModelAndView("ajout-correspondant");
                returned.addObject("correspondant", correspondant);
                returned.addObject("entrepriseList", entrepriseRepository.findAll());
            } catch(Exception ex) {
                // Si le correspondant n'existe pas, rediriger vers la liste
                List<Correspondant> listCorrespondant = new ArrayList<Correspondant>(correspondantRepository.findAll());
                returned = new ModelAndView("correspondant");
                returned.addObject("correspondantList", listCorrespondant);
            }
        } else {
            // Aucun ID fourni, créer un nouveau correspondant
            returned = new ModelAndView("ajout-correspondant");
            returned.addObject("correspondant", new Correspondant());
            returned.addObject("entrepriseList", entrepriseRepository.findAll());
        }
        
        return returned;
    }
    
    @RequestMapping(value="deletecorrespondant.do", method=RequestMethod.POST)
    public ModelAndView handlePostDeleteCorrespondant(HttpServletRequest request) {
        ModelAndView returned;
        
        String idStr = request.getParameter("id");
        int id = Tools.getIntFromString(idStr);
        
        if (id != -1) {
            try {
                Correspondant correspondant = correspondantRepository.getReferenceById(id);
                correspondantRepository.remove(correspondant);
            } catch(Exception ex) {
                // Correspondant introuvable
            }
        }
        
        // Retour à la liste
        List<Correspondant> listCorrespondant = new ArrayList<Correspondant>(correspondantRepository.findAll());
        returned = new ModelAndView("correspondant");
        returned.addObject("correspondantList", listCorrespondant);
        
        return returned;
    }
}
