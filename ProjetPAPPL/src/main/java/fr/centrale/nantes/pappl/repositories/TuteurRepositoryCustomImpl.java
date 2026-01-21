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
            if ((nbsujet != null)&&(nbsujet!=-1)){
                item.setNbSujetSouhaite(nbsujet);
            }
            tuteurRepository.saveAndFlush(item);
            return item;
        }

        return null;
    }
    
    @Override
    public void remove(Tuteur tuteurid){
        if(tuteurid !=null){
            tuteurid=tuteurRepository.getReferenceById(tuteurid.getId());
        }
        if(tuteurid!=null){
            tuteurRepository.delete(tuteurid);
        }
    }
    @Override
    public Tuteur update(Tuteur tu, String nom, String prenom, Integer nbsujet) {
        if (tu != null && tu.getId() != null) {
        // On travaille sur l'objet déjà rattaché à la session
            if (nom != null) {
                tu.setNom(nom);
            }
        if (prenom != null) {
            tu.setPrenom(prenom);
        }
        if (nbsujet != null) {
            tu.setNbSujetSouhaite(nbsujet);
        }
        tuteurRepository.saveAndFlush(tu);
        return tu;
        }
        return null;
    }

}
