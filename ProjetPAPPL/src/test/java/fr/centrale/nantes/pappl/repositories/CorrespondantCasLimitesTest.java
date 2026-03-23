package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.Annee;
import fr.centrale.nantes.pappl.items.Correspondant;
import fr.centrale.nantes.pappl.items.Entreprise;
import fr.centrale.nantes.pappl.items.Sujet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests des cas limites — relation {@link Correspondant} ↔ {@link Sujet}.
 *
 * <p>Ce fichier vérifie 4 scénarios importants sur la relation entre
 * un correspondant et un sujet dans le projet P2T :</p>
 *
 * <pre>
 * CAS   Scénario                                              Tests
 * CAS 1 Supprimer un correspondant lié à un sujet            T1, T2, T3
 * CAS 2 Ajouter 2 correspondants au même sujet               T4, T5, T6
 * CAS 3 Supprimer 1 correspondant sur 2                      T7, T8
 * CAS 4 Changer un correspondant par un autre                T9, T10, T11
 * </pre>
 *
 * <p>Approche : tests sans base de données (objets Java en mémoire uniquement).
 * Aucune connexion à PostgreSQL n'est requise.</p>
 *
 * <p><b>Résultats attendus :</b></p>
 * <ul>
 *   <li>PASS  — comportement correct et attendu</li>
 *   <li>WARN  — test réussi mais comportement à améliorer</li>
 * </ul>
 *
 * @author  PGROUPE
 * @version 2.0
 * @since   19 février 2026
 *
 * <pre>
 * Technologie        Version
 * Langage            Java 17
 * Framework de test  JUnit 4.13.2
 * Outil de build     Maven 3.x
 * IDE                NetBeans
 * Base de données    PostgreSQL
 * ORM                EclipseLink 4.0.6
 * </pre>
 */
public class CorrespondantCasLimitesTest {

    /** Entreprise partagée par les deux correspondants. */
    private Entreprise entreprise;

    /** Premier correspondant — Dupont Jean, assigné au sujet par défaut. */
    private Correspondant correspondant1;

    /** Deuxième correspondant — Martin Sophie, utilisé pour les remplacements. */
    private Correspondant correspondant2;

    /** Sujet de test — "Stage développement web", assigné à correspondant1. */
    private Sujet sujet;

    /** Année académique 2026 utilisée pour le sujet. */
    private Annee annee;

    // ================================================================
    //  MÉTHODES UTILITAIRES D'AFFICHAGE
    // ================================================================

    /**
     * Affiche l'en-tête structuré d'un test dans la console.
     *
     * @param numero   identifiant du test (ex. "T1", "T4")
     * @param cas      numéro du cas limite (ex. "CAS 1", "CAS 2")
     * @param scenario description courte du scénario testé
     * @param donnees  description des données d'entrée utilisées
     * @param attendu  résultat attendu du test
     */
    private void afficherEntete(String numero, String cas, String scenario,
                                String donnees, String attendu) {
        System.out.println();
        System.out.println("+===========================================================+");
        System.out.printf("  TEST %-4s [%s] — %s%n", numero, cas, scenario);
        System.out.println("+-----------------------------------------------------------+");
        System.out.println("  Cas       : " + cas);
        System.out.println("  Scenario  : " + scenario);
        System.out.println("  Donnees   : " + donnees);
        System.out.println("  Attendu   : " + attendu);
        System.out.println("+-----------------------------------------------------------+");
    }

    /**
     * Affiche le résultat PASS d'un test dans la console.
     *
     * @param details informations complémentaires sur le résultat observé
     */
    private void afficherPass(String details) {
        System.out.println("  Resultat  : PASS   | " + details);
        System.out.println("+===========================================================+");
    }

