package tn.esprit.tpautoloc.service.strategy;

import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;

import java.math.BigDecimal;

@Component
public class TarifLongueDureeStrategy implements ITarificationStrategy {
    private static final BigDecimal REMISE_LONGUE_DUREE = new BigDecimal("0.80");
    @Override
    public BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation) {
        return TarificationSupport.calculerTarifBase(vehicule, reservation)
                .multiply(REMISE_LONGUE_DUREE);
    }
    @Override
    public String getCode() {
        return "LONGUE_DUREE";
    }
}
