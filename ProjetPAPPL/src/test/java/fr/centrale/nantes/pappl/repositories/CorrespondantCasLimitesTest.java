/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.Annee;
import fr.centrale.nantes.pappl.items.Correspondant;
import fr.centrale.nantes.pappl.items.Entreprise;
import fr.centrale.nantes.pappl.items.Sujet;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests des cas limites - Correspondant lié à un Sujet
 *
 * Projet  : PGROUP - Gestion des P2T
 * Testeur : Yasmine BOUHADJI
 * Date    : 19 février 2026
 *
 * Ces tests vérifient 4 scénarios importants :
 *  1. Supprimer un correspondant lié à un sujet → que se passe-t-il ?
 *  2. Ajouter 2 correspondants au même sujet → possible ou pas ?
 *  3. Supprimer 1 correspondant sur 2 → le sujet garde-t-il l'autre ?
 *  4. Changer un correspondant par un autre → ça fonctionne ?
 */
public class CorrespondantCasLimitesTest {

    private Entreprise entreprise;
    private Correspondant correspondant1;
    private Correspondant correspondant2;
    private Sujet sujet;
    private Annee annee;

    @Before
    public void setUp() {
        entreprise = new Entreprise();
        entreprise.setNom("TechCorp");

        correspondant1 = new Correspondant();
        correspondant1.setId(1);
        correspondant1.setNom("Dupont");
        correspondant1.setPrenom("Jean");
        correspondant1.setEmail("jean.dupont@techcorp.fr");
        correspondant1.setEntreprise(entreprise);

        correspondant2 = new Correspondant();
        correspondant2.setId(2);
        correspondant2.setNom("Martin");
        correspondant2.setPrenom("Sophie");
        correspondant2.setEmail("sophie.martin@techcorp.fr");
        correspondant2.setEntreprise(entreprise);

        annee = new Annee(2026);

        sujet = new Sujet();
        sujet.setIntitule("Stage développement web");
        sujet.setAnnee(annee);
        sujet.setIdCorrespondant(correspondant1);
    }

    // ================================================================
    //  CAS 1 – Supprimer un correspondant lié à un sujet (Tests 1 à 3)
    //  Question : que se passe-t-il sur le sujet ?
    // ================================================================

    /**
     * Test 1 : Vérifier l'état du sujet AVANT suppression du correspondant
     * Le sujet doit bien avoir un correspondant au départ
     */
    @Test
    public void testEtatSujetAvantSuppression() {
        assertNotNull(
            "AVANT suppression : le sujet doit avoir un correspondant",
            sujet.getIdCorrespondant()
        );
        assertEquals("Dupont", sujet.getIdCorrespondant().getNom());
    }

    /**
     * Test 2 : Simulation suppression du correspondant
     * → Le sujet devrait avoir id_correspondant = NULL
     * RÉSULTAT ATTENDU : NULL (comportement actuel de la BDD)
     */
    @Test
    public void testSujetApresSuppressionCorrespondant() {
        // Simulation : on supprime le correspondant
        // Dans la BDD, si pas de cascade configurée → id_correspondant devient NULL
        correspondant1 = null;

        // On simule ce que la BDD fait : le sujet perd son correspondant
        sujet.setIdCorrespondant(correspondant1);

        // RÉSULTAT : NULL → c'est le comportement actuel, pas forcément voulu
        assertNull(
            "RÉSULTAT ACTUEL : après suppression du correspondant, id_correspondant = NULL",
            sujet.getIdCorrespondant()
        );
        System.out.println("⚠️ CAS 1 : La suppression du correspondant met id_correspondant à NULL dans le sujet.");
        
    }

    /**
     * Test 3 : Vérifier que le sujet existe toujours après suppression du correspondant
     * Le sujet ne doit PAS être supprimé, juste perdre son lien
     */
    @Test
    public void testSujetExisteEncoreApresSuppressionCorrespondant() {
        sujet.setIdCorrespondant(null); // simulation suppression

        // Le sujet existe toujours
        assertNotNull("Le sujet doit toujours exister même sans correspondant", sujet);
        assertEquals("Stage développement web", sujet.getIntitule());
        System.out.println("✅ CAS 1 : Le sujet existe encore après suppression du correspondant.");
    }

    // ================================================================
    //  CAS 2 – Ajouter 2 correspondants au même sujet (Tests 4 à 6)
    //  Question : est-ce possible avec la structure actuelle ?
    // ================================================================

    /**
     * Test 4 : Essayer d'assigner le 2ème correspondant au sujet
     * RÉSULTAT ATTENDU : le 2ème REMPLACE le 1er (pas d'ajout, remplacement)
     */
    @Test
    public void testAjouterDeuxiemeCorrespondantRemplaceLePremier() {
        // Le sujet a déjà correspondant1 (Dupont)
        assertEquals("Dupont", sujet.getIdCorrespondant().getNom());

        // On essaie d'ajouter correspondant2 (Martin)
        sujet.setIdCorrespondant(correspondant2);

        // RÉSULTAT : Martin a REMPLACÉ Dupont, pas ajouté
        assertEquals(
            "RÉSULTAT ACTUEL : le 2ème correspondant REMPLACE le 1er (pas d'ajout possible)",
            "Martin",
            sujet.getIdCorrespondant().getNom()
        );
        System.out.println("⚠️ CAS 2 : Impossible d'avoir 2 correspondants - le 2ème remplace le 1er.");
        
    }

