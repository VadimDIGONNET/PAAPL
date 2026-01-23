/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.ChoixEleveTypo;
import java.util.List;

/**
 *
 * @author agach
 */
public interface ChoixEleveTypoRepositoryCustom {
    List<ChoixEleveTypo> findByEleveId(int eleveId);
}
