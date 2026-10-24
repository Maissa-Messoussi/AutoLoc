
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.service.IAgenceService;
import tn.esprit.tpautoloc.service.IClientService;

import static org.junit.jupiter.api.Assertions.*;

import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.domain.Equipement;
import tn.esprit.tpautoloc.domain.Maintenance;
import tn.esprit.tpautoloc.domain.enums.CategorieVehicule;
import tn.esprit.tpautoloc.domain.enums.StatutVehicule;
import tn.esprit.tpautoloc.service.IVehiculeService;
import tn.esprit.tpautoloc.service.IEquipementService;
import tn.esprit.tpautoloc.service.IMaintenanceService;

import java.math.BigDecimal;
import java.time.LocalDate;

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
        // 1. Créer une agence
        Agence agence = new Agence();
        agence.setNom("AgenceTest");
        agence.setVille("Tunis");
        agence.setAdresse("Centre-ville");
        agence.setTelephone("71222333");

        // 2. Enregistrer l'agence
        Agence saved = agenceService.create(agence);
        assertNotNull(saved);
        assertNotNull(saved.getIdAgence());

        System.out.println("Agence créée : " + saved);

        // 3. Afficher toutes les agences
        System.out.println("Toutes les agences : "
                + agenceService.findAll());

        // 4. Rechercher l'agence
        Agence found = agenceService.findById(saved.getIdAgence());
        assertNotNull(found);
        assertEquals("AgenceTest", found.getNom());

        System.out.println("Agence recherchée : " + found);

        // 5. Afficher le nombre d'agences
        System.out.println("Nombre d'agences : "
                + agenceService.findAll().size());

        // 6. Supprimer et vérifier
        Long id = saved.getIdAgence();
        agenceService.deleteById(id);

        assertThrows(RuntimeException.class,
                () -> agenceService.findById(id));

        System.out.println("Agence supprimée avec succès !");
    }

    @Test
    void testClientService() {
        // 1. Créer deux clients
        Client client1 = new Client();
        client1.setNom("Ben Ali");
        client1.setPrenom("Amine");
        client1.setEmail("amine.test@example.com");
        client1.setTelephone("20111222");
        client1.setNumPermis("PERMIS-TEST-001");

        Client client2 = new Client();
        client2.setNom("Trabelsi");
        client2.setPrenom("Sarra");
        client2.setEmail("sarra.test@example.com");
        client2.setTelephone("22111222");
        client2.setNumPermis("PERMIS-TEST-002");

        // 2. Enregistrer les clients
        Client saved1 = clientService.create(client1);
        Client saved2 = clientService.create(client2);

        assertNotNull(saved1.getIdClient());
        assertNotNull(saved2.getIdClient());

        // 3. Afficher tous les clients
        System.out.println("Tous les clients : "
                + clientService.findAll());

        // 4. Rechercher un client
        Client found = clientService.findById(saved1.getIdClient());
        assertNotNull(found);

        System.out.println("Client recherché : " + found);

        // 5. Afficher le nombre de clients
        System.out.println("Nombre de clients : "
                + clientService.findAll().size());

        // 6. Supprimer un client et vérifier
        Long id = saved1.getIdClient();
        clientService.deleteById(id);

        assertThrows(RuntimeException.class,
                () -> clientService.findById(id));

        System.out.println("Client supprimé avec succès !");
    }


    @Test
    void testVehiculeService() {
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("TEST-123");
        vehicule.setMarque("Renault");
        vehicule.setModele("Clio");
        vehicule.setTarifJournalier(new BigDecimal("80.00"));

        // Vérifie les noms des constantes dans tes deux enums
        vehicule.setCategorie(CategorieVehicule.values()[0]);
        vehicule.setStatut(StatutVehicule.values()[0]);

        Vehicule saved = vehiculeService.create(vehicule);
        assertNotNull(saved);
        assertNotNull(saved.getIdVehicule());

        System.out.println("Véhicule créé : " + saved);
        System.out.println("Tous les véhicules : "
                + vehiculeService.findAll());

        Vehicule found = vehiculeService.findById(saved.getIdVehicule());
        assertNotNull(found);
        System.out.println("Véhicule recherché : " + found);

        System.out.println("Nombre de véhicules : "
                + vehiculeService.findAll().size());

        Long id = saved.getIdVehicule();
        vehiculeService.deleteById(id);

        assertThrows(RuntimeException.class,
                () -> vehiculeService.findById(id));

        System.out.println("Véhicule supprimé !");
    }


    @Test
    void testEquipementService() {
        Equipement equipement = new Equipement();
        equipement.setLibelle("GPS de test");

        Equipement saved = equipementService.create(equipement);
        assertNotNull(saved);
        assertNotNull(saved.getIdEquipement());

        System.out.println("Équipement créé : " + saved);
        System.out.println("Tous les équipements : "
                + equipementService.findAll());

        Equipement found = equipementService.findById(
                saved.getIdEquipement());
        assertNotNull(found);
        assertEquals("GPS de test", found.getLibelle());

        System.out.println("Équipement recherché : " + found);
        System.out.println("Nombre d'équipements : "
                + equipementService.findAll().size());

        Long id = saved.getIdEquipement();
        equipementService.deleteById(id);

        assertThrows(RuntimeException.class,
                () -> equipementService.findById(id));

        System.out.println("Équipement supprimé !");
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

        System.out.println("Maintenance créée : " + saved);
        System.out.println("Toutes les maintenances : "
                + maintenanceService.findAll());

        Maintenance found = maintenanceService.findById(
                saved.getIdMaintenance());
        assertNotNull(found);
        System.out.println("Maintenance recherchée : " + found);

        System.out.println("Nombre de maintenances : "
                + maintenanceService.findAll().size());

        Long id = saved.getIdMaintenance();
        maintenanceService.deleteById(id);

        assertThrows(RuntimeException.class,
                () -> maintenanceService.findById(id));

        System.out.println("Maintenance supprimée !");
    }



}
