package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.Annee;
import fr.centrale.nantes.pappl.items.Correspondant;
import fr.centrale.nantes.pappl.items.Sujet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires de la classe {@link Sujet}.
 *
 * <p>Ce fichier vérifie le comportement de la classe {@link Sujet} :
 * création, getters/setters, relation avec {@link Correspondant} et
 * {@link Annee}, ainsi que les méthodes {@code equals()} et
 * {@code toString()}.</p>
 *
 * <p>Approche : tests sans base de données (objets Java en mémoire uniquement).
 * Aucune connexion à PostgreSQL n'est requise.</p>
 *
 * <pre>
 * N°    Scénario                              Statut
 * T1    Création avec intitulé et année       A tester
 * T2    Intitulé null accepté                 A tester
 * T3    Intitulé vide accepté                 A tester
 * T4    Assigner un correspondant             A tester
 * T5    Sujet sans correspondant (null)       A tester
 * T6    Changer l'année                       A tester
 * T7    equals() — même numéro               A tester
 * T8    toString() — contient le numéro      A tester
 * </pre>
 *
 * @author  P2T
 * @version 2.0
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
public class SujetTest {

    /** Sujet principal utilisé dans la majorité des tests. */
    private Sujet sujet;

    /** Année académique 2026 assignée au sujet par défaut. */
    private Annee annee;

    /** Correspondant utilisé pour tester la relation avec le sujet. */
    private Correspondant correspondant;

    // ================================================================
    //  MÉTHODES UTILITAIRES D'AFFICHAGE
    // ================================================================

