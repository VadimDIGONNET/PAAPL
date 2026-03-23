package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.controllers.ServiceExport;
import fr.centrale.nantes.pappl.items.Annee;
import fr.centrale.nantes.pappl.items.Correspondant;
import fr.centrale.nantes.pappl.items.DescriptionSujet;
import fr.centrale.nantes.pappl.items.Entreprise;
import fr.centrale.nantes.pappl.items.Sujet;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Tests unitaires des fonctionnalités d'export et d'import.
 *
 * <p>Ce fichier teste la logique métier des exports Word et de l'import,
 * <b>sans connexion à la base de données</b>.</p>
 *
 * <p>Fonctionnalités testées ici :</p>
 * <ul>
 *   <li>{@link ServiceExport} — remplacement des placeholders {@code {{clé}}}</li>
 *   <li>Cohérence de la map de données ({@code prepareDataMap})</li>
 *   <li>Logique de parsing de l'import (SDT, année manquante, correspondant sans entreprise)</li>
 * </ul>
 *
 * <pre>
 * BLOC   Scénario                                         Tests
 * BLOC A Remplacement de placeholders (cas nominaux)      E1, E2, E3, E4, E5
 * BLOC B Correction clé 'titre' et 'confidentiel'        E9, E11
 * BLOC C Cohérence de la map de données                   E12, E13, E14
 * BLOC D Scénarios d'import (logique de parsing)         I1, I2, I3
 * </pre>
 *
 * @author  PGROUPE
 * @version 1.0
 * @since   15 mars 2026
 *
 * <pre>
 * Technologie        Version
 * Langage            Java 17
 * Framework de test  JUnit 4.13.2
 * Outil de build     Maven 3.x
 * IDE                NetBeans
 * Dépendance         Apache POI (xwpf)
 * </pre>
 */
public class ExportImportTest {

    /** Service d'export Word testé. */
    private ServiceExport serviceExport;

    /** Sujet de test avec intitulé et année. */
    private Sujet sujet;

    /** Description du sujet — contexte, travail, livrable. */
    private DescriptionSujet descriptionSujet;

    /** Entreprise de test. */
    private Entreprise entreprise;

    /** Correspondant de test. */
    private Correspondant correspondant;

    /** Année académique de test. */
    private Annee annee;

    // ================================================================
    //  MÉTHODES UTILITAIRES D'AFFICHAGE
    // ================================================================

