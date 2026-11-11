package tn.esprit.tpautoloc.service.strategy;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;

final class TarificationSupport {

    private TarificationSupport() {
    }

    static BigDecimal calculerTarifBase(Vehicule vehicule, Reservation reservation) {
        if (vehicule == null) {
            throw new IllegalArgumentException("Le véhicule est obligatoire");
        }
        if (reservation == null) {
            throw new IllegalArgumentException("La réservation est obligatoire");
        }
        if (vehicule.getTarifJournalier() == null
                || vehicule.getTarifJournalier().signum() < 0) {
            throw new IllegalArgumentException(
                    "Le tarif journalier doit être renseigné et non négatif");
        }
        if (reservation.getDateDebut() == null || reservation.getDateFin() == null) {
            throw new IllegalArgumentException(
                    "Les dates de début et de fin sont obligatoires");
        }

        long nombreJours = ChronoUnit.DAYS.between(
                reservation.getDateDebut(), reservation.getDateFin());
        if (nombreJours <= 0) {
            throw new IllegalArgumentException(
                    "La date de fin doit être postérieure à la date de début");
        }

        return vehicule.getTarifJournalier().multiply(BigDecimal.valueOf(nombreJours));
    }
}
