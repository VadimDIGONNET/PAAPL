/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.Annee;
import fr.centrale.nantes.pappl.items.DescriptionSujet;
import fr.centrale.nantes.pappl.items.Sujet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 *
 * @author agach
 */
@Repository
public interface DescriptionSujetRepository extends JpaRepository<DescriptionSujet,Integer>,DescriptionSujetRepositoryCustom{
    
    List<Object[]> findAllWithSujet();
    List<Object[]> findAllWithSujetWithAnnee(@Param("annee") int annee);

    
}
