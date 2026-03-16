package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.Annee;
import fr.centrale.nantes.pappl.items.Correspondant;
import fr.centrale.nantes.pappl.items.Entreprise;
import fr.centrale.nantes.pappl.items.Sujet;
import fr.centrale.nantes.pappl.items.Tuteur;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests des relations entre objets du projet P2T.
 *
 * <p>Ce fichier teste les relations suivantes :</p>
 * <ul>
 *   <li><b>R1 à R4</b> — Relation {@link Sujet} &lt;-&gt; {@link Tuteur}</li>
 *   <li><b>R5 à R8</b> — Relation {@link Sujet} &lt;-&gt;
 *       {@link Correspondant} &lt;-&gt; {@link Entreprise}</li>
 * </ul>
 *
 * <p>Approche : tests sans base de données (objets Java en mémoire uniquement).
 * Aucune connexion à PostgreSQL n'est requise.</p>
 *
 * @author   Safa BOUZIDI
 * @version 1.0
 * @since   15 mars 2026
 *
 * <pre>
 * Technologie        Version
 * Langage            Java 17
 * Framework de test  JUnit 4.13.2
 * Outil de build     Maven 3.x
 * IDE                NetBeans
 * </pre>
 */
public class RelationsTest {

    /** Sujet numéro 1 utilisé dans les tests. */
    private Sujet sujet1;

    /** Sujet numéro 2 utilisé dans les tests. */
    private Sujet sujet2;

    /** Tuteur commun utilisé dans les tests R1 à R4. */
    private Tuteur tuteur;

    /** Premier correspondant lié à sujet1 et à entreprise TechCorp. */
    private Correspondant correspondant1;

    /** Deuxième correspondant lié à sujet2 et à entreprise TechCorp. */
    private Correspondant correspondant2;

    /** Entreprise partagée par les deux correspondants. */
    private Entreprise entreprise;

    /** Année académique 2026 utilisée pour tous les sujets. */
    private Annee annee;

    // ================================================================
    //  MÉTHODES UTILITAIRES D'AFFICHAGE
    // ================================================================

    /**
     * Affiche l'en-tête structuré d'un test dans la console.
     *
     * @param numero   identifiant du test (ex. "R1", "R5")
     * @param scenario description courte du scénario testé
     * @param donnees  description des données d'entrée utilisées
     * @param attendu  résultat attendu du test
     */
    private void afficherEntete(String numero, String scenario,
                                String donnees, String attendu) {
        System.out.println();
        System.out.println("+===========================================================+");
        System.out.printf("  TEST %-4s — %s%n", numero, scenario);
        System.out.println("+-----------------------------------------------------------+");
        System.out.println("  Scenario  : " + scenario);
        System.out.println("  Donnees   : " + donnees);
        System.out.println("  Attendu   : " + attendu);
        System.out.println("+-----------------------------------------------------------+");
    }

    /**
     * Affiche le résultat d'un test dans la console.
     *
     * @param succes  {@code true} si le test est réussi, {@code false} sinon
     * @param details informations complémentaires sur le résultat observé
     */
    private void afficherResultat(boolean succes, String details) {
        String statut = succes ? "REUSSI" : "ECHEC ";
        System.out.println("  Resultat  : " + statut + " | " + details);
        System.out.println("+===========================================================+");
    }

    // ================================================================
    //  INITIALISATION ET NETTOYAGE
    // ================================================================

