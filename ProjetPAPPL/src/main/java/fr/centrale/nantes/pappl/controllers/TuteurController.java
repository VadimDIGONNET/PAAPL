/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import fr.centrale.nantes.pappl.items.Annee;
import fr.centrale.nantes.pappl.items.ChoixEleveDomaine;
import fr.centrale.nantes.pappl.items.ChoixEleveTypo;
import fr.centrale.nantes.pappl.items.Eleve;
import fr.centrale.nantes.pappl.items.Entreprise;
import fr.centrale.nantes.pappl.items.Sujet;
import fr.centrale.nantes.pappl.items.Tuteur;
import fr.centrale.nantes.pappl.repositories.ChoixEleveTypoRepository;
import fr.centrale.nantes.pappl.repositories.AnneeRepository;
import fr.centrale.nantes.pappl.repositories.TuteurRepository;
import fr.centrale.nantes.pappl.repositories.SujetRepository;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Size;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import static org.eclipse.tags.shaded.org.apache.xalan.lib.ExsltDatetime.date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
    private SujetRepository SujetRepository;
    
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
    
    @PostMapping(value = "importtuteur.do")
    public ModelAndView importCsv(@RequestParam("file") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView("main"); // Définit la vue par défaut

        if (file.isEmpty()) {
            modelAndView.addObject("message", "Veuillez sélectionner un fichier CSV.");
            return modelAndView;
        }

        List<Tuteur> tuteurs = new ArrayList<>();
        // Utilisation du StandardCharsets.UTF_8 pour éviter les problèmes d'accents
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), java.nio.charset.StandardCharsets.UTF_8))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split("[,;]"); 
                if (data.length >= 5) {
                    Tuteur tuteur = new Tuteur();
                    
                    try {
                        tuteur.setId(Integer.parseInt(data[0].trim().replaceAll("\\s", "")));
                    } catch (NumberFormatException e) {
                        tuteur.setId(-1);
                    }
                    tuteur.setNom(data[1].trim());
                    tuteur.setPrenom(data[2].trim());
                    try {
                        tuteur.setNbSujetSouhaite(Integer.parseInt(data[3].trim().replaceAll("\\s", "")));
                    } catch (NumberFormatException e) {
                        tuteur.setNbSujetSouhaite(-1);
                    }
                    List<Sujet> sujetCollection = new ArrayList<>();
                    for (int i = 4; i < 3+tuteur.getNbSujetSouhaite(); i++) { 
                        try {
                            int idSujet = Integer.parseInt(data[i].trim());
                            Sujet sujet = SujetRepository.findById(idSujet).orElse(null);
                            if (sujet != null) {
                                //Ajouter le numéro du sujet et l'id du tuteur dans Choix_Sujet une fois la table créer
                                sujetCollection.add(sujet);
                            }
                        } catch (NumberFormatException e) {}
                    }
                    tuteur.setSujetCollection(sujetCollection);

                    tuteurs.add(tuteur);
                }
            }

            if (!tuteurs.isEmpty()) {
                tuteurRepository.saveAll(tuteurs);
                modelAndView.addObject("message", "Import réussi : " + tuteurs.size() + " tuteurs ajoutés.");
            } else {
                modelAndView.addObject("message", "Aucune donnée valide trouvée dans le fichier.");
            }

        } catch (IOException e) {
            modelAndView.addObject("message", "Erreur de lecture : " + e.getMessage());
        }
        modelAndView.setViewName("main"); 
        return modelAndView;
    }
}
