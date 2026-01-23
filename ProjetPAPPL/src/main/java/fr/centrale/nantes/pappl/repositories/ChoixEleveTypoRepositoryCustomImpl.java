/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

/**
 *
 * @author agach
 */
import fr.centrale.nantes.pappl.items.ChoixEleveTypo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public class ChoixEleveTypoRepositoryCustomImpl implements ChoixEleveTypoRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ChoixEleveTypo> findByEleveId(int eleveId) {
        String query = "SELECT c FROM ChoixEleveTypo c WHERE c.idEleve.id = :eleveId";
        return entityManager.createQuery(query, ChoixEleveTypo.class)
                .setParameter("eleveId", eleveId)
                .getResultList();
    }
}
