/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.Eleve;
import fr.centrale.nantes.pappl.items.Sujet;

/**
 *
 * @author agach
 */
public interface EleveRepositoryCustom {
    
    public Eleve update(Eleve eleveId, String nom, String prenom, String nationalite, String email, String td, Sujet numero_sujet);
    public void remove(Eleve eleveId);
    public Eleve create(String nom, String prenom, String nationalite, String email, String td, Sujet numero_sujet);
}
