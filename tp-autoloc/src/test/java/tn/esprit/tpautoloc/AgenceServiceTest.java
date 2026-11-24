package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.service.IAgenceService;
import tn.esprit.tpautoloc.web.dto.AgenceDTO;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AgenceServiceTest {

    @Autowired
    private IAgenceService agenceService;

    @Test
    void testAgenceService() {
        // 1. Créer une agence
        AgenceDTO dto = AgenceDTO.builder()
                .nom("AgenceTest")
                .ville("Tunis")
                .adresse("Centre-ville")
                .telephone("71222333")
                .build();

        // 2. Enregistrer l'agence
        AgenceDTO enregistree = agenceService.creer(dto);
        assertNotNull(enregistree);
        assertNotNull(enregistree.getId());
        System.out.println("Agence créée : " + enregistree.getNom());

        Long id = enregistree.getId();

        // 3. Afficher toutes les agences
        agenceService.findAll().forEach(a -> System.out.println(a.getNom()));

        // 4. Rechercher par ID
        AgenceDTO trouvee = agenceService.findById(id);
        assertNotNull(trouvee);
        assertEquals("AgenceTest", trouvee.getNom());

        // 5. Nombre d'agences
        System.out.println("Nombre d'agences : " + agenceService.findAll().size());

        // 6. Supprimer
        agenceService.deleteById(id);

        // 7. Vérifier que l'agence n'existe plus
        assertThrows(RuntimeException.class, () -> agenceService.findById(id));
    }
}