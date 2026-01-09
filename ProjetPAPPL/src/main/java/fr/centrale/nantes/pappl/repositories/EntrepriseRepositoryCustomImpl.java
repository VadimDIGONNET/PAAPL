/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

/**
 *
 * @author vadimdigonnet
 */
import fr.centrale.nantes.pappl.items.Entreprise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
@Repository
public class EntrepriseRepositoryCustomImpl implements EntrepriseRepositoryCustom{
    @Autowired
    @Lazy
    private EntrepriseRepository entrepriseRepository;
    
    @Override
    public Entreprise create(String nom, String adresse, Integer codePostal, Integer tel, String siteWeb, String description){
        if((nom!=null)&&(!nom.isEmpty())){
            Entreprise item=new Entreprise();
            item.setNom(nom);
            if (adresse != null){
                item.setAdresse(adresse);
            }
            if (codePostal != null){
                item.setCodePostal(codePostal);
            }
            if (tel != null){
            item.setTel(tel);
            }
            if (siteWeb != null){
            item.setSiteWeb(siteWeb);
            }
            if (description != null){
            item.setDescription(description);
            }       
            entrepriseRepository.saveAndFlush(item);
            return item;
        }

        return null;
    }
    
    @Override
    public void remove(Entreprise nom) {
        /**if (nom != null) {
            // Ensure valididy from database
            nom = entrepriseRepository.getReferenceByID(nom.getNom());
        }**/
        if (nom != null) {
            entrepriseRepository.delete(nom);
        }
    }
    
    @Override
    public Entreprise update(Entreprise entreprise, String adresse, Integer codePostal, Integer tel, String siteWeb, String description) {
        if (entreprise != null) {
            // Ensure valididy from database
            entreprise = entrepriseRepository.getReferenceById(entreprise.getNom());
        }
        if ((entreprise != null)) {
            // Update data
            if (adresse != null){
                entreprise.setAdresse(adresse);
            }
            if (codePostal != null){
                entreprise.setCodePostal(codePostal);
            }
            if (tel != null){
            entreprise.setTel(tel);
            }
            if (siteWeb != null){
            entreprise.setSiteWeb(siteWeb);
            }
            if (description != null){
            entreprise.setDescription(description);
            }       
            // Save to database
            entrepriseRepository.saveAndFlush(entreprise);
            // Ensure we have the last version
            entreprise = entrepriseRepository.getReferenceById(entreprise.getNom());
        }
        return entreprise;
    }

}