    /**
     * Affiche l'en-tête structuré d'un test dans la console.
     *
     * @param numero   identifiant du test (ex. "T1", "T4")
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
     *   <li>Un {@link Correspondant} "Dupont Jean"</li>
     *   <li>Un {@link Sujet} "Stage web" avec l'année 2026</li>
     * </ul>
     */
    @Before
    public void setUp() {
        System.out.println();
        System.out.println("############################################################");
        System.out.println("#              INITIALISATION DU TEST                      #");
        System.out.println("############################################################");

        annee = new Annee(2026);

        correspondant = new Correspondant();
        correspondant.setId(1);
        correspondant.setNom("Dupont");
        correspondant.setPrenom("Jean");
        correspondant.setEmail("jean.dupont@ecn.fr");

        sujet = new Sujet();
        sujet.setIntitule("Stage web");
        sujet.setAnnee(annee);

        System.out.println("  Sujet     : intitule='" + sujet.getIntitule()
                + "' | annee=" + sujet.getAnnee().getAnnee());
        System.out.println("  Corr.     : "
                + correspondant.getNom() + " " + correspondant.getPrenom());
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
    //  TESTS
    // ================================================================

    /**
     * <b>T1</b> — Création d'un sujet avec intitulé et année.
     *
     * <p>Vérifie que le sujet créé dans {@code setUp()} est non null
     * et que ses champs ont les valeurs attendues.</p>
     *
     * <ul>
     *   <li>Données : intitule="Stage web", annee=2026</li>
     *   <li>Attendu : getIntitule()="Stage web" | getAnnee().getAnnee()=2026</li>
     * </ul>
     */
    @Test
    public void testCreationSujetAvecIntituleEtAnnee() {
        afficherEntete(
            "T1",
            "Creation d'un sujet avec intitule et annee",
            "intitule='Stage web' | annee=2026",
            "getIntitule()='Stage web' | getAnnee().getAnnee()=2026 | non null"
        );

        boolean nonNull    = sujet != null;
        boolean intituleOk = "Stage web".equals(sujet.getIntitule());
        boolean anneeOk    = Integer.valueOf(2026).equals(sujet.getAnnee().getAnnee());

        afficherResultat(nonNull && intituleOk && anneeOk,
            "non null=" + nonNull
            + " | intitule='" + sujet.getIntitule() + "'"
            + " | annee=" + sujet.getAnnee().getAnnee());

        assertNotNull("Le sujet ne doit pas etre null", sujet);
        assertEquals("L'intitule doit etre 'Stage web'",
                "Stage web", sujet.getIntitule());
        assertEquals("L'annee doit etre 2026",
                Integer.valueOf(2026), sujet.getAnnee().getAnnee());
    }

    /**
     * <b>T2</b> — Intitulé null accepté sans exception.
     *
     * <p>Vérifie que {@code setIntitule(null)} ne lève pas d'exception
     * et que {@code getIntitule()} retourne null.</p>
     *
     * <ul>
     *   <li>Données : setIntitule(null)</li>
     *   <li>Attendu : getIntitule() = null</li>
     * </ul>
     */
    @Test
    public void testSujetIntituleNull() {
        afficherEntete(
            "T2",
            "Intitule null accepte sans exception",
            "setIntitule(null)",
            "getIntitule() = null"
        );

        sujet.setIntitule(null);
        boolean ok = sujet.getIntitule() == null;

        afficherResultat(ok,
            "getIntitule()=" + sujet.getIntitule());

        assertNull("L'intitule doit etre null", sujet.getIntitule());
    }

    /**
     * <b>T3</b> — Intitulé vide accepté sans exception.
     *
     * <p>Vérifie que {@code setIntitule("")} est accepté et retourné tel quel.</p>
     *
     * <ul>
     *   <li>Données : setIntitule("")</li>
     *   <li>Attendu : getIntitule() = ""</li>
     * </ul>
     */
    @Test
    public void testSujetIntituleVide() {
        afficherEntete(
            "T3",
            "Intitule vide accepte sans exception",
            "setIntitule(\"\")",
            "getIntitule() = \"\""
        );

        sujet.setIntitule("");
        boolean ok = "".equals(sujet.getIntitule());

        afficherResultat(ok,
            "getIntitule()='" + sujet.getIntitule() + "'");

        assertEquals("L'intitule vide doit etre accepte",
                "", sujet.getIntitule());
    }

    /**
     * <b>T4</b> — Assigner un correspondant à un sujet.
     *
     * <p>Vérifie que le correspondant est accessible depuis le sujet
     * après assignation.</p>
     *
     * <ul>
     *   <li>Données : setIdCorrespondant(correspondant "Dupont")</li>
     *   <li>Attendu : getIdCorrespondant() non null | getNom()="Dupont"</li>
     * </ul>
     */
    @Test
    public void testAssignerCorrespondant() {
        afficherEntete(
            "T4",
            "Assigner un correspondant au sujet",
            "setIdCorrespondant(correspondant1) — Dupont Jean",
            "getIdCorrespondant() non null | getNom()='Dupont'"
        );

        sujet.setIdCorrespondant(correspondant);

        boolean nonNull = sujet.getIdCorrespondant() != null;
        boolean nomOk   = "Dupont".equals(sujet.getIdCorrespondant().getNom());

        afficherResultat(nonNull && nomOk,
            "non null=" + nonNull
            + " | nom='" + sujet.getIdCorrespondant().getNom() + "'");

        assertNotNull("Le correspondant ne doit pas etre null",
                sujet.getIdCorrespondant());
        assertEquals("Le nom du correspondant doit etre 'Dupont'",
                "Dupont", sujet.getIdCorrespondant().getNom());
    }

    /**
     * <b>T5</b> — Sujet sans correspondant (null).
     *
     * <p>Vérifie qu'un sujet peut exister sans correspondant
     * et que {@code getIdCorrespondant()} retourne null.</p>
     *
     * <ul>
     *   <li>Données : setIdCorrespondant(null)</li>
     *   <li>Attendu : getIdCorrespondant() = null</li>
     * </ul>
     */
    @Test
    public void testSujetSansCorrespondant() {
        afficherEntete(
            "T5",
            "Sujet sans correspondant (null)",
            "setIdCorrespondant(null)",
            "getIdCorrespondant() = null"
        );

        sujet.setIdCorrespondant(null);
        boolean ok = sujet.getIdCorrespondant() == null;

        afficherResultat(ok,
            "getIdCorrespondant()=" + sujet.getIdCorrespondant());

        assertNull("Le correspondant doit etre null",
                sujet.getIdCorrespondant());
    }

    /**
     * <b>T6</b> — Changer l'année d'un sujet.
     *
     * <p>Vérifie que l'année d'un sujet peut être modifiée
     * et que la nouvelle valeur est bien enregistrée.</p>
     *
     * <ul>
     *   <li>Données : setAnnee(new Annee(2025)) — était 2026</li>
     *   <li>Attendu : getAnnee().getAnnee() = 2025</li>
     * </ul>
     */
    @Test
    public void testChangerAnnee() {
        afficherEntete(
            "T6",
            "Changer l'annee d'un sujet",
            "setAnnee(new Annee(2025)) — annee precedente=2026",
            "getAnnee().getAnnee() = 2025"
        );

        int anneeAvant = sujet.getAnnee().getAnnee();
        sujet.setAnnee(new Annee(2025));
        int anneeApres = sujet.getAnnee().getAnnee();

        boolean ok = anneeApres == 2025;

        afficherResultat(ok,
            "avant=" + anneeAvant + " | apres=" + anneeApres);

        assertEquals("L'annee doit etre 2025 apres changement",
                Integer.valueOf(2025), sujet.getAnnee().getAnnee());
    }

    /**
     * <b>T7</b> — {@code equals()} entre deux sujets avec le même numéro.
     *
     * <p>Vérifie que deux instances de {@link Sujet} ayant le même
     * numéro (PK) sont considérées égales par {@code equals()}.</p>
     *
     * <ul>
     *   <li>Données : new Sujet(42) et new Sujet(42)</li>
     *   <li>Attendu : equals() = true</li>
     * </ul>
     */
    @Test
    public void testEqualsMemeNumero() {
        afficherEntete(
            "T7",
            "equals() — deux sujets avec le meme numero",
            "new Sujet(42) vs new Sujet(42)",
            "equals() = true"
        );

        Sujet s1 = new Sujet(42);
        Sujet s2 = new Sujet(42);
        boolean ok = s1.equals(s2);

        afficherResultat(ok,
            "s1.numero=" + s1.getNumero()
            + " | s2.numero=" + s2.getNumero()
            + " | equals=" + ok);

        assertEquals("Deux sujets avec le meme numero doivent etre egaux", s1, s2);
    }

    /**
     * <b>T8</b> — {@code toString()} contient le numéro du sujet.
     *
     * <p>Vérifie que la représentation textuelle d'un {@link Sujet}
     * inclut bien son numéro d'identification.</p>
     *
     * <ul>
     *   <li>Données : new Sujet(99)</li>
     *   <li>Attendu : toString() contient "99"</li>
     * </ul>
     */
    @Test
    public void testToStringContientNumero() {
        afficherEntete(
            "T8",
            "toString() contient le numero du sujet",
            "new Sujet(99)",
            "toString() contient '99'"
        );

        Sujet s = new Sujet(99);
        String resultat = s.toString();
        boolean ok = resultat.contains("99");

        afficherResultat(ok,
            "toString()='" + resultat + "'"
            + " | contient '99'=" + ok);

        assertTrue("toString() doit contenir le numero 99",
                resultat.contains("99"));
    }
}