    /**
     * Initialise les objets communs avant chaque test.
     *
     * <p>Crée :</p>
     * <ul>
     *   <li>Une {@link Annee} 2026</li>
     *   <li>Une {@link Entreprise} "TechCorp"</li>
     *   <li>Deux {@link Correspondant} liés à TechCorp</li>
     *   <li>Deux {@link Sujet} avec numéros distincts (1 et 2)</li>
     *   <li>Un {@link Tuteur} "Bernard Paul"</li>
     * </ul>
     */
    @Before
    public void setUp() {
        System.out.println();
        System.out.println("############################################################");
        System.out.println("#              INITIALISATION DU TEST                      #");
        System.out.println("############################################################");

        annee = new Annee(2026);

        entreprise = new Entreprise("TechCorp");
        entreprise.setAdresse("12 rue de l'Innovation");
        entreprise.setCodePostal(44000);

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

        // IMPORTANT : numéros distincts pour que equals() fonctionne
        sujet1 = new Sujet();
        sujet1.setNumero(1);
        sujet1.setIntitule("Stage developpement web");
        sujet1.setAnnee(annee);
        sujet1.setIdCorrespondant(correspondant1);

        sujet2 = new Sujet();
        sujet2.setNumero(2);
        sujet2.setIntitule("Stage intelligence artificielle");
        sujet2.setAnnee(annee);
        sujet2.setIdCorrespondant(correspondant2);

        tuteur = new Tuteur();
        tuteur.setId(1);
        tuteur.setNom("Bernard");
        tuteur.setPrenom("Paul");
        tuteur.setNbSujetSouhaite(2);

        System.out.println("  Sujet1    : [" + sujet1.getNumero() + "] "
                + sujet1.getIntitule());
        System.out.println("  Sujet2    : [" + sujet2.getNumero() + "] "
                + sujet2.getIntitule());
        System.out.println("  Tuteur    : "
                + tuteur.getNom() + " " + tuteur.getPrenom());
        System.out.println("  Corr.1    : "
                + correspondant1.getNom() + " -> " + entreprise.getNom());
        System.out.println("  Corr.2    : "
                + correspondant2.getNom() + " -> " + entreprise.getNom());
        System.out.println("############################################################");
    }

    /**
     * Affiche un séparateur de fin après chaque test.
     */
    @After
    public void tearDown() {
        System.out.println("------------------------------------------------------------");
        System.out.println("  FIN DU TEST");
        System.out.println("------------------------------------------------------------");
    }

    // ================================================================
    //  RELATION SUJET <-> TUTEUR  (R1 à R4)
    // ================================================================

    /**
     * <b>R1</b> — Assigner un tuteur à un sujet.
     *
     * <p>Vérifie que lorsqu'on ajoute sujet1 à la collection du tuteur,
     * le tuteur possède bien 1 sujet et ce sujet est accessible.</p>
     *
     * <ul>
     *   <li>Données : Tuteur "Bernard" + Sujet "Stage developpement web"</li>
     *   <li>Attendu : getSujetCollection().size() = 1, contains(sujet1) = true</li>
     * </ul>
     */
    @Test
    public void testR1AssignerTuteurAuSujet() {
        afficherEntete(
            "R1",
            "Assigner un tuteur a un sujet",
            "Tuteur 'Bernard' + Sujet [1] 'Stage developpement web'",
            "getSujetCollection().size()=1 | contains(sujet1)=true"
        );

        List<Sujet> sujets = new ArrayList<>();
        sujets.add(sujet1);
        tuteur.setSujetCollection(sujets);

        boolean taille = tuteur.getSujetCollection().size() == 1;
        boolean contient = tuteur.getSujetCollection().contains(sujet1);

        afficherResultat(taille && contient,
            "taille=" + tuteur.getSujetCollection().size()
            + " | contient sujet1=" + contient);

        assertNotNull("La collection ne doit pas etre null",
                tuteur.getSujetCollection());
        assertTrue("Le sujet doit etre dans la collection",
                contient);
        assertEquals("La taille doit etre 1",
                1, tuteur.getSujetCollection().size());
    }

    /**
     * <b>R2</b> — Un tuteur peut avoir plusieurs sujets.
     *
     * <p>Vérifie qu'un tuteur peut référencer 2 sujets distincts
     * dans sa collection.</p>
     *
     * <ul>
     *   <li>Données : 1 tuteur avec sujet1 + sujet2</li>
     *   <li>Attendu : getSujetCollection().size() = 2</li>
     * </ul>
     */
    @Test
    public void testR2TuteurPlusieursSujets() {
        afficherEntete(
            "R2",
            "Un tuteur peut avoir plusieurs sujets",
            "1 tuteur <- sujet1 [1] + sujet2 [2]",
            "getSujetCollection().size() = 2"
        );

        List<Sujet> sujets = new ArrayList<>();
        sujets.add(sujet1);
        sujets.add(sujet2);
        tuteur.setSujetCollection(sujets);

        boolean ok = tuteur.getSujetCollection().size() == 2;

        afficherResultat(ok,
            "taille=" + tuteur.getSujetCollection().size()
            + " | contient sujet1=" + tuteur.getSujetCollection().contains(sujet1)
            + " | contient sujet2=" + tuteur.getSujetCollection().contains(sujet2));

        assertEquals("La taille doit etre 2",
                2, tuteur.getSujetCollection().size());
    }

