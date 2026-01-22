/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import fr.centrale.nantes.pappl.items.Correspondant;
import fr.centrale.nantes.pappl.items.DescriptionSujet;
import fr.centrale.nantes.pappl.items.Entreprise;
import fr.centrale.nantes.pappl.items.Sujet;
import fr.centrale.nantes.pappl.repositories.EntrepriseRepository;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
        Entreprise a =null;
        try{
            List<Entreprise> listEntreprise = new ArrayList<Entreprise>(entrepriseRepository.findAll());
            a = entrepriseRepository.getReferenceById(nom);
        }catch(Exception ex){
        }
       
        if(a!=null){
            entrepriseRepository.update(a, adresse, codePostal, tel, siteWeb, description);
        }else{
            entrepriseRepository.create(nom, adresse, codePostal, tel, siteWeb, description);
        }
        ModelAndView returned = new ModelAndView("liste_entreprise");
        List<Entreprise> listEntreprise = new ArrayList<Entreprise>(entrepriseRepository.findAll());
        Collections.sort(listEntreprise, Entreprise.getComparator());
        returned.addObject("entrepriseList", listEntreprise);
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
        ModelAndView returned =new ModelAndView("liste_entreprise");
        List<Entreprise> listEntreprise = new ArrayList<Entreprise>(entrepriseRepository.findAll());
        Collections.sort(listEntreprise, Entreprise.getComparator());
        returned.addObject("entrepriseList", listEntreprise);
        return returned;
    }
    
    @RequestMapping(value = "editentreprise.do", method = RequestMethod.POST)
    public ModelAndView handleEditPost(HttpServletRequest request) {
        ModelAndView returned;

        String nom = request.getParameter("nom");

        if (nom!=null) {
            Entreprise entreprise = entrepriseRepository.getReferenceById(nom);
            returned = new ModelAndView("ajout-entreprise");
            returned.addObject("entreprise", entreprise);
        } else {
            List<Entreprise> listEntreprise = new ArrayList<Entreprise>(entrepriseRepository.findAll());
            returned = new ModelAndView("ajout-entreprise");
            Collections.sort(listEntreprise, Entreprise.getComparator());
            returned.addObject("entrepriseList", listEntreprise);        }
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
        returned = new ModelAndView("liste_entreprise");
        returned.addObject("entrepriseList", listEntreprise);

        return returned;
    }
    
    @PostMapping(value = "importentreprise.do")
    public ModelAndView importCsv(@RequestParam("file") MultipartFile file) {
        ModelAndView modelAndView = new ModelAndView("main"); // Définit la vue par défaut

        if (file.isEmpty()) {
            modelAndView.addObject("message", "Veuillez sélectionner un fichier CSV.");
            return modelAndView;
        }

        List<Entreprise> entreprises = new ArrayList<>();
        // Utilisation du StandardCharsets.UTF_8 pour éviter les problèmes d'accents
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), java.nio.charset.StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Utilisation d'une regex pour gérer virgule ou point-virgule
                String[] data = line.split("[,;]"); 

                if (data.length >= 6) {
                    Entreprise entreprise = new Entreprise();
                    entreprise.setNom(data[0].trim());
                    entreprise.setAdresse(data[1].trim());

                    // Nettoyage des espaces pour les entiers
                    try {
                        entreprise.setCodePostal(Integer.parseInt(data[2].trim().replaceAll("\\s", "")));
                    } catch (NumberFormatException e) {
                        entreprise.setCodePostal(0);
                    }

                    try {
                        entreprise.setTel(Integer.parseInt(data[3].trim().replaceAll("\\s", "")));
                    } catch (NumberFormatException e) {
                        entreprise.setTel(0);
                    }

                    entreprise.setSiteWeb(data[4].trim());
                    entreprise.setDescription(data[5].trim());
                    entreprises.add(entreprise);
                }
            }

            if (!entreprises.isEmpty()) {
                entrepriseRepository.saveAll(entreprises);
                modelAndView.addObject("message", "Import réussi : " + entreprises.size() + " entreprises ajoutées.");
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


