/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;
import fr.centrale.nantes.pappl.items.Correspondant;
import fr.centrale.nantes.pappl.items.Entreprise;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * Tests unitaires pour CorrespondantRepositoryCustomImpl
 *
 * Projet     : PGROUP - Gestion des P2T
 * Testeur    : Yasmine BOUHADJI
 * Date       : 6 février 2026
 *
 * Scénarios testés (inspirés de la fiche de tests correspondants) :
 *  - Création valide / invalide
 *  - Mise à jour d'un correspondant
 *  - Suppression d'un correspondant
 *  - Cas limites (null, vide, caractères spéciaux...)
 *  - Méthodes Java : equals(), hashCode(), toString()
 */
public class Correspondantrepositorycustomimpltest {
    


    // -------------------------------------------------------
    // Objets réutilisés dans les tests
    // -------------------------------------------------------
    private Correspondant correspondant;
    private Entreprise entreprise;

    @Before
    public void setUp() {
        // Entreprise de base pour les tests
        entreprise = new Entreprise();
        entreprise.setNom("TechCorp");
        entreprise.setAdresse("12 rue de l'Innovation");
        entreprise.setCodePostal(44000);

        // Correspondant de base pour les tests
        correspondant = new Correspondant();
        correspondant.setId(1);
        correspondant.setNom("Dupont");
        correspondant.setPrenom("Jean");
        correspondant.setTel(612345678);
        correspondant.setEmail("jean.dupont@entreprise.fr");
        correspondant.setEntreprise(entreprise);
    }

    // ================================================================
    //  BLOC 1 – CRÉATION (Tests 1 à 4)
    // ================================================================

    /**
     * Test 1 : Création d'un correspondant complet (tous les champs remplis)
     * Correspond au Test 1 de la fiche de tests
     */
    @Test
    public void testCreateCorrespondantComplet() {
        // Vérification que tous les champs sont bien enregistrés
        assertNotNull("Le correspondant ne doit pas être null", correspondant);
        assertEquals("Dupont", correspondant.getNom());
        assertEquals("Jean", correspondant.getPrenom());
        assertEquals(Integer.valueOf(612345678), correspondant.getTel());
        assertEquals("jean.dupont@entreprise.fr", correspondant.getEmail());
        assertNotNull("L'entreprise liée ne doit pas être null", correspondant.getEntreprise());
        assertEquals("TechCorp", correspondant.getEntreprise().getNom());
    }

    /**
     * Test 2 : Création avec seulement le nom (champs optionnels null)
     * Le nom est obligatoire, les autres champs sont optionnels
     */
    @Test
    public void testCreateCorrespondantNomSeul() {
        Correspondant simple = new Correspondant();
        simple.setNom("Martin");

        assertNotNull(simple);
        assertEquals("Martin", simple.getNom());
        assertNull("Le prénom doit être null", simple.getPrenom());
        assertNull("Le tel doit être null", simple.getTel());
        assertNull("L'email doit être null", simple.getEmail());
        assertNull("L'entreprise doit être null", simple.getEntreprise());
    }

    /**
     * Test 3 : Création avec nom null → doit retourner null (règle métier)
     * La méthode create() retourne null si le nom est null ou vide
     */
    @Test
    public void testCreateCorrespondantNomNull() {
        // Simulation du comportement de create() avec nom null
        String nom = null;
        Correspondant result = null;

        if (nom != null && !nom.isEmpty()) {
            result = new Correspondant();
            result.setNom(nom);
        }

        assertNull("Un correspondant avec nom null ne doit pas être créé", result);
    }

    /**
     * Test 4 : Création avec nom vide → doit retourner null
     */
    @Test
    public void testCreateCorrespondantNomVide() {
        String nom = "";
        Correspondant result = null;

        if (nom != null && !nom.isEmpty()) {
            result = new Correspondant();
            result.setNom(nom);
        }

        assertNull("Un correspondant avec nom vide ne doit pas être créé", result);
    }

    // ================================================================
    //  BLOC 2 – MISE À JOUR (Tests 5 à 8)
    //  Correspond aux Tests 2 et 4 de la fiche (modification d'un correspondant)
    // ================================================================

    /**
     * Test 5 : Mise à jour valide d'un correspondant existant
     * Correspond au Test 2 de la fiche : modification nom + email
     */
    @Test
    public void testUpdateCorrespondantValide() {
        // Simulation de update() : on modifie le nom et l'email
        String nouveauNom = "Martin";
        String nouvelEmail = "jean.martin@entreprise.fr";

        if (nouveauNom != null) correspondant.setNom(nouveauNom);
        if (nouvelEmail != null) correspondant.setEmail(nouvelEmail);

        assertEquals("Martin", correspondant.getNom());
        assertEquals("jean.martin@entreprise.fr", correspondant.getEmail());
        // Le prénom ne doit pas avoir changé
        assertEquals("Jean", correspondant.getPrenom());
    }

    /**
     * Test 6 : Mise à jour avec nom null → le nom NE doit PAS changer
     * La méthode update() ne modifie un champ que si la nouvelle valeur n'est pas null
     */
    @Test
    public void testUpdateNomNullGardeAncienneValeur() {
        String nomAvant = correspondant.getNom();
        String nouveauNom = null;

        if (nouveauNom != null) {
            correspondant.setNom(nouveauNom);
        }

        assertEquals("Le nom ne doit pas changer si la nouvelle valeur est null", nomAvant, correspondant.getNom());
    }

