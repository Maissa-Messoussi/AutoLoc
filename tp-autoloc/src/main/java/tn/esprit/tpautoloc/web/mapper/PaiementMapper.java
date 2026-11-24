package tn.esprit.tpautoloc.web.mapper;

import org.mapstruct.Mapper;
import tn.esprit.tpautoloc.domain.Paiement;
import tn.esprit.tpautoloc.web.dto.PaiementDTO;

@Mapper(componentModel = "spring")
public interface PaiementMapper {

    PaiementDTO toDto(Paiement paiement);

    Paiement toEntity(PaiementDTO dto);
}
