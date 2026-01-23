/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.springframework.stereotype.Controller;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author vadimdigonnet
 */


@WebServlet("/ImportProjetServlet")
@MultipartConfig // Indispensable pour l'upload de fichier
public class ImportController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Récupération du fichier depuis le formulaire HTML
        Part filePart = request.getPart("fichierProjet"); // Nom du champ <input type="file" name="fichierProjet">
        
        if (filePart == null || filePart.getSize() == 0) {
            response.sendRedirect("erreur.jsp");
            return;
        }

        // Variables pour stocker les données
        String structure = "";
        String titre = "";
        StringBuilder contexte = new StringBuilder();
        StringBuilder travail = new StringBuilder();

        // Variable "interrupteur" pour savoir quelle zone on est en train de lire
        String zoneActuelle = "RIEN";

        try (InputStream is = filePart.getInputStream();
             XWPFDocument document = new XWPFDocument(is)) {

            // 2. On lit le document paragraphe par paragraphe
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                String ligne = paragraph.getText().trim();

                // Ignorer les lignes vides
                if (ligne.isEmpty()) continue;

                // --- DÉTECTION DES BALISES ---

                // Cas 1 : Données simples (sur la même ligne)
                if (ligne.contains("{{STRUCTURE}}")) {
                    // On enlève la balise pour ne garder que le texte
                    structure = ligne.replace("{{STRUCTURE}}", "").trim();
                    continue; // On passe au paragraphe suivant
                }
                if (ligne.contains("{{TITRE}}")) {
                    titre = ligne.replace("{{TITRE}}", "").trim();
                    continue;
                }

                // Cas 2 : Blocs de texte (Context, Travail, etc.)
                // Si on trouve une balise de début, on active l'interrupteur
                if (ligne.contains("{{DEBUT_CONTEXTE}}")) {
                    zoneActuelle = "CONTEXTE";
                    continue;
                }
                if (ligne.contains("{{FIN_CONTEXTE}}")) {
                    zoneActuelle = "RIEN";
                    continue;
                }

                if (ligne.contains("{{DEBUT_TRAVAIL}}")) {
                    zoneActuelle = "TRAVAIL";
                    continue;
                }
                if (ligne.contains("{{FIN_TRAVAIL}}")) {
                    zoneActuelle = "RIEN";
                    continue;
                }

                // --- ENREGISTREMENT DU TEXTE ---
                
                // Si l'interrupteur est allumé sur une zone, on ajoute la ligne
                if (zoneActuelle.equals("CONTEXTE")) {
                    contexte.append(ligne).append("\n");
                } else if (zoneActuelle.equals("TRAVAIL")) {
                    travail.append(ligne).append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Affiche l'erreur dans la console serveur
        }

        // --- 3. VÉRIFICATION ET SAUVEGARDE ---
        
        System.out.println("--- IMPORT RÉUSSI ---");
        System.out.println("Structure : " + structure);
        System.out.println("Titre : " + titre);
        System.out.println("Contexte : " + contexte.toString());

        // ICI : Appeler votre classe DAO pour insérer en base de données
        // projetDao.creerProjet(structure, titre, contexte.toString(), travail.toString());

        // Redirection vers une page de succès
        response.sendRedirect("liste-projets.jsp");
    }
}
