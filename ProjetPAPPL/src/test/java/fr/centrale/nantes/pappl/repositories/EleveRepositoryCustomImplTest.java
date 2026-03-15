package fr.centrale.nantes.pappl.repositories;

import fr.centrale.nantes.pappl.items.Eleve;
import fr.centrale.nantes.pappl.items.Sujet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EleveRepositoryCustomImplTest {

    @Mock
    private EleveRepository eleveRepository;

    @InjectMocks
    private EleveRepositoryCustomImpl eleveRepositoryCustom;

    private Eleve eleve;
    private Sujet sujet;

    @Before
    public void setUp() {
        eleve = new Eleve();
        eleve.setId(1);
        eleve.setNom("Dupont");
        eleve.setPrenom("Jean");
        eleve.setEmail("jean.dupont@ec-nantes.fr");

        sujet = new Sujet();
        sujet.setIntitule("Stage IA");
    }

    // =============================================
    // TESTS CREATE
    // =============================================

    @Test
    public void testCreate_NomValide_RetourneEleve() {
        when(eleveRepository.saveAndFlush(any(Eleve.class))).thenReturn(eleve);

        Eleve result = eleveRepositoryCustom.create(
            "Dupont", "Jean", "Française",
            "jean.dupont@ec-nantes.fr", "TD1", sujet
        );

        assertNotNull("L'élève créé ne doit pas être null", result);
        assertEquals("Dupont", result.getNom());
        verify(eleveRepository, times(1)).saveAndFlush(any(Eleve.class));
    }

    @Test
    public void testCreate_NomNull_RetourneNull() {
        Eleve result = eleveRepositoryCustom.create(
            null, "Jean", "Française",
            "jean@test.fr", "TD1", sujet
        );

        assertNull("Si le nom est null, create doit retourner null", result);
        verify(eleveRepository, never()).saveAndFlush(any());
    }

    @Test
    public void testCreate_NomVide_RetourneNull() {
        Eleve result = eleveRepositoryCustom.create(
            "", "Jean", "Française",
            "jean@test.fr", "TD1", sujet
        );

        assertNull("Si le nom est vide, create doit retourner null", result);
        verify(eleveRepository, never()).saveAndFlush(any());
    }

    @Test
    public void testCreate_SansEmail_RetourneEleve() {
        when(eleveRepository.saveAndFlush(any(Eleve.class))).thenReturn(eleve);

        Eleve result = eleveRepositoryCustom.create(
            "Dupont", "Jean", "Française",
            null, "TD1", null
        );

        assertNotNull("L'élève peut être créé sans email", result);
        verify(eleveRepository, times(1)).saveAndFlush(any(Eleve.class));
    }

    // =============================================
    // TESTS REMOVE
    // =============================================

    @Test
    public void testRemove_EleveValide_SuppressionEffectuee() {
        when(eleveRepository.getReferenceById(1)).thenReturn(eleve);
        doNothing().when(eleveRepository).delete(eleve);

        eleveRepositoryCustom.remove(eleve);

        verify(eleveRepository, times(1)).delete(eleve);
    }

    @Test
    public void testRemove_EleveNull_RienNePasse() {
        eleveRepositoryCustom.remove(null);

        verify(eleveRepository, never()).delete(any());
    }

    // =============================================
    // TESTS UPDATE
    // =============================================

    @Test
    public void testUpdate_EleveValide_MiseAJourEffectuee() {
        when(eleveRepository.getReferenceById(1)).thenReturn(eleve);
        when(eleveRepository.saveAndFlush(any(Eleve.class))).thenReturn(eleve);

        Eleve result = eleveRepositoryCustom.update(
            eleve, "Martin", "Sophie",
            "Française", "sophie@test.fr", "TD2", sujet
        );

        assertNotNull("Le résultat de l'update ne doit pas être null", result);
        verify(eleveRepository, times(1)).saveAndFlush(any(Eleve.class));
    }

    @Test
    public void testUpdate_EleveNull_RetourneNull() {
        Eleve result = eleveRepositoryCustom.update(
            null, "Martin", "Sophie",
            "Française", "sophie@test.fr", "TD2", sujet
        );

        assertNull("Update avec élève null doit retourner null", result);
        verify(eleveRepository, never()).saveAndFlush(any());
    }
}