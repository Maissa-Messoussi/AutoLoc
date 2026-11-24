package tn.esprit.tpautoloc.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AutoLocManager {

    private static final String LONGUE_DUREE = "LongDuree";
    private static final String WEEKEND = "Weekend";
    private static final String STANDARD = "Standard";

    public void louerVehicule(String typeVehicule, int nombreJours, String clientEmail) {
        double prix = 0;
        if (LONGUE_DUREE.equals(typeVehicule)) {
            prix = nombreJours * 120.0;
        } else if (WEEKEND.equals(typeVehicule)) {
            prix = nombreJours * 180.0;
        } else if (STANDARD.equals(typeVehicule)) {
            prix = nombreJours * 150.0;
        }
        log.info("Contrat créé");
        log.info("Prix total : {}", prix);

        envoyerEmail(clientEmail);
        enregistrerPaiement();
        sauvegarderContrat();
    }

    private void enregistrerPaiement() {
        // Volontairement vide : l'enregistrement du paiement n'est pas encore implémenté
    }

    private void envoyerEmail(String email) {
        log.info("Email envoyé à {}", email);
    }

    private void sauvegarderContrat() {
        log.info("Contrat sauvegardé");
    }
}