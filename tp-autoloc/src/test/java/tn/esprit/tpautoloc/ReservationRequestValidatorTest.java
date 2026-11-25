package tn.esprit.tpautoloc;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import tn.esprit.tpautoloc.web.dto.ReservationRequestDTO;
import tn.esprit.tpautoloc.web.validator.ReservationRequestValidator;

/** Test unitaire pur : pas de Spring, pas de base de données. */
class ReservationRequestValidatorTest {

    private final ReservationRequestValidator validator = new ReservationRequestValidator();

    private Errors valider(LocalDate debut, LocalDate fin) {
        ReservationRequestDTO dto = ReservationRequestDTO.builder()
                .clientId(1L).vehiculeId(1L).dateDebut(debut).dateFin(fin).build();
        Errors errors = new BeanPropertyBindingResult(dto, "dto");
        validator.validate(dto, errors);
        return errors;
    }

    @Test
    void datesCoherentesSontAcceptees() {
        Errors errors = valider(LocalDate.now().plusDays(1), LocalDate.now().plusDays(5));
        assertFalse(errors.hasErrors());
    }

    @Test
    void dateDebutAujourdhuiEstAcceptee() {
        assertFalse(valider(LocalDate.now(), LocalDate.now().plusDays(2)).hasErrors());
    }

    @Test
    void dateFinAvantDateDebutEstRejetee() {
        Errors errors = valider(LocalDate.now().plusDays(5), LocalDate.now().plusDays(2));
        assertNotNull(errors.getFieldError("dateFin"));
    }

    @Test
    void dateFinEgaleDateDebutEstRejetee() {
        LocalDate jour = LocalDate.now().plusDays(3);
        assertNotNull(valider(jour, jour).getFieldError("dateFin"));
    }

    @Test
    void dateDebutPasseeEstRejetee() {
        Errors errors = valider(LocalDate.now().minusDays(1), LocalDate.now().plusDays(2));
        assertNotNull(errors.getFieldError("dateDebut"));
    }

    @Test
    void datesAbsentesSontLaisseesAuBeanValidation() {
        assertFalse(valider(null, null).hasErrors());
    }
}
