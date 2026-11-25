package tn.esprit.tpautoloc;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.domain.enums.CategorieVehicule;
import tn.esprit.tpautoloc.domain.enums.RoleEmploye;
import tn.esprit.tpautoloc.domain.enums.StatutVehicule;
import tn.esprit.tpautoloc.service.IAgenceService;
import tn.esprit.tpautoloc.service.IClientService;
import tn.esprit.tpautoloc.service.IEmployeService;
import tn.esprit.tpautoloc.service.IEquipementService;
import tn.esprit.tpautoloc.service.IMaintenanceService;
import tn.esprit.tpautoloc.service.IVehiculeService;
import tn.esprit.tpautoloc.web.dto.AgenceDTO;
import tn.esprit.tpautoloc.web.dto.ClientDTO;
import tn.esprit.tpautoloc.web.dto.EmployeDTO;
import tn.esprit.tpautoloc.web.dto.EquipementDTO;
import tn.esprit.tpautoloc.web.dto.MaintenanceDTO;
import tn.esprit.tpautoloc.web.dto.VehiculeDTO;

@SpringBootTest
class ServiceTest {

    @Autowired private IAgenceService agenceService;
    @Autowired private IClientService clientService;
    @Autowired private IVehiculeService vehiculeService;
    @Autowired private IEquipementService equipementService;
    @Autowired private IMaintenanceService maintenanceService;
    @Autowired private IEmployeService employeService;

    private VehiculeDTO nouveauVehicule() {
        return VehiculeDTO.builder()
                .immatriculation("SRV-" + UUID.randomUUID().toString().substring(0, 8))
                .marque("Renault").modele("Clio")
                .categorie(CategorieVehicule.CITADINE)
                .tarifJournalier(new BigDecimal("80.00"))
                .statut(StatutVehicule.DISPONIBLE).build();
    }

    @Test
    void testAgenceService() {
        AgenceDTO saved = agenceService.creer(AgenceDTO.builder()
                .nom("AgenceTest").ville("Tunis").adresse("Centre-ville").telephone("71222333").build());
        assertNotNull(saved.getId());
        assertEquals("AgenceTest", agenceService.findById(saved.getId()).getNom());
        Long id = saved.getId();
        agenceService.deleteById(id);
        assertThrows(RuntimeException.class, () -> agenceService.findById(id));
    }

    @Test
    void testClientService() {
        String s = UUID.randomUUID().toString().substring(0, 8);
        ClientDTO saved = clientService.creer(ClientDTO.builder()
                .nom("Ben Ali").prenom("Amine").email("amine." + s + "@example.com")
                .telephone("20111222").numPermis("PERMIS-" + s).build());
        assertNotNull(saved.getId());
        Long id = saved.getId();
        assertNotNull(clientService.findById(id));
        clientService.deleteById(id);
        assertThrows(RuntimeException.class, () -> clientService.findById(id));
    }

    @Test
    void testVehiculeService() {
        VehiculeDTO saved = vehiculeService.creer(nouveauVehicule());
        assertNotNull(saved.getId());
        assertNotNull(vehiculeService.findById(saved.getId()));
        Long id = saved.getId();
        vehiculeService.deleteById(id);
        assertThrows(RuntimeException.class, () -> vehiculeService.findById(id));
    }

    @Test
    void testEmployeService() {
        EmployeDTO saved = employeService.creer(EmployeDTO.builder()
                .nom("Ben Ali").prenom("Amine").role(RoleEmploye.AGENT).build());
        assertNotNull(saved.getId());
        assertEquals("Ben Ali", employeService.findById(saved.getId()).getNom());
        Long id = saved.getId();
        employeService.deleteById(id);
        assertThrows(RuntimeException.class, () -> employeService.findById(id));
    }

    @Test
    void testEquipementService() {
        EquipementDTO saved = equipementService.creer(
                EquipementDTO.builder().libelle("GPS de test").build());
        assertNotNull(saved.getId());
        assertEquals("GPS de test", equipementService.findById(saved.getId()).getLibelle());
        Long id = saved.getId();
        equipementService.deleteById(id);
        assertThrows(RuntimeException.class, () -> equipementService.findById(id));
    }

    @Test
    void testMaintenanceService() {
        Long vehiculeId = vehiculeService.creer(nouveauVehicule()).getId();

        MaintenanceDTO saved = maintenanceService.creer(MaintenanceDTO.builder()
                .dateDebut(LocalDate.of(2026, 10, 15)).dateFin(LocalDate.of(2026, 10, 16))
                .description("Maintenance de test").vehiculeId(vehiculeId).build());
        assertNotNull(saved.getId());
        assertEquals(vehiculeId, maintenanceService.findById(saved.getId()).getVehiculeId());

        Long id = saved.getId();
        maintenanceService.deleteById(id);
        assertThrows(RuntimeException.class, () -> maintenanceService.findById(id));
        vehiculeService.deleteById(vehiculeId);
    }
}