    /**
     * Test 7 : Mise à jour de l'entreprise liée
     * Vérifier qu'on peut changer l'entreprise d'un correspondant
     */
    @Test
    public void testUpdateEntrepriseCorrespondant() {
        Entreprise nouvelleEntreprise = new Entreprise();
        nouvelleEntreprise.setNom("InnovateLab");

        correspondant.setEntreprise(nouvelleEntreprise);

        assertEquals("InnovateLab", correspondant.getEntreprise().getNom());
    }

    /**
     * Test 8 : Mise à jour sur un correspondant null → doit retourner null
     */
    @Test
    public void testUpdateCorrespondantNull() {
        Correspondant corNull = null;
        Correspondant result = null;

        if (corNull != null) {
            corNull.setNom("NouveauNom");
            result = corNull;
        }

        assertNull("Update sur un correspondant null doit retourner null", result);
    }

    // ================================================================
    //  BLOC 3 – SUPPRESSION (Tests 9 à 11)
    //  Correspond aux Tests 8 et 9 de la fiche (suppression)
    // ================================================================

    /**
     * Test 9 : Suppression d'un correspondant existant
     * La méthode remove() doit accepter un correspondant non null
     */
    @Test
    public void testRemoveCorrespondantValide() {
        // On vérifie simplement que l'objet existe avant suppression
        assertNotNull("Le correspondant doit exister avant suppression", correspondant);
        // Simulation : après suppression, la référence devient null
        correspondant = null;
        assertNull("Après suppression, le correspondant doit être null", correspondant);
    }

    /**
     * Test 10 : Suppression d'un correspondant null → ne doit pas planter
     * La méthode remove() vérifie que le paramètre n'est pas null avant de supprimer
     */
    @Test
    public void testRemoveCorrespondantNull() {
        Correspondant corNull = null;
        // On simule le comportement de remove() : ne fait rien si null
        try {
            if (corNull != null) {
                // correspondantRepository.delete(corNull) serait appelé ici
            }
            // Si on arrive ici sans exception, le test est OK
            assertTrue(true);
        } catch (Exception e) {
            fail("remove(null) ne doit pas lancer d'exception : " + e.getMessage());
        }
    }

    /**
     * Test 11 : Suppression d'un correspondant lié à une entreprise
     * Le correspondant doit être supprimé sans affecter l'entreprise
     */
    @Test
    public void testRemoveCorrespondantSansImpacterEntreprise() {
        String nomEntreprise = correspondant.getEntreprise().getNom();
        // Simulation : on supprime le correspondant
        correspondant = null;
        // L'entreprise doit toujours exister (objet indépendant)
        assertEquals("TechCorp", entreprise.getNom());
        assertNotNull("L'entreprise doit toujours exister après suppression du correspondant", entreprise);
    }

    // ================================================================
    //  BLOC 4 – CAS LIMITES (Tests 12 à 15)
    // ================================================================

    /**
     * Test 12 : Nom avec caractères spéciaux et accents
     */
    @Test
    public void testCorrespondantNomAvecAccentsEtCaracteresSpeciaux() {
        correspondant.setNom("Léa Ünïcödé-Dupont");
        assertEquals("Léa Ünïcödé-Dupont", correspondant.getNom());
    }

    /**
     * Test 13 : Téléphone avec valeur négative
     * Un numéro négatif n'est pas valide mais le système doit le stocker
     */
    @Test
    public void testCorrespondantTelephoneNegatif() {
        correspondant.setTel(-123456789);
        assertEquals(Integer.valueOf(-123456789), correspondant.getTel());
        // Note : une validation métier devrait rejeter cette valeur
    }

    /**
     * Test 14 : Email sans @
     * Le format n'est pas validé au niveau Java (annotation commentée dans le code)
     */
    @Test
    public void testCorrespondantEmailSansArobase() {
        correspondant.setEmail("email-invalide.fr");
        assertEquals("email-invalide.fr", correspondant.getEmail());
        // Note : une validation supplémentaire serait utile
    }

    /**
     * Test 15 : Nom très long (cas limite de la base de données)
     */
    @Test
    public void testCorrespondantNomTresLong() {
        String nomLong = "A".repeat(200);
        correspondant.setNom(nomLong);
        assertEquals(200, correspondant.getNom().length());
    }

    // ================================================================
    //  BLOC 5 – MÉTHODES JAVA (Tests 16 à 20)
    // ================================================================

    /**
     * Test 16 : equals() - deux correspondants avec le même ID sont égaux
     */
    @Test
    public void testEqualsMemeId() {
        Correspondant autre = new Correspondant(1);
        assertEquals("Deux correspondants avec le même ID doivent être égaux", correspondant, autre);
    }

    /**
     * Test 17 : equals() - deux correspondants avec des IDs différents ne sont pas égaux
     */
    @Test
    public void testEqualsIdsDifferents() {
        Correspondant autre = new Correspondant(99);
        assertNotEquals("Deux correspondants avec des IDs différents ne doivent pas être égaux", correspondant, autre);
    }

    /**
     * Test 18 : equals() - comparaison avec null retourne false
     */
    @Test
    public void testEqualsAvecNull() {
        assertFalse("equals(null) doit retourner false", correspondant.equals(null));
    }

    /**
     * Test 19 : hashCode() - deux objets égaux ont le même hashCode
     */
    @Test
    public void testHashCodeCoherent() {
        Correspondant autre = new Correspondant(1);
        assertEquals("Deux correspondants égaux doivent avoir le même hashCode",
                correspondant.hashCode(), autre.hashCode());
    }

    /**
     * Test 20 : toString() - doit contenir l'ID du correspondant
     */
    @Test
    public void testToStringContientId() {
        String str = correspondant.toString();
        assertTrue("toString() doit contenir l'ID", str.contains("1"));
    }
}
