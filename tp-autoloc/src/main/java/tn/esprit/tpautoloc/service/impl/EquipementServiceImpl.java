package tn.esprit.tpautoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Equipement;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IEquipementRepository;
import tn.esprit.tpautoloc.service.IEquipementService;
import tn.esprit.tpautoloc.web.dto.EquipementDTO;
import tn.esprit.tpautoloc.web.mapper.EquipementMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class EquipementServiceImpl implements IEquipementService {

    private final IEquipementRepository equipementRepository;
    private final EquipementMapper equipementMapper;

    @Override
    public EquipementDTO creer(EquipementDTO dto) {
        Equipement equipement = equipementMapper.toEntity(dto);
        return equipementMapper.toDto(equipementRepository.save(equipement));
    }

    @Override
    @Transactional(readOnly = true)
    public EquipementDTO findById(Long id) {
        return equipementMapper.toDto(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EquipementDTO> findAll() {
        return equipementRepository.findAll().stream().map(equipementMapper::toDto).toList();
    }

    @Override
    public EquipementDTO update(Long id, EquipementDTO dto) {
        Equipement existant = getOrThrow(id);
        existant.setLibelle(dto.getLibelle());
        return equipementMapper.toDto(equipementRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        equipementRepository.delete(getOrThrow(id));
    }

    private Equipement getOrThrow(Long id) {
        return equipementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Équipement", id));
    }
}
