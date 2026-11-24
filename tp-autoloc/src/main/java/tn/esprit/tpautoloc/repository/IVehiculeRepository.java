package tn.esprit.tpautoloc.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.domain.enums.CategorieVehicule;
import tn.esprit.tpautoloc.domain.enums.StatutVehicule;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IVehiculeRepository extends JpaRepository<Vehicule, Long> {

    // ===== Exercice 1 : Query Methods =====
    List<Vehicule> findByAgenceVille(String ville);

    List<Vehicule> findByStatutAndAgence_IdAgence(StatutVehicule statut, Long agenceId);

    Page<Vehicule> findByStatut(StatutVehicule statut, Pageable pageable);

    List<Vehicule> findByAgence_VilleAndCategorie(String ville, CategorieVehicule categorie);

    boolean existsByIdVehiculeAndStatut(Long vehiculeId, StatutVehicule statut);

    long countByStatut(StatutVehicule statut);

    List<Vehicule> findByTarifJournalierLessThan(BigDecimal tarif);

    List<Vehicule> findByTarifJournalierGreaterThan(BigDecimal tarif);

    List<Vehicule> findAllByOrderByTarifJournalierAsc();

    // ===== Exercice 7.1 : véhicules disponibles sur une période =====
    @Query("""
            SELECT v FROM Vehicule v
            WHERE v.agence.ville = :ville
              AND v.categorie = :categorie
              AND v.statut = tn.esprit.tpautoloc.domain.enums.StatutVehicule.DISPONIBLE
              AND NOT EXISTS (
                  SELECT r FROM Reservation r
                  WHERE r.vehicule = v
                    AND r.statut IN (tn.esprit.tpautoloc.domain.enums.StatutReservation.EN_ATTENTE,
                                     tn.esprit.tpautoloc.domain.enums.StatutReservation.CONFIRMEE)
                    AND r.dateDebut <= :dateFin
                    AND r.dateFin >= :dateDebut
              )
            """)
    List<Vehicule> rechercherVehiculesDisponibles(
            @Param("ville") String ville,
            @Param("categorie") CategorieVehicule categorie,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin);

    // ===== Exercice 7.2 : véhicules à libérer =====
    @Query("""
            SELECT DISTINCT v FROM Vehicule v JOIN v.reservations r
            WHERE v.statut = tn.esprit.tpautoloc.domain.enums.StatutVehicule.LOUE
              AND r.statut = tn.esprit.tpautoloc.domain.enums.StatutReservation.CONFIRMEE
              AND r.dateFin <= :aujourdhui
            """)
    List<Vehicule> trouverVehiculesAReleaser(@Param("aujourdhui") LocalDate aujourdhui);
}