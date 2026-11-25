package tn.esprit.tpautoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tpautoloc.domain.Maintenance;
import tn.esprit.tpautoloc.web.dto.MaintenanceDTO;

@Mapper(componentModel = "spring")
public interface MaintenanceMapper {

    @Mapping(source = "idMaintenance", target = "id")
    @Mapping(source = "vehicule.idVehicule", target = "vehiculeId")
    MaintenanceDTO toDto(Maintenance maintenance);

    @Mapping(target = "idMaintenance", ignore = true)
    @Mapping(target = "vehicule", ignore = true)
    Maintenance toEntity(MaintenanceDTO dto);
}
