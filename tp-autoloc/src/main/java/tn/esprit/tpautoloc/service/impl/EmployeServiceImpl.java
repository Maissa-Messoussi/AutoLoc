package tn.esprit.tpautoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Agence;
import tn.esprit.tpautoloc.domain.Employe;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IAgenceRepository;
import tn.esprit.tpautoloc.repository.IEmployeRepository;
import tn.esprit.tpautoloc.service.IEmployeService;
import tn.esprit.tpautoloc.web.dto.EmployeDTO;
import tn.esprit.tpautoloc.web.mapper.EmployeMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeServiceImpl implements IEmployeService {

    private static final String EMPLOYE = "Employé";
    private static final String AGENCE = "Agence";

    private final IEmployeRepository employeRepository;
    private final IAgenceRepository agenceRepository;
    private final EmployeMapper employeMapper;

    @Override
    public EmployeDTO creer(EmployeDTO dto) {
        Employe employe = employeMapper.toEntity(dto);
        lierAgence(employe, dto);
        return employeMapper.toDto(employeRepository.save(employe));
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeDTO findById(Long id) {
        return employeMapper.toDto(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeDTO> findAll() {
        return employeRepository.findAll().stream().map(employeMapper::toDto).toList();
    }

    @Override
    public EmployeDTO update(Long id, EmployeDTO dto) {
        Employe existant = getOrThrow(id);
        existant.setNom(dto.getNom());
        existant.setPrenom(dto.getPrenom());
        existant.setRole(dto.getRole());
        lierAgence(existant, dto);
        return employeMapper.toDto(employeRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        employeRepository.delete(getOrThrow(id));
    }

    @Override
    public void affecterEmployeAAgence(Long employeId, Long agenceId) {
        Employe employe = getOrThrow(employeId);
        Agence agence = agenceRepository.findById(agenceId)
                .orElseThrow(() -> new ResourceNotFoundException(AGENCE, agenceId));
        employe.setAgence(agence);
        employeRepository.save(employe);
    }

    @Override
    public void desaffecterEmployeDeAgence(Long employeId) {
        Employe employe = getOrThrow(employeId);
        if (employe.getAgence() == null) {
            throw new IllegalArgumentException("L'employé n'est rattaché à aucune agence");
        }
        employe.setAgence(null);
        employeRepository.save(employe);
    }

    private void lierAgence(Employe employe, EmployeDTO dto) {
        if (dto.getAgenceId() != null) {
            employe.setAgence(agenceRepository.findById(dto.getAgenceId())
                    .orElseThrow(() -> new ResourceNotFoundException(AGENCE, dto.getAgenceId())));
        }
    }

    private Employe getOrThrow(Long id) {
        return employeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(EMPLOYE, id));
    }
}
