package tn.esprit.tpautoloc;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.service.IAgenceService;
import tn.esprit.tpautoloc.web.dto.AgenceDTO;

@SpringBootTest
class AgenceServiceTest {

    @Autowired
    private IAgenceService agenceService;

    @Test
    void testAgenceService() {
        AgenceDTO enregistree = agenceService.creer(AgenceDTO.builder()
                .nom("AgenceTest").ville("Tunis")
                .adresse("Centre-ville").telephone("71222333").build());
        assertNotNull(enregistree.getId());
        Long id = enregistree.getId();

        agenceService.findAll().forEach(a -> System.out.println(a.getNom()));

        AgenceDTO trouvee = agenceService.findById(id);
        assertEquals("AgenceTest", trouvee.getNom());

        trouvee.setVille("Sfax");
        assertEquals("Sfax", agenceService.update(id, trouvee).getVille());

        System.out.println("Nombre d'agences : " + agenceService.findAll().size());

        agenceService.deleteById(id);
        assertThrows(RuntimeException.class, () -> agenceService.findById(id));
    }
}
