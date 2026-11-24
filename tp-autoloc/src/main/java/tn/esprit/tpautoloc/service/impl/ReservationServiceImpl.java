package tn.esprit.tpautoloc.service.impl;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.enums.StatutReservation;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IClientRepository;
import tn.esprit.tpautoloc.repository.IReservationRepository;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;
import tn.esprit.tpautoloc.service.IReservationService;
import tn.esprit.tpautoloc.service.observer.ReservationSubject;
import tn.esprit.tpautoloc.web.dto.ReservationRequestDTO;
import tn.esprit.tpautoloc.web.dto.ReservationResponseDTO;
import tn.esprit.tpautoloc.web.mapper.ReservationMapper;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReservationServiceImpl implements IReservationService {

    private final IReservationRepository reservationRepository;
    private final IClientRepository clientRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final ReservationSubject reservationSubject;
    private final ReservationMapper reservationMapper;

    private void valider(Reservation reservation) {
        if (reservation.getDateDebut() != null
                && reservation.getDateFin() != null
                && reservation.getDateFin().isBefore(reservation.getDateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin ne peut pas être antérieure à la date de début");
        }
    }

    @Override
    public ReservationResponseDTO creer(ReservationRequestDTO dto) {
        Reservation reservation = reservationMapper.toEntity(dto);
        valider(reservation);
        reservation.setClient(clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client", dto.getClientId())));
        reservation.setVehicule(vehiculeRepository.findById(dto.getVehiculeId())
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule", dto.getVehiculeId())));
        reservation.setStatut(StatutReservation.EN_ATTENTE);
        return reservationMapper.toDto(reservationRepository.save(reservation));
    }

    @Override
    @Transactional(readOnly = true)
    public ReservationResponseDTO findById(Long id) {
        return reservationMapper.toDto(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationResponseDTO> findAll() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDto).toList();
    }

    @Override
    public ReservationResponseDTO update(Long id, ReservationRequestDTO dto) {
        Reservation existante = getOrThrow(id);
        Reservation donnees = reservationMapper.toEntity(dto);
        valider(donnees);

        existante.setDateDebut(donnees.getDateDebut());
        existante.setDateFin(donnees.getDateFin());
        if (dto.getClientId() != null) {
            existante.setClient(clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Client", dto.getClientId())));
        }
        if (dto.getVehiculeId() != null) {
            existante.setVehicule(vehiculeRepository.findById(dto.getVehiculeId())
                    .orElseThrow(() -> new ResourceNotFoundException("Véhicule", dto.getVehiculeId())));
        }
        return reservationMapper.toDto(reservationRepository.save(existante));
    }

    @Override
    public ReservationResponseDTO annulerReservation(Long id) {
        Reservation reservation = getOrThrow(id);
        if (reservation.getStatut() == StatutReservation.ANNULEE) {
            return reservationMapper.toDto(reservation);
        }
        reservation.setStatut(StatutReservation.ANNULEE);
        Reservation annulee = reservationRepository.save(reservation);
        reservationSubject.notifierAnnulation(annulee);
        return reservationMapper.toDto(annulee);
    }

    @Override
    public void deleteById(Long id) {
        reservationRepository.delete(getOrThrow(id));
    }

    // ===== Atelier 6 (Observer) =====

    @Override
    public Reservation creerReservation(Client client) {
        // Le client du test n'est pas encore en base : on l'enregistre d'abord
        Client clientPersiste = (client.getIdClient() == null)
                ? clientRepository.save(client)
                : client;

        Reservation reservation = Reservation.builder()
                .client(clientPersiste)
                .dateDebut(LocalDate.now())
                .statut(StatutReservation.CONFIRMEE)
                .build();
        Reservation saved = reservationRepository.save(reservation);
        // Déclenche le process métier via le pattern Observer
        reservationSubject.notifierConfirmation(saved);
        return saved;
    }

    @Override
    public Reservation annulerReservation(Reservation reservation) {
        if (reservation.getStatut() == StatutReservation.ANNULEE) {
            return reservation;
        }
        reservation.setStatut(StatutReservation.ANNULEE);
        Reservation annulee = (reservation.getIdReservation() != null)
                ? reservationRepository.save(reservation)
                : reservation;
        reservationSubject.notifierAnnulation(annulee);
        return annulee;
    }
    private Reservation getOrThrow(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Réservation", id));
    }
}