package tn.esprit.tpautoloc;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.domain.enums.CategorieVehicule;
import tn.esprit.tpautoloc.domain.enums.RoleEmploye;
import tn.esprit.tpautoloc.domain.enums.StatutVehicule;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.service.IAgenceService;
import tn.esprit.tpautoloc.service.IEquipementService;
import tn.esprit.tpautoloc.service.IMaintenanceService;
import tn.esprit.tpautoloc.service.IEmployeService;
import tn.esprit.tpautoloc.service.IVehiculeService;
import tn.esprit.tpautoloc.web.dto.AgenceDTO;
import tn.esprit.tpautoloc.web.dto.EmployeDTO;
import tn.esprit.tpautoloc.web.dto.EquipementDTO;
import tn.esprit.tpautoloc.web.dto.MaintenanceDTO;
import tn.esprit.tpautoloc.web.dto.VehiculeDTO;

@SpringBootTest
class AffectationServiceTest {

    private static final long ID_INEXISTANT = 999_999L;

    @Autowired
    private IAgenceService agenceService;
    @Autowired
    private IVehiculeService vehiculeService;
    @Autowired
    private IEmployeService employeService;
    @Autowired
    private IEquipementService equipementService;
    @Autowired
    private IMaintenanceService maintenanceService;

    private final List<Long> maintenanceIds = new ArrayList<>();
    private final List<Long> equipementIds = new ArrayList<>();
    private final List<Long> vehiculeIds = new ArrayList<>();
    private final List<Long> employeIds = new ArrayList<>();
    private final List<Long> agenceIds = new ArrayList<>();

    @AfterEach
    void nettoyer() {
        // Ordre important : on supprime d'abord les enfants (clés étrangères)
        maintenanceIds.forEach(maintenanceService::deleteById);
        vehiculeIds.forEach(vehiculeService::deleteById);
        equipementIds.forEach(equipementService::deleteById);
        employeIds.forEach(employeService::deleteById);
        agenceIds.forEach(agenceService::deleteById);
    }

    private Long creerAgence(String nom) {
        AgenceDTO agence = agenceService.creer(AgenceDTO.builder()
                .nom(nom).ville("Tunis").adresse("Centre").telephone("71000000").build());
        agenceIds.add(agence.getId());
        return agence.getId();
    }

    private Long creerVehicule() {
        VehiculeDTO vehicule = vehiculeService.creer(VehiculeDTO.builder()
                .immatriculation("AFF-" + UUID.randomUUID().toString().substring(0, 8))
                .marque("Renault").modele("Clio")
                .categorie(CategorieVehicule.CITADINE)
                .tarifJournalier(new BigDecimal("80.00"))
                .statut(StatutVehicule.DISPONIBLE)
                .build());
        vehiculeIds.add(vehicule.getId());
        return vehicule.getId();
    }

    private Long creerEmploye() {
        Long id = employeService.creer(EmployeDTO.builder()
                .nom("Ben Ali").prenom("Amine").role(RoleEmploye.AGENT).build()).getId();
        employeIds.add(id);
        return id;
    }

    // ===== Véhicule =====

    @Test
    void affecterVehiculeAAgenceExistante() {
        Long agenceId = creerAgence("Agence A");
        Long vehiculeId = creerVehicule();

        vehiculeService.affecterVehiculeAAgence(vehiculeId, agenceId);

        assertEquals(agenceId, vehiculeService.findById(vehiculeId).getAgenceId());
    }

    @Test
    void affecterVehiculeInexistantLeveUneErreur() {
        Long agenceId = creerAgence("Agence A");
        assertThrows(ResourceNotFoundException.class,
                () -> vehiculeService.affecterVehiculeAAgence(ID_INEXISTANT, agenceId));
    }

    @Test
    void affecterVehiculeAAgenceInexistanteLaisseLaBaseInchangee() {
        Long agenceId = creerAgence("Agence A");
        Long vehiculeId = creerVehicule();
        vehiculeService.affecterVehiculeAAgence(vehiculeId, agenceId);

        assertThrows(ResourceNotFoundException.class,
                () -> vehiculeService.affecterVehiculeAAgence(vehiculeId, ID_INEXISTANT));

        // Pas d'état incohérent : le véhicule reste dans son agence d'origine
        assertEquals(agenceId, vehiculeService.findById(vehiculeId).getAgenceId());
    }

    @Test
    void transfererVehiculeVersUneAutreAgence() {
        Long agenceA = creerAgence("Agence A");
        Long agenceB = creerAgence("Agence B");
        Long vehiculeId = creerVehicule();

        vehiculeService.affecterVehiculeAAgence(vehiculeId, agenceA);
        vehiculeService.affecterVehiculeAAgence(vehiculeId, agenceB);

        assertEquals(agenceB, vehiculeService.findById(vehiculeId).getAgenceId());
    }

