package tn.esprit.tpautoloc.service;

import java.util.List;
import tn.esprit.tpautoloc.web.dto.EmployeDTO;

public interface IEmployeService {
    EmployeDTO creer(EmployeDTO dto);
    EmployeDTO findById(Long id);
    List<EmployeDTO> findAll();
    EmployeDTO update(Long id, EmployeDTO dto);
    void deleteById(Long id);
    void affecterEmployeAAgence(Long employeId, Long agenceId);
    void desaffecterEmployeDeAgence(Long employeId);
}
