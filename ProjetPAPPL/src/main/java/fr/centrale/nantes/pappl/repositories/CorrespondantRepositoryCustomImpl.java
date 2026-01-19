/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;
import fr.centrale.nantes.pappl.items.Correspondant;
import fr.centrale.nantes.pappl.items.Entreprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

/**
 *
 * @author agach
 */
import org.springframework.stereotype.Repository;

@Repository
public class CorrespondantRepositoryCustomImpl implements CorrespondantRepositoryCustom {
    
    @Autowired
    @Lazy
    private CorrespondantRepository correspondantRepository;
    
    @Override
    public Correspondant create(String nom, String prenom, Integer tel, String email, Entreprise entreprise) {
        if ((nom != null) && (!nom.isEmpty())) {
            Correspondant item = new Correspondant();
            item.setNom(nom);
            
            if (prenom != null) {
                item.setPrenom(prenom);
            }
            if (tel != null) {
                item.setTel(tel);
            }
            if (email != null) {
                item.setEmail(email);
            }
            if (entreprise != null) {
                item.setEntreprise(entreprise);
            }
            
            correspondantRepository.saveAndFlush(item);
            return item;
        }
        return null;
    }
    
    @Override
    public void remove(Correspondant correspondant) {
        if (correspondant != null) {
            correspondantRepository.delete(correspondant);
        }
    }
    
    @Override
    public Correspondant update(Correspondant correspondant, String nom, String prenom, Integer tel, String email, Entreprise entreprise) {
        if (correspondant != null) {
            // Ensure validity from database
            correspondant = correspondantRepository.getReferenceById(correspondant.getId());
        }
        if (correspondant != null) {
            // Update data
            if (nom != null) {
                correspondant.setNom(nom);
            }
            if (prenom != null) {
                correspondant.setPrenom(prenom);
            }
            if (tel != null) {
                correspondant.setTel(tel);
            }
            if (email != null) {
                correspondant.setEmail(email);
            }
            if (entreprise != null) {
                correspondant.setEntreprise(entreprise);
            }
            
            // Save to database
            correspondantRepository.saveAndFlush(correspondant);
            // Ensure we have the last version
            correspondant = correspondantRepository.getReferenceById(correspondant.getId());
        }
        return correspondant;
    }
}
