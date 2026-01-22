/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import fr.centrale.nantes.pappl.items.Eleve;
import fr.centrale.nantes.pappl.items.Sujet;
import fr.centrale.nantes.pappl.repositories.EleveRepository;
import fr.centrale.nantes.pappl.repositories.SujetRepository;
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
 * @author vadimdigonnet
 */
@Controller
public class EleveController {
    @Autowired
    private EleveRepository eleveRepository;
    private SujetRepository sujetRepository;
    @RequestMapping(value="saveeleve.do", method=RequestMethod.POST)
public ModelAndView handlePostSaveEntreprise(HttpServletRequest request) {
    // Récupération des paramètres
    String eleveId = request.getParameter("id");
    String nom = request.getParameter("nom");
    String prenom = request.getParameter("prenom");
    String nationalite = request.getParameter("nationalite");
    String email = request.getParameter("email");
    String td = request.getParameter("td");
    
   // 1. On récupère le NUMERO tapé dans le formulaire 
    String numeroSujetSaisi1 = request.getParameter("numero_sujet");
    int numeroSujetSaisi = Tools.getIntFromString(numeroSujetSaisi1);
    
    // 2. On cherche le vrai objet Sujet en base
    Sujet leSujetAssocie = null;
    if (numeroSujetSaisi1 != null && !numeroSujetSaisi1.isEmpty()) {
        leSujetAssocie = sujetRepository.getReferenceById(numeroSujetSaisi);
        
        // Petit log pour déboguer si ça ne marche pas
        if (leSujetAssocie == null) {
            System.out.println("ERREUR : Aucun sujet trouvé avec le numéro " + numeroSujetSaisi);
        }
    }

    // Gestion de l'Eleve
    int id = Tools.getIntFromString(eleveId);
    
    if (id > 0) {
        // C'est une mise à jour : on récupère l'élève existant
        Eleve eleve = eleveRepository.getReferenceById(id);
        eleveRepository.update(eleve, nom, prenom, nationalite, email, td, leSujetAssocie);
    } else {
        // C'est une création : on n'appelle PAS getReferenceById
        eleveRepository.create(nom, prenom, nationalite, email, td, leSujetAssocie);
    }

    // Retour à la liste
    List<Eleve> myList = new ArrayList<Eleve>(eleveRepository.findAll());
    Collections.sort(myList, Eleve.getComparator());
    ModelAndView returned = new ModelAndView("liste_eleve");
    returned.addObject("eleveList", myList);

    return returned;
}
    
    @RequestMapping(value="ajouteleve.do",method=RequestMethod.POST)
    public ModelAndView HandlePostcreateeleve(){
        ModelAndView returned;
        Eleve nouveau=new Eleve();
        returned=new ModelAndView("ajout_eleve");
        returned.addObject("eleveList",nouveau);
        List<Eleve> listEntreprise =new ArrayList<Eleve>( eleveRepository.findAll());
        Collections.sort(listEntreprise, Eleve.getComparator());
        returned.addObject("eleveList",listEntreprise);
        return returned;
    }
    
    @RequestMapping(value="eleve.do",method=RequestMethod.POST)
    public ModelAndView handlePostUsers(HttpServletRequest request){
        List<Eleve> listEntreprise =new ArrayList<Eleve>( eleveRepository.findAll());
        Collections.sort(listEntreprise, Eleve.getComparator());
        ModelAndView returned =new ModelAndView("liste_eleve");
        returned.addObject("eleveList",listEntreprise);
        return returned;
    }
    
    @RequestMapping(value = "editeleve.do", method = RequestMethod.POST)
    public ModelAndView handleEditPost(HttpServletRequest request) {
        ModelAndView returned;

        String idStr = request.getParameter("id");
        int id = Tools.getIntFromString(idStr);

        if (id > 0) {
            Eleve eleve = eleveRepository.getReferenceById(id);
            returned = new ModelAndView("ajout_eleve");
            returned.addObject("eleve", eleve);
        } else {
            List<Eleve> myList = new ArrayList<Eleve>(eleveRepository.findAll());
            Collections.sort(myList, Eleve.getComparator());
            returned = new ModelAndView("ajout_eleve");
            returned.addObject("eleve", myList);
        }
        List<Eleve> listEntreprise =new ArrayList<Eleve>( eleveRepository.findAll());
        Collections.sort(listEntreprise, Eleve.getComparator());
        returned.addObject("eleveList",listEntreprise);
        return returned;
    }
    
    @RequestMapping(value = "deleteeleve.do", method = RequestMethod.POST)
    public ModelAndView handlePostDeleteUser(HttpServletRequest request) {
        ModelAndView returned;

        String idStr = request.getParameter("id");
        int id = Tools.getIntFromString(idStr);
        if (id > 0) {
            Eleve eleveId = eleveRepository.getReferenceById(id);
            eleveRepository.remove(eleveId);
        }
        
        // return to list
        List<Eleve> listEleve = new ArrayList<Eleve>(eleveRepository.findAll());
        Collections.sort(listEleve, Eleve.getComparator());
        returned = new ModelAndView("liste_eleve");
        returned.addObject("eleveList", listEleve);
        List<Eleve> listEntreprise =new ArrayList<Eleve>( eleveRepository.findAll());
        Collections.sort(listEntreprise, Eleve.getComparator());
        returned.addObject("eleveList",listEntreprise);
        return returned;
    }
}