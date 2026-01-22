/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

/**
 *
 * @author vadimdigonnet
 */
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
    public Sujet create(Integer numeroSujet , String intitule) {
            Sujet item=new Sujet();
            item.setNumeroDuSujet(numeroSujet);
            item.setIntitule(intitule);
            SujetRepository.saveAndFlush(item);
            //return DescriptionSujetRepository.getReferenceById(item.getNumero());
            return item;
    }
    
    @Override
    public Sujet update(Sujet sujet ,Integer numeroSujet , String intitule){
        if (!(intitule.isEmpty()) && (numeroSujet !=null) && (intitule !=null)){
            sujet.setIntitule(intitule);
            sujet.setNumeroDuSujet(numeroSujet);
            SujetRepository.saveAndFlush(sujet);
            return sujet;
            
            
        
        }
        return null;
    }
    
    
}
