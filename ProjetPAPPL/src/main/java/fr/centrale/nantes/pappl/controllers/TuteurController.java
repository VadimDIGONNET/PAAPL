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
    
    @RequestMapping(value="tuteurs.do",method=RequestMethod.POST)
    public ModelAndView handlePostUsers(HttpServletRequest request){
        List<Tuteur> listTuteur =new ArrayList<Tuteur>( tuteurRepository.findAll());
        ModelAndView returned =new ModelAndView("liste_tuteurs");
        returned.addObject("liste_tuteurs",listTuteur);
        return returned;
    }
    
    @RequestMapping(value="savettuteur.do", method=RequestMethod.POST)
    public ModelAndView handlePostSaveEntreprise(HttpServletRequest request) {
        // Récupération des paramètres
        String idStr=request.getParameter("id");
        String nom = request.getParameter("Nom");
        String prenom = request.getParameter("Prenom");
        String nbsujet = request.getParameter("Nombre de sujet souhaité");
        int nbsuj = Tools.getIntFromString(nbsujet);
        int id =Tools.getIntFromString(idStr);
        if(id!=-1){
            tuteurRepository.update(id,nom,prenom,nbsuj);
        }else{
            tuteurRepository.create(nom, prenom, nbsuj);
        }
        ModelAndView returned = new ModelAndView("main");
        returned.addObject("liste_tuteurs", tuteurRepository.findAll());
        return returned;
    }
    
    @RequestMapping(value="ajouttuteur.do",method=RequestMethod.POST)
    public ModelAndView HandlePostcreateentreprise(){
        ModelAndView returned;
        Tuteur nouveau=new Tuteur();
        returned=new ModelAndView("ajout-tuteur");
        returned.addObject("ajout-tuteur",nouveau);
        return returned;
    }
    
    @RequestMapping(value = "edittuteur.do", method = RequestMethod.POST)
    public ModelAndView handleEditPost(HttpServletRequest request) {
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
        return returned;
    }
    @RequestMapping(value = "deletetuteur.do", method = RequestMethod.POST)
    public ModelAndView handlePostDeleteUser(HttpServletRequest request) {
        ModelAndView returned;

        String idst = request.getParameter("id");
        int id=Tools.getIntFromString(idst);
        if (id != -1) {
            Tuteur tuteur = tuteurRepository.getReferenceById(id);
            tuteurRepository.remove(tuteur);
        }
        
        // return to list
        List<Tuteur> listTuteur = new ArrayList<Tuteur>(tuteurRepository.findAll());
        returned = new ModelAndView("tuteur");
        returned.addObject("tuteurlist", listTuteur);

        return returned;
    }
}
