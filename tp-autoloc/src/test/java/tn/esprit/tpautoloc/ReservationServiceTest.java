package tn.esprit.tpautoloc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.enums.StatutReservation;
import tn.esprit.tpautoloc.service.IReservationService;

@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private IReservationService reservationService;

    private Client creerClient() {
        Client client = new Client();
        client.setNom("Ben Ali");
        client.setPrenom("Amine");
        client.setEmail("amine.test@example.com");
        client.setTelephone("20111222");
        client.setNumPermis("PERMIS-TEST-003");
        return client;
    }

    @Test
    void testCreationReservation() {
        System.out.println("===== TEST CREATION =====");
        Reservation reservation = reservationService.creerReservation(creerClient());

        assertEquals(StatutReservation.CONFIRMEE, reservation.getStatut());
        // Console attendue :
        // [EMAIL] Confirmation envoyee au client ...
        // [SMS] Confirmation envoyee au ...
    }

    @Test
    void testAnnulationReservation() {
        System.out.println("===== TEST ANNULATION =====");
        Reservation reservation = reservationService.creerReservation(creerClient());

        reservationService.annulerReservation(reservation);

        assertEquals(StatutReservation.ANNULEE, reservation.getStatut());
        // Console attendue :
        // [EMAIL] Annulation notifiee au client ...
        // [SMS] Annulation notifiee au ...
    }
}