package tn.esprit.tpautoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tn.esprit.tpautoloc.domain.Client;
import tn.esprit.tpautoloc.web.dto.ClientDTO;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source = "idClient", target = "id")
    ClientDTO toDto(Client client);

    @Mapping(target = "idClient", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    void toEntity(ClientDTO dto, @MappingTarget Client client);
}