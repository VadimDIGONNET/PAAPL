/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // Ajouté
import org.springframework.web.bind.annotation.RequestParam; // Ajouté
import org.springframework.web.bind.annotation.ResponseBody; // Ajouté

@Controller
public class ThemeController {

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        String theme = (String) session.getAttribute("userTheme");
        if (theme == null) theme = "light";
        
        model.addAttribute("themeClass", theme.equals("dark") ? "dark-mode" : "");
        return "main"; 
    }

    // AJOUTE CETTE MÉTHODE :
    @PostMapping("/setTheme.do")
    @ResponseBody
    public void setTheme(@RequestParam String mode, HttpSession session) {
        session.setAttribute("userTheme", mode);
    }
}