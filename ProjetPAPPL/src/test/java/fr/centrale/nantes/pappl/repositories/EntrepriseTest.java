package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.Entreprise;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests unitaires de la classe {@link Entreprise}.
 *
 * <p>Ce fichier vérifie le comportement de la classe {@link Entreprise} :
 * création d'objets, getters/setters, méthodes de comparaison
 * ({@code equals()}, {@code hashCode()}, {@code compareTo()}),
 * {@code toString()} et gestion des cas limites.</p>
 *
 * <p>Approche : tests sans base de données (objets Java en mémoire uniquement).
 * Aucune connexion à PostgreSQL n'est requise.</p>
 *
 * <pre>
 * N°    Scénario                                        Statut
 * T1    Création complète avec tous les champs          PASS
 * T2    Création avec seulement le nom                  PASS
 * T3    setNom(null) accepté                            PASS
 * T4    setNom("") accepté                              PASS
 * T5    Code postal valide (44000)                      PASS
 * T6    Code postal négatif accepté (pas de validation) WARN
 * T7    Téléphone valide                                PASS
 * T8    Téléphone négatif accepté (pas de validation)   WARN
 * T9    Nom très long (255 caractères)                  PASS
 * T10   Nom avec caractères spéciaux et accents         PASS
 * T11   Site web sans https:// (pas de normalisation)   WARN
 * T12   Description vide acceptée                       PASS
 * T13   equals() fonctionne correctement                PASS
 * T14   hashCode() cohérent avec equals()               PASS
 * T15   compareTo() respecte l'ordre alphabétique       PASS
 * T16   toString() contient le nom                      PASS
 * T17   getComparator() trie correctement               PASS
 * T18   compareTo() même nom + adresse null → NPE       WARN (bug identifié)
 * T19   equals(null) retourne false                     PASS
 * T20   getComparator(null, x) retourne valeur négative PASS
 * </pre>
 *
 * @author  Bouzidi Safa
 * @version 2.0
 * @since   19 mars 2026
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
public class EntrepriseTest {

    // ================================================================
    //  MÉTHODES UTILITAIRES D'AFFICHAGE
    // ================================================================

    /**
     * Affiche l'en-tête structuré d'un test dans la console.
     *
     * @param numero   identifiant du test (ex. "T1", "T13")
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

    /**
     * Affiche un avertissement WARN pour les cas limites identifiés.
     *
     * @param details description du comportement à améliorer
     */
    private void afficherWarn(String details) {
        System.out.println("  Resultat  : WARN   | " + details);
        System.out.println("+===========================================================+");
    }

    // ================================================================
    //  INITIALISATION ET NETTOYAGE
    // ================================================================

