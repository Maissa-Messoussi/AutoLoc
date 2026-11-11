package tn.esprit.tpautoloc.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.tpautoloc.domain.enums.ModePaiement;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaiementDTO {
    private Long id;
    private BigDecimal montant;
    private LocalDate datePaiement;
    private ModePaiement modePaiement;
    private Long contratId;
}
