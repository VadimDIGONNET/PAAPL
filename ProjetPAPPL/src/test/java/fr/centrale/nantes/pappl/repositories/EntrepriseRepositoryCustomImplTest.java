package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.Entreprise;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EntrepriseRepositoryCustomImplTest {

    @Mock
    private EntrepriseRepository entrepriseRepository;

    @InjectMocks
    private EntrepriseRepositoryCustomImpl entrepriseRepositoryCustom;

    private Entreprise entreprise;

    @Before
    public void setUp() {
        entreprise = new Entreprise();
        entreprise.setNom("TechCorp");
        entreprise.setAdresse("1 rue de la Paix");
        entreprise.setCodePostal(44000);
    }

    // =============================================
    // TESTS CREATE
    // =============================================

    @Test
    public void testCreate_NomValide_RetourneEntreprise() {
        when(entrepriseRepository.saveAndFlush(any(Entreprise.class)))
            .thenReturn(entreprise);

        Entreprise result = entrepriseRepositoryCustom.create(
            "TechCorp", "1 rue de la Paix",
            44000, 0240000000, "www.techcorp.fr", "Entreprise tech"
        );

        assertNotNull("L'entreprise créée ne doit pas être null", result);
        assertEquals("TechCorp", result.getNom());
        verify(entrepriseRepository, times(1)).saveAndFlush(any(Entreprise.class));
    }

    @Test
    public void testCreate_NomNull_RetourneNull() {
        Entreprise result = entrepriseRepositoryCustom.create(
            null, "1 rue de la Paix",
            44000, null, null, null
        );

        assertNull("Si le nom est null, create doit retourner null", result);
        verify(entrepriseRepository, never()).saveAndFlush(any());
    }

    @Test
    public void testCreate_NomVide_RetourneNull() {
        Entreprise result = entrepriseRepositoryCustom.create(
            "", "adresse", 44000, null, null, null
        );

        assertNull("Si le nom est vide, create doit retourner null", result);
        verify(entrepriseRepository, never()).saveAndFlush(any());
    }

    @Test
    public void testCreate_SansOptionnels_RetourneEntreprise() {
        when(entrepriseRepository.saveAndFlush(any(Entreprise.class)))
            .thenReturn(entreprise);

        Entreprise result = entrepriseRepositoryCustom.create(
            "TechCorp", null, null, null, null, null
        );

        assertNotNull("L'entreprise peut être créée sans champs optionnels", result);
        verify(entrepriseRepository, times(1)).saveAndFlush(any(Entreprise.class));
    }

    // =============================================
    // TESTS REMOVE
    // =============================================

    @Test
    public void testRemove_EntrepriseValide_SuppressionEffectuee() {
        doNothing().when(entrepriseRepository).delete(entreprise);

        entrepriseRepositoryCustom.remove(entreprise);

        verify(entrepriseRepository, times(1)).delete(entreprise);
    }

    @Test
    public void testRemove_EntrepriseNull_RienNePasse() {
        entrepriseRepositoryCustom.remove(null);

        verify(entrepriseRepository, never()).delete(any());
    }

    // =============================================
    // TESTS UPDATE
    // =============================================

    @Test
    public void testUpdate_EntrepriseValide_MiseAJourEffectuee() {
        when(entrepriseRepository.getReferenceById("TechCorp"))
            .thenReturn(entreprise);
        when(entrepriseRepository.saveAndFlush(any(Entreprise.class)))
            .thenReturn(entreprise);

        Entreprise result = entrepriseRepositoryCustom.update(
            entreprise, "2 rue Nouvelle",
            75000, 123456789, "www.nouveau.fr", "Nouvelle description"
        );

        assertNotNull("Le résultat de l'update ne doit pas être null", result);
        verify(entrepriseRepository, times(1)).saveAndFlush(any(Entreprise.class));
    }

    @Test
    public void testUpdate_EntrepriseNull_RetourneNull() {
        Entreprise result = entrepriseRepositoryCustom.update(
            null, "adresse", 44000, null, null, null
        );

        assertNull("Update avec entreprise null doit retourner null", result);
        verify(entrepriseRepository, never()).saveAndFlush(any());
    }
}