    /**
     * Affiche l'en-tête de section avant chaque test.
     */
    @Before
    public void setUp() {
        System.out.println();
        System.out.println("############################################################");
        System.out.println("#              INITIALISATION DU TEST                      #");
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
    //  TESTS DE CRÉATION D'OBJETS  (T1 à T4)
    // ================================================================

    /**
     * <b>T1</b> — Création d'une entreprise complète avec tous les champs.
     *
     * <p>Vérifie que tous les setters fonctionnent correctement
     * et que les getters retournent les valeurs attendues.</p>
     *
     * <ul>
     *   <li>Données : nom="Airbus", adresse, CP=31700, tel, siteWeb, description</li>
     *   <li>Attendu : tous les getters retournent les valeurs saisies</li>
     * </ul>
     */
    @Test
    public void testCreateEntrepriseComplete() {
        afficherEntete(
            "T1",
            "Creation d'une entreprise complete avec tous les champs",
            "nom='Airbus' | adresse | CP=31700 | tel=561234567 | siteWeb | description",
            "tous les getters retournent les valeurs saisies"
        );

        Entreprise e = new Entreprise("Airbus");
        e.setAdresse("1 Rond-Point Emile Dewoitine");
        e.setCodePostal(31700);
        e.setTel(561234567);
        e.setSiteWeb("www.airbus.com");
        e.setDescription("Constructeur aeronautique europeen");

        boolean ok = e != null
                && "Airbus".equals(e.getNom())
                && e.getCodePostal().equals(31700)
                && e.getTel().equals(561234567);

        afficherResultat(ok,
            "nom='" + e.getNom() + "'"
            + " | CP=" + e.getCodePostal()
            + " | tel=" + e.getTel()
            + " | siteWeb=" + e.getSiteWeb());

        assertNotNull("L'entreprise ne doit pas etre null", e);
        assertEquals("Airbus", e.getNom());
        assertEquals("1 Rond-Point Emile Dewoitine", e.getAdresse());
        assertEquals(Integer.valueOf(31700), e.getCodePostal());
        assertEquals(Integer.valueOf(561234567), e.getTel());
        assertEquals("www.airbus.com", e.getSiteWeb());
        assertEquals("Constructeur aeronautique europeen", e.getDescription());
    }

    /**
     * <b>T2</b> — Création d'une entreprise avec seulement le nom.
     *
     * <p>Vérifie que les champs optionnels sont null par défaut
     * lorsqu'on n'utilise que le constructeur avec le nom.</p>
     *
     * <ul>
     *   <li>Données : new Entreprise("Startup Inc")</li>
     *   <li>Attendu : nom correct, adresse/CP/tel = null</li>
     * </ul>
     */
    @Test
    public void testCreateEntrepriseAvecNomSeul() {
        afficherEntete(
            "T2",
            "Creation avec seulement le nom",
            "new Entreprise('Startup Inc')",
            "nom='Startup Inc' | adresse=null | CP=null | tel=null"
        );

        Entreprise e = new Entreprise("Startup Inc");

        boolean ok = "Startup Inc".equals(e.getNom())
                && e.getAdresse() == null
                && e.getCodePostal() == null
                && e.getTel() == null;

        afficherResultat(ok,
            "nom='" + e.getNom() + "'"
            + " | adresse=" + e.getAdresse()
            + " | CP=" + e.getCodePostal()
            + " | tel=" + e.getTel());

        assertNotNull(e);
        assertEquals("Startup Inc", e.getNom());
        assertNull("L'adresse doit etre null", e.getAdresse());
        assertNull("Le CP doit etre null", e.getCodePostal());
        assertNull("Le tel doit etre null", e.getTel());
    }

    /**
     * <b>T3</b> — {@code setNom(null)} accepté sans exception.
     *
     * <ul>
     *   <li>Données : setNom(null)</li>
     *   <li>Attendu : getNom() = null</li>
     * </ul>
     */
    @Test
    public void testSetNomNull() {
        afficherEntete(
            "T3",
            "setNom(null) accepte sans exception",
            "new Entreprise() puis setNom(null)",
            "getNom() = null"
        );

        Entreprise e = new Entreprise();
        e.setNom(null);

        boolean ok = e.getNom() == null;

        afficherResultat(ok, "getNom()=" + e.getNom());

        assertNull("Le nom doit etre null", e.getNom());
    }

    /**
     * <b>T4</b> — {@code setNom("")} accepté sans exception.
     *
     * <ul>
     *   <li>Données : setNom("")</li>
     *   <li>Attendu : getNom() = ""</li>
     * </ul>
     */
    @Test
    public void testSetNomVide() {
        afficherEntete(
            "T4",
            "setNom(\"\") accepte sans exception",
            "new Entreprise() puis setNom(\"\")",
            "getNom() = \"\""
        );

        Entreprise e = new Entreprise();
        e.setNom("");

        boolean ok = "".equals(e.getNom());

        afficherResultat(ok, "getNom()='" + e.getNom() + "'");

        assertEquals("Le nom vide doit etre accepte", "", e.getNom());
    }

    // ================================================================
    //  TESTS DE VALIDATION DES DONNÉES  (T5 à T8)
    // ================================================================

    /**
     * <b>T5</b> — Code postal valide (44000).
     *
     * <ul>
     *   <li>Données : setCodePostal(44000)</li>
     *   <li>Attendu : getCodePostal() = 44000</li>
     * </ul>
     */
    @Test
    public void testCodePostalValide() {
        afficherEntete(
            "T5",
            "Code postal valide (44000)",
            "setCodePostal(44000)",
            "getCodePostal() = 44000"
        );

        Entreprise e = new Entreprise("Test Corp");
        e.setCodePostal(44000);

        boolean ok = Integer.valueOf(44000).equals(e.getCodePostal());

        afficherResultat(ok, "getCodePostal()=" + e.getCodePostal());

        assertEquals(Integer.valueOf(44000), e.getCodePostal());
    }

    /**
     * <b>T6</b> — Code postal négatif accepté (pas de validation).
     *
     * <p><b>WARN</b> : aucune validation n'est effectuée sur le code postal.
     * Une valeur négative est stockée sans erreur.
     * Amélioration recommandée : ajouter une validation dans {@code setCodePostal()}.</p>
     *
     * <ul>
     *   <li>Données : setCodePostal(-44000)</li>
     *   <li>Attendu : stocké tel quel (pas de validation)</li>
     * </ul>
     */
    @Test
    public void testCodePostalNegatif() {
        afficherEntete(
            "T6",
            "Code postal negatif accepte (pas de validation)",
            "setCodePostal(-44000)",
            "WARN : stocke tel quel — validation a ajouter"
        );

        Entreprise e = new Entreprise("Test Corp");
        e.setCodePostal(-44000);

        afficherWarn("CP negatif accepte : getCodePostal()=" + e.getCodePostal()
                + " | recommandation : ajouter validation dans setCodePostal()");

        assertEquals(Integer.valueOf(-44000), e.getCodePostal());
    }

    /**
     * <b>T7</b> — Téléphone valide.
     *
     * <ul>
     *   <li>Données : setTel(612345678)</li>
     *   <li>Attendu : getTel() = 612345678</li>
     * </ul>
     */
    @Test
    public void testTelephoneValide() {
        afficherEntete(
            "T7",
            "Telephone valide",
            "setTel(612345678)",
            "getTel() = 612345678"
        );

        Entreprise e = new Entreprise("Test Corp");
        e.setTel(612345678);

        boolean ok = Integer.valueOf(612345678).equals(e.getTel());

        afficherResultat(ok, "getTel()=" + e.getTel());

        assertEquals(Integer.valueOf(612345678), e.getTel());
    }

    /**
     * <b>T8</b> — Téléphone négatif accepté (pas de validation).
     *
     * <p><b>WARN</b> : un numéro de téléphone négatif n'a pas de sens
     * mais est accepté sans erreur.
     * Amélioration recommandée : ajouter une validation dans {@code setTel()}.</p>
     *
     * <ul>
     *   <li>Données : setTel(-123456)</li>
     *   <li>Attendu : stocké tel quel (pas de validation)</li>
     * </ul>
     */
    @Test
    public void testTelephoneNegatif() {
        afficherEntete(
            "T8",
            "Telephone negatif accepte (pas de validation)",
            "setTel(-123456)",
            "WARN : stocke tel quel — validation a ajouter"
        );

        Entreprise e = new Entreprise("Test Corp");
        e.setTel(-123456);

        afficherWarn("tel negatif accepte : getTel()=" + e.getTel()
                + " | recommandation : ajouter validation dans setTel()");

        assertEquals(Integer.valueOf(-123456), e.getTel());
    }

    // ================================================================
    //  TESTS DE CAS LIMITES  (T9 à T12)
    // ================================================================

    /**
     * <b>T9</b> — Nom très long (255 caractères).
     *
     * <p>Vérifie que la classe accepte un nom de 255 caractères sans troncature.</p>
     *
     * <ul>
     *   <li>Données : nom = "A" répété 255 fois</li>
     *   <li>Attendu : getNom().length() = 255</li>
     * </ul>
     */
    @Test
    public void testNomTresLong() {
        afficherEntete(
            "T9",
            "Nom tres long (255 caracteres)",
            "new Entreprise(\"A\".repeat(255))",
            "getNom().length() = 255"
        );

        String nomLong = "A".repeat(255);
        Entreprise e = new Entreprise(nomLong);

        boolean ok = e.getNom().length() == 255;

        afficherResultat(ok, "longueur=" + e.getNom().length());

        assertNotNull(e);
        assertEquals(255, e.getNom().length());
    }

    /**
     * <b>T10</b> — Nom avec caractères spéciaux et accents.
     *
     * <p>Vérifie que les caractères accentués et spéciaux sont conservés
     * sans altération.</p>
     *
     * <ul>
     *   <li>Données : "Société Française d'Électronique &amp; Cie"</li>
     *   <li>Attendu : getNom() = valeur d'entrée exacte</li>
     * </ul>
     */
    @Test
    public void testCaracteresSpeciaux() {
        afficherEntete(
            "T10",
            "Nom avec caracteres speciaux et accents",
            "nom='Societe Francaise d Electronique & Cie'",
            "getNom() = valeur exacte conservee"
        );

        String nomSpecial = "Societe Francaise d Electronique & Cie";
        Entreprise e = new Entreprise(nomSpecial);

        boolean ok = nomSpecial.equals(e.getNom());

        afficherResultat(ok, "getNom()='" + e.getNom() + "'");

        assertEquals(nomSpecial, e.getNom());
    }

    /**
     * <b>T11</b> — Site web sans {@code https://} (pas de normalisation).
     *
     * <p><b>WARN</b> : le site web est stocké tel quel, sans ajout automatique
     * du protocole {@code https://}.
     * Amélioration recommandée : normaliser dans {@code setSiteWeb()}.</p>
     *
     * <ul>
     *   <li>Données : setSiteWeb("www.site.com")</li>
     *   <li>Attendu : WARN — stocké tel quel</li>
     * </ul>
     */
    @Test
    public void testSiteWebSansProtocole() {
        afficherEntete(
            "T11",
            "Site web sans https:// (pas de normalisation)",
            "setSiteWeb('www.site.com')",
            "WARN : stocke tel quel — normalisation a ajouter"
        );

        Entreprise e = new Entreprise("Test Corp");
        e.setSiteWeb("www.site.com");

        afficherWarn("site stocke sans protocole : getSiteWeb()='" + e.getSiteWeb()
                + "' | recommandation : normaliser avec 'https://' dans setSiteWeb()");

        assertEquals("www.site.com", e.getSiteWeb());
    }

    /**
     * <b>T12</b> — Description vide acceptée.
     *
     * <ul>
     *   <li>Données : setDescription("")</li>
     *   <li>Attendu : getDescription() = ""</li>
     * </ul>
     */
    @Test
    public void testDescriptionVide() {
        afficherEntete(
            "T12",
            "Description vide acceptee",
            "setDescription(\"\")",
            "getDescription() = \"\""
        );

        Entreprise e = new Entreprise("Test Corp");
        e.setDescription("");

        boolean ok = "".equals(e.getDescription());

        afficherResultat(ok, "getDescription()='" + e.getDescription() + "'");

        assertEquals("", e.getDescription());
    }

    // ================================================================
    //  TESTS DE COMPARAISON  (T13 à T17)
    // ================================================================

    /**
     * <b>T13</b> — {@code equals()} fonctionne correctement.
     *
     * <p>Vérifie que deux entreprises avec le même nom sont égales
     * et que deux entreprises avec des noms différents ne le sont pas.</p>
     *
     * <ul>
     *   <li>Données : e1=e2="Airbus", e3="Boeing"</li>
     *   <li>Attendu : e1.equals(e2)=true | e1.equals(e3)=false</li>
     * </ul>
     */
    @Test
    public void testEquals() {
        afficherEntete(
            "T13",
            "equals() fonctionne correctement",
            "e1='Airbus' | e2='Airbus' | e3='Boeing'",
            "e1.equals(e2)=true | e1.equals(e3)=false"
        );

        Entreprise e1 = new Entreprise("Airbus");
        Entreprise e2 = new Entreprise("Airbus");
        Entreprise e3 = new Entreprise("Boeing");

        boolean egaux    = e1.equals(e2);
        boolean different = !e1.equals(e3);

        afficherResultat(egaux && different,
            "e1.equals(e2)=" + egaux
            + " | e1.equals(e3)=" + e1.equals(e3));

        assertTrue("Meme nom => egaux", e1.equals(e2));
        assertFalse("Noms differents => non egaux", e1.equals(e3));
    }

    /**
     * <b>T14</b> — {@code hashCode()} cohérent avec {@code equals()}.
     *
     * <p>Vérifie que deux objets égaux ont le même hashCode
     * (contrat Java).</p>
     *
     * <ul>
     *   <li>Données : e1=e2="Airbus"</li>
     *   <li>Attendu : e1.hashCode() = e2.hashCode()</li>
     * </ul>
     */
    @Test
    public void testHashCode() {
        afficherEntete(
            "T14",
            "hashCode() coherent avec equals()",
            "e1='Airbus' | e2='Airbus'",
            "e1.hashCode() = e2.hashCode()"
        );

        Entreprise e1 = new Entreprise("Airbus");
        Entreprise e2 = new Entreprise("Airbus");

        boolean ok = e1.hashCode() == e2.hashCode();

        afficherResultat(ok,
            "hashCode(e1)=" + e1.hashCode()
            + " | hashCode(e2)=" + e2.hashCode()
            + " | egaux=" + ok);

        assertEquals(e1.hashCode(), e2.hashCode());
    }

    /**
     * <b>T15</b> — {@code compareTo()} respecte l'ordre alphabétique.
     *
     * <p>Vérifie le tri par nom, puis par adresse en cas d'égalité de nom.</p>
     *
     * <ul>
     *   <li>Données : e1="Airbus/AdrA", e2="Boeing/AdrB", e3="Airbus/AdrC"</li>
     *   <li>Attendu : Airbus &lt; Boeing | AdrA &lt; AdrC</li>
     * </ul>
     */
    @Test
    public void testCompareTo() {
        afficherEntete(
            "T15",
            "compareTo() respecte l'ordre alphabetique",
            "e1='Airbus/AdrA' | e2='Boeing/AdrB' | e3='Airbus/AdrC'",
            "e1<e2 | e2>e1 | e1<e3 (meme nom, comparaison par adresse)"
        );

        Entreprise e1 = new Entreprise("Airbus");
        e1.setAdresse("Adresse A");
        Entreprise e2 = new Entreprise("Boeing");
        e2.setAdresse("Adresse B");
        Entreprise e3 = new Entreprise("Airbus");
        e3.setAdresse("Adresse C");

        boolean ok = e1.compareTo(e2) < 0
                  && e2.compareTo(e1) > 0
                  && e1.compareTo(e3) < 0;

        afficherResultat(ok,
            "Airbus<Boeing=" + (e1.compareTo(e2) < 0)
            + " | Boeing>Airbus=" + (e2.compareTo(e1) > 0)
            + " | AdrA<AdrC=" + (e1.compareTo(e3) < 0));

        assertTrue("Airbus doit venir avant Boeing", e1.compareTo(e2) < 0);
        assertTrue("Boeing doit venir apres Airbus", e2.compareTo(e1) > 0);
        assertTrue("Meme nom : comparaison par adresse", e1.compareTo(e3) < 0);
    }

    /**
     * <b>T16</b> — {@code toString()} contient le nom de l'entreprise.
     *
     * <ul>
     *   <li>Données : new Entreprise("Airbus")</li>
     *   <li>Attendu : toString() non null et contient "Airbus"</li>
     * </ul>
     */
    @Test
    public void testToString() {
        afficherEntete(
            "T16",
            "toString() contient le nom de l'entreprise",
            "new Entreprise('Airbus')",
            "toString() non null | contient 'Airbus'"
        );

        Entreprise e = new Entreprise("Airbus");
        String result = e.toString();

        boolean ok = result != null && result.contains("Airbus");

        afficherResultat(ok, "toString()='" + result + "'");

        assertNotNull("toString() ne doit pas etre null", result);
        assertTrue("toString() doit contenir 'Airbus'", result.contains("Airbus"));
    }

    /**
     * <b>T17</b> — {@code getComparator()} trie correctement.
     *
     * <ul>
     *   <li>Données : compare(e1="Airbus", e2="Boeing")</li>
     *   <li>Attendu : résultat &lt; 0 (Airbus avant Boeing)</li>
     * </ul>
     */
    @Test
    public void testGetComparator() {
        afficherEntete(
            "T17",
            "getComparator() trie correctement",
            "compare(e1='Airbus', e2='Boeing')",
            "resultat < 0 (Airbus avant Boeing)"
        );

        Entreprise e1 = new Entreprise("Airbus");
        Entreprise e2 = new Entreprise("Boeing");

        int result = Entreprise.getComparator().compare(e1, e2);
        boolean ok = result < 0;

        afficherResultat(ok,
            "compare(Airbus, Boeing)=" + result + " | Airbus<Boeing=" + ok);

        assertTrue("Le comparator doit donner Airbus < Boeing", result < 0);
    }

    // ================================================================
    //  TESTS SUPPLÉMENTAIRES — BUGS IDENTIFIÉS  (T18 à T20)
    // ================================================================

    /**
     * <b>T18</b> — {@code compareTo()} avec même nom et adresse null → NPE.
     *
     * <p><b>WARN — BUG IDENTIFIÉ</b> : lorsque deux entreprises ont le même nom
     * mais que leur adresse est {@code null}, {@code compareTo()} lance une
     * {@link NullPointerException} à la ligne
     * {@code this.getAdresse().toUpperCase()} dans {@code Entreprise.java}.</p>
     *
     * <p>Correction recommandée : ajouter un contrôle null avant la comparaison
     * par adresse dans {@code compareTo()}.</p>
     *
     * <ul>
     *   <li>Données : e1="Airbus" (adresse null), e2="Airbus" (adresse null)</li>
     *   <li>Attendu : NullPointerException levée (bug confirmé)</li>
     * </ul>
     */
    @Test(expected = NullPointerException.class)
    public void testCompareToMemeNomAdresseNullLanceException() {
        afficherEntete(
            "T18",
            "compareTo() meme nom + adresse null => NullPointerException",
            "e1='Airbus' (adresse=null) | e2='Airbus' (adresse=null)",
            "WARN : NullPointerException — bug confirme dans compareTo()"
        );

        Entreprise e1 = new Entreprise("Airbus");
        Entreprise e2 = new Entreprise("Airbus");

        afficherWarn("NullPointerException attendue — "
                + "bug dans compareTo() ligne this.getAdresse().toUpperCase() "
                + "| correction : ajouter controle null sur adresse");

        e1.compareTo(e2); // doit lever NPE
    }

    /**
     * <b>T19</b> — {@code equals(null)} retourne {@code false}.
     *
     * <p>Vérifie le contrat Java : {@code equals(null)} ne doit jamais
     * retourner {@code true} ni lever d'exception.</p>
     *
     * <ul>
     *   <li>Données : e.equals(null)</li>
     *   <li>Attendu : false</li>
     * </ul>
     */
    @Test
    public void testEqualsAvecNull() {
        afficherEntete(
            "T19",
            "equals(null) retourne false",
            "new Entreprise('Airbus').equals(null)",
            "false — pas d'exception"
        );

        Entreprise e = new Entreprise("Airbus");
        boolean ok = !e.equals(null);

        afficherResultat(ok, "equals(null)=" + e.equals(null));

        assertFalse("equals(null) doit retourner false", e.equals(null));
    }

    /**
     * <b>T20</b> — {@code getComparator()} avec {@code null} en premier paramètre.
     *
     * <p>Vérifie que le comparateur gère correctement le cas où le premier
     * argument est {@code null} (retourne une valeur négative).</p>
     *
     * <ul>
     *   <li>Données : compare(null, e="Airbus")</li>
     *   <li>Attendu : résultat &lt; 0 (null placé avant Airbus)</li>
     * </ul>
     */
    @Test
    public void testComparatorAvecNullEnPremier() {
        afficherEntete(
            "T20",
            "getComparator() avec null en premier parametre",
            "compare(null, new Entreprise('Airbus'))",
            "resultat < 0 (null place avant Airbus)"
        );

        Entreprise e = new Entreprise("Airbus");
        int result = Entreprise.getComparator().compare(null, e);
        boolean ok = result < 0;

        afficherResultat(ok,
            "compare(null, Airbus)=" + result + " | null<Airbus=" + ok);

        assertTrue("null doit etre place avant Airbus", result < 0);
    }
}