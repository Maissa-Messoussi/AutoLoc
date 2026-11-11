package tn.esprit.tpautoloc.service.strategy;

import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.domain.Vehicule;

import java.math.BigDecimal;

@Component
public class TarifStandardStrategy implements ITarificationStrategy {
    @Override
    public BigDecimal calculerTarif(Vehicule vehicule, Reservation reservation) {
        return TarificationSupport.calculerTarifBase(vehicule, reservation);
    }
    @Override
    public String getCode() {
        return "STANDARD";
    }
}
