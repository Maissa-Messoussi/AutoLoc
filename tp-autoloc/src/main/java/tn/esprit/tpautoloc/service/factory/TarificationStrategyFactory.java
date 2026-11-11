package tn.esprit.tpautoloc.service.factory;

import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.service.strategy.ITarificationStrategy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TarificationStrategyFactory {
    private static final long DUREE_LONGUE_MINIMALE = 7;
    private final Map<String, ITarificationStrategy> strategies;

    public TarificationStrategyFactory(List<ITarificationStrategy> strategyList) {
        if (strategyList == null || strategyList.isEmpty()) {
            throw new IllegalArgumentException("Au moins une stratégie de tarification est obligatoire");
        }
        this.strategies = strategyList.stream()
                .collect(Collectors.toUnmodifiableMap(
                        ITarificationStrategy::getCode,
                        Function.identity(),
                        (premiere, seconde) -> {
                            throw new IllegalArgumentException(
                                    "Code de stratégie dupliqué : " + premiere.getCode());
                        }));
    }

    public ITarificationStrategy resoudre(Reservation reservation) {
        if (reservation == null) {
            throw new IllegalArgumentException("La réservation est obligatoire");
        }
        if (reservation.getDateDebut() == null || reservation.getDateFin() == null) {
            throw new IllegalArgumentException(
                    "Les dates de début et de fin sont obligatoires");
        }
        if (!reservation.getDateFin().isAfter(reservation.getDateDebut())) {
            throw new IllegalArgumentException(
                    "La date de fin doit être postérieure à la date de début");
        }

        String code = estWeekend(reservation)
                ? "WEEKEND"
                : ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin())
                >= DUREE_LONGUE_MINIMALE
                ? "LONGUE_DUREE"
                : "STANDARD";

        ITarificationStrategy strategy = strategies.get(code);
        if (strategy == null) {
            throw new IllegalStateException("Stratégie de tarification introuvable : " + code);
        }
        return strategy;
    }

    private boolean estWeekend(Reservation reservation) {
        LocalDate date = reservation.getDateDebut();
        while (!date.isAfter(reservation.getDateFin().minusDays(1))) {
            DayOfWeek jour = date.getDayOfWeek();
            if (jour == DayOfWeek.SATURDAY || jour == DayOfWeek.SUNDAY) {
                return true;
            }
            date = date.plusDays(1);
        }
        return false;
    }
}
