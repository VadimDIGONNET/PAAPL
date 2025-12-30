/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.DescriptionSujet;


/**
 *
 * @author agach
 */
public interface DescriptionSujetRepositoryCustom {
    public DescriptionSujet create(String contexte,String travail, String livrable, String entreprise);
    public DescriptionSujet update(DescriptionSujet numero, String contexte, String travail, String livrable, String entreprise);
}
