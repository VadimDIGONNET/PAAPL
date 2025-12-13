/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import fr.centrale.nantes.pappl.items.Sujet;
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
    
    @RequestMapping(value="sujet.do",method=RequestMethod.POST)
    public ModelAndView sujet(){
        return new ModelAndView("sujet");
    }
    
    @Autowired
    private SujetRepository sujetRepository;
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
    
    @RequestMapping(value="ajoutsujet.do",method=RequestMethod.POST)
    public ModelAndView nvsujet(HttpServletRequest request){
        ModelAndView returned;
        try{
            request.setCharacterEncoding("UTF-8");
        }catch(UnsupportedEncodingException ex){
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE,null,ex);
        }
        String idStr=request.getParameter("id");
        String Titre = request.getParameter("Titre");
        String Entreprise= request.getParameter("Entreprise");
        String Resume =request.getParameter("Résumé");
        int id =getIntFromString(idStr);
        Sujet sujetId=sujetRepository.getReferenceById(id);
        sujetRepository.update(id,Titre,Entreprise,Resume);
        
        returned =new ModelAndView("sujut");
        Collection<Sujet> myList= sujetRepository.findAll();
        returned.addObject("sujetList",myList);
        return returned;
    }
}

