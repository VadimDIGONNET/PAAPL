/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

/**
 *
 * @author agach
 */
import fr.centrale.nantes.pappl.items.ChoixEleveDomaine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChoixEleveDomaineRepository extends JpaRepository<ChoixEleveDomaine,Integer>,ChoixEleveDomaineRepositoryCustom{
    
}
