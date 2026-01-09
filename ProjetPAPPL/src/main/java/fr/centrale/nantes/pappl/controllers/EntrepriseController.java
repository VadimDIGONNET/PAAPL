/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import fr.centrale.nantes.pappl.items.DescriptionSujet;
import fr.centrale.nantes.pappl.items.Entreprise;
import fr.centrale.nantes.pappl.items.Sujet;
import fr.centrale.nantes.pappl.repositories.EntrepriseRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author agach
 */
@Controller
public class EntrepriseController {
    @Autowired
    private EntrepriseRepository entrepriseRepository;
    @RequestMapping(value="saveentreprise.do", method=RequestMethod.POST)
    public ModelAndView handlePostSaveEntreprise(HttpServletRequest request) {
        // Récupération des paramètres
        String nom = request.getParameter("nom");
        String adresse = request.getParameter("adresse");
        String codePostalStr = request.getParameter("codePostal");
        String telStr = request.getParameter("tel");
        String siteWeb = request.getParameter("siteWeb");
        String description = request.getParameter("description");
        int codePostal = Tools.getIntFromString(codePostalStr);
        int tel = Tools.getIntFromString(telStr);
        entrepriseRepository.create(nom, adresse, codePostal, tel, siteWeb, description);
        ModelAndView returned = new ModelAndView("main");
        returned.addObject("entreprises", entrepriseRepository.findAll());
        return returned;
    }
    
    @RequestMapping(value="ajoutentreprise.do",method=RequestMethod.POST)
    public ModelAndView HandlePostcreateentreprise(){
        ModelAndView returned;
        Entreprise nouveau=new Entreprise();
        returned=new ModelAndView("ajout-entreprise");
        returned.addObject("ajout-entreprise",nouveau);
        return returned;
    }
    
    @RequestMapping(value="entreprise.do",method=RequestMethod.POST)
    public ModelAndView handlePostUsers(HttpServletRequest request){
        List<Entreprise> listEntreprise =new ArrayList<Entreprise>( entrepriseRepository.findAll());
        ModelAndView returned =new ModelAndView("entreprise");
        returned.addObject("entrepriseList",listEntreprise);
        return returned;
    }
    
    @RequestMapping(value = "editentreprise.do", method = RequestMethod.POST)
    public ModelAndView handleEditPost(HttpServletRequest request) {
        ModelAndView returned;

        String nom = request.getParameter("nom");

        if (nom!=null) {
            Optional<Entreprise> entreprise = entrepriseRepository.findById(nom);
            returned = new ModelAndView("entreprise");
            returned.addObject("entreprise", entreprise.get());
        } else {
            List<Entreprise> listEntreprise = new ArrayList<Entreprise>(entrepriseRepository.findAll());
            returned = new ModelAndView("entreprise");
            returned.addObject("entrepriseList", listEntreprise);
        }
        return returned;
    }
    
    @RequestMapping(value = "deleteentreprise.do", method = RequestMethod.POST)
    public ModelAndView handlePostDeleteUser(HttpServletRequest request) {
        ModelAndView returned;

        String name = request.getParameter("nom");
        if (name != null) {
            Entreprise nom = entrepriseRepository.getReferenceById(name);
            entrepriseRepository.remove(nom);
        }
        
        // return to list
        List<Entreprise> listEntreprise = new ArrayList<Entreprise>(entrepriseRepository.findAll());
        Collections.sort(listEntreprise, Entreprise.getComparator());
        returned = new ModelAndView("entreprise");
        returned.addObject("entrepriseList", listEntreprise);

        return returned;
    }
}