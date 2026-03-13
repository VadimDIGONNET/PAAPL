/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

/**
 *
 * @author agach
 */

import fr.centrale.nantes.pappl.items.Correspondant;
import fr.centrale.nantes.pappl.items.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/**
 *
 * @author agach
 */

@Repository
public interface CorrespondantRepository extends JpaRepository<Correspondant, Integer>, CorrespondantRepositoryCustom {
    
    @Query("SELECT c FROM Correspondant c")
    List<Correspondant> findAllCorrespondants();
    
    @Query("SELECT c FROM Correspondant c WHERE c.entreprise = :entreprise")
    List<Correspondant> findByEntreprise(@Param("entreprise") Entreprise entreprise);
    
    Correspondant findByNom(String nom);

}