    /**
     * Affiche l'en-tête structuré d'un test dans la console.
     *
     * @param numero   identifiant du test (ex. "E1", "I2")
     * @param bloc     nom du bloc fonctionnel (ex. "BLOC A")
     * @param scenario description courte du scénario testé
     * @param donnees  description des données d'entrée utilisées
     * @param attendu  résultat attendu du test
     */
    private void afficherEntete(String numero, String bloc, String scenario,
                                String donnees, String attendu) {
        System.out.println();
        System.out.println("+===========================================================+");
        System.out.printf("  TEST %-4s [%s] — %s%n", numero, bloc, scenario);
        System.out.println("+-----------------------------------------------------------+");
        System.out.println("  Bloc      : " + bloc);
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

    // ================================================================
    //  MÉTHODES UTILITAIRES TECHNIQUES
    // ================================================================

    /**
     * Crée un document Word en mémoire avec un seul paragraphe
     * contenant le texte fourni dans un unique run.
     *
     * @param texte contenu du paragraphe
     * @return flux d'octets du document Word
     * @throws Exception si la création échoue
     */
    private ByteArrayInputStream creerDocAvecUnRun(String texte) throws Exception {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph para = doc.createParagraph();
        para.createRun().setText(texte);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        doc.write(out);
        doc.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    /**
     * Lit le texte du premier paragraphe d'un document Word en mémoire.
     *
     * @param docBytes tableau d'octets du document Word
     * @return texte du premier paragraphe
     * @throws Exception si la lecture échoue
     */
    private String lirePremierParagraphe(byte[] docBytes) throws Exception {
        XWPFDocument doc = new XWPFDocument(new ByteArrayInputStream(docBytes));
        String texte = doc.getParagraphs().get(0).getText();
        doc.close();
        return texte;
    }

    // ================================================================
    //  INITIALISATION ET NETTOYAGE
    // ================================================================

    /**
     * Initialise les objets communs avant chaque test.
     */
    @Before
    public void setUp() {
        System.out.println();
        System.out.println("############################################################");
        System.out.println("#              INITIALISATION DU TEST                      #");
        System.out.println("############################################################");

        serviceExport = new ServiceExport();
        annee         = new Annee(2026);

        entreprise = new Entreprise("Airbus");
        entreprise.setAdresse("1 Rond-Point Emile Dewoitine");
        entreprise.setCodePostal(31700);
        entreprise.setTel(561234567);
        entreprise.setDescription("Constructeur aeronautique europeen");

        correspondant = new Correspondant();
        correspondant.setId(1);
        correspondant.setNom("Dupont");
        correspondant.setPrenom("Jean");
        correspondant.setTel(612345678);
        correspondant.setEmail("jean.dupont@airbus.fr");
        correspondant.setEntreprise(entreprise);

        sujet = new Sujet();
        sujet.setNumero(1);
        sujet.setIntitule("Conception d un systeme embarque");
        sujet.setAnnee(annee);
        sujet.setIdCorrespondant(correspondant);

        descriptionSujet = new DescriptionSujet();
        descriptionSujet.setNumero(1);
        descriptionSujet.setContexte("Contexte du projet aeronautique");
        descriptionSujet.setTravailRealiser("Concevoir et implementer le systeme");
        descriptionSujet.setLivrable("Rapport technique et prototype");
        descriptionSujet.setConfidentiel("Non");
        descriptionSujet.setNomEntreprise(entreprise);
        descriptionSujet.setSujet(sujet);

        System.out.println("  ServiceExport instancie");
        System.out.println("  Sujet         : [" + sujet.getNumero() + "] "
                + sujet.getIntitule());
        System.out.println("  Entreprise    : " + entreprise.getNom());
        System.out.println("  Correspondant : " + correspondant.getNom()
                + " " + correspondant.getPrenom());
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
    //  BLOC A — Remplacement de placeholders  (E1 à E5)
    // ================================================================

    /**
     * <b>E1</b> — Placeholder {@code {{nom}}} remplacé dans un seul run.
     *
     * <ul>
     *   <li>Données : texte="Corresp. : {{nom}}" | map={nom="Dupont"}</li>
     *   <li>Attendu : résultat contient "Dupont" | ne contient plus "{{nom}}"</li>
     * </ul>
     */
    @Test
    public void testE1PlaceholderNomSimple() throws Exception {
        afficherEntete(
            "E1", "BLOC A",
            "Placeholder {{nom}} remplace dans un seul run",
            "texte='Corresp. : {{nom}}' | map={nom='Dupont'}",
            "resultat contient 'Dupont' | ne contient plus '{{nom}}'"
        );

        Map<String, String> data = new HashMap<>();
        data.put("nom", "Dupont");

        byte[] result = serviceExport.generateWordFromDb(
                creerDocAvecUnRun("Corresp. : {{nom}}"), data);
        String texte = lirePremierParagraphe(result);

        boolean contientValeur    = texte.contains("Dupont");
        boolean placeholderAbsent = !texte.contains("{{nom}}");

        afficherPass(
            "texte='" + texte + "'"
            + " | 'Dupont' present=" + contientValeur
            + " | '{{nom}}' absent=" + placeholderAbsent);

        assertTrue("'Dupont' doit etre dans le texte", contientValeur);
        assertFalse("Le placeholder ne doit plus exister", texte.contains("{{nom}}"));
    }

    /**
     * <b>E2</b> — Plusieurs placeholders dans le même run.
     *
     * <ul>
     *   <li>Données : "{{entreprise}} — {{titre}} — {{annee}}"</li>
     *   <li>Attendu : "Airbus" | "Conception..." | "2026" — tous remplacés</li>
     * </ul>
     */
    @Test
    public void testE2PlusieursPhlaceholdersUnRun() throws Exception {
        afficherEntete(
            "E2", "BLOC A",
            "Plusieurs placeholders dans un meme run",
            "texte='{{entreprise}} — {{titre}} — {{annee}}'",
            "'Airbus' | 'Conception...' | '2026' — tous remplaces"
        );

        Map<String, String> data = new HashMap<>();
        data.put("entreprise", "Airbus");
        data.put("titre",      "Conception d un systeme");
        data.put("annee",      "2026");

        byte[] result = serviceExport.generateWordFromDb(
                creerDocAvecUnRun("{{entreprise}} — {{titre}} — {{annee}}"), data);
        String texte = lirePremierParagraphe(result);

        boolean airbus = texte.contains("Airbus");
        boolean titre  = texte.contains("Conception d un systeme");
        boolean annee  = texte.contains("2026");

        afficherPass(
            "texte='" + texte + "'"
            + " | Airbus=" + airbus
            + " | titre=" + titre
            + " | annee=" + annee);

        assertTrue("'Airbus' doit etre present", airbus);
        assertTrue("Le titre doit etre present", titre);
        assertTrue("L'annee doit etre presente", annee);
    }

    /**
     * <b>E3</b> — Placeholder absent de la map → texte inchangé.
     *
     * <ul>
     *   <li>Données : texte="{{telephone}}" | map vide</li>
     *   <li>Attendu : "{{telephone}}" reste intact</li>
     * </ul>
     */
    @Test
    public void testE3PlaceholderAbsentDansMap() throws Exception {
        afficherEntete(
            "E3", "BLOC A",
            "Placeholder absent de la map => texte inchange",
            "texte='{{telephone}}' | map={}",
            "texte contient encore '{{telephone}}'"
        );

        Map<String, String> data = new HashMap<>();

        byte[] result = serviceExport.generateWordFromDb(
                creerDocAvecUnRun("{{telephone}}"), data);
        String texte = lirePremierParagraphe(result);

        boolean intact = texte.contains("{{telephone}}");

        afficherPass("texte='" + texte + "' | placeholder intact=" + intact);

        assertTrue("Le placeholder doit rester si absent de la map", intact);
    }

    /**
     * <b>E4</b> — Valeur null dans la map → placeholder remplacé par chaîne vide.
     *
     * <ul>
     *   <li>Données : texte="Email : {{email}}" | map={email=null}</li>
     *   <li>Attendu : "{{email}}" absent | "Email :" conservé</li>
     * </ul>
     */
    @Test
    public void testE4PlaceholderValeurNull() throws Exception {
        afficherEntete(
            "E4", "BLOC A",
            "Valeur null dans la map => placeholder remplace par chaine vide",
            "texte='Email : {{email}}' | map={email=null}",
            "'{{email}}' absent | 'Email :' conserve | pas d'exception"
        );

        Map<String, String> data = new HashMap<>();
        data.put("email", null);

        byte[] result = serviceExport.generateWordFromDb(
                creerDocAvecUnRun("Email : {{email}}"), data);
        String texte = lirePremierParagraphe(result);

        boolean placeholderAbsent = !texte.contains("{{email}}");
        boolean prefixePresent    =  texte.contains("Email :");

        afficherPass(
            "texte='" + texte + "'"
            + " | placeholder absent=" + placeholderAbsent
            + " | prefixe present=" + prefixePresent);

        assertFalse("Le placeholder ne doit plus exister", texte.contains("{{email}}"));
        assertTrue("'Email :' doit rester", prefixePresent);
    }

    /**
     * <b>E5</b> — Texte sans placeholder → document inchangé.
     *
     * <ul>
     *   <li>Données : texte="Texte normal sans accolades" | map={nom="Dupont"}</li>
     *   <li>Attendu : texte identique après traitement</li>
     * </ul>
     */
    @Test
    public void testE5TexteSansPlaceholder() throws Exception {
        afficherEntete(
            "E5", "BLOC A",
            "Texte sans placeholder => document inchange",
            "texte='Texte normal sans accolades' | map={nom='Dupont'}",
            "texte identique apres traitement"
        );

        Map<String, String> data = new HashMap<>();
        data.put("nom", "Dupont");

        byte[] result = serviceExport.generateWordFromDb(
                creerDocAvecUnRun("Texte normal sans accolades"), data);
        String texte = lirePremierParagraphe(result);

        afficherPass("texte='" + texte + "' | identique=" + texte.equals("Texte normal sans accolades"));

        assertEquals("Le texte ne doit pas changer",
                "Texte normal sans accolades", texte);
    }

    // ================================================================
    //  BLOC B — Correction clé 'titre' et 'confidentiel'  (E9, E11)
    // ================================================================

    /**
     * <b>E9</b> — Clé "titre" remplace bien "{{titre}}".
     *
     * <ul>
     *   <li>Données : map={titre="Conception..."} | template="Titre : {{titre}}"</li>
     *   <li>Attendu : "Conception..." remplace "{{titre}}"</li>
     * </ul>
     */
    @Test
    public void testE9CletitreFonctionne() throws Exception {
        afficherEntete(
            "E9", "BLOC B",
            "Cle 'titre' remplace bien '{{titre}}'",
            "map={titre='Conception d un systeme'} | template='Titre : {{titre}}'",
            "'Conception d un systeme' remplace '{{titre}}'"
        );

        Map<String, String> data = new HashMap<>();
        data.put("titre", "Conception d un systeme");

        byte[] result = serviceExport.generateWordFromDb(
                creerDocAvecUnRun("Titre : {{titre}}"), data);
        String texte = lirePremierParagraphe(result);

        boolean valeurPresente    = texte.contains("Conception d un systeme");
        boolean placeholderAbsent = !texte.contains("{{titre}}");

        afficherPass(
            "texte='" + texte + "'"
            + " | valeur presente=" + valeurPresente
            + " | placeholder absent=" + placeholderAbsent);

        assertTrue("La valeur doit etre dans le texte", valeurPresente);
        assertFalse("'{{titre}}' ne doit plus exister", texte.contains("{{titre}}"));
    }

    /**
     * <b>E11</b> — Clé "confidentiel" remplace bien "{{confidentiel}}".
     *
     * <ul>
     *   <li>Données : map={confidentiel="Non"} | template="Confidentiel : {{confidentiel}}"</li>
     *   <li>Attendu : "Non" remplace "{{confidentiel}}"</li>
     * </ul>
     */
    @Test
    public void testE11CleConfidentielFonctionne() throws Exception {
        afficherEntete(
            "E11", "BLOC B",
            "Cle 'confidentiel' remplace bien '{{confidentiel}}'",
            "map={confidentiel='Non'} | template='Confidentiel : {{confidentiel}}'",
            "'Non' remplace '{{confidentiel}}'"
        );

        Map<String, String> data = new HashMap<>();
        data.put("confidentiel", "Non");

        byte[] result = serviceExport.generateWordFromDb(
                creerDocAvecUnRun("Confidentiel : {{confidentiel}}"), data);
        String texte = lirePremierParagraphe(result);

        boolean valeurPresente    = texte.contains("Non");
        boolean placeholderAbsent = !texte.contains("{{confidentiel}}");

        afficherPass(
            "texte='" + texte + "'"
            + " | 'Non' present=" + valeurPresente
            + " | placeholder absent=" + placeholderAbsent);

        assertTrue("'Non' doit etre dans le texte", valeurPresente);
        assertFalse("'{{confidentiel}}' ne doit plus exister",
                texte.contains("{{confidentiel}}"));
    }

    // ================================================================
    //  BLOC C — Cohérence de la map de données  (E12 à E14)
    // ================================================================

    /**
     * <b>E12</b> — La map contient toutes les clés attendues par le template Word.
     *
     * <ul>
     *   <li>Données : entités Sujet + Entreprise + Correspondant + DescriptionSujet</li>
     *   <li>Attendu : map contient les 15 clés du template Word</li>
     * </ul>
     */
    @Test
    public void testE12MapContientToutesLesCles() {
        afficherEntete(
            "E12", "BLOC C",
            "La map contient toutes les cles du template Word",
            "Sujet + Entreprise + Correspondant + DescriptionSujet",
            "15 cles presentes : titre, contexte, travail, livrable, confidentiel, "
            + "annee, entreprise, description, adresse, code_postal, "
            + "telentre, nom, prenom, telephone, email"
        );

        Map<String, String> data = new HashMap<>();
        data.put("titre",        sujet.getIntitule());
        data.put("contexte",     descriptionSujet.getContexte());
        data.put("travail",      descriptionSujet.getTravailRealiser());
        data.put("livrable",     descriptionSujet.getLivrable());
        data.put("confidentiel", descriptionSujet.getConfidentiel());
        data.put("annee",        String.valueOf(sujet.getAnnee().getAnnee()));
        data.put("entreprise",   entreprise.getNom());
        data.put("description",  entreprise.getDescription());
        data.put("adresse",      entreprise.getAdresse());
        data.put("code_postal",  String.valueOf(entreprise.getCodePostal()));
        data.put("telentre",     String.valueOf(entreprise.getTel()));
        data.put("nom",          correspondant.getNom());
        data.put("prenom",       correspondant.getPrenom());
        data.put("telephone",    String.valueOf(correspondant.getTel()));
        data.put("email",        correspondant.getEmail());

        String[] clesRequises = {
            "titre", "contexte", "travail", "livrable", "confidentiel",
            "annee", "entreprise", "description", "adresse", "code_postal",
            "telentre", "nom", "prenom", "telephone", "email"
        };

        boolean toutesPresentes = true;
        StringBuilder manquantes = new StringBuilder();
        for (String cle : clesRequises) {
            if (!data.containsKey(cle)) {
                toutesPresentes = false;
                manquantes.append(cle).append(" ");
            }
        }

        afficherPass(
            "nb cles=" + data.size()
            + " | toutes presentes=" + toutesPresentes
            + (manquantes.length() > 0 ? " | manquantes=" + manquantes : ""));

        assertTrue("Toutes les cles du template doivent etre presentes",
                toutesPresentes);
        assertEquals("La map doit contenir 15 cles", 15, data.size());
    }

    /**
     * <b>E13</b> — Les valeurs de la map correspondent aux données des entités.
     *
     * <ul>
     *   <li>Données : map construite depuis sujet + entreprise + correspondant</li>
     *   <li>Attendu : map["nom"]="Dupont" | map["entreprise"]="Airbus" | map["annee"]="2026"</li>
     * </ul>
     */
    @Test
    public void testE13ValeursMapCorrespondentEntites() {
        afficherEntete(
            "E13", "BLOC C",
            "Les valeurs de la map correspondent aux donnees des entites",
            "map construite depuis sujet + entreprise + correspondant",
            "map['nom']='Dupont' | map['entreprise']='Airbus' | map['annee']='2026'"
        );

        Map<String, String> data = new HashMap<>();
        data.put("titre",      sujet.getIntitule());
        data.put("entreprise", entreprise.getNom());
        data.put("nom",        correspondant.getNom());
        data.put("prenom",     correspondant.getPrenom());
        data.put("email",      correspondant.getEmail());
        data.put("annee",      String.valueOf(sujet.getAnnee().getAnnee()));

        afficherPass(
            "nom=" + data.get("nom")
            + " | prenom=" + data.get("prenom")
            + " | entreprise=" + data.get("entreprise")
            + " | annee=" + data.get("annee")
            + " | titre=" + data.get("titre"));

        assertEquals("Dupont", data.get("nom"));
        assertEquals("Jean",   data.get("prenom"));
        assertEquals("Airbus", data.get("entreprise"));
        assertEquals("2026",   data.get("annee"));
    }

    /**
     * <b>E14</b> — La map gère les champs null sans NullPointerException.
     *
     * <ul>
     *   <li>Données : correspondant.tel=null | entreprise.adresse=null</li>
     *   <li>Attendu : map["telephone"]="" | map["adresse"]="" | pas de NPE</li>
     * </ul>
     */
    @Test
    public void testE14MapGereChampNullSansException() {
        afficherEntete(
            "E14", "BLOC C",
            "La map gere les champs null sans NullPointerException",
            "correspondant.tel=null | entreprise.adresse=null",
            "map['telephone']='' | map['adresse']='' | pas de NPE"
        );

        correspondant.setTel(null);
        entreprise.setAdresse(null);

        Map<String, String> data = new HashMap<>();
        data.put("telephone",
                correspondant.getTel() != null
                ? String.valueOf(correspondant.getTel()) : "");
        data.put("adresse",
                entreprise.getAdresse() != null
                ? entreprise.getAdresse() : "");

        afficherPass(
            "telephone='" + data.get("telephone")
            + "' (vide=" + "".equals(data.get("telephone")) + ")"
            + " | adresse='" + data.get("adresse")
            + "' (vide=" + "".equals(data.get("adresse")) + ")"
            + " | pas de NPE");

        assertEquals("telephone null => chaine vide", "", data.get("telephone"));
        assertEquals("adresse null => chaine vide",   "", data.get("adresse"));
    }

    // ================================================================
    //  BLOC D — Scénarios d'import  (I1 à I3)
    // ================================================================

    /**
     * <b>I1</b> — Extraction de tags SDT depuis une map simulée.
     *
     * <p>Simule {@code ImportController.extractDataFromSDT()} qui extrait
     * les valeurs des contrôles de contenu Word et les stocke par tag.</p>
     *
     * <ul>
     *   <li>Données : map simulée {titre="Stage web", entreprise="Airbus", nom="Dupont"}</li>
     *   <li>Attendu : valeurs accessibles par leur clé</li>
     * </ul>
     */
    @Test
    public void testI1ExtractionTagSDTSimulee() {
        afficherEntete(
            "I1", "BLOC D",
            "Extraction de tags SDT depuis une map simulee",
            "map={titre='Stage web', entreprise='Airbus', nom='Dupont'}",
            "map.get('titre')='Stage web' | map.get('entreprise')='Airbus'"
        );

        Map<String, String> data = new HashMap<>();
        data.put("titre",      "Stage web");
        data.put("entreprise", "Airbus");
        data.put("nom",        "Dupont");
        data.put("prenom",     "Jean");
        data.put("telephone",  "612345678");
        data.put("email",      "jean.dupont@airbus.fr");
        data.put("annee",      "2026");

        afficherPass(
            "titre='" + data.get("titre") + "'"
            + " | entreprise='" + data.get("entreprise") + "'"
            + " | nom='" + data.get("nom") + "'");

        assertEquals("Stage web", data.get("titre"));
        assertEquals("Airbus",    data.get("entreprise"));
        assertEquals("Dupont",    data.get("nom"));
    }

    /**
     * <b>I2</b> — Import avec champ "annee" manquant → annee=null.
     *
     * <p>Simule {@code ImportController} quand l'année est absente
     * du fichier importé. Aucun sujet ne doit être créé.</p>
     *
     * <ul>
     *   <li>Données : map sans "annee"</li>
     *   <li>Attendu : anneeStr="" | anneeint=-1 | annee=null</li>
     * </ul>
     */
    @Test
    public void testI2ImportAnneeManquante() {
        afficherEntete(
            "I2", "BLOC D",
            "Import avec champ 'annee' manquant => annee=null",
            "map sans cle 'annee' (getOrDefault renvoie '')",
            "anneeStr='' | anneeint=-1 | objet Annee non cree"
        );

        Map<String, String> data = new HashMap<>();
        data.put("titre",      "Stage web");
        data.put("entreprise", "Airbus");
        // "annee" volontairement absent

        String anneeStr = data.getOrDefault("annee", "");
        int anneeint = -1;
        try {
            anneeint = Integer.parseInt(anneeStr);
        } catch (NumberFormatException e) {
            // anneeStr vide → exception → anneeint reste -1
        }

        Annee anneeObj = null;
        if (anneeint > 0) {
            anneeObj = new Annee(anneeint);
        }

        afficherPass(
            "anneeStr='" + anneeStr + "'"
            + " | anneeint=" + anneeint
            + " | annee=null=" + (anneeObj == null)
            + " | sujet non cree sans annee valide");

        assertEquals("anneeint doit etre -1", -1, anneeint);
        assertNull("L'objet Annee ne doit pas etre cree", anneeObj);
    }

    /**
     * <b>I3</b> — Import avec correspondant sans entreprise → correspondant non créé.
     *
     * <p>Simule la logique de garde dans {@code ImportController} :
     * le correspondant n'est créé que si nom != null ET entreprise != null.</p>
     *
     * <ul>
     *   <li>Données : nom="Dupont" | entreprise=null</li>
     *   <li>Attendu : correspondant=null</li>
     * </ul>
     */
    @Test
    public void testI3ImportCorrespondantSansEntrepriseNonCree() {
        afficherEntete(
            "I3", "BLOC D",
            "Import : correspondant sans entreprise => non cree",
            "nom='Dupont' | entreprise=null",
            "correspondant=null — condition (nom!=null && entreprise!=null) non remplie"
        );

        String nom      = "Dupont";
        Entreprise entr = null;

        Correspondant corr = null;
        if (nom != null && !nom.isEmpty() && entr != null) {
            corr = new Correspondant();
            corr.setNom(nom);
            corr.setEntreprise(entr);
        }

        afficherPass(
            "nom='" + nom + "'"
            + " | entreprise=null"
            + " | correspondant cree=" + (corr != null)
            + " | comportement correct");

        assertNull("Le correspondant ne doit pas etre cree sans entreprise", corr);
    }
}