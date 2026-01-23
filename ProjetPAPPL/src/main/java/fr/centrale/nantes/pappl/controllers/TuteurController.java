/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import fr.centrale.nantes.pappl.items.Annee;
import fr.centrale.nantes.pappl.items.Eleve;
import fr.centrale.nantes.pappl.items.Entreprise;
import fr.centrale.nantes.pappl.items.Tuteur;
import fr.centrale.nantes.pappl.repositories.AnneeRepository;
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

    @Autowired
    private AnneeRepository anneeRepository;

    @RequestMapping(value = "tuteurs.do", method = RequestMethod.POST)
    public ModelAndView handlePostUserstuteur(HttpServletRequest request) {

        Object sessionAnnee = request.getSession().getAttribute("anneeCourante");
        ModelAndView returned = new ModelAndView("liste_tuteurs");

        if (sessionAnnee == null) {
            List<Tuteur> listTuteur = new ArrayList<>(tuteurRepository.findAll());
            returned.addObject("tuteurList", listTuteur);
            Collections.sort(listTuteur, Tuteur.getComparator());

        } else {
            int var = (Integer) sessionAnnee;
            List<Tuteur> listTuteur = new ArrayList<>(tuteurRepository.findAllWithAnnee(var));
            returned.addObject("tuteurList", listTuteur);
            Collections.sort(listTuteur, Tuteur.getComparator());

        }
        return returned;
    }

    @RequestMapping(value = "savetuteur.do", method = RequestMethod.POST)
    public ModelAndView handlePostSaveTuteur(HttpServletRequest request) {
        String idStr = request.getParameter("id");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String nbsujetStr = request.getParameter("nbsujet");
        String anneestr = request.getParameter("annee");

        int nbsuj = Tools.getIntFromString(nbsujetStr);
        int id = Tools.getIntFromString(idStr);
        int anneeint = Tools.getIntFromString(anneestr);
        Annee annee = null;
        if (anneestr != null && !anneestr.isEmpty()) {
            try {
                annee = anneeRepository.getReferenceById(anneeint);
            } catch (Exception ex) {
                // L'entreprise n'existe pas
            }
        }
        if (id == -1) {
            tuteurRepository.create(nom, prenom, nbsuj, annee);
        } else {
            Tuteur tuteurExistants = tuteurRepository.getReferenceById(id);
            tuteurRepository.update(tuteurExistants, nom, prenom, nbsuj, annee);
        }
        ModelAndView returned = new ModelAndView("liste_tuteurs");
        List<Tuteur> listTuteur = new ArrayList<>(tuteurRepository.findAll());
        Collections.sort(listTuteur, Tuteur.getComparator());
        returned.addObject("tuteurList", listTuteur);
        return returned;
    }

    @RequestMapping(value = "ajouttuteur.do", method = RequestMethod.POST)
    public ModelAndView HandlePostcreateTuteur() {
        ModelAndView returned;
        Tuteur nouveau = new Tuteur();
        returned = new ModelAndView("ajout-tuteur");
        returned.addObject("ajout-tuteur", nouveau);
        return returned;
    }

    @RequestMapping(value = "edittuteur.do", method = RequestMethod.POST)
    public ModelAndView handleEditPosttuteur(HttpServletRequest request) {
        ModelAndView returned;

        String idstr = request.getParameter("id");
        int id = Tools.getIntFromString(idstr);
        if (id != -1) {
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

    @RequestMapping(value = "reutilisertuteur.do", method = RequestMethod.POST)
    public ModelAndView handleReuseTuteur(HttpServletRequest request) {
        String idst = request.getParameter("id");
        int id = Tools.getIntFromString(idst);
        if (id != -1) {
            Tuteur tuteur = tuteurRepository.getReferenceById(id);
            Annee annee = new Annee();
            tuteurRepository.create(tuteur.getNom(), tuteur.getPrenom(), tuteur.getNbSujetSouhaite(), annee);
        }
        List<Tuteur> listTuteur = new ArrayList<>(tuteurRepository.findAll());
        ModelAndView returned = new ModelAndView("liste_tuteurs");
        Collections.sort(listTuteur, Tuteur.getComparator());
        returned.addObject("tuteurList", listTuteur);
        return returned;
    }
}
