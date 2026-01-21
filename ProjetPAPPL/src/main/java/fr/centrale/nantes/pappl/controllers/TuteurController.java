/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import fr.centrale.nantes.pappl.items.Entreprise;
import fr.centrale.nantes.pappl.items.Tuteur;
import fr.centrale.nantes.pappl.repositories.TuteurRepository;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
public class TuteurController {
    @Autowired
    private TuteurRepository tuteurRepository;
    
    @RequestMapping(value="tuteurs.do", method=RequestMethod.POST)
    public ModelAndView handlePostUserstuteur(HttpServletRequest request) {
        List<Tuteur> listTuteur = new ArrayList<>(tuteurRepository.findAll());
        Collections.sort(listTuteur, Tuteur.getComparator()); 
        ModelAndView returned = new ModelAndView("liste_tuteurs");
        returned.addObject("tuteurList", listTuteur);
        return returned;
    }
    
    @RequestMapping(value="savetuteur.do", method=RequestMethod.POST)
public ModelAndView handlePostSaveTuteur(HttpServletRequest request) {
    String idStr = request.getParameter("id");
    String nom = request.getParameter("nom"); 
    String prenom = request.getParameter("prenom"); 
    String nbsujetStr = request.getParameter("nbsujet"); 
    
    int nbsuj = Tools.getIntFromString(nbsujetStr);
    int id = Tools.getIntFromString(idStr);
    if (id == -1) {
        tuteurRepository.create(nom, prenom, nbsuj);
    } else {
        Tuteur tuteurExistants = tuteurRepository.getReferenceById(id);
        tuteurRepository.update(tuteurExistants, nom, prenom, nbsuj);
    }
    ModelAndView returned = new ModelAndView("liste_tuteurs");
    List<Tuteur> listTuteur = new ArrayList<>(tuteurRepository.findAll());
    Collections.sort(listTuteur, Tuteur.getComparator());
    returned.addObject("tuteurList", listTuteur);
    return returned;
}
    
    @RequestMapping(value="ajouttuteur.do",method=RequestMethod.POST)
    public ModelAndView HandlePostcreateTuteur(){
        ModelAndView returned;
        Tuteur nouveau=new Tuteur();
        returned=new ModelAndView("ajout-tuteur");
        returned.addObject("ajout-tuteur",nouveau);
        return returned;
    }
    
    @RequestMapping(value = "edittuteur.do", method = RequestMethod.POST)
    public ModelAndView handleEditPosttuteur(HttpServletRequest request) {
        ModelAndView returned;

        String idstr = request.getParameter("id");
        int id= Tools.getIntFromString(idstr);
        if (id!=-1) {
            Tuteur tuteur = tuteurRepository.getReferenceById(id);
            returned = new ModelAndView("ajout-tuteur");
            returned.addObject("tuteur", tuteur);
        } else {
            List<Tuteur> listTuteur = new ArrayList<Tuteur>(tuteurRepository.findAll());
            returned = new ModelAndView("ajout-tuteur");
            returned.addObject("tuteurlist", listTuteur);
        }
        List<Tuteur> listTuteur = new ArrayList<>(tuteurRepository.findAll());
        Collections.sort(listTuteur, Tuteur.getComparator());
        returned.addObject("tuteurList", listTuteur);
        return returned;
    }
    @RequestMapping(value = "deletetuteur.do", method = RequestMethod.POST)
    public ModelAndView handlePostDeletetuteur(HttpServletRequest request) {
        String idst = request.getParameter("id");
        int id = Tools.getIntFromString(idst);
    
        if (id != -1) {
            Tuteur tuteur = tuteurRepository.getReferenceById(id);
            tuteurRepository.remove(tuteur);
        }
    
        List<Tuteur> listTuteur = new ArrayList<>(tuteurRepository.findAll());
        ModelAndView returned = new ModelAndView("liste_tuteurs");
        Collections.sort(listTuteur, Tuteur.getComparator());
        returned.addObject("tuteurList", listTuteur); 
        return returned;
    }
}
