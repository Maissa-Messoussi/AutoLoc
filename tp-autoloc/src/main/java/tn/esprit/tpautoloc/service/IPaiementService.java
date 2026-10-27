package tn.esprit.tpautoloc.service;

import java.util.List;
import tn.esprit.tpautoloc.domain.Paiement;

public interface IPaiementService {
    Paiement create(Paiement paiement);
    Paiement findById(Long id);
    List<Paiement> findAll();
    Paiement update(Long id, Paiement paiement);
    void deleteById(Long id);
}