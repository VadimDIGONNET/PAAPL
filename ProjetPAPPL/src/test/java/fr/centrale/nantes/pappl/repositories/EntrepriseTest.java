package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.Entreprise;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

/**
 * TESTS pour la classe Entreprise
 * 
 * Teste la logique métier SANS base de données :
 * - Création d'objets Entreprise
 * - equals()
 * - hashCode()
 * - compareTo()
 * - toString()
 */
public class EntrepriseTest {
    
    @Before
    public void setUp() {
        System.out.println("\n========================================");
        System.out.println("Initialisation du test");
        System.out.println("========================================");
    }
    
    @After
    public void tearDown() {
        System.out.println("Test terminé\n");
    }
    
    // ============================================================
    // TESTS DE CRÉATION D'OBJETS
    // ============================================================
    
    @Test
    public void testCreateEntrepriseComplete() {
        System.out.println("\n>>> TEST 1 : Créer une entreprise complète");
        
        // ACT
        Entreprise e = new Entreprise("Airbus");
        e.setAdresse("1 Rond-Point Émile Dewoitine");
        e.setCodePostal(31700);
        e.setTel(561234567);
        e.setSiteWeb("www.airbus.com");
        e.setDescription("Constructeur aéronautique européen");
        
        // ASSERT
        assertNotNull("L'entreprise ne devrait pas être null", e);
        assertEquals("Le nom devrait être 'Airbus'", "Airbus", e.getNom());
        assertEquals("L'adresse devrait correspondre", "1 Rond-Point Émile Dewoitine", e.getAdresse());
        assertEquals("Le code postal devrait être 31700", Integer.valueOf(31700), e.getCodePostal());
        assertEquals("Le téléphone devrait correspondre", Integer.valueOf(561234567), e.getTel());
        assertEquals("Le site web devrait correspondre", "www.airbus.com", e.getSiteWeb());
        assertEquals("La description devrait correspondre", "Constructeur aéronautique européen", e.getDescription());
        
        System.out.println("✅ RÉUSSI : Entreprise créée avec tous les champs");
        System.out.println("   Nom: " + e.getNom());
        System.out.println("   Adresse: " + e.getAdresse());
    }
    
    @Test
    public void testCreateEntrepriseAvecNomSeul() {
        System.out.println("\n>>> TEST 2 : Créer une entreprise avec seulement le nom");
        
        // ACT
        Entreprise e = new Entreprise("Startup Inc");
        
        // ASSERT
        assertNotNull("L'entreprise ne devrait pas être null", e);
        assertEquals("Le nom devrait être 'Startup Inc'", "Startup Inc", e.getNom());
        assertNull("L'adresse devrait être null", e.getAdresse());
        assertNull("Le code postal devrait être null", e.getCodePostal());
        assertNull("Le téléphone devrait être null", e.getTel());
        
        System.out.println("✅ RÉUSSI : Entreprise créée avec seulement le nom");
    }
    
    @Test
    public void testSetNomNull() {
        System.out.println("\n>>> TEST 3 : Tester setNom(null)");
        
        // ACT
        Entreprise e = new Entreprise();
        e.setNom(null);
        
        // ASSERT
        assertNull("Le nom devrait être null", e.getNom());
        
        System.out.println("✅ RÉUSSI : setNom(null) fonctionne");
    }
    
    @Test
    public void testSetNomVide() {
        System.out.println("\n>>> TEST 4 : Tester setNom(\"\")");
        
        // ACT
        Entreprise e = new Entreprise();
        e.setNom("");
        
        // ASSERT
        assertEquals("Le nom devrait être vide", "", e.getNom());
        
        System.out.println("✅ RÉUSSI : setNom(\"\") fonctionne");
    }
    
    // ============================================================
    // TESTS DE VALIDATION DES DONNÉES
    // ============================================================
    
    @Test
    public void testCodePostalValide() {
        System.out.println("\n>>> TEST 5 : Code postal valide (44000)");
        
        // ACT
        Entreprise e = new Entreprise("Test Corp");
        e.setCodePostal(44000);
        
        // ASSERT
        assertEquals("Le code postal devrait être 44000", Integer.valueOf(44000), e.getCodePostal());
        
        System.out.println("✅ RÉUSSI : Code postal valide accepté");
    }
    
    @Test
    public void testCodePostalNegatif() {
        System.out.println("\n>>> TEST 6 : Code postal négatif");
        
        // ACT
        Entreprise e = new Entreprise("Test Corp");
        e.setCodePostal(-44000);
        
        // ASSERT
        assertEquals("Le code postal devrait être stocké (pas de validation)", Integer.valueOf(-44000), e.getCodePostal());
        
        System.out.println("⚠️  INFO : Code postal négatif accepté (pas de validation dans la classe)");
    }
    
    @Test
    public void testTelephoneValide() {
        System.out.println("\n>>> TEST 7 : Téléphone valide");
        
        // ACT
        Entreprise e = new Entreprise("Test Corp");
        e.setTel(612345678);
        
        // ASSERT
        assertEquals("Le téléphone devrait être stocké", Integer.valueOf(612345678), e.getTel());
        
        System.out.println("✅ RÉUSSI : Téléphone valide accepté");
    }
    
