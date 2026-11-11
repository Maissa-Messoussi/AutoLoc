package tn.esprit.tpautoloc.service.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.domain.Reservation;


@Component
public class SmsNotificationObserver implements IReservationObserver {

    private static final Logger log = LoggerFactory.getLogger(SmsNotificationObserver.class);

    @Override
    public void onReservationConfirmee(Reservation reservation) {
        log.info("[SMS] Confirmation envoyée au {} pour la réservation #{}",
                telephone(reservation), reservation.getIdReservation());
    }

    @Override
    public void onReservationAnnulee(Reservation reservation) {
        log.info("[SMS] Annulation notifiée au {} pour la réservation #{}",
                telephone(reservation), reservation.getIdReservation());
    }

    private String telephone(Reservation reservation) {
        return reservation != null && reservation.getClient() != null
                ? reservation.getClient().getTelephone()
                : "inconnu";
    }
}
