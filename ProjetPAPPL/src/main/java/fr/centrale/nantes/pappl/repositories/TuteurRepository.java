/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;
import fr.centrale.nantes.pappl.items.Tuteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vadimdigonnet
 */
@Repository
public interface TuteurRepository extends JpaRepository<Tuteur,Integer>,TuteurRepositoryCustom {
    
}