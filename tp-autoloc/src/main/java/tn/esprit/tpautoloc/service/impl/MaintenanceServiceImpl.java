package tn.esprit.tpautoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Maintenance;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IMaintenanceRepository;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;
import tn.esprit.tpautoloc.service.IMaintenanceService;
import tn.esprit.tpautoloc.web.dto.MaintenanceDTO;
import tn.esprit.tpautoloc.web.mapper.MaintenanceMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements IMaintenanceService {

    private static final String VEHICULE = "Véhicule";

    private final IMaintenanceRepository maintenanceRepository;
    private final IVehiculeRepository vehiculeRepository;
    private final MaintenanceMapper maintenanceMapper;

    @Override
    public MaintenanceDTO creer(MaintenanceDTO dto) {
        Maintenance maintenance = maintenanceMapper.toEntity(dto);
        valider(maintenance);
        lierVehicule(maintenance, dto);
        return maintenanceMapper.toDto(maintenanceRepository.save(maintenance));
    }

    @Override
    @Transactional(readOnly = true)
    public MaintenanceDTO findById(Long id) {
        return maintenanceMapper.toDto(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<MaintenanceDTO> findAll() {
        return maintenanceRepository.findAll().stream().map(maintenanceMapper::toDto).toList();
    }

    @Override
    public MaintenanceDTO update(Long id, MaintenanceDTO dto) {
        Maintenance existant = getOrThrow(id);
        Maintenance donnees = maintenanceMapper.toEntity(dto);
        valider(donnees);

        existant.setDateDebut(donnees.getDateDebut());
        existant.setDateFin(donnees.getDateFin());
        existant.setDescription(donnees.getDescription());
        lierVehicule(existant, dto);
        return maintenanceMapper.toDto(maintenanceRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        maintenanceRepository.delete(getOrThrow(id));
    }

    @Override
    public void affecterMaintenanceAVehicule(Long maintenanceId, Long vehiculeId) {
        Maintenance maintenance = getOrThrow(maintenanceId);
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId)
                .orElseThrow(() -> new ResourceNotFoundException(VEHICULE, vehiculeId));
        maintenance.setVehicule(vehicule);
        maintenanceRepository.save(maintenance);
    }

    @Override
    public void desaffecterMaintenanceDeVehicule(Long maintenanceId) {
        Maintenance maintenance = getOrThrow(maintenanceId);
        if (maintenance.getVehicule() == null) {
            throw new IllegalArgumentException("La maintenance n'est rattachée à aucun véhicule");
        }
        maintenance.setVehicule(null);
        maintenanceRepository.save(maintenance);
    }

    private void lierVehicule(Maintenance maintenance, MaintenanceDTO dto) {
        if (dto.getVehiculeId() != null) {
            maintenance.setVehicule(vehiculeRepository.findById(dto.getVehiculeId())
                    .orElseThrow(() -> new ResourceNotFoundException(VEHICULE, dto.getVehiculeId())));
        }
    }

    private void valider(Maintenance maintenance) {
        if (maintenance.getDateDebut() != null
                && maintenance.getDateFin() != null
                && maintenance.getDateFin().isBefore(maintenance.getDateDebut())) {
            throw new IllegalArgumentException("La date de fin ne peut pas être antérieure à la date de début");
        }
    }

    private Maintenance getOrThrow(Long id) {
        return maintenanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Maintenance", id));
    }
}
