/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.ChoixEleveDomaine;
import java.util.List;

/**
 *
 * @author agach
 */
public interface ChoixEleveDomaineRepositoryCustom {
    List<ChoixEleveDomaine> findByEleveId(int eleveId);
}
