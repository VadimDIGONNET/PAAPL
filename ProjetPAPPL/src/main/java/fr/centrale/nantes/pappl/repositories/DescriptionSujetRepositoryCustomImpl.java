/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

/**
 *
 * @author agach
 */
import fr.centrale.nantes.pappl.items.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
public class DescriptionSujetRepositoryCustomImpl implements DescriptionSujetRepositoryCustom {
    @Autowired
    @Lazy
    private DescriptionSujetRepository DescriptionSujetRepository;
    
    @Override
    public DescriptionSujet update(DescriptionSujet numero, String contexte, String travail, String livrable, Entreprise entreprise){
        if(numero !=null){
            numero = DescriptionSujetRepository.getReferenceById(numero.getNumero());
        }
        if((numero != null) && (contexte !=null)&&(!contexte.isEmpty())&&(livrable !=null)&&(!livrable.isEmpty())){
            numero.setContexte(contexte);
            numero.setTravailRealiser(travail);
            numero.setLivrable(livrable);
            DescriptionSujetRepository.saveAndFlush(numero);
            numero=DescriptionSujetRepository.getReferenceById(numero.getNumero());
        }
        return numero;
    }
    
    
    @Override
    public DescriptionSujet create(Integer id, String contexte, String travail, String livrable, Entreprise entreprise){
        if((entreprise!=null)&&(contexte!=null)&&(!contexte.isEmpty())&&(id !=null)){
            DescriptionSujet item=new DescriptionSujet();
            item.setNumero(id);
            item.setContexte(contexte);
            item.setTravailRealiser(travail);
            item.setLivrable(livrable);
            item.setNomEntreprise(entreprise);
            DescriptionSujetRepository.saveAndFlush(item);
            //return DescriptionSujetRepository.getReferenceById(item.getNumero());
            return item;
        }
        return null;
    }
    
    @Override
    public void remove(DescriptionSujet desc){
        if (desc != null) {
            DescriptionSujetRepository.delete(desc);
        }
    
    }
}
