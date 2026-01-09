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
        if((nom!=null)&&(!nom.isEmpty())&&(adresse!=null)&&(!adresse.isEmpty())){
            Entreprise item=new Entreprise();
            item.setNom(nom);
            item.setAdresse(adresse);
            item.setCodePostal(codePostal);
            item.setTel(tel);
            item.setSiteWeb(siteWeb);
            item.setDescription(description);
            entrepriseRepository.saveAndFlush(item);
            return item;
        }

        return null;
    }
}

