/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
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
                && (login.equals("admin")) && (password.equals("admin"))){
            returned = new ModelAndView("redirect:main.do");
  
        }else{
            returned = new ModelAndView("index");
        }
        return returned;
    }
    
    @RequestMapping(value="main.do",method=RequestMethod.POST)
    public ModelAndView handlereturntomenu(HttpServletRequest request){
        return new ModelAndView("main");
    }
            
}