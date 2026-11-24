package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.service.IClientService;
import tn.esprit.tpautoloc.web.dto.ClientDTO;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    private IClientService clientService;

    @Test
    void testClientService() {
        // 1. Créer deux clients
        ClientDTO client1 = ClientDTO.builder()
                .nom("Ben Ali").prenom("Ahmed")
                .email("ahmed.test@example.com")
                .telephone("20111222").numPermis("PERMIS001")
                .dateInscription(LocalDate.now())
                .build();

        ClientDTO client2 = ClientDTO.builder()
                .nom("Trabelsi").prenom("Amira")
                .email("amira.test@example.com")
                .telephone("22123456").numPermis("PERMIS002")
                .dateInscription(LocalDate.now())
                .build();

        // 2. Enregistrer
        ClientDTO saved1 = clientService.creer(client1);
        ClientDTO saved2 = clientService.creer(client2);

        assertNotNull(saved1.getId());
        assertNotNull(saved2.getId());

        // 3. Afficher tous les clients
        clientService.findAll().forEach(c -> System.out.println(c.getNom()));

        // 4. Rechercher par ID
        Long id = saved1.getId();
        ClientDTO trouve = clientService.findById(id);
        assertNotNull(trouve);
        assertEquals("Ben Ali", trouve.getNom());

        // 5. Nombre de clients
        System.out.println("Nombre de clients : " + clientService.findAll().size());

        // 6. Supprimer et vérifier
        clientService.deleteById(id);
        assertThrows(RuntimeException.class, () -> clientService.findById(id));

        // 7. Nettoyage du second client
        clientService.deleteById(saved2.getId());
    }
}