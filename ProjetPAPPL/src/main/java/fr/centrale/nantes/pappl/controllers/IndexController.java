/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import fr.centrale.nantes.pappl.items.Annee;
import fr.centrale.nantes.pappl.repositories.AnneeRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
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
public class IndexController {
    
        @Autowired
    private AnneeRepository anneeRepository;

    @RequestMapping(value="index.do")
    public ModelAndView handleIndexGet(){
        return new ModelAndView("index");
    }
    
    @RequestMapping(value = "login.do", method = RequestMethod.POST)
    public ModelAndView handleLoginPost(HttpServletRequest request){
        ModelAndView returned;
        
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if ((login != null) && (password != null) 
                && (login.equals("admin")) && (password.equals("admin"))){   //ligne a modifier pour changer le login et mot de passe
            returned = new ModelAndView("redirect:main.do");
  
        }else{
            returned = new ModelAndView("index");
        }
        return returned;
    }
    
    @RequestMapping(value="main.do",method=RequestMethod.POST)
    public ModelAndView handlereturntomenu(HttpServletRequest request){
        List<Annee> listAnnee = new ArrayList<Annee>(anneeRepository.findAll());

        ModelAndView returned = new ModelAndView("main");
        returned.addObject("anneeList", listAnnee);
        return returned;
    }
            
}