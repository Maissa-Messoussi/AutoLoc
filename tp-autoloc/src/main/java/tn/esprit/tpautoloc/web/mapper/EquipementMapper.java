package tn.esprit.tpautoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tpautoloc.domain.Equipement;
import tn.esprit.tpautoloc.web.dto.EquipementDTO;

@Mapper(componentModel = "spring")
public interface EquipementMapper {

    @Mapping(source = "idEquipement", target = "id")
    EquipementDTO toDto(Equipement equipement);

    @Mapping(target = "idEquipement", ignore = true)
    @Mapping(target = "vehicules", ignore = true)
    Equipement toEntity(EquipementDTO dto);
}
