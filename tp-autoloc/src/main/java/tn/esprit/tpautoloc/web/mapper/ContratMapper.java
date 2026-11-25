package tn.esprit.tpautoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tpautoloc.domain.Contrat;
import tn.esprit.tpautoloc.web.dto.ContratDTO;

@Mapper(componentModel = "spring")
public interface ContratMapper {

    @Mapping(source = "idContrat", target = "id")
    @Mapping(source = "reservation.idReservation", target = "reservationId")
    ContratDTO toDto(Contrat contrat);

    // La réservation est liée par le service
    @Mapping(target = "idContrat", ignore = true)
    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "paiements", ignore = true)
    Contrat toEntity(ContratDTO dto);
}
