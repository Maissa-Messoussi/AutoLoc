package tn.esprit.tpautoloc;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.tpautoloc.exception.GlobalExceptionHandler;
import tn.esprit.tpautoloc.service.IAgenceService;
import tn.esprit.tpautoloc.service.IClientService;
import tn.esprit.tpautoloc.service.IContratService;
import tn.esprit.tpautoloc.service.IEmployeService;
import tn.esprit.tpautoloc.service.IEquipementService;
import tn.esprit.tpautoloc.service.IMaintenanceService;
import tn.esprit.tpautoloc.service.IPaiementService;
import tn.esprit.tpautoloc.service.IReservationService;
import tn.esprit.tpautoloc.service.IVehiculeService;
import tn.esprit.tpautoloc.web.controller.AgenceController;
import tn.esprit.tpautoloc.web.controller.ClientController;
import tn.esprit.tpautoloc.web.controller.ContratController;
import tn.esprit.tpautoloc.web.controller.EmployeController;
import tn.esprit.tpautoloc.web.controller.EquipementController;
import tn.esprit.tpautoloc.web.controller.MaintenanceController;
import tn.esprit.tpautoloc.web.controller.PaiementController;
import tn.esprit.tpautoloc.web.controller.ReservationController;
import tn.esprit.tpautoloc.web.controller.VehiculeController;
import tn.esprit.tpautoloc.web.validator.ReservationRequestValidator;

/**
 * Tests HTTP de validation (réponse 400) sans démarrer Spring ni la base de données :
 * les contrôleurs sont montés avec des services simulés (Mockito).
 * Chaque requête est rejetée avant d'atteindre le service.
 */
class ValidationControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
                        new AgenceController(mock(IAgenceService.class)),
                        new ClientController(mock(IClientService.class)),
                        new ContratController(mock(IContratService.class)),
                        new EmployeController(mock(IEmployeService.class)),
                        new EquipementController(mock(IEquipementService.class)),
                        new MaintenanceController(mock(IMaintenanceService.class)),
                        new PaiementController(mock(IPaiementService.class)),
                        new VehiculeController(mock(IVehiculeService.class)),
                        new ReservationController(mock(IReservationService.class),
                                new ReservationRequestValidator()))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    private void postEtAttendre400(String url, String json) throws Exception {
        mockMvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    void clientAvecEmailInvalideEstRejete() throws Exception {
        mockMvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nom\":\"Ben Ali\",\"prenom\":\"Amine\",\"email\":\"pas-un-email\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email").value("L'adresse email est invalide"));
    }

    @Test
    void vehiculeSansImmatriculationEstRejete() throws Exception {
        postEtAttendre400("/vehicules",
                "{\"marque\":\"Renault\",\"modele\":\"Clio\",\"categorie\":\"CITADINE\","
                        + "\"tarifJournalier\":80,\"agenceId\":1}");
    }

    @Test
    void paiementAvecMontantNulEstRejete() throws Exception {
        postEtAttendre400("/paiements",
                "{\"montant\":0,\"modePaiement\":\"CARTE\",\"contratId\":1}");
    }

    @Test
    void paiementAvecMontantNegatifEstRejete() throws Exception {
        postEtAttendre400("/paiements",
                "{\"montant\":-10,\"modePaiement\":\"CARTE\",\"contratId\":1}");
    }

    @Test
    void reservationSansDatesEstRejetee() throws Exception {
        postEtAttendre400("/reservations", "{\"clientId\":1,\"vehiculeId\":1}");
    }

    @Test
    void reservationDateFinAvantDateDebutEstRejeteeParLeValidateur() throws Exception {
        String debut = LocalDate.now().plusDays(10).toString();
        String fin = LocalDate.now().plusDays(5).toString();
        mockMvc.perform(post("/reservations").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"clientId\":1,\"vehiculeId\":1,\"dateDebut\":\"" + debut
                                + "\",\"dateFin\":\"" + fin + "\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("postérieure")));
    }

    @Test
    void employeSansRoleEstRejete() throws Exception {
        postEtAttendre400("/employes", "{\"nom\":\"Ben Ali\",\"prenom\":\"Amine\"}");
    }

    @Test
    void equipementSansLibelleEstRejete() throws Exception {
        postEtAttendre400("/equipements", "{\"libelle\":\"  \"}");
    }

    @Test
    void maintenanceSansDescriptionEstRejetee() throws Exception {
        postEtAttendre400("/maintenances",
                "{\"dateDebut\":\"2026-12-01\",\"dateFin\":\"2026-12-02\",\"vehiculeId\":1}");
    }

    @Test
    void contratAvecMontantNegatifEstRejete() throws Exception {
        postEtAttendre400("/contrats", "{\"montantTotal\":-5}");
    }

    @Test
    void agenceSansVilleEstRejetee() throws Exception {
        postEtAttendre400("/agences", "{\"nom\":\"Agence\"}");
    }
}