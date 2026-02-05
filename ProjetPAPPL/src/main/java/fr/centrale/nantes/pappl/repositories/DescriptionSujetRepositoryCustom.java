/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.*;


/**
 *
 * @author agach
 */
public interface DescriptionSujetRepositoryCustom {
    public DescriptionSujet create(Integer id ,String contexte,String travail, String livrable, Entreprise entreprise,String confidentiel);
    public DescriptionSujet update(DescriptionSujet numero, String contexte, String travail, String livrable, Entreprise entreprise,String confidentiel);
    public void remove(DescriptionSujet desc);
}
