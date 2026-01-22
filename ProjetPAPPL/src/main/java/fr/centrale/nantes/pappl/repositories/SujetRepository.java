/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;
import fr.centrale.nantes.pappl.items.Sujet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author vadimdigonnet
 */
@Repository
public interface SujetRepository extends JpaRepository<Sujet,Integer>,SujetRepositoryCustom {

    @Query("SELECT s FROM Sujet s WHERE s.numero_du_sujet = :leNum")
    public Sujet findByNumeroCustom(@Param("leNum") int numero);
}
