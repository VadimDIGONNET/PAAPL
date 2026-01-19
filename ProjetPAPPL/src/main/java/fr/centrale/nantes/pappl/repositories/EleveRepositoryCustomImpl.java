/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

/**
 *
 * @author agach
 */
import fr.centrale.nantes.pappl.items.Eleve;
import fr.centrale.nantes.pappl.items.Sujet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

@Repository
public class EleveRepositoryCustomImpl implements EleveRepositoryCustom{
    @Autowired
    @Lazy
    private EleveRepository eleveRepository;
    @Override
    public Eleve create(String nom, String prenom, String nationalite, String email, String td, Sujet numero_sujet){
        if((nom!=null)&&(!nom.isEmpty())){
            Eleve item=new Eleve();
            item.setNom(nom);
            if (prenom != null){
                item.setPrenom(prenom);
            }
            if (nationalite != null){
                item.setNationalite(nationalite);
            }
            if (email != null){
            item.setEmail(email);
            }
            if (td != null){
            item.setTd(td);
            }
            if (numero_sujet != null){
            item.setNumeroSujet(numero_sujet);
            }       
            eleveRepository.saveAndFlush(item);
            return item;
        }

        return null;
    }
    
    @Override
    public void remove(Eleve eleveId) {
        if (eleveId != null) {
            // Ensure valididy from database
            eleveId = eleveRepository.getReferenceById(eleveId.getId());
        }
        if (eleveId != null) {
            eleveRepository.delete(eleveId);
        }
    }
    
    @Override
    public Eleve update(Eleve eleveId, String nom, String prenom, String nationalite, String email, String td, Sujet numero_sujet) {
        if (eleveId != null) {
            // Ensure valididy from database
            eleveId = eleveRepository.getReferenceById(eleveId.getId());
        }
        if ((eleveId != null)
                && (nom != null) && (! prenom.isEmpty())){
            // Update data
            eleveId.setNom(nom);
            eleveId.setPrenom(prenom);
            eleveId.setNationalite(nationalite);
            eleveId.setEmail(email);
            eleveId.setTd(td);
            eleveId.setNumeroSujet(numero_sujet);
            // Save to database
            eleveRepository.saveAndFlush(eleveId);
            // Ensure we have the last version
            eleveId = eleveRepository.getReferenceById(eleveId.getId());
        }
        return eleveId;
    }

}