    @Test
    public void testTelephoneNegatif() {
        System.out.println("\n>>> TEST 8 : Téléphone négatif");
        
        // ACT
        Entreprise e = new Entreprise("Test Corp");
        e.setTel(-123456);
        
        // ASSERT
        assertEquals("Le téléphone devrait être stocké (pas de validation)", Integer.valueOf(-123456), e.getTel());
        
        System.out.println("⚠️  INFO : Téléphone négatif accepté (pas de validation)");
    }
    
    // ============================================================
    // TESTS DE CAS LIMITES
    // ============================================================
    
    @Test
    public void testNomTresLong() {
        System.out.println("\n>>> TEST 9 : Nom très long (255 caractères)");
        
        // ACT
        String nomLong = "A".repeat(255);
        Entreprise e = new Entreprise(nomLong);
        
        // ASSERT
        assertNotNull("L'entreprise devrait être créée", e);
        assertEquals("Le nom devrait avoir 255 caractères", 255, e.getNom().length());
        
        System.out.println("✅ RÉUSSI : Nom de 255 caractères accepté");
    }
    
    @Test
    public void testCaracteresSpeciaux() {
        System.out.println("\n>>> TEST 10 : Nom avec caractères spéciaux et accents");
        
        // ACT
        String nomSpecial = "Société Française d'Électronique & Cie";
        Entreprise e = new Entreprise(nomSpecial);
        
        // ASSERT
        assertEquals("Le nom avec accents devrait être conservé", nomSpecial, e.getNom());
        
        System.out.println("✅ RÉUSSI : Caractères spéciaux et accents acceptés");
    }
    
    @Test
    public void testSiteWebSansProtocole() {
        System.out.println("\n>>> TEST 11 : Site web sans https://");
        
        // ACT
        Entreprise e = new Entreprise("Test Corp");
        e.setSiteWeb("www.site.com");
        
        // ASSERT
        assertEquals("Le site web devrait être accepté tel quel", "www.site.com", e.getSiteWeb());
        
        System.out.println("✅ RÉUSSI : Site web sans protocole accepté");
    }
    
    @Test
    public void testDescriptionVide() {
        System.out.println("\n>>> TEST 12 : Description vide");
        
        // ACT
        Entreprise e = new Entreprise("Test Corp");
        e.setDescription("");
        
        // ASSERT
        assertEquals("La description vide devrait être acceptée", "", e.getDescription());
        
        System.out.println("✅ RÉUSSI : Description vide acceptée");
    }
    
    // ============================================================
    // TESTS DE COMPARAISON (equals, hashCode, compareTo)
    // ============================================================
    
    @Test
    public void testEquals() {
        System.out.println("\n>>> TEST 13 : Test de la méthode equals()");
        
        // ACT
        Entreprise e1 = new Entreprise("Airbus");
        Entreprise e2 = new Entreprise("Airbus");
        Entreprise e3 = new Entreprise("Boeing");
        
        // ASSERT
        assertTrue("Deux entreprises avec le même nom devraient être égales", e1.equals(e2));
        assertFalse("Deux entreprises avec des noms différents ne devraient pas être égales", e1.equals(e3));
        
        System.out.println("✅ RÉUSSI : equals() fonctionne correctement");
    }
    
    @Test
    public void testHashCode() {
        System.out.println("\n>>> TEST 14 : Test de la méthode hashCode()");
        
        // ACT
        Entreprise e1 = new Entreprise("Airbus");
        Entreprise e2 = new Entreprise("Airbus");
        
        // ASSERT
        assertEquals("Deux objets égaux doivent avoir le même hashCode", e1.hashCode(), e2.hashCode());
        
        System.out.println("✅ RÉUSSI : hashCode() cohérent avec equals()");
    }
    
    @Test
    public void testCompareTo() {
        System.out.println("\n>>> TEST 15 : Test de la méthode compareTo()");
        
        // ACT
        Entreprise e1 = new Entreprise("Airbus");
        e1.setAdresse("Adresse A");
        
        Entreprise e2 = new Entreprise("Boeing");
        e2.setAdresse("Adresse B");
        
        Entreprise e3 = new Entreprise("Airbus");
        e3.setAdresse("Adresse C");
        
        // ASSERT
        assertTrue("Airbus devrait venir avant Boeing", e1.compareTo(e2) < 0);
        assertTrue("Boeing devrait venir après Airbus", e2.compareTo(e1) > 0);
        assertTrue("Même nom : comparaison par adresse", e1.compareTo(e3) < 0);
        
        System.out.println("✅ RÉUSSI : compareTo() fonctionne (ordre alphabétique)");
    }
    
    @Test
    public void testToString() {
        System.out.println("\n>>> TEST 16 : Test de la méthode toString()");
        
        // ACT
        Entreprise e = new Entreprise("Airbus");
        String result = e.toString();
        
        // ASSERT
        assertNotNull("toString() ne devrait pas retourner null", result);
        assertTrue("toString() devrait contenir le nom", result.contains("Airbus"));
        
        System.out.println("✅ RÉUSSI : toString() retourne : " + result);
    }
    
    @Test
    public void testGetComparator() {
        System.out.println("\n>>> TEST 17 : Test du Comparator");
        
        // ACT
        Entreprise e1 = new Entreprise("Airbus");
        Entreprise e2 = new Entreprise("Boeing");
        
        int result = Entreprise.getComparator().compare(e1, e2);
        
        // ASSERT
        assertTrue("Le comparator devrait donner Airbus < Boeing", result < 0);
        
        System.out.println("✅ RÉUSSI : getComparator() fonctionne");
    }
}