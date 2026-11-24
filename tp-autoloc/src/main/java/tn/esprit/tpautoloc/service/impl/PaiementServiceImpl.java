package tn.esprit.tpautoloc.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.tpautoloc.domain.Paiement;
import tn.esprit.tpautoloc.exception.ResourceNotFoundException;
import tn.esprit.tpautoloc.repository.IContratRepository;
import tn.esprit.tpautoloc.repository.IPaiementRepository;
import tn.esprit.tpautoloc.service.IPaiementService;
import tn.esprit.tpautoloc.web.dto.PaiementDTO;
import tn.esprit.tpautoloc.web.mapper.PaiementMapper;

@Service
@RequiredArgsConstructor
@Transactional
public class PaiementServiceImpl implements IPaiementService {

    private final IPaiementRepository paiementRepository;
    private final IContratRepository contratRepository;
    private final PaiementMapper paiementMapper;

    @Override
    public PaiementDTO creer(PaiementDTO dto) {
        Paiement paiement = paiementMapper.toEntity(dto);
        lierContrat(paiement, dto);
        return toDto(paiementRepository.save(paiement));
    }

    @Override
    @Transactional(readOnly = true)
    public PaiementDTO findById(Long id) {
        return toDto(getOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaiementDTO> findAll() {
        return paiementRepository.findAll().stream().map(this::toDto).toList();
    }

    @Override
    public PaiementDTO update(Long id, PaiementDTO dto) {
        Paiement existant = getOrThrow(id);
        Paiement donnees = paiementMapper.toEntity(dto);

        existant.setMontant(donnees.getMontant());
        existant.setDatePaiement(donnees.getDatePaiement());
        existant.setModePaiement(donnees.getModePaiement());
        lierContrat(existant, dto);
        return toDto(paiementRepository.save(existant));
    }

    @Override
    public void deleteById(Long id) {
        paiementRepository.delete(getOrThrow(id));
    }

    private void lierContrat(Paiement paiement, PaiementDTO dto) {
        if (dto.getContratId() != null) {
            paiement.setContrat(contratRepository.findById(dto.getContratId())
                    .orElseThrow(() -> new ResourceNotFoundException("Contrat", dto.getContratId())));
        }
    }

    private PaiementDTO toDto(Paiement paiement) {
        return paiementMapper.toDto(paiement);
    }

    private Paiement getOrThrow(Long id) {
        return paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement", id));
    }
}