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
import fr.centrale.nantes.pappl.repositories.EleveRepository;
import fr.centrale.nantes.pappl.repositories.SujetRepository;
import fr.centrale.nantes.pappl.repositories.ChoixEleveDomaineRepository;
import fr.centrale.nantes.pappl.repositories.ChoixEleveTypoRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
 * @author vadimdigonnet
 */
@Controller
public class EleveController {
    @Autowired
    private EleveRepository eleveRepository;
    @Autowired
    private SujetRepository sujetRepository;
    @Autowired
    private ChoixEleveTypoRepository choixEleveTypoRepository;
    @Autowired
    private ChoixEleveDomaineRepository choixEleveDomaineRepository;
    
    @RequestMapping(value="saveeleve.do", method=RequestMethod.POST)
    public ModelAndView handlePostSaveEntreprise(HttpServletRequest request) {
        // Récupération des paramètres
        String eleveId = request.getParameter("id");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String nationalite = request.getParameter("nationalite");
        String email = request.getParameter("email");
        String td = request.getParameter("td");

        String numeroSujetSaisi1 = request.getParameter("numero_sujet");
        int numeroSujetSaisi = Tools.getIntFromString(numeroSujetSaisi1);

        Sujet leSujetAssocie = null;
        if (numeroSujetSaisi1 != null && !numeroSujetSaisi1.isEmpty()) {
            leSujetAssocie = sujetRepository.findByNumeroCustom(numeroSujetSaisi);

            if (leSujetAssocie == null) {
                System.out.println("ERREUR : Aucun sujet trouvé avec le numéro " + numeroSujetSaisi);
            }
        }

        // Gestion de l'Eleve
        int id = Tools.getIntFromString(eleveId);

        if (id > 0) {
            Eleve eleve = eleveRepository.getReferenceById(id);
            eleveRepository.update(eleve, nom, prenom, nationalite, email, td, leSujetAssocie);
        } else {
            eleveRepository.create(nom, prenom, nationalite, email, td, leSujetAssocie);
        }

        Object sessionAnnee = request.getSession().getAttribute("anneeCourante");
        ModelAndView returned = new ModelAndView("liste_eleve");

        if (sessionAnnee == null) {
            List<Eleve> myList = new ArrayList<Eleve>(eleveRepository.findAll());
            returned.addObject("eleveList", myList);
            Collections.sort(myList, Eleve.getComparator());
        } else {
            int var = (Integer) sessionAnnee;
             List<Eleve> myList = new ArrayList<Eleve>(eleveRepository.findAllWithAnnee(var));
            returned.addObject("eleveList", myList);
            Collections.sort(myList, Eleve.getComparator());

        }
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
        
        Object sessionAnnee = request.getSession().getAttribute("anneeCourante");
        ModelAndView returned = new ModelAndView("liste_eleve");

        if (sessionAnnee == null) {
            List<Eleve> myList = new ArrayList<Eleve>(eleveRepository.findAll());
            returned.addObject("eleveList", myList);
            Collections.sort(myList, Eleve.getComparator());


        } else {
            int var = (Integer) sessionAnnee;
             List<Eleve> myList = new ArrayList<Eleve>(eleveRepository.findAllWithAnnee(var));
            returned.addObject("eleveList", myList);
            Collections.sort(myList, Eleve.getComparator());

        }
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
    
    @PostMapping(value = "importeleve.do")
    public ModelAndView importCsv(@RequestParam("file") MultipartFile file, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("main"); // Définit la vue par défaut

        if (file.isEmpty()) {
            try {
                response.setContentType("text/csv; charset=UTF-8");
                response.setHeader("Content-Disposition", "attachment; filename=\"template_eleves.csv\"");
                String header = "id,nom,prenom,nationalite,email,td,annee,numeroSujet\n";
                response.getWriter().write(header);
                response.getWriter().flush();
                return null; 
            } catch (IOException e) {
            modelAndView.addObject("message", "Erreur lors de la génération du template : " + e.getMessage());
            return modelAndView;
            }
        }

        List<Eleve> eleves = new ArrayList<>();
        // Utilisation du StandardCharsets.UTF_8 pour éviter les problèmes d'accents
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), java.nio.charset.StandardCharsets.UTF_8))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                // Utilisation d'une regex pour gérer virgule ou point-virgule
                String[] data = line.split("[,;]"); 
                if (data.length >= 8) {
                    Eleve eleve = new Eleve();
                    try {
                        eleve.setId(Integer.parseInt(data[0].trim().replaceAll("\\s", "")));
                    } catch (NumberFormatException e) {
                        eleve.setId(-1);
                    }
                    eleve.setNom(data[1].trim());
                    eleve.setPrenom(data[2].trim());
                    eleve.setNationalite(data[3].trim());
                    eleve.setEmail(data[4].trim());
                    eleve.setTd(data[5].trim());
                    
                    try {
                        int anneeValue = Integer.parseInt(data[6].trim());
                        Annee annee = new Annee(anneeValue);
                        eleve.setAnnee(annee);
                    } catch (NumberFormatException e) {
                        eleve.setAnnee(null);
                    }
                    
                    try {
                        int numeroSujet = Integer.parseInt(data[7].trim());
                        Sujet sujet = sujetRepository.findByNumeroCustom(numeroSujet);
                        eleve.setNumeroSujet(sujet);
                    } catch (NumberFormatException e) {
                        eleve.setNumeroSujet(null);
                    }
                    
                    /* List<ChoixEleveTypo> choixTypoList = new ArrayList<>();
                    for (int i = 8; i < 10; i++) { 
                        try {
                            int idTypo = Integer.parseInt(data[i].trim());
                            ChoixEleveTypo choixTypo = choixEleveTypoRepository.findById(idTypo).orElse(null);
                            if (choixTypo != null) {
                                choixTypo.setIdEleve(eleve); 
                                choixTypoList.add(choixTypo);
                            }
                        } catch (NumberFormatException e) {}
                    }
                    eleve.setChoixEleveTypoCollection(choixTypoList);

                    List<ChoixEleveDomaine> choixDomaineList = new ArrayList<>();
                    for (int i = 10; i < 12; i++) { 
                        try {
                            int idDomaine = Integer.parseInt(data[i].trim());
                            ChoixEleveDomaine choixDomaine = choixEleveDomaineRepository.findById(idDomaine).orElse(null);
                            if (choixDomaine != null) {
                                choixDomaine.setIdEleve(eleve); 
                                choixDomaineList.add(choixDomaine);
                            }
                        } catch (NumberFormatException e) {}
                    }
                    eleve.setChoixEleveDomaineCollection(choixDomaineList); */

                    
                    eleves.add(eleve);
                }
            }

            if (!eleves.isEmpty()) {
                eleveRepository.saveAll(eleves);
                modelAndView.addObject("message", "Import réussi : " + eleves.size() + " élèves ajoutés.");
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