package tn.esprit.tpautoloc;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.service.IClientService;
import tn.esprit.tpautoloc.web.dto.ClientDTO;

@SpringBootTest
class ClientServiceTest {

    @Autowired
    private IClientService clientService;

    private ClientDTO nouveauClient(String nom) {
        String suffixe = UUID.randomUUID().toString().substring(0, 8);
        return ClientDTO.builder()
                .nom(nom).prenom("Test")
                .email(nom.toLowerCase() + "." + suffixe + "@example.com")
                .telephone("20111222").numPermis("PERMIS-" + suffixe)
                .dateInscription(LocalDate.now()).build();
    }

    @Test
    void testClientService() {
        ClientDTO saved1 = clientService.creer(nouveauClient("BenAli"));
        ClientDTO saved2 = clientService.creer(nouveauClient("Trabelsi"));
        assertNotNull(saved1.getId());
        assertNotNull(saved2.getId());

        clientService.findAll().forEach(c -> System.out.println(c.getNom()));

        Long id = saved1.getId();
        assertEquals("BenAli", clientService.findById(id).getNom());

        saved1.setTelephone("99999999");
        assertEquals("99999999", clientService.update(id, saved1).getTelephone());

        System.out.println("Nombre de clients : " + clientService.findAll().size());

        clientService.deleteById(id);
        assertThrows(RuntimeException.class, () -> clientService.findById(id));
        clientService.deleteById(saved2.getId());
    }
}