    @Test
    void desaffecterVehiculeMetLaCleEtrangereANull() {
        Long agenceId = creerAgence("Agence A");
        Long vehiculeId = creerVehicule();
        vehiculeService.affecterVehiculeAAgence(vehiculeId, agenceId);

        vehiculeService.desaffecterVehiculeDeAgence(vehiculeId);

        assertNull(vehiculeService.findById(vehiculeId).getAgenceId());
        // Comportement défini : désaffecter un véhicule sans agence est refusé
        assertThrows(IllegalArgumentException.class,
                () -> vehiculeService.desaffecterVehiculeDeAgence(vehiculeId));
    }

    @Test
    void desaffecterVehiculeInexistantLeveUneErreur() {
        assertThrows(ResourceNotFoundException.class,
                () -> vehiculeService.desaffecterVehiculeDeAgence(ID_INEXISTANT));
    }

    // ===== Employé =====

    @Test
    void affecterEmployeAAgenceExistante() {
        Long agenceId = creerAgence("Agence A");
        Long employeId = creerEmploye();

        employeService.affecterEmployeAAgence(employeId, agenceId);

        assertEquals(agenceId, employeService.findById(employeId).getAgenceId());
    }

    @Test
    void affecterEmployeInexistantOuAgenceInexistante() {
        Long agenceId = creerAgence("Agence A");
        Long employeId = creerEmploye();

        assertThrows(ResourceNotFoundException.class,
                () -> employeService.affecterEmployeAAgence(ID_INEXISTANT, agenceId));
        assertThrows(ResourceNotFoundException.class,
                () -> employeService.affecterEmployeAAgence(employeId, ID_INEXISTANT));
        assertNull(employeService.findById(employeId).getAgenceId());
    }

    @Test
    void transfererEmployeVersUneAutreAgence() {
        Long agenceA = creerAgence("Agence A");
        Long agenceB = creerAgence("Agence B");
        Long employeId = creerEmploye();

        employeService.affecterEmployeAAgence(employeId, agenceA);
        employeService.affecterEmployeAAgence(employeId, agenceB);

        assertEquals(agenceB, employeService.findById(employeId).getAgenceId());
    }

    @Test
    void desaffecterEmploye() {
        Long agenceId = creerAgence("Agence A");
        Long employeId = creerEmploye();
        employeService.affecterEmployeAAgence(employeId, agenceId);

        employeService.desaffecterEmployeDeAgence(employeId);

        assertNull(employeService.findById(employeId).getAgenceId());
        assertThrows(IllegalArgumentException.class,
                () -> employeService.desaffecterEmployeDeAgence(employeId));
    }

    // ===== Équipement <-> Véhicule =====

    @Test
    void affecterEtDesaffecterEquipement() {
        Long vehiculeId = creerVehicule();
        Long equipementId = equipementService.creer(
                EquipementDTO.builder().libelle("GPS").build()).getId();
        equipementIds.add(equipementId);

        vehiculeService.affecterEquipementAVehicule(vehiculeId, equipementId);
        vehiculeService.affecterEquipementAVehicule(vehiculeId, equipementId); // idempotent
        vehiculeService.desaffecterEquipementDeVehicule(vehiculeId, equipementId);

        assertThrows(IllegalArgumentException.class,
                () -> vehiculeService.desaffecterEquipementDeVehicule(vehiculeId, equipementId));
        assertThrows(ResourceNotFoundException.class,
                () -> vehiculeService.affecterEquipementAVehicule(vehiculeId, ID_INEXISTANT));
    }

    // ===== Maintenance <-> Véhicule =====

    @Test
    void affecterEtDesaffecterMaintenance() {
        Long vehiculeA = creerVehicule();
        Long vehiculeB = creerVehicule();
        Long maintenanceId = maintenanceService.creer(MaintenanceDTO.builder()
                .dateDebut(java.time.LocalDate.now()).dateFin(java.time.LocalDate.now().plusDays(1))
                .description("Vidange").vehiculeId(vehiculeA).build()).getId();
        maintenanceIds.add(maintenanceId);

        maintenanceService.affecterMaintenanceAVehicule(maintenanceId, vehiculeB);
        assertEquals(vehiculeB, maintenanceService.findById(maintenanceId).getVehiculeId());

        assertThrows(ResourceNotFoundException.class,
                () -> maintenanceService.affecterMaintenanceAVehicule(maintenanceId, ID_INEXISTANT));

        maintenanceService.desaffecterMaintenanceDeVehicule(maintenanceId);
        assertNull(maintenanceService.findById(maintenanceId).getVehiculeId());
        assertThrows(IllegalArgumentException.class,
                () -> maintenanceService.desaffecterMaintenanceDeVehicule(maintenanceId));
    }
}
