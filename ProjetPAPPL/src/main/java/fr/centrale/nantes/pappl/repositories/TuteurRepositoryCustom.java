/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.Tuteur;


/**
 *
 * @author vadimdigonnet
 */
public interface TuteurRepositoryCustom {
    public Tuteur create(String nom, String prenom, Integer nbsujet);
    public void remove(Tuteur id);
    public Tuteur update(Tuteur tu, String nom, String prenom, Integer nbsujet);
}
