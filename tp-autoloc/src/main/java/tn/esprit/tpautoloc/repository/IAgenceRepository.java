package tn.esprit.tpautoloc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.tpautoloc.domain.Agence;

import java.util.Optional;

public interface IAgenceRepository extends JpaRepository<Agence, Long> {

    Optional<Agence> findByVille(String ville);

    Optional<Agence> findByNomContaining(String nom);
}