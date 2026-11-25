package tn.esprit.tpautoloc.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tn.esprit.tpautoloc.domain.Employe;
import tn.esprit.tpautoloc.web.dto.EmployeDTO;

@Mapper(componentModel = "spring")
public interface EmployeMapper {

    @Mapping(source = "idEmploye", target = "id")
    @Mapping(source = "agence.idAgence", target = "agenceId")
    EmployeDTO toDto(Employe employe);

    @Mapping(target = "idEmploye", ignore = true)
    @Mapping(target = "agence", ignore = true)
    Employe toEntity(EmployeDTO dto);
}
