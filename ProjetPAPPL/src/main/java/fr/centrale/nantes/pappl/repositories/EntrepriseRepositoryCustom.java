/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.Entreprise;

/**
 *
 * @author vadimdigonnet
 */
public interface EntrepriseRepositoryCustom {
    public Entreprise create(String nom, String adresse, Integer codePostal, Integer tel, String siteWeb, String description);
    public void remove(Entreprise nom);
    public Entreprise update(Entreprise entreprise, String adresse, Integer codePostal, Integer tel, String siteWeb, String description);
}
