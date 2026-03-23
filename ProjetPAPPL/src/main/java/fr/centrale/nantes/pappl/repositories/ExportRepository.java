/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.Eleve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExportRepository extends JpaRepository<Eleve, Integer> {

    @Query(value = "SELECT " +
           "    s.numero, " + 
           "    ent.nom as ent_nom, " + 
           "    s.intitule, " + 
           "    ent.adresse, " + 
           "    ent.code_postal, " + 
           "    c.nom as cor_nom, " + 
           "    c.tel, " + 
           "    c.email, " + 
           "    el1.nom as e1n, el2.nom as e2n, el3.nom as e3n, el4.nom as e4n, el5.nom as e5n, el6.nom as e6n, el7.nom as e7n, " + 
           "    el1.email as e1e, el2.email as e2e, el3.email as e3e, el4.email as e4e, el5.email as e5e, el6.email as e6e, el7.email as e7e " + 
           "FROM sujet s " +
           "JOIN correspondant c ON c.id = s.id_correspondant " +
           "JOIN entreprise ent ON ent.nom = c.entreprise " +
           "LEFT JOIN (SELECT *, ROW_NUMBER() OVER(PARTITION BY numero_sujet ORDER BY nom) as rn FROM eleve) el1 ON el1.numero_sujet = s.numero AND el1.rn = 1 " +
           "LEFT JOIN (SELECT *, ROW_NUMBER() OVER(PARTITION BY numero_sujet ORDER BY nom) as rn FROM eleve) el2 ON el2.numero_sujet = s.numero AND el2.rn = 2 " +
           "LEFT JOIN (SELECT *, ROW_NUMBER() OVER(PARTITION BY numero_sujet ORDER BY nom) as rn FROM eleve) el3 ON el3.numero_sujet = s.numero AND el3.rn = 3 " +
           "LEFT JOIN (SELECT *, ROW_NUMBER() OVER(PARTITION BY numero_sujet ORDER BY nom) as rn FROM eleve) el4 ON el4.numero_sujet = s.numero AND el4.rn = 4 " +
           "LEFT JOIN (SELECT *, ROW_NUMBER() OVER(PARTITION BY numero_sujet ORDER BY nom) as rn FROM eleve) el5 ON el5.numero_sujet = s.numero AND el5.rn = 5 " +
           "LEFT JOIN (SELECT *, ROW_NUMBER() OVER(PARTITION BY numero_sujet ORDER BY nom) as rn FROM eleve) el6 ON el6.numero_sujet = s.numero AND el6.rn = 6 " +
           "LEFT JOIN (SELECT *, ROW_NUMBER() OVER(PARTITION BY numero_sujet ORDER BY nom) as rn FROM eleve) el7 ON el7.numero_sujet = s.numero AND el7.rn = 7", 
           nativeQuery = true)
    List<Object[]> fetchDataForExcel();
}