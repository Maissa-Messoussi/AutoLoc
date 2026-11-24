package tn.esprit.tpautoloc.service;

import java.util.List;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.web.dto.ReservationRequestDTO;
import tn.esprit.tpautoloc.web.dto.ReservationResponseDTO;

public interface IReservationService {
    ReservationResponseDTO creer(ReservationRequestDTO dto);
    ReservationResponseDTO findById(Long id);
    List<ReservationResponseDTO> findAll();
    ReservationResponseDTO update(Long id, ReservationRequestDTO dto);
    ReservationResponseDTO annulerReservation(Long id);
    void deleteById(Long id);

    // Atelier 6 (Observer)
    Reservation creerReservation(Client client);
    Reservation annulerReservation(Reservation reservation);
}