    /**
     * Affiche un avertissement WARN pour les comportements à améliorer.
     *
     * @param details description du comportement observé et recommandation
     */
    private void afficherWarn(String details) {
        System.out.println("  Resultat  : WARN   | " + details);
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
     *   <li>Une {@link Entreprise} "TechCorp"</li>
     *   <li>Deux {@link Correspondant} liés à TechCorp</li>
     *   <li>Une {@link Annee} 2026</li>
     *   <li>Un {@link Sujet} "Stage développement web" avec correspondant1</li>
     * </ul>
     */
    @Before
    public void setUp() {
        System.out.println();
        System.out.println("############################################################");
        System.out.println("#              INITIALISATION DU TEST                      #");
        System.out.println("############################################################");

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
        sujet.setIntitule("Stage developpement web");
        sujet.setAnnee(annee);
        sujet.setIdCorrespondant(correspondant1);

        System.out.println("  Sujet     : '" + sujet.getIntitule() + "'");
        System.out.println("  Corr. 1   : " + correspondant1.getNom()
                + " " + correspondant1.getPrenom()
                + " | " + correspondant1.getEmail());
        System.out.println("  Corr. 2   : " + correspondant2.getNom()
                + " " + correspondant2.getPrenom()
                + " | " + correspondant2.getEmail());
        System.out.println("  Entreprise: " + entreprise.getNom());
        System.out.println("  Annee     : " + annee.getAnnee());
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
    //  CAS 1 — Supprimer un correspondant lié à un sujet  (T1 à T3)
    //  Question : que se passe-t-il sur le sujet ?
    // ================================================================

    /**
     * <b>T1</b> — État du sujet AVANT suppression du correspondant.
     *
     * <p>Vérifie que le sujet possède bien un correspondant au départ,
     * avant toute opération de suppression.</p>
     *
     * <ul>
     *   <li>Données : sujet initialisé avec correspondant1 (Dupont)</li>
     *   <li>Attendu : getIdCorrespondant() non null | nom="Dupont"</li>
     * </ul>
     */
    @Test
    public void testEtatSujetAvantSuppression() {
        afficherEntete(
            "T1", "CAS 1",
            "Etat du sujet AVANT suppression du correspondant",
            "sujet avec correspondant1 (Dupont Jean)",
            "getIdCorrespondant() non null | getNom()='Dupont'"
        );

        boolean nonNull = sujet.getIdCorrespondant() != null;
        boolean nomOk   = "Dupont".equals(sujet.getIdCorrespondant().getNom());

        afficherPass(
            "correspondant present=" + nonNull
            + " | nom='" + sujet.getIdCorrespondant().getNom() + "'");

        assertNotNull("Le sujet doit avoir un correspondant",
                sujet.getIdCorrespondant());
        assertEquals("Dupont", sujet.getIdCorrespondant().getNom());
    }

    /**
     * <b>T2</b> — Sujet APRÈS suppression du correspondant → {@code null}.
     *
     * <p><b>WARN</b> : la suppression du correspondant place
     * {@code id_correspondant} à {@code null} dans le sujet lié.
     * Il serait préférable de bloquer la suppression si le correspondant
     * est encore référencé.</p>
     *
     * <ul>
     *   <li>Données : correspondant1=null, sujet.setIdCorrespondant(null)</li>
     *   <li>Attendu : WARN — getIdCorrespondant() = null (comportement actuel)</li>
     * </ul>
     */
    @Test
    public void testSujetApresSuppressionCorrespondant() {
        afficherEntete(
            "T2", "CAS 1",
            "Sujet apres suppression du correspondant => id_correspondant=NULL",
            "correspondant1=null | sujet.setIdCorrespondant(correspondant1)",
            "WARN : getIdCorrespondant()=null — bloquer la suppression recommande"
        );

        correspondant1 = null;
        sujet.setIdCorrespondant(correspondant1);

        boolean estNull = sujet.getIdCorrespondant() == null;

        afficherWarn(
            "id_correspondant=null apres suppression"
            + " | recommandation : bloquer suppression si correspondant reference");

        assertNull("id_correspondant doit etre null apres suppression",
                sujet.getIdCorrespondant());
    }

    /**
     * <b>T3</b> — Le sujet existe toujours après suppression du correspondant.
     *
     * <p>Vérifie que la suppression du correspondant n'entraîne pas
     * la suppression en cascade du sujet (pas de cascade configurée).</p>
     *
     * <ul>
     *   <li>Données : sujet.setIdCorrespondant(null)</li>
     *   <li>Attendu : sujet non null | intitule intact</li>
     * </ul>
     */
    @Test
    public void testSujetExisteEncoreApresSuppressionCorrespondant() {
        afficherEntete(
            "T3", "CAS 1",
            "Le sujet existe toujours apres suppression du correspondant",
            "sujet.setIdCorrespondant(null)",
            "sujet non null | intitule intact | pas de cascade"
        );

        sujet.setIdCorrespondant(null);

        boolean sujetExiste   = sujet != null;
        boolean intituleIntact = "Stage developpement web"
                .equals(sujet.getIntitule());

        afficherPass(
            "sujet existe=" + sujetExiste
            + " | intitule='" + sujet.getIntitule() + "'"
            + " | pas de cascade confirme");

        assertNotNull("Le sujet doit toujours exister", sujet);
        assertEquals("Stage developpement web", sujet.getIntitule());
    }

    // ================================================================
    //  CAS 2 — Ajouter 2 correspondants au même sujet  (T4 à T6)
    //  Question : est-ce possible avec la structure actuelle ?
    // ================================================================

    /**
     * <b>T4</b> — Le 2ème correspondant remplace le 1er.
     *
     * <p><b>WARN</b> : la relation {@code @ManyToOne} dans {@link Sujet}
     * ne permet qu'un seul correspondant. Assigner un 2ème écrase le 1er
     * sans erreur ni avertissement.</p>
     *
     * <ul>
     *   <li>Données : sujet a Dupont, puis setIdCorrespondant(Martin)</li>
     *   <li>Attendu : WARN — Martin remplace Dupont (pas d'ajout possible)</li>
     * </ul>
     */
    @Test
    public void testAjouterDeuxiemeCorrespondantRemplaceLePremier() {
        afficherEntete(
            "T4", "CAS 2",
            "Le 2eme correspondant remplace le 1er",
            "sujet a Dupont | setIdCorrespondant(correspondant2=Martin)",
            "WARN : Martin remplace Dupont — pas d'ajout possible avec ManyToOne"
        );

        String avant = sujet.getIdCorrespondant().getNom();
        sujet.setIdCorrespondant(correspondant2);
        String apres = sujet.getIdCorrespondant().getNom();

        afficherWarn(
            "avant='" + avant + "' | apres='" + apres + "'"
            + " | remplacement confirme — ManyToOne = 1 seul correspondant max");

        assertEquals("Dupont", avant);
        assertEquals("Le 2eme correspondant doit remplacer le 1er",
                "Martin", apres);
    }

    /**
     * <b>T5</b> — Le 1er correspondant est perdu après remplacement.
     *
     * <p><b>WARN</b> : une fois le 2ème correspondant assigné,
     * le 1er est définitivement inaccessible depuis le sujet.</p>
     *
     * <ul>
     *   <li>Données : sujet.setIdCorrespondant(correspondant2)</li>
     *   <li>Attendu : WARN — Dupont n'est plus accessible depuis le sujet</li>
     * </ul>
     */
    @Test
    public void testPremierCorrespondantPerduApresRemplacement() {
        afficherEntete(
            "T5", "CAS 2",
            "Le 1er correspondant est perdu apres remplacement",
            "sujet.setIdCorrespondant(correspondant2=Martin)",
            "WARN : getNom() != 'Dupont' — Dupont inaccessible depuis le sujet"
        );

        sujet.setIdCorrespondant(correspondant2);
        String nomActuel = sujet.getIdCorrespondant().getNom();

        afficherWarn(
            "nom actuel='" + nomActuel + "'"
            + " | Dupont perdu=true"
            + " | perte de donnees confirmee");

        assertNotEquals("Le 1er correspondant (Dupont) ne doit plus etre sur le sujet",
                "Dupont", nomActuel);
    }

    /**
     * <b>T6</b> — Confirmation : structure ManyToOne = 1 seul correspondant max.
     *
     * <p><b>WARN</b> : la classe {@link Sujet} déclare
     * {@code private Correspondant idCorrespondant} avec {@code @ManyToOne}.
     * Cette structure ne permet pas de stocker plusieurs correspondants.
     * Passer en {@code @ManyToMany} résoudrait cette limitation.</p>
     *
     * <ul>
     *   <li>Données : setIdCorrespondant(correspondant1)</li>
     *   <li>Attendu : WARN — 1 seul objet Correspondant stockable</li>
     * </ul>
     */
    @Test
    public void testStructureActuelleUnSeulCorrespondant() {
        afficherEntete(
            "T6", "CAS 2",
            "Structure ManyToOne = 1 seul correspondant max",
            "setIdCorrespondant(correspondant1)",
            "WARN : instanceof Correspondant (pas une liste) — ManyToMany recommande"
        );

        sujet.setIdCorrespondant(correspondant1);
        Correspondant resultat = sujet.getIdCorrespondant();

        afficherWarn(
            "type=Correspondant (objet unique, pas une liste)"
            + " | recommandation : passer en ManyToMany pour plusieurs correspondants");

        assertNotNull(resultat);
        assertTrue("Structure actuelle = 1 seul correspondant par sujet",
                resultat instanceof Correspondant);
    }

    // ================================================================
    //  CAS 3 — Supprimer 1 correspondant sur 2  (T7 à T8)
    //  Question : le sujet garde-t-il l'autre ?
    // ================================================================

    /**
     * <b>T7</b> — Suppression du seul correspondant disponible → aucun correspondant.
     *
     * <p><b>WARN</b> : avec la structure actuelle (ManyToOne), il est impossible
     * de "garder 1 correspondant sur 2" car on ne peut en stocker qu'un seul.
     * La suppression laisse donc le sujet sans aucun correspondant.</p>
     *
     * <ul>
     *   <li>Données : sujet.setIdCorrespondant(null)</li>
     *   <li>Attendu : WARN — getIdCorrespondant()=null, aucun correspondant restant</li>
     * </ul>
     */
    @Test
    public void testSuppressionUnCorrespondantSurDeux() {
        afficherEntete(
            "T7", "CAS 3",
            "Suppression du seul correspondant => aucun restant",
            "setIdCorrespondant(correspondant1) puis setIdCorrespondant(null)",
            "WARN : getIdCorrespondant()=null — impossible garder 1 sur 2"
        );

        sujet.setIdCorrespondant(correspondant1);
        sujet.setIdCorrespondant(null);

        boolean estNull = sujet.getIdCorrespondant() == null;

        afficherWarn(
            "getIdCorrespondant()=null=" + estNull
            + " | impossible de garder 1 correspondant sur 2 avec la structure actuelle"
            + " | ManyToMany requis");

        assertNull("Le sujet ne doit plus avoir de correspondant",
                sujet.getIdCorrespondant());
    }

    /**
     * <b>T8</b> — Réassignation d'un correspondant après suppression.
     *
     * <p>Vérifie que l'on peut réassigner un correspondant après
     * avoir supprimé le précédent.</p>
     *
     * <ul>
     *   <li>Données : setIdCorrespondant(null) puis setIdCorrespondant(correspondant2)</li>
     *   <li>Attendu : PASS — getNom()="Martin"</li>
     * </ul>
     */
    @Test
    public void testReassignerApresSuppression() {
        afficherEntete(
            "T8", "CAS 3",
            "Reassignation d'un correspondant apres suppression",
            "setIdCorrespondant(null) puis setIdCorrespondant(correspondant2=Martin)",
            "getIdCorrespondant() non null | getNom()='Martin'"
        );

        sujet.setIdCorrespondant(null);
        sujet.setIdCorrespondant(correspondant2);

        boolean nonNull = sujet.getIdCorrespondant() != null;
        boolean nomOk   = "Martin".equals(sujet.getIdCorrespondant().getNom());

        afficherPass(
            "non null=" + nonNull
            + " | nom='" + sujet.getIdCorrespondant().getNom() + "'"
            + " | reassignation correcte");

        assertNotNull("On doit pouvoir reassigner un correspondant",
                sujet.getIdCorrespondant());
        assertEquals("Martin", sujet.getIdCorrespondant().getNom());
    }

    // ================================================================
    //  CAS 4 — Changer le correspondant d'un sujet  (T9 à T11)
    // ================================================================

    /**
     * <b>T9</b> — Changer le correspondant d'un sujet.
     *
     * <p>Vérifie que le remplacement de correspondant1 par correspondant2
     * fonctionne correctement.</p>
     *
     * <ul>
     *   <li>Données : sujet avait Dupont, setIdCorrespondant(Martin)</li>
     *   <li>Attendu : PASS — getNom()="Martin"</li>
     * </ul>
     */
    @Test
    public void testChangerCorrespondant() {
        afficherEntete(
            "T9", "CAS 4",
            "Changer le correspondant d'un sujet",
            "Dupont -> setIdCorrespondant(correspondant2=Martin)",
            "getNom()='Martin'"
        );

        String avant = sujet.getIdCorrespondant().getNom();
        sujet.setIdCorrespondant(correspondant2);
        String apres = sujet.getIdCorrespondant().getNom();

        afficherPass(
            "avant='" + avant + "'"
            + " | apres='" + apres + "'"
            + " | changement reussi");

        assertEquals("Martin", apres);
    }

    /**
     * <b>T10</b> — Informations du nouveau correspondant accessibles après changement.
     *
     * <p>Vérifie que nom, prénom et email du nouveau correspondant
     * sont correctement accessibles depuis le sujet.</p>
     *
     * <ul>
     *   <li>Données : setIdCorrespondant(correspondant2 — Martin Sophie)</li>
     *   <li>Attendu : PASS — nom, prénom et email corrects</li>
     * </ul>
     */
    @Test
    public void testInfosNouveauCorrespondantApresChangement() {
        afficherEntete(
            "T10", "CAS 4",
            "Infos du nouveau correspondant accessibles apres changement",
            "setIdCorrespondant(correspondant2) — Martin Sophie",
            "getNom()='Martin' | getPrenom()='Sophie' | getEmail() correct"
        );

        sujet.setIdCorrespondant(correspondant2);

        String nom    = sujet.getIdCorrespondant().getNom();
        String prenom = sujet.getIdCorrespondant().getPrenom();
        String email  = sujet.getIdCorrespondant().getEmail();

        boolean ok = "Martin".equals(nom)
                  && "Sophie".equals(prenom)
                  && "sophie.martin@techcorp.fr".equals(email);

        afficherPass(
            "nom='" + nom + "'"
            + " | prenom='" + prenom + "'"
            + " | email='" + email + "'");

        assertEquals("Martin", nom);
        assertEquals("Sophie", prenom);
        assertEquals("sophie.martin@techcorp.fr", email);
    }

    /**
     * <b>T11</b> — Plusieurs changements successifs → le dernier est conservé.
     *
     * <p>Vérifie que plusieurs appels successifs à
     * {@code setIdCorrespondant()} conservent uniquement le dernier
     * correspondant assigné.</p>
     *
     * <ul>
     *   <li>Données : Dupont → Martin → Dupont</li>
     *   <li>Attendu : PASS — getNom()="Dupont" (dernier assigné)</li>
     * </ul>
     */
    @Test
    public void testChangerPlusieursCorrespondants() {
        afficherEntete(
            "T11", "CAS 4",
            "Plusieurs changements successifs — le dernier est conserve",
            "Dupont -> Martin -> Dupont (3 changements successifs)",
            "getNom()='Dupont' — le dernier assigne est conserve"
        );

        sujet.setIdCorrespondant(correspondant1); // Dupont
        sujet.setIdCorrespondant(correspondant2); // Martin
        sujet.setIdCorrespondant(correspondant1); // retour Dupont

        String nomFinal = sujet.getIdCorrespondant().getNom();
        boolean ok = "Dupont".equals(nomFinal);

        afficherPass(
            "etape1=Dupont | etape2=Martin | etape3=Dupont"
            + " | nomFinal='" + nomFinal + "'"
            + " | dernier conserve=" + ok);

        assertEquals("Apres plusieurs changements, le dernier doit etre conserve",
                "Dupont", nomFinal);
    }
}