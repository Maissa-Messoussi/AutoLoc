package tn.esprit.tpautoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.enums.StatutReservation;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IReservationRepository;
import tn.esprit.tpautoloc.service.IReservationService;
import tn.esprit.tpautoloc.service.observer.ReservationSubject;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final ReservationSubject reservationSubject;

    private void valider(Reservation reservation) {
        if (reservation.getDateDebut() != null
                && reservation.getDateFin() != null
                && reservation.getDateFin().isBefore(reservation.getDateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début"
            );
        }
    }

    @Override
    public Reservation create(Reservation reservation) {
        if (reservation.getIdReservation() != null) {
            throw new IllegalArgumentException("Une nouvelle réservation ne doit pas avoir d'identifiant");
        }
        valider(reservation);
        Reservation saved = reservationRepository.save(reservation);
        if (saved.getStatut() == StatutReservation.CONFIRMEE) {
            reservationSubject.notifierConfirmation(saved);
        }
        return saved;
    }

    @Override
    public Reservation findById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation", id));
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation update(Long id, Reservation reservation) {
        Reservation existante = findById(id);
        StatutReservation ancienStatut = existante.getStatut();
        valider(reservation);

        existante.setDateDebut(reservation.getDateDebut());
        existante.setDateFin(reservation.getDateFin());
        existante.setStatut(reservation.getStatut());

        Reservation miseAJour = reservationRepository.save(existante);
        notifierChangementStatut(ancienStatut, miseAJour);
        return miseAJour;
    }

    @Override
    public Reservation annulerReservation(Long id) {
        Reservation reservation = findById(id);
        if (reservation.getStatut() == StatutReservation.ANNULEE) {
            return reservation;
        }
        reservation.setStatut(StatutReservation.ANNULEE);
        Reservation annulee = reservationRepository.save(reservation);
        reservationSubject.notifierAnnulation(annulee);
        return annulee;
    }

    private void notifierChangementStatut(
            StatutReservation ancienStatut, Reservation reservation) {
        if (ancienStatut != StatutReservation.CONFIRMEE
                && reservation.getStatut() == StatutReservation.CONFIRMEE) {
            reservationSubject.notifierConfirmation(reservation);
        } else if (ancienStatut != StatutReservation.ANNULEE
                && reservation.getStatut() == StatutReservation.ANNULEE) {
            reservationSubject.notifierAnnulation(reservation);
        }
    }

    @Override
    public void deleteById(Long id) {
        reservationRepository.delete(findById(id));
    }
}