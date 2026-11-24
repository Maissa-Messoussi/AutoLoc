package tn.esprit.tpautoloc.web.mapper;

import org.mapstruct.Mapper;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.web.dto.VehiculeDTO;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    VehiculeDTO toDto(Vehicule vehicule);

    Vehicule toEntity(VehiculeDTO dto);
}