    /**
     * <b>R3</b> — Supprimer un sujet de la collection du tuteur.
     *
     * <p>Vérifie que la suppression de sujet1 retire bien cet élément
     * sans affecter sujet2.</p>
     *
     * <ul>
     *   <li>Données : tuteur avec {sujet1, sujet2}, puis remove(sujet1)</li>
     *   <li>Attendu : sujet1 absent, sujet2 présent, taille = 1</li>
     * </ul>
     */
    @Test
    public void testR3SupprimerSujetDuTuteur() {
        afficherEntete(
            "R3",
            "Supprimer un sujet de la collection du tuteur",
            "remove(sujet1) — tuteur avait {sujet1, sujet2}",
            "sujet1 absent | sujet2 present | taille=1"
        );

        List<Sujet> sujets = new ArrayList<>();
        sujets.add(sujet1);
        sujets.add(sujet2);
        tuteur.setSujetCollection(sujets);

        tuteur.getSujetCollection().remove(sujet1);

        boolean sujet1Absent  = !tuteur.getSujetCollection().contains(sujet1);
        boolean sujet2Present =  tuteur.getSujetCollection().contains(sujet2);
        boolean tailleOk      =  tuteur.getSujetCollection().size() == 1;

        afficherResultat(sujet1Absent && sujet2Present && tailleOk,
            "sujet1 present=" + !sujet1Absent
            + " | sujet2 present=" + sujet2Present
            + " | taille=" + tuteur.getSujetCollection().size());

        assertFalse("sujet1 ne doit plus etre dans la collection", !sujet1Absent);
        assertTrue("sujet2 doit toujours etre dans la collection", sujet2Present);
        assertEquals("Il doit rester 1 sujet", 1,
                tuteur.getSujetCollection().size());
    }

    /**
     * <b>R4</b> — Tuteur sans sujet assigné.
     *
     * <p>Vérifie qu'une collection vide est correctement gérée.</p>
     *
     * <ul>
     *   <li>Données : setSujetCollection(new ArrayList vide)</li>
     *   <li>Attendu : getSujetCollection().isEmpty() = true</li>
     * </ul>
     */
    @Test
    public void testR4TuteurSansSujet() {
        afficherEntete(
            "R4",
            "Tuteur sans sujet assigne",
            "setSujetCollection(collection vide)",
            "getSujetCollection().isEmpty() = true"
        );

        tuteur.setSujetCollection(new ArrayList<>());

        boolean ok = tuteur.getSujetCollection().isEmpty();

        afficherResultat(ok,
            "isEmpty=" + tuteur.getSujetCollection().isEmpty()
            + " | taille=" + tuteur.getSujetCollection().size());

        assertNotNull("La collection ne doit pas etre null",
                tuteur.getSujetCollection());
        assertTrue("La collection doit etre vide",
                tuteur.getSujetCollection().isEmpty());
    }

    // ================================================================
    //  RELATION SUJET <-> CORRESPONDANT <-> ENTREPRISE  (R5 à R8)
    // ================================================================

    /**
     * <b>R5</b> — Accéder à l'entreprise depuis un sujet via le correspondant.
     *
     * <p>Vérifie le chaînage :
     * sujet1 → getIdCorrespondant() → getEntreprise() → getNom().</p>
     *
     * <ul>
     *   <li>Données : sujet1 -> correspondant1 (Dupont) -> TechCorp</li>
     *   <li>Attendu : getNom() = "TechCorp"</li>
     * </ul>
     */
    @Test
    public void testR5AccederEntrepriseDepuisSujet() {
        afficherEntete(
            "R5",
            "Acceder a l'entreprise depuis un sujet via le correspondant",
            "sujet1 -> correspondant1 (Dupont) -> entreprise",
            "getNom() = 'TechCorp'"
        );

        String nomEntreprise = sujet1.getIdCorrespondant()
                                     .getEntreprise().getNom();
        boolean ok = "TechCorp".equals(nomEntreprise);

        afficherResultat(ok,
            "correspondant=" + sujet1.getIdCorrespondant().getNom()
            + " | entreprise=" + nomEntreprise);

        assertNotNull("Le correspondant ne doit pas etre null",
                sujet1.getIdCorrespondant());
        assertNotNull("L'entreprise ne doit pas etre null",
                sujet1.getIdCorrespondant().getEntreprise());
        assertEquals("Le nom de l'entreprise doit etre 'TechCorp'",
                "TechCorp", nomEntreprise);
    }

