
package tn.esprit.tpautoloc.service.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IAgenceRepository;
import tn.esprit.tpautoloc.service.IAgenceService;

@Service
@RequiredArgsConstructor
public class AgenceServiceImpl implements IAgenceService {

    private final IAgenceRepository agenceRepository;

    private void valider(Agence agence) {
        if (agence.getNom() == null || agence.getNom().isBlank()) {
            throw new IllegalArgumentException(
                    "Le nom de l'agence est obligatoire"
            );
        }
    }

    // 1. Créer une agence
    @Override
    public Agence create(Agence agence) {
        if (agence.getIdAgence() != null) {
            throw new IllegalArgumentException(
                    "Une nouvelle agence ne doit pas avoir d'identifiant"
            );
        }

        valider(agence);
        return agenceRepository.save(agence);
    }

    // 2. Rechercher une agence par ID
    @Override
    public Agence findById(Long id) {
        return agenceRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Agence", id)
                );
    }

    // 3. Afficher toutes les agences
    @Override
    public List<Agence> findAll() {
        return agenceRepository.findAll();
    }

    // 4. Modifier une agence
    @Override
    public Agence update(Long id, Agence agence) {
        Agence existante = findById(id);
        valider(agence);

        existante.setNom(agence.getNom());
        existante.setVille(agence.getVille());
        existante.setAdresse(agence.getAdresse());
        existante.setTelephone(agence.getTelephone());

        return agenceRepository.save(existante);
    }

    // 5. Supprimer une agence par ID
    @Override
    public void deleteById(Long id) {
        agenceRepository.delete(findById(id));
    }

    // 6. Compter le nombre d'agences
    @Override
    public long count() {
        return agenceRepository.count();
    }
}
