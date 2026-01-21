/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import fr.centrale.nantes.pappl.items.DescriptionSujet;
import fr.centrale.nantes.pappl.items.Entreprise;
import fr.centrale.nantes.pappl.repositories.*;
import fr.centrale.nantes.pappl.repositories.EntrepriseRepository;
import fr.centrale.nantes.pappl.items.Sujet;
import fr.centrale.nantes.pappl.repositories.DescriptionSujetRepository;
import fr.centrale.nantes.pappl.repositories.SujetRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class MainController {
    @Autowired
    private SujetRepository sujetRepository;
    
    @Autowired
    private DescriptionSujetRepository DescriptionSujetRepository;
    
    @Autowired
    private EntrepriseRepository entrepriseRepository;
    
    
    private int getIntFromString(String value){
        int intValue=-1;
        try{
            intValue = Integer.parseInt(value);
        }catch(NumberFormatException ex){
            Logger.getLogger(MainController.class.getName()).log(Level.WARNING,null,ex);
        }
        return intValue;
    }
    
    @RequestMapping(value="savesujet.do", method=RequestMethod.POST)
    public ModelAndView handlePostSaveSujet(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        String contexte = request.getParameter("contexte");
        String travail = request.getParameter("travail");
        String livrable = request.getParameter("livrable");
        String intitule = request.getParameter("intitule");
    
        String entrepriseNom = request.getParameter("entreprise"); 
        int id = Tools.getIntFromString(idStr);
        
        Entreprise entreprise = null;
        if (entrepriseNom != null && !entrepriseNom.isEmpty()) {
            try {
                entreprise = entrepriseRepository.getReferenceById(entrepriseNom);
            } catch(Exception ex) {
                // L'entreprise n'existe pas
            }
        }

        if (id == -1) {
            Sujet sujet = new Sujet();
            sujet = sujetRepository.create(intitule);
            id = sujet.getNumero();
            
            DescriptionSujet item = DescriptionSujetRepository.create(id ,contexte, travail, livrable, entreprise);
            //id = item.getNumero();
        } else {
            DescriptionSujet dSujet = DescriptionSujetRepository.getReferenceById(id);
            DescriptionSujetRepository.update(dSujet, contexte, travail, livrable, entreprise);
        }
        ModelAndView returned = new ModelAndView("main");
        returned.addObject("main", DescriptionSujetRepository.findAll());

        return returned;
    }
    
    @RequestMapping(value="createsujet.do",method=RequestMethod.POST)
    public ModelAndView HandlePostcreatesujet(){
        ModelAndView returned;
        DescriptionSujet nouveau=new DescriptionSujet();
        returned=new ModelAndView("ajout-sujet");
        returned.addObject("ajout-sujet",nouveau);
        // Ajouter la liste des entreprises pour le dropdown
        returned.addObject("entrepriseList", entrepriseRepository.findAll());
        return returned;
    }
    
    @RequestMapping(value="main.do")
    public ModelAndView handlePostMain() {
    ModelAndView returned = new ModelAndView("main");
    return returned;
}
    
        @RequestMapping(value="sujet.do", method=RequestMethod.POST)
    public ModelAndView handlePostSujets(HttpServletRequest request) {
        List<DescriptionSujet> listSujet = new ArrayList<DescriptionSujet>(DescriptionSujetRepository.findAll());
        ModelAndView returned = new ModelAndView("liste_sujet");
        returned.addObject("sujetList", listSujet);
        return returned;
    }
}

