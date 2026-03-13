/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

/**
 *
 * @author vadimdigonnet
 */
import fr.centrale.nantes.pappl.items.ChoixEleveDomaine;
import fr.centrale.nantes.pappl.items.Eleve;
import fr.centrale.nantes.pappl.items.Annee;
import fr.centrale.nantes.pappl.items.Correspondant;
import fr.centrale.nantes.pappl.items.Sujet;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
@Repository
public class SujetRepositoryCustomImpl implements SujetRepositoryCustom{
    @Autowired
    @Lazy
    private SujetRepository SujetRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Sujet create(Integer numeroSujet , String intitule, Annee annee,Correspondant correspondant) {
            Sujet item=new Sujet();
            item.setNumeroDuSujet(numeroSujet);
            item.setIntitule(intitule);
            item.setAnnee(annee);
            item.setIdCorrespondant(correspondant);
            SujetRepository.saveAndFlush(item);
            //return DescriptionSujetRepository.getReferenceById(item.getNumero());
            return item;
    }

    public List<Sujet> findBySujetId(int sujetId) {
        String query = "SELECT c FROM Sujet c WHERE c.idSujet.id = :sujetId";
        return entityManager.createQuery(query, Sujet.class)
                .setParameter("eleveId", sujetId)
                .getResultList();
    }
    
    public void remove(Sujet sujetId) {
        if (sujetId != null) {
            // Ensure valididy from database
            sujetId = SujetRepository.getReferenceById(sujetId.getNumero());
        }
        if (sujetId != null) {
            SujetRepository.delete(sujetId);
        }
    }
    
    @Override
    public Sujet update(Sujet sujet ,Integer numeroSujet , String intitule,Annee annee,Correspondant correspondant){
        if (!(intitule.isEmpty()) && (numeroSujet !=null) && (intitule !=null)){
            sujet.setIntitule(intitule);
            sujet.setNumeroDuSujet(numeroSujet);
            sujet.setAnnee(annee);
            sujet.setIdCorrespondant(correspondant);
            
            SujetRepository.saveAndFlush(sujet);
            return sujet;
            
            
        
        }
        return null;
    }
    
}
