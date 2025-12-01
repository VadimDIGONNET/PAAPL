/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;
import fr.centrale.nantes.pappl.items.TypologieMission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author agach
 */
@Repository
public interface TypologieMissionRepository extends JpaRepository<TypologieMission,Integer>,TypologieMissionRepositoryCustom {
    
}