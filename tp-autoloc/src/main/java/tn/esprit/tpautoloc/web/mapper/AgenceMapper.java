package tn.esprit.tpautoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.web.dto.AgenceDTO;

@Mapper(componentModel = "spring")
public interface AgenceMapper {

    @Mapping(source = "idAgence", target = "id")
    AgenceDTO toDto(Agence agence);

    @Mapping(target = "idAgence", ignore = true)
    @Mapping(target = "employes", ignore = true)
    @Mapping(target = "vehicules", ignore = true)
    void toEntity(AgenceDTO dto, @MappingTarget Agence agence);
}