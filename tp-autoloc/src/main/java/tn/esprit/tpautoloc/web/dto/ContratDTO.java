package tn.esprit.tpautoloc.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContratDTO {
    private Long id;
    private LocalDate dateSignature;
    private BigDecimal montantTotal;
    private Boolean valide;
    private Long reservationId;
}