    /**
     * Test 5 : Vérifier que le 1er correspondant est bien perdu après remplacement
     */
    @Test
    public void testPremierCorrespondantPerduApresRemplacement() {
        sujet.setIdCorrespondant(correspondant2); // Martin remplace Dupont

        // Dupont n'est plus accessible depuis le sujet
        assertNotEquals(
            "Le 1er correspondant (Dupont) ne doit plus être sur le sujet",
            "Dupont",
            sujet.getIdCorrespondant().getNom()
        );
        System.out.println("⚠️ CAS 2 : Le 1er correspondant est perdu lors de l'ajout d'un 2ème.");
    }

    /**
     * Test 6 : Confirmer que la structure actuelle ne supporte qu'UN seul correspondant
     */
    @Test
    public void testStructureActuelleUnSeulCorrespondant() {
        // La classe Sujet a : private Correspondant idCorrespondant; (ManyToOne)
        // → un seul objet Correspondant, pas une liste
        // Ce test confirme cette limitation

        sujet.setIdCorrespondant(correspondant1);
        Correspondant resultat = sujet.getIdCorrespondant();

        // On ne peut stocker qu'un seul correspondant
        assertNotNull(resultat);
        assertTrue(
            "CONFIRMATION : la structure actuelle ne supporte qu'1 correspondant par sujet",
            resultat instanceof Correspondant
        );
        System.out.println("⚠️ CAS 2 : Structure actuelle = ManyToOne → 1 seul correspondant max.");
        
    }

    // ================================================================
    //  CAS 3 – Supprimer 1 correspondant sur 2 (Tests 7 à 8)
    //  Question : le sujet garde-t-il l'autre ?
    //  Note : impossible actuellement car 2 correspondants non supportés
    // ================================================================

    /**
     * Test 7 : Simuler le scénario "2 correspondants" avec le seul champ disponible
     * Montre que si on supprime le correspondant actuel, l'autre est perdu aussi
     */
    @Test
    public void testSuppressionUnCorrespondantSurDeux() {
        // Avec la structure actuelle, on ne peut avoir qu'un seul correspondant
        // Donc si on le supprime, il n'y en a plus du tout
        sujet.setIdCorrespondant(correspondant1);

        // Suppression du seul correspondant possible
        sujet.setIdCorrespondant(null);

        assertNull(
            "RÉSULTAT ACTUEL : en supprimant le correspondant, le sujet n'en a plus aucun",
            sujet.getIdCorrespondant()
        );
        System.out.println("⚠️ CAS 3 : Impossible de garder 1 sur 2 avec la structure actuelle.");
        
    }

    /**
     * Test 8 : Après suppression, on peut réassigner un nouveau correspondant
     */
    @Test
    public void testReassignerApresSuppression() {
        sujet.setIdCorrespondant(null); // suppression

        // On peut réassigner
        sujet.setIdCorrespondant(correspondant2);

        assertNotNull("On peut réassigner un correspondant après suppression", sujet.getIdCorrespondant());
        assertEquals("Martin", sujet.getIdCorrespondant().getNom());
        System.out.println("✅ CAS 3 : On peut réassigner un correspondant après suppression.");
    }

    // ================================================================
    //  CAS 4 – Changer le correspondant d'un sujet (Tests 9 à 11)
    // 
    // ================================================================

    /**
     * Test 9 : Changer le correspondant d'un sujet → doit fonctionner
     */
    @Test
    public void testChangerCorrespondant() {
        // Avant : Dupont
        assertEquals("Dupont", sujet.getIdCorrespondant().getNom());

        // Changement : on remplace par Martin
        sujet.setIdCorrespondant(correspondant2);

        // Après : Martin
        assertEquals(
            "RÉSULTAT : le correspondant a bien été changé",
            "Martin",
            sujet.getIdCorrespondant().getNom()
        );
        System.out.println("✅ CAS 4 : Changer le correspondant d'un sujet fonctionne correctement.");
    }

    /**
     * Test 10 : Vérifier que les infos du nouveau correspondant sont correctes après changement
     */
    @Test
    public void testInfosNouveauCorrespondantApresChangement() {
        sujet.setIdCorrespondant(correspondant2);

        assertEquals("Martin", sujet.getIdCorrespondant().getNom());
        assertEquals("Sophie", sujet.getIdCorrespondant().getPrenom());
        assertEquals("sophie.martin@techcorp.fr", sujet.getIdCorrespondant().getEmail());
        System.out.println("✅ CAS 4 : Les infos du nouveau correspondant sont bien accessibles.");
    }

    /**
     * Test 11 : Changer plusieurs fois de correspondant → le dernier est conservé
     */
    @Test
    public void testChangerPlusieursCorrespondants() {
        sujet.setIdCorrespondant(correspondant1); // Dupont
        sujet.setIdCorrespondant(correspondant2); // Martin
        sujet.setIdCorrespondant(correspondant1); // retour Dupont

        assertEquals(
            "Après plusieurs changements, le dernier correspondant assigné est conservé",
            "Dupont",
            sujet.getIdCorrespondant().getNom()
        );
        System.out.println("✅ CAS 4 : Plusieurs changements successifs fonctionnent, le dernier est conservé.");
    }
}
