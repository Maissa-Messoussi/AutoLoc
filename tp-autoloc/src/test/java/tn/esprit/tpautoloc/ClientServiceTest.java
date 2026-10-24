
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.service.IClientService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    private IClientService clientService;

    @Test
    void testClientService() {

        // 1. Créer le premier client
        Client client1 = new Client();
        client1.setNom("Ben Ali");
        client1.setPrenom("Ahmed");
        client1.setEmail("ahmed.test@example.com");
        client1.setTelephone("20111222");
        client1.setNumPermis("PERMIS001");
        client1.setDateInscription(LocalDate.now());

        // 2. Créer le deuxième client
        Client client2 = new Client();
        client2.setNom("Trabelsi");
        client2.setPrenom("Amira");
        client2.setEmail("amira.test@example.com");
        client2.setTelephone("22123456");
        client2.setNumPermis("PERMIS002");
        client2.setDateInscription(LocalDate.now());

        // 3. Enregistrer les deux clients
        Client clientEnregistre1 = clientService.create(client1);
        Client clientEnregistre2 = clientService.create(client2);

        assertNotNull(clientEnregistre1.getIdClient());
        assertNotNull(clientEnregistre2.getIdClient());

        System.out.println("Premier client créé : " + clientEnregistre1);
        System.out.println("Deuxième client créé : " + clientEnregistre2);

        // 4. Afficher tous les clients
        System.out.println("Liste des clients :");
        clientService.findAll().forEach(System.out::println);

        // 5. Rechercher un client par ID
        Long id = clientEnregistre1.getIdClient();

        Client clientTrouve = clientService.findById(id);

        assertNotNull(clientTrouve);
        System.out.println("Client recherché : " + clientTrouve);

        // 6. Afficher le nombre de clients
        System.out.println(
                "Nombre de clients : " + clientService.count()
        );

        // 7. Supprimer le premier client
        clientService.deleteById(id);

        // 8. Vérifier que le client n'existe plus
        assertThrows(
                RuntimeException.class,
                () -> clientService.findById(id)
        );

        System.out.println("Client supprimé avec succès !");
    }
}
