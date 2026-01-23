/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;
import fr.centrale.nantes.pappl.items.Eleve;
import fr.centrale.nantes.pappl.items.Tuteur;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vadimdigonnet
 */
@Repository
public interface TuteurRepository extends JpaRepository<Tuteur,Integer>,TuteurRepositoryCustom {
    
            List<Tuteur> findAllWithAnnee(@Param("annee") int annee);

    
}