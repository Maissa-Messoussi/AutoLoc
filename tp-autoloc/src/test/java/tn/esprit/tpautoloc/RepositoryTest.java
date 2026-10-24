
package tn.esprit.tpautoloc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.repository.IAgenceRepository;

import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.repository.IClientRepository;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;
import tn.esprit.tpautoloc.domain.enums.CategorieVehicule;
import tn.esprit.tpautoloc.domain.enums.StatutVehicule;
import tn.esprit.tpautoloc.domain.Equipement;
import tn.esprit.tpautoloc.repository.IEquipementRepository;
import java.math.BigDecimal;
import tn.esprit.tpautoloc.domain.Employe;
import tn.esprit.tpautoloc.repository.IEmployeRepository;
import tn.esprit.tpautoloc.domain.enums.RoleEmploye;
import tn.esprit.tpautoloc.domain.Maintenance;
import tn.esprit.tpautoloc.repository.IMaintenanceRepository;


@SpringBootTest
class RepositoryTest {

    @Autowired
    private IAgenceRepository agenceRepository;
    @Autowired
    private IClientRepository clientRepository;
    @Autowired
    private IVehiculeRepository vehiculeRepository;
    @Autowired
    private IEquipementRepository equipementRepository;
    @Autowired
    private IEmployeRepository employeRepository;
    @Autowired
    private IMaintenanceRepository maintenanceRepository;

    @Test
    void testAgenceRepository() {

        // 1. Créer une agence
        Agence agence = new Agence();
        agence.setNom("Agence Test");
        agence.setVille("Tunis");
        agence.setAdresse("Centre-ville");
        agence.setTelephone("70123456");

        // 2. Enregistrer l'agence
        Agence agenceEnregistree = agenceRepository.save(agence);
        System.out.println("Agence enregistrée : " + agenceEnregistree);

        assertNotNull(agenceEnregistree.getIdAgence());

        Long id = agenceEnregistree.getIdAgence();

        // 3. Afficher toutes les agences
        System.out.println("Toutes les agences : "
                + agenceRepository.findAll());

        // 4. Rechercher par identifiant
        var resultat = agenceRepository.findById(id);
        System.out.println("Agence recherchée : " + resultat);

        assertTrue(resultat.isPresent());

        // 5. Vérifier l'existence
        assertTrue(agenceRepository.existsById(id));

        // 6. Compter les agences
        System.out.println("Nombre d'agences : "
                + agenceRepository.count());

        // 7. Supprimer l'agence
        agenceRepository.deleteById(id);

        // 8. Vérifier la suppression
        assertFalse(agenceRepository.existsById(id));

        System.out.println("Agence supprimée avec succès !");
    }


    @Test
    void testClientRepository() {

        // 1. Créer deux clients
        Client client1 = new Client();
        client1.setNom("Ben Ali");
        client1.setPrenom("Amine");
        client1.setEmail("amine.test@example.com");
        client1.setTelephone("20111222");
        client1.setNumPermis("PERMIS-TEST-001");
        client1.setDateInscription(LocalDate.now());

        Client client2 = new Client();
        client2.setNom("Trabelsi");
        client2.setPrenom("Sarra");
        client2.setEmail("sarra.test@example.com");
        client2.setTelephone("22111222");
        client2.setNumPermis("PERMIS-TEST-002");
        client2.setDateInscription(LocalDate.now());

        // 2. Enregistrer les clients
        client1 = clientRepository.save(client1);
        client2 = clientRepository.save(client2);

        assertNotNull(client1.getIdClient());
        assertNotNull(client2.getIdClient());

        System.out.println("Client 1 enregistré : " + client1.getIdClient());
        System.out.println("Client 2 enregistré : " + client2.getIdClient());

        // 3. Afficher tous les clients
        System.out.println("Tous les clients : "
                + clientRepository.findAll());

        // 4. Rechercher le premier client
        assertTrue(clientRepository.findById(client1.getIdClient()).isPresent());

        // 5. Tester l'existence
        assertTrue(clientRepository.existsById(client1.getIdClient()));
        assertFalse(clientRepository.existsById(-1L));

        // 6. Compter les clients
        System.out.println("Nombre de clients : "
                + clientRepository.count());
    }


    @Test
    void testVehiculeRepository() {

        // 1. Créer un véhicule
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("TEST-123");
        vehicule.setMarque("Toyota");
        vehicule.setModele("Yaris");
        vehicule.setCategorie(CategorieVehicule.values()[0]);
        vehicule.setTarifJournalier(new BigDecimal("120.00"));
        vehicule.setStatut(StatutVehicule.values()[0]);

        // 2. Enregistrer le véhicule
        vehicule = vehiculeRepository.save(vehicule);

        assertNotNull(vehicule.getIdVehicule());
        Long id = vehicule.getIdVehicule();

        System.out.println("Véhicule enregistré : " + vehicule.getImmatriculation());

        // 3. Afficher tous les véhicules
        System.out.println("Tous les véhicules : " + vehiculeRepository.findAll());

        // 4. Compter les véhicules
        System.out.println("Nombre de véhicules : " + vehiculeRepository.count());

        // 5. Modifier le véhicule
        vehicule.setTarifJournalier(new BigDecimal("150.00"));
        vehiculeRepository.save(vehicule);

        assertEquals(
                new BigDecimal("150.00"),
                vehiculeRepository.findById(id).orElseThrow().getTarifJournalier()
        );

        System.out.println("Tarif modifié avec succès !");

        // 6. Supprimer le véhicule
        vehiculeRepository.deleteById(id);

        assertFalse(vehiculeRepository.existsById(id));

        System.out.println("Véhicule supprimé avec succès !");
    }


    @Test
    void testEquipementRepository() {

        // 1. Créer un équipement
        Equipement equipement = new Equipement();
        equipement.setLibelle("GPS");

        // 2. Enregistrer l'équipement
        equipement = equipementRepository.save(equipement);
        Long id = equipement.getIdEquipement();

        assertNotNull(id);
        System.out.println("Équipement enregistré : " + equipement);

        // 3. Lire les équipements
        System.out.println("Liste des équipements : "
                + equipementRepository.findAll());

        // 4. Vérifier l'existence
        assertTrue(equipementRepository.existsById(id));

        // 5. Supprimer l'équipement
        equipementRepository.deleteById(id);

        // 6. Vérifier la suppression
        assertFalse(equipementRepository.existsById(id));

        System.out.println("Équipement supprimé avec succès !");
    }

    @Test
    void testEmployeRepository() {

        // 1. Créer une agence pour l'employé
        Agence agence = new Agence();
        agence.setNom("Agence Employe Test");
        agence.setAdresse("Tunis");
        agence = agenceRepository.save(agence);

        // 2. Créer l'employé
        Employe employe = new Employe();
        employe.setNom("Ben Ali");
        employe.setPrenom("Amine");
        employe.setRole(RoleEmploye.values()[0]);
        employe.setAgence(agence);

        // 3. Enregistrer l'employé
        employe = employeRepository.save(employe);
        Long id = employe.getIdEmploye();

        assertNotNull(id);
        System.out.println("Employé enregistré : " + employe.getNom());

        // 4. Lire les employés
        System.out.println("Liste des employés : "
                + employeRepository.findAll());

        // 5. Vérifier l'existence
        assertTrue(employeRepository.existsById(id));

        // 6. Supprimer l'employé
        employeRepository.deleteById(id);
        assertFalse(employeRepository.existsById(id));

        // 7. Supprimer l'agence de test
        agenceRepository.deleteById(agence.getIdAgence());

        System.out.println("Employé supprimé avec succès !");
    }


    @Test
    void testMaintenanceRepository() {

        // 1. Créer un véhicule
        Vehicule vehicule = new Vehicule();
        vehicule.setImmatriculation("TEST-MAINT-001");
        vehicule.setMarque("Toyota");
        vehicule.setModele("Yaris");
        vehicule.setCategorie(CategorieVehicule.values()[0]);
        vehicule.setTarifJournalier(new BigDecimal("120.00"));
        vehicule.setStatut(StatutVehicule.values()[0]);

        vehicule = vehiculeRepository.save(vehicule);

        // 2. Créer la maintenance
        Maintenance maintenance = new Maintenance();
        maintenance.setDateDebut(LocalDate.now());
        maintenance.setDateFin(LocalDate.now().plusDays(2));
        maintenance.setDescription("Maintenance de test");
        maintenance.setVehicule(vehicule);

        // 3. Enregistrer la maintenance
        maintenance = maintenanceRepository.save(maintenance);
        Long id = maintenance.getIdMaintenance();

        assertNotNull(id);
        System.out.println("Maintenance enregistrée : "
                + maintenance.getDescription());

        // 4. Lire les maintenances
        System.out.println("Liste des maintenances : "
                + maintenanceRepository.findAll());

        // 5. Vérifier l'existence
        assertTrue(maintenanceRepository.existsById(id));

        // 6. Supprimer la maintenance
        maintenanceRepository.deleteById(id);
        assertFalse(maintenanceRepository.existsById(id));

        // 7. Supprimer le véhicule de test
        vehiculeRepository.deleteById(vehicule.getIdVehicule());

        System.out.println("Maintenance supprimée avec succès !");
    }





}
