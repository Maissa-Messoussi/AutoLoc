package tn.esprit.tpautoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.tpautoloc.domain.Contrat;
import tn.esprit.tpautoloc.web.dto.ChiffreAffaireAgenceDTO;

import java.time.LocalDate;
import java.util.List;

public interface IContratRepository extends JpaRepository<Contrat, Long> {

    // ===== Exercice 9.1 : chiffre d'affaires par agence =====
    // Règle : somme des montantTotal des contrats valides, groupée par agence du véhicule réservé
    @Query("""
            SELECT new tn.esprit.tpautoloc.web.dto.ChiffreAffaireAgenceDTO(a.idAgence, a.nom, SUM(c.montantTotal))
            FROM Contrat c JOIN c.reservation r JOIN r.vehicule v JOIN v.agence a
            WHERE c.valide = true
            GROUP BY a.idAgence, a.nom
            """)
    List<ChiffreAffaireAgenceDTO> chiffreAffaireParAgence();

    // ===== Exercice 9.2 : contrats arrivant à échéance =====
    // Échéance = date de fin de la réservation liée (Contrat n'a pas de dateEcheance)
    @Query("""
            SELECT c FROM Contrat c
            WHERE c.reservation.dateFin BETWEEN :aujourdhui AND :dateLimite
            """)
    List<Contrat> trouverContratsAEcheanceProche(
            @Param("aujourdhui") LocalDate aujourdhui,
            @Param("dateLimite") LocalDate dateLimite);
}