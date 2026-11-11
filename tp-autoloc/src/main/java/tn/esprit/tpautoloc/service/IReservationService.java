package tn.esprit.tpautoloc.service;

import java.util.List;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Reservation;

public interface IReservationService {
    Reservation create(Reservation reservation);
    Reservation findById(Long id);
    List<Reservation> findAll();
    Reservation update(Long id, Reservation reservation);
    Reservation annulerReservation(Long id);
    void deleteById(Long id);

    // Ajouts pour l'Observer (Atelier 6)
    Reservation creerReservation(Client client);
    Reservation annulerReservation(Reservation reservation);
}