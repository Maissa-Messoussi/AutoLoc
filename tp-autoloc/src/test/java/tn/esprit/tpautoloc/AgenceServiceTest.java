
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.service.IAgenceService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AgenceServiceTest {

    @Autowired
    private IAgenceService agenceService;

    @Test
    void testAgenceService() {

        // 1. Créer une agence
        Agence agence = new Agence();
        agence.setNom("AgenceTest");
        agence.setVille("Tunis");
        agence.setAdresse("Centre-ville");
        agence.setTelephone("71222333");

        // 2. Enregistrer l'agence
        Agence agenceEnregistree = agenceService.create(agence);

        assertNotNull(agenceEnregistree);
        assertNotNull(agenceEnregistree.getIdAgence());

        System.out.println("Agence créée : " + agenceEnregistree);

        Long id = agenceEnregistree.getIdAgence();

        // 3. Afficher toutes les agences
        System.out.println("Liste des agences :");
        agenceService.findAll().forEach(System.out::println);

        // 4. Rechercher une agence par son ID
        Agence agenceTrouvee = agenceService.findById(id);

        assertNotNull(agenceTrouvee);
        System.out.println("Agence recherchée : " + agenceTrouvee);

        // 5. Afficher le nombre d'agences
        System.out.println("Nombre d'agences : " + agenceService.count());

        // 6. Supprimer l'agence
        agenceService.deleteById(id);

        // 7. Vérifier que l'agence n'existe plus
        assertThrows(
                RuntimeException.class,
                () -> agenceService.findById(id)
        );

        System.out.println("Agence supprimée avec succès !");
    }
}
