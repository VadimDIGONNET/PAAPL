/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.*;
import java.util.List;


/**
 *
 * @author vadimdigonnet
 */
public interface SujetRepositoryCustom {
    
    public Sujet create( Integer numeroSujet , String intitule);
    public void remove(Sujet sujetId);
    List<Sujet> findBySujetId(int sujetId);
    
}
