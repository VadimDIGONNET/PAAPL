/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

/**
 *
 * @author agach
 */
import fr.centrale.nantes.pappl.items.DescriptionSujet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
public class DescriptionSujetRepositoryCustomImpl implements DescriptionSujetRepositoryCustom {
    @Autowired
    @Lazy
    private DescriptionSujetRepository DescriptionSujetRepository;
    
    @Override
    public DescriptionSujet update(DescriptionSujet numero, String contexte, String travail, String livrable, String entreprise){
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
    public DescriptionSujet create(String contexte, String travail, String livrable, String entreprise){
        if((entreprise!=null)&&(!entreprise.isEmpty())&&(contexte!=null)&&(!contexte.isEmpty())){
            DescriptionSujet item=new DescriptionSujet();
            item.setContexte(contexte);
            item.setTravailRealiser(travail);
            item.setLivrable(livrable);
            DescriptionSujetRepository.saveAndFlush(item);
            return DescriptionSujetRepository.getReferenceById(item.getNumero());
        }
        return null;
    }
}
