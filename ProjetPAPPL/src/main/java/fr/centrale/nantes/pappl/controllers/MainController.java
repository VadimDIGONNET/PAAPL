/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import fr.centrale.nantes.pappl.items.DescriptionSujet;
import fr.centrale.nantes.pappl.repositories.DescriptionSujetRepositoryCustomImpl;
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
    private DescriptionSujetRepository DescriptionSujetRepository;
    
    @RequestMapping(value="sujet.do",method=RequestMethod.POST)
    public ModelAndView handlePostUsers(HttpServletRequest request){
        List<Sujet> myList =new ArrayList<Sujet>( sujetRepository.findAll());
        ModelAndView returned =new ModelAndView("sujet");
        returned.addObject("sujetList",myList);
        return returned;
    }
    
    private int getIntFromString(String value){
        int intValue=-1;
        try{
            intValue = Integer.parseInt(value);
        }catch(NumberFormatException ex){
            Logger.getLogger(MainController.class.getName()).log(Level.WARNING,null,ex);
        }
        return intValue;
    }
    
    @RequestMapping(value="savesujet.do",method=RequestMethod.POST)
    public ModelAndView handlePostSaveUser(HttpServletRequest request){
        ModelAndView returned;
        String idStr=request.getParameter("id");
        String contexte=request.getParameter("contexte");
        String travail = request.getParameter("travail");
        String livrable= request.getParameter("livrable");
        String entreprise =request.getParameter("entreprise");
        int id =Tools.getIntFromString(idStr);
        if(id==-1){
            DescriptionSujet item=DescriptionSujetRepository.create(contexte,travail,livrable,entreprise);
            id=item.getNumero();
        }
        DescriptionSujet DSujet =DescriptionSujetRepository.getReferenceById(id);
        DescriptionSujetRepository.update(DSujet,contexte,travail,livrable,entreprise);
        
        
        returned =new ModelAndView("main");
        Collection<DescriptionSujet> myList= DescriptionSujetRepository.findAll();
        returned.addObject("main",myList);
        return returned;
    }
    
    @RequestMapping(value="createsujet.do",method=RequestMethod.POST)
    public ModelAndView HandlePostcreateuser(){
        ModelAndView returned;
        DescriptionSujet nouveau=new DescriptionSujet();
        returned=new ModelAndView("ajout-sujet");
        returned.addObject("ajout-sujet",nouveau);
        return returned;
    }
}

