/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import fr.centrale.nantes.pappl.items.Annee;
import fr.centrale.nantes.pappl.items.Correspondant;
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

    @Autowired
    private AnneeRepository anneeRepository;
    
    @Autowired
    private CorrespondantRepository correspondantRepository;

    private int getIntFromString(String value) {
        int intValue = -1;
        try {
            intValue = Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.WARNING, null, ex);
        }
        return intValue;
    }

    @RequestMapping(value = "setAnnee.do", method = RequestMethod.POST)
    public ModelAndView setAnnee(HttpServletRequest request) {
        int anneeChoisie = Integer.parseInt(request.getParameter("annee")); // déjà int
        request.getSession().setAttribute("anneeCourante", anneeChoisie);
        return new ModelAndView("redirect:main.do");
    }

    @RequestMapping(value = "savesujet.do", method = RequestMethod.POST)
    public ModelAndView handlePostSaveSujet(HttpServletRequest request) {

        String idStr = request.getParameter("id");
        String numero_sujet = request.getParameter("numeroSujet");
        String contexte = request.getParameter("contexte");
        String travail = request.getParameter("travail");
        String livrable = request.getParameter("livrable");
        String intitule = request.getParameter("intitule");
        String anneestr = request.getParameter("annee");
        String confidentiel=request.getParameter("confidentiel");
        String corresnom= request.getParameter("correspondant");

        String entrepriseNom = request.getParameter("entreprise");
        int numeroSujet = Tools.getIntFromString(numero_sujet);
        int id = Tools.getIntFromString(idStr);
        int anneeint = Tools.getIntFromString(anneestr);
        Entreprise entreprise = null;
        Correspondant correspondant = null;
        if (entrepriseNom != null && !entrepriseNom.isEmpty()) {
            try {
                entreprise = entrepriseRepository.getReferenceById(entrepriseNom);
            } catch (Exception ex) {
                // L'entreprise n'existe pas
            }
        }
        if (corresnom != null && !corresnom.isEmpty()) {
            try {         
                correspondant = correspondantRepository.findByNom(corresnom);
            } catch (Exception ex) {
                // L'entreprise n'existe pas
            }
        }
        Annee annee = null;
        if (anneestr != null && !anneestr.isEmpty()) {
            try {
                annee = anneeRepository.getReferenceById(anneeint);
            } catch (Exception ex) {
                // L'entreprise n'existe pas
            }
        }
        if ((idStr == null) || (id == -1)) {
            Sujet sujet = new Sujet();
            sujet = sujetRepository.create(numeroSujet, intitule, annee,correspondant);
            id = sujet.getNumero();

            DescriptionSujet item = DescriptionSujetRepository.create(id, contexte, travail, livrable, entreprise,confidentiel);
            //id = item.getNumero();
        } else {

            DescriptionSujet dSujet = DescriptionSujetRepository.getReferenceById(id);
            sujetRepository.update(dSujet.getSujet(), numeroSujet, intitule, annee, correspondant);
            DescriptionSujetRepository.update(dSujet, contexte, travail, livrable, entreprise,confidentiel);
        }
        ModelAndView returned = new ModelAndView("liste_sujet");
        Object sessionAnnee = request.getSession().getAttribute("anneeCourante");

        if (sessionAnnee == null) {
            List<Object> listSujet = new ArrayList<Object>(DescriptionSujetRepository.findAllWithSujet());
            returned.addObject("sujetList", listSujet);

        } else {
            int var = (Integer) sessionAnnee;
            List<Object> listSujet = new ArrayList<Object>(DescriptionSujetRepository.findAllWithSujetWithAnnee(var));
            returned.addObject("sujetList", listSujet);

        }
        return returned;
    }

    @RequestMapping(value = "createsujet.do", method = RequestMethod.POST)
    public ModelAndView HandlePostcreatesujet() {
        ModelAndView returned;
        DescriptionSujet nouveau = new DescriptionSujet();
        returned = new ModelAndView("ajout-sujet");
        returned.addObject("ajout-sujet", nouveau);
        // Ajouter la liste des entreprises pour le dropdown
        returned.addObject("entrepriseList", entrepriseRepository.findAll());
        returned.addObject("correspondantList", correspondantRepository.findAll());
        return returned;
    }

    @RequestMapping(value = "main.do")
    public ModelAndView handlePostMain(HttpServletRequest request) {

        List<Annee> listAnnee = new ArrayList<Annee>(anneeRepository.findAll());

        ModelAndView returned = new ModelAndView("main");
        returned.addObject("anneeList", listAnnee);
        return returned;
    }

    @RequestMapping(value = "sujet.do", method = RequestMethod.POST)
    public ModelAndView handlePostSujets(HttpServletRequest request) {

        Object sessionAnnee = request.getSession().getAttribute("anneeCourante");
        ModelAndView returned = new ModelAndView("liste_sujet");

        if (sessionAnnee == null) {
            List<Object> listSujet = new ArrayList<Object>(DescriptionSujetRepository.findAllWithSujet());
            returned.addObject("sujetList", listSujet);

        } else {
            int var = (Integer) sessionAnnee;
            List<Object> listSujet = new ArrayList<Object>(DescriptionSujetRepository.findAllWithSujetWithAnnee(var));
            returned.addObject("sujetList", listSujet);

        }

        return returned;
    }

    @RequestMapping(value = "editsujet.do", method = RequestMethod.POST)
    public ModelAndView handleEditPost(HttpServletRequest request) {
        ModelAndView returned;

        String idStr = request.getParameter("id");
        int id = Tools.getIntFromString(idStr);

        if (id != -1) {
            try {
                DescriptionSujet descriptionSujet = DescriptionSujetRepository.getReferenceById(id);
                Sujet sujet = sujetRepository.getReferenceById(id);
                returned = new ModelAndView("ajout-sujet");
                returned.addObject("DescriptionSujet", descriptionSujet);
                returned.addObject("sujet", sujet);

                returned.addObject("correspondantList", correspondantRepository.findAll());
                returned.addObject("entrepriseList", entrepriseRepository.findAll());
            } catch (Exception ex) {
                // Si le sujet n'existe pas
                // Si le correspondant n'existe pas, rediriger vers la liste
                List<DescriptionSujet> listSujet = new ArrayList<DescriptionSujet>(DescriptionSujetRepository.findAll());
                returned = new ModelAndView("liste_sujet");
                returned.addObject("sujetList", listSujet);
            }
        } else {
            // Aucun ID fourni, créer un nouveau correspondant
            returned = new ModelAndView("ajout-sujet");
            returned.addObject("descriptionSujet", new DescriptionSujet());
            returned.addObject("correspondantList", correspondantRepository.findAll());
            returned.addObject("entrepriseList", entrepriseRepository.findAll());
        }

        return returned;
    }

    @RequestMapping(value = "deletesujet.do", method = RequestMethod.POST)
    public ModelAndView handlePostDeleteSujet(HttpServletRequest request) {
        ModelAndView returned;

        String idStr = request.getParameter("id");
        int id = Tools.getIntFromString(idStr);

        if (id != -1) {
            try {
                DescriptionSujet desc = DescriptionSujetRepository.getReferenceById(id);
                Sujet sujet = desc.getSujet();
                DescriptionSujetRepository.remove(desc);
                sujetRepository.remove(sujet);

            } catch (Exception ex) {
                // Correspondant introuvable
            }
        }

        // Retour à la liste
        List<Object> listSujet = new ArrayList<Object>(DescriptionSujetRepository.findAllWithSujet());

        returned = new ModelAndView("liste_sujet");
        returned.addObject("sujetList", listSujet);
        return returned;
    }

    @RequestMapping(value = "clearAnnee.do", method = RequestMethod.POST)
    public ModelAndView clearAnnee(HttpServletRequest request) {

        // Supprime le filtre en session
        request.getSession().removeAttribute("anneeCourante");

        return new ModelAndView("redirect:main.do");
    }

    @RequestMapping(value = "/guide.do", method = RequestMethod.GET) 
    public ModelAndView showGuide() {
        return new ModelAndView("guide"); 
    }
}
