/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.infosi.prwebspring.repositories;
import fr.centrale.nantes.infosi.prwebspring.items.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author agach
 */
@Repository
public interface BorrowRepository extends JpaRepository<Borrow,Integer>,BorrowRepositoryCustom{
    
}