    /**
     * <b>R6</b> — Changer l'entreprise du correspondant d'un sujet.
     *
     * <p>Vérifie que le sujet reflète immédiatement le changement
     * d'entreprise sur son correspondant (relation par référence).</p>
     *
     * <ul>
     *   <li>Données : correspondant1.setEntreprise("InnovateLab")</li>
     *   <li>Attendu : sujet1 voit "InnovateLab" via son correspondant</li>
     * </ul>
     */
    @Test
    public void testR6ChangerEntrepriseCorrespondant() {
        afficherEntete(
            "R6",
            "Changer l'entreprise du correspondant d'un sujet",
            "correspondant1.setEntreprise(new Entreprise('InnovateLab'))",
            "sujet1.getIdCorrespondant().getEntreprise().getNom() = 'InnovateLab'"
        );

        String avantChangement = sujet1.getIdCorrespondant()
                                       .getEntreprise().getNom();

        Entreprise nouvelleEntreprise = new Entreprise("InnovateLab");
        correspondant1.setEntreprise(nouvelleEntreprise);

        String apresChangement = sujet1.getIdCorrespondant()
                                       .getEntreprise().getNom();
        boolean ok = "InnovateLab".equals(apresChangement);

        afficherResultat(ok,
            "avant=" + avantChangement
            + " | apres=" + apresChangement);

        assertEquals("L'entreprise doit etre 'InnovateLab' apres changement",
                "InnovateLab", apresChangement);
    }

    /**
     * <b>R7</b> — Correspondant sans entreprise (null).
     *
     * <p>Vérifie qu'affecter {@code null} à l'entreprise d'un correspondant
     * ne provoque pas d'exception et que le sujet continue d'exister.</p>
     *
     * <ul>
     *   <li>Données : correspondant1.setEntreprise(null)</li>
     *   <li>Attendu : getEntreprise() = null, aucune exception levée</li>
     * </ul>
     */
    @Test
    public void testR7CorrespondantSansEntreprise() {
        afficherEntete(
            "R7",
            "Correspondant sans entreprise (null)",
            "correspondant1.setEntreprise(null)",
            "getEntreprise() = null | pas d'exception | sujet1 intact"
        );

        correspondant1.setEntreprise(null);

        boolean entrepriseNull    = correspondant1.getEntreprise() == null;
        boolean sujetExiste       = sujet1 != null;
        boolean correspondantExiste = sujet1.getIdCorrespondant() != null;

        afficherResultat(entrepriseNull,
            "getEntreprise()=null : " + entrepriseNull
            + " | sujet1 intact : " + sujetExiste
            + " | correspondant intact : " + correspondantExiste);

        assertNull("L'entreprise doit etre null",
                correspondant1.getEntreprise());
        assertNotNull("Le sujet doit toujours exister", sujet1);
        assertNotNull("Le correspondant du sujet doit toujours exister",
                sujet1.getIdCorrespondant());
    }

    /**
     * <b>R8</b> — Deux correspondants de la même entreprise sur deux sujets différents.
     *
     * <p>Vérifie que deux sujets distincts, ayant chacun un correspondant
     * différent mais rattachés à la même entreprise, partagent bien
     * le même nom d'entreprise.</p>
     *
     * <ul>
     *   <li>Données : correspondant1 ∈ TechCorp, correspondant2 ∈ TechCorp</li>
     *   <li>Attendu : entreprise(sujet1) = entreprise(sujet2) = "TechCorp"</li>
     * </ul>
     */
    @Test
    public void testR8DeuxCorrespondantsMemeEntreprise() {
        afficherEntete(
            "R8",
            "Deux correspondants de la meme entreprise sur deux sujets differents",
            "corr1 (Dupont) ∈ TechCorp -> sujet1 | corr2 (Martin) ∈ TechCorp -> sujet2",
            "entreprise(sujet1) = entreprise(sujet2) = 'TechCorp'"
        );

        String entrepriseSujet1 = sujet1.getIdCorrespondant()
                                        .getEntreprise().getNom();
        String entrepriseSujet2 = sujet2.getIdCorrespondant()
                                        .getEntreprise().getNom();

        boolean ok = entrepriseSujet1.equals(entrepriseSujet2);

        afficherResultat(ok,
            "entreprise(sujet1)=" + entrepriseSujet1
            + " | entreprise(sujet2)=" + entrepriseSujet2
            + " | egal=" + ok);

        assertEquals("Les deux entreprises doivent avoir le meme nom",
                entrepriseSujet1, entrepriseSujet2);
        assertEquals("L'entreprise doit etre 'TechCorp'",
                "TechCorp", entrepriseSujet1);
        assertEquals("L'entreprise doit etre 'TechCorp'",
                "TechCorp", entrepriseSujet2);
    }
}