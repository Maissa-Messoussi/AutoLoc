package tn.esprit.tpautoloc.service.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.domain.Reservation;

@Component
public class EmailNotificationObserver implements IReservationObserver {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationObserver.class);

    @Override
    public void onReservationConfirmee(Reservation reservation) {
        log.info("[EMAIL] Confirmation envoyée au client {} pour la réservation #{}",
                email(reservation), reservation.getIdReservation());
    }

    @Override
    public void onReservationAnnulee(Reservation reservation) {
        log.info("[EMAIL] Annulation notifiée au client {} pour la réservation #{}",
                email(reservation), reservation.getIdReservation());
    }

    private String email(Reservation reservation) {
        return reservation != null && reservation.getClient() != null
                ? reservation.getClient().getEmail()
                : "inconnu";
    }
}
