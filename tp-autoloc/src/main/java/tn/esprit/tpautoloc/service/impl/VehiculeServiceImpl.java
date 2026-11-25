package tn.esprit.tpautoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.domain.Equipement;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IAgenceRepository;
import tn.esprit.tpautoloc.repository.IEquipementRepository;
import tn.esprit.tpautoloc.repository.IVehiculeRepository;
import tn.esprit.tpautoloc.service.IVehiculeService;
import tn.esprit.tpautoloc.web.dto.VehiculeDTO;
import tn.esprit.tpautoloc.web.mapper.VehiculeMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class VehiculeServiceImpl implements IVehiculeService {

    private final IVehiculeRepository vehiculeRepository;
    private final IAgenceRepository agenceRepository;
    private final IEquipementRepository equipementRepository;
    private final VehiculeMapper vehiculeMapper;

    @Override
    public VehiculeDTO creer(VehiculeDTO dto) {
        Vehicule vehicule = vehiculeMapper.toEntity(dto);
        valider(vehicule);
        lierAgence(vehicule, dto);
        return toDto(vehiculeRepository.save(vehicule));
    }

    @Override
    @Transactional(readOnly = true)
    public VehiculeDTO findById(Long id) {
        return toDto(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculeDTO> findAll() {
        return vehiculeRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public VehiculeDTO update(Long id, VehiculeDTO dto) {
        Vehicule existant = getOrThrow(id);
        Vehicule donnees = vehiculeMapper.toEntity(dto);
        valider(donnees);

        existant.setImmatriculation(donnees.getImmatriculation());
        existant.setMarque(donnees.getMarque());
        existant.setModele(donnees.getModele());
        existant.setCategorie(donnees.getCategorie());
        existant.setTarifJournalier(donnees.getTarifJournalier());
        existant.setStatut(donnees.getStatut());
        lierAgence(existant, dto);
        return toDto(vehiculeRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        vehiculeRepository.delete(getOrThrow(id));
    }

    @Override
    public void affecterVehiculeAAgence(Long vehiculeId, Long agenceId) {
        Vehicule vehicule = getOrThrow(vehiculeId);
        Agence agence = agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ResourceNotFoundException("Agence", agenceId));
        vehicule.setAgence(agence);
        vehiculeRepository.save(vehicule);
    }

    @Override
    public void desaffecterVehiculeDeAgence(Long vehiculeId) {
        Vehicule vehicule = getOrThrow(vehiculeId);
        if (vehicule.getAgence() == null) {
            throw new IllegalArgumentException("Le véhicule n'est rattaché à aucune agence");
        }
        vehicule.setAgence(null);
        vehiculeRepository.save(vehicule);
    }

    @Override
    public void affecterEquipementAVehicule(Long vehiculeId, Long equipementId) {
        Vehicule vehicule = getOrThrow(vehiculeId);
        Equipement equipement = equipementRepository.findById(equipementId)
                .orElseThrow(() -> new ResourceNotFoundException("Équipement", equipementId));
        boolean dejaAffecte = vehicule.getEquipements().stream()
                .anyMatch(e -> e.getIdEquipement().equals(equipementId));
        if (!dejaAffecte) {
            vehicule.getEquipements().add(equipement);
            vehiculeRepository.save(vehicule);
        }
    }

    @Override
    public void desaffecterEquipementDeVehicule(Long vehiculeId, Long equipementId) {
        Vehicule vehicule = getOrThrow(vehiculeId);
        boolean retire = vehicule.getEquipements()
                .removeIf(e -> e.getIdEquipement().equals(equipementId));
        if (!retire) {
            throw new IllegalArgumentException("Cet équipement n'est pas affecté à ce véhicule");
        }
        vehiculeRepository.save(vehicule);
    }

    private void lierAgence(Vehicule vehicule, VehiculeDTO dto) {
        if (dto.getAgenceId() != null) {
            vehicule.setAgence(agenceRepository.findById(dto.getAgenceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Agence", dto.getAgenceId())));
        }
    }

    private void valider(Vehicule vehicule) {
        if (vehicule.getTarifJournalier() != null && vehicule.getTarifJournalier().signum() < 0) {
            throw new IllegalArgumentException("Le tarif journalier ne peut pas être négatif");
        }
    }

    private VehiculeDTO toDto(Vehicule vehicule) {
        return vehiculeMapper.toDto(vehicule);
    }

    private Vehicule getOrThrow(Long id) {
        return vehiculeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Véhicule", id));
    }
}