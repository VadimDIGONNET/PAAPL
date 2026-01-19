/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

/**
 *
 * @author vadimdigonnet
 */
import fr.centrale.nantes.pappl.items.Tuteur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
@Repository
public class TuteurRepositoryCustomImpl implements TuteurRepositoryCustom{
    @Autowired
    @Lazy
    private TuteurRepository tuteurRepository;
    
    @Override
    public Tuteur create(String nom, String prenom, Integer nbsujet){
        if((nom!=null)&&(!nom.isEmpty())){
            Tuteur item=new Tuteur();
            item.setNom(nom);
            if (prenom != null){
                item.setPrenom(prenom);
            }
            if (nbsujet != null){
                item.setNbSujetSouhaite(nbsujet);
            }
            tuteurRepository.saveAndFlush(item);
            return item;
        }

        return null;
    }
    
    @Override
    public void remove(Tuteur nom) {
        /**if (nom != null) {
            // Ensure valididy from database
            nom = entrepriseRepository.getReferenceByID(nom.getNom());
        }**/
        if (nom != null) {
            tuteurRepository.delete(nom);
        }
    }
    
    @Override
    public Tuteur update(Integer id, String nom, String prenom, Integer nbsujet) {
        if (id != null) {
            Tuteur tuteur=new Tuteur();
            tuteur = tuteurRepository.getReferenceById(id);
            if (nom != null){
                tuteur.setNom(nom);
            }
            if (prenom != null){
                tuteur.setPrenom(prenom);
            }
            if (nbsujet != null){
            tuteur.setNbSujetSouhaite(nbsujet);
            }
            tuteurRepository.saveAndFlush(tuteur);
            tuteur = tuteurRepository.getReferenceById(tuteur.getId());
        }
        Tuteur tuteur=new Tuteur();
        tuteur = tuteurRepository.getReferenceById(id);
        return tuteur;
    }

}
