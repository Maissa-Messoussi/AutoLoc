package tn.esprit.tpautoloc.service.strategy;

import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;

import java.math.BigDecimal;

@Component
public class TarifWeekendStrategy implements ITarificationStrategy {
    private static final BigDecimal REMISE_WEEKEND = new BigDecimal("0.90");

    @Override
    public BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation) {
        return TarificationSupport.calculerTarifBase(vehicule, reservation)
                .multiply(REMISE_WEEKEND);
    }

    @Override
    public String getCode() {
        return "WEEKEND";
    }
}
