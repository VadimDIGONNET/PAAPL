/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;
import fr.centrale.nantes.pappl.items.Correspondant;
import fr.centrale.nantes.pappl.items.Entreprise;

/**
 *
 * @author agach
 */
public interface CorrespondantRepositoryCustom {
    
    Correspondant create(String nom, String prenom, Integer tel, String email, Entreprise entreprise);
    
    void remove(Correspondant correspondant);
    
    Correspondant update(Correspondant correspondant, String nom, String prenom, Integer tel, String email, Entreprise entreprise);
}
