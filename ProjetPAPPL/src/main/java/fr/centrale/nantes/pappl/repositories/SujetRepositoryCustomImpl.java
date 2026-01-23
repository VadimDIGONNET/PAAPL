/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

/**
 *
 * @author vadimdigonnet
 */
import fr.centrale.nantes.pappl.items.Annee;
import fr.centrale.nantes.pappl.items.Sujet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
@Repository
public class SujetRepositoryCustomImpl implements SujetRepositoryCustom{
    @Autowired
    @Lazy
    private SujetRepository SujetRepository;

    @Override
    public Sujet create(Integer numeroSujet , String intitule, Annee annee) {
            Sujet item=new Sujet();
            item.setNumeroDuSujet(numeroSujet);
            item.setIntitule(intitule);
            item.setAnnee(annee);
            SujetRepository.saveAndFlush(item);
            //return DescriptionSujetRepository.getReferenceById(item.getNumero());
            return item;
    }
    
    @Override
    public Sujet update(Sujet sujet ,Integer numeroSujet , String intitule,Annee annee){
        if (!(intitule.isEmpty()) && (numeroSujet !=null) && (intitule !=null)){
            sujet.setIntitule(intitule);
            sujet.setNumeroDuSujet(numeroSujet);
            sujet.setAnnee(annee);
            SujetRepository.saveAndFlush(sujet);
            return sujet;
            
            
        
        }
        return null;
    }
    
    @Override
    public void remove(Sujet sujet){
        if (sujet != null) {
            SujetRepository.delete(sujet);
        }
    
    }
    
}
