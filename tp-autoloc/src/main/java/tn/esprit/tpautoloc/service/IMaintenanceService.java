package tn.esprit.tpautoloc.service;

import java.util.List;
import tn.esprit.tpautoloc.web.dto.MaintenanceDTO;

public interface IMaintenanceService {
    MaintenanceDTO creer(MaintenanceDTO dto);
    MaintenanceDTO findById(Long id);
    List<MaintenanceDTO> findAll();
    MaintenanceDTO update(Long id, MaintenanceDTO dto);
    void deleteById(Long id);
    void affecterMaintenanceAVehicule(Long maintenanceId, Long vehiculeId);
    void desaffecterMaintenanceDeVehicule(Long maintenanceId);
}
