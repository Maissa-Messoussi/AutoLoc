package tn.esprit.tpautoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.web.dto.VehiculeDTO;

@Mapper(componentModel = "spring")
public interface VehiculeMapper {

    @Mapping(source = "idVehicule", target = "id")
    @Mapping(source = "agence.idAgence", target = "agenceId")
    VehiculeDTO toDto(Vehicule vehicule);

    // L'identifiant est généré par la base ; l'agence est posée par le service
    @Mapping(target = "idVehicule", ignore = true)
    @Mapping(target = "agence", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "maintenances", ignore = true)
    @Mapping(target = "equipements", ignore = true)
    Vehicule toEntity(VehiculeDTO dto);
}
