package tn.esprit.tpautoloc.service.factory;

import org.springframework.stereotype.Component;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.service.strategy.ITarificationStrategy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TarificationStrategyFactory {
    private static final long DUREE_LONGUE_MINIMALE = 7;
    private static final Set<DayOfWeek> JOURS_WEEKEND =
            EnumSet.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

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

        String code = determinerCode(reservation);

        ITarificationStrategy strategy = strategies.get(code);
        if (strategy == null) {
            throw new IllegalStateException("Stratégie de tarification introuvable : " + code);
        }
        return strategy;
    }

    private String determinerCode(Reservation reservation) {
        if (estWeekend(reservation)) {
            return "WEEKEND";
        }
        long duree = ChronoUnit.DAYS.between(reservation.getDateDebut(), reservation.getDateFin());
        if (duree >= DUREE_LONGUE_MINIMALE) {
            return "LONGUE_DUREE";
        }
        return "STANDARD";
    }

    private boolean estWeekend(Reservation reservation) {
        LocalDate date = reservation.getDateDebut();
        LocalDate dernierJour = reservation.getDateFin().minusDays(1);
        while (!date.isAfter(dernierJour)) {
            if (JOURS_WEEKEND.contains(date.getDayOfWeek())) {
                return true;
            }
            date = date.plusDays(1);
        }
        return false;
    }
}