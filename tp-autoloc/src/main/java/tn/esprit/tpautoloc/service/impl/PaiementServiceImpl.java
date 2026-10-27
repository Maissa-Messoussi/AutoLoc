package tn.esprit.tpautoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpautoloc.domain.Paiement;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IPaiementRepository;
import tn.esprit.tpautoloc.service.IPaiementService;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;

    @Override
    public Paiement create(Paiement paiement) {
        if (paiement.getIdPaiement() != null) {
            throw new IllegalArgumentException(
                    "Un nouveau paiement ne doit pas avoir d'identifiant");
        }
        return paiementRepository.save(paiement);
    }

    @Override
    public Paiement findById(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }

    @Override
    public List<Paiement> findAll() {
        return paiementRepository.findAll();
    }

    @Override
    public Paiement update(Long id, Paiement paiement) {
        Paiement existant = findById(id);
        existant.setMontant(paiement.getMontant());
        existant.setDatePaiement(paiement.getDatePaiement());
        existant.setModePaiement(paiement.getModePaiement());
        return paiementRepository.save(existant);
    }

    @Override
    public void deleteById(Long id) {
        paiementRepository.delete(findById(id));
    }
}