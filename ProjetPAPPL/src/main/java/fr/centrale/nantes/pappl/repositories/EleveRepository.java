/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.Eleve;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author agach
 */
import org.springframework.stereotype.Repository;

@Repository
public interface EleveRepository extends JpaRepository<Eleve,Integer>,EleveRepositoryCustom{
    
        List<Eleve> findAllWithAnnee(@Param("annee") int annee);

    
}
