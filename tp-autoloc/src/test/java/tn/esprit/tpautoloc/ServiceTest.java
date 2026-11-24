package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Equipement;
import tn.esprit.tpautoloc.domain.Maintenance;
import tn.esprit.tpautoloc.domain.enums.CategorieVehicule;
import tn.esprit.tpautoloc.domain.enums.StatutVehicule;
import tn.esprit.tpautoloc.service.IAgenceService;
import tn.esprit.tpautoloc.service.IClientService;
import tn.esprit.tpautoloc.service.IEquipementService;
import tn.esprit.tpautoloc.service.IMaintenanceService;
import tn.esprit.tpautoloc.service.IVehiculeService;
import tn.esprit.tpautoloc.web.dto.AgenceDTO;
import tn.esprit.tpautoloc.web.dto.ClientDTO;
import tn.esprit.tpautoloc.web.dto.VehiculeDTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ServiceTest {

    @Autowired
    private IAgenceService agenceService;

    @Autowired
    private IClientService clientService;

    @Autowired
    private IVehiculeService vehiculeService;

    @Autowired
    private IEquipementService equipementService;

    @Autowired
    private IMaintenanceService maintenanceService;

    @Test
    void testAgenceService() {
        AgenceDTO dto = AgenceDTO.builder()
                .nom("AgenceTest").ville("Tunis")
                .adresse("Centre-ville").telephone("71222333")
                .build();

        AgenceDTO saved = agenceService.creer(dto);
        assertNotNull(saved);
        assertNotNull(saved.getId());

        System.out.println("Toutes les agences : " + agenceService.findAll().size());

        AgenceDTO found = agenceService.findById(saved.getId());
        assertNotNull(found);
        assertEquals("AgenceTest", found.getNom());

        Long id = saved.getId();
        agenceService.deleteById(id);
        assertThrows(RuntimeException.class, () -> agenceService.findById(id));
    }

    @Test
    void testClientService() {
        ClientDTO client1 = ClientDTO.builder()
                .nom("Ben Ali").prenom("Amine")
                .email("amine.service@example.com")
                .telephone("20111222").numPermis("PERMIS-SRV-001")
                .build();

        ClientDTO client2 = ClientDTO.builder()
                .nom("Trabelsi").prenom("Sarra")
                .email("sarra.service@example.com")
                .telephone("22111222").numPermis("PERMIS-SRV-002")
                .build();

        ClientDTO saved1 = clientService.creer(client1);
        ClientDTO saved2 = clientService.creer(client2);

        assertNotNull(saved1.getId());
        assertNotNull(saved2.getId());

        ClientDTO found = clientService.findById(saved1.getId());
        assertNotNull(found);

        System.out.println("Nombre de clients : " + clientService.findAll().size());

        Long id = saved1.getId();
        clientService.deleteById(id);
        assertThrows(RuntimeException.class, () -> clientService.findById(id));

        clientService.deleteById(saved2.getId());
    }

    @Test
    void testVehiculeService() {
        VehiculeDTO dto = VehiculeDTO.builder()
                .immatriculation("TEST-123")
                .marque("Renault")
                .modele("Clio")
                .tarifJournalier(new BigDecimal("80.00"))
                .categorie(CategorieVehicule.CITADINE)
                .statut(StatutVehicule.DISPONIBLE)
                .build();

        VehiculeDTO saved = vehiculeService.creer(dto);
        assertNotNull(saved);
        assertNotNull(saved.getId());

        VehiculeDTO found = vehiculeService.findById(saved.getId());
        assertNotNull(found);

        System.out.println("Nombre de véhicules : " + vehiculeService.findAll().size());

        Long id = saved.getId();
        vehiculeService.deleteById(id);
        assertThrows(RuntimeException.class, () -> vehiculeService.findById(id));
    }

    @Test
    void testEquipementService() {
        Equipement equipement = new Equipement();
        equipement.setLibelle("GPS de test");

        Equipement saved = equipementService.create(equipement);
        assertNotNull(saved);
        assertNotNull(saved.getIdEquipement());

        Equipement found = equipementService.findById(saved.getIdEquipement());
        assertNotNull(found);
        assertEquals("GPS de test", found.getLibelle());

        System.out.println("Nombre d'équipements : " + equipementService.findAll().size());

        Long id = saved.getIdEquipement();
        equipementService.deleteById(id);
        assertThrows(RuntimeException.class, () -> equipementService.findById(id));
    }

    @Test
    void testMaintenanceService() {
        Maintenance maintenance = new Maintenance();
        maintenance.setDateDebut(LocalDate.of(2026, 10, 15));
        maintenance.setDateFin(LocalDate.of(2026, 10, 16));
        maintenance.setDescription("Maintenance de test");

        Maintenance saved = maintenanceService.create(maintenance);
        assertNotNull(saved);
        assertNotNull(saved.getIdMaintenance());

        Maintenance found = maintenanceService.findById(saved.getIdMaintenance());
        assertNotNull(found);

        System.out.println("Nombre de maintenances : " + maintenanceService.findAll().size());

        Long id = saved.getIdMaintenance();
        maintenanceService.deleteById(id);
        assertThrows(RuntimeException.class, () -> maintenanceService.findById(id));
    }
}