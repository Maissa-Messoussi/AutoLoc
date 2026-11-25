package tn.esprit.tpautoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.service.IEmployeService;
import tn.esprit.tpautoloc.web.dto.EmployeDTO;

import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
@Tag(name = "Employes", description = "Gestion des employes")
public class EmployeController {

    private final IEmployeService employeService;

    @Operation(summary = "Lister")
    @ApiResponse(responseCode = "200", description = "Liste")
    @GetMapping
    public ResponseEntity<List<EmployeDTO>> findAll() {
        return ResponseEntity.ok(employeService.findAll());
    }

    @Operation(summary = "Consulter par identifiant")
    @ApiResponse(responseCode = "200", description = "Trouve")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeDTO> findById(
            @Parameter(description = "Identifiant") @PathVariable Long id) {
        return ResponseEntity.ok(employeService.findById(id));
    }

    @Operation(summary = "Creer")
    @ApiResponse(responseCode = "201", description = "Cree")
    @ApiResponse(responseCode = "400", description = "Donnees invalides")
    @PostMapping
    public ResponseEntity<EmployeDTO> creer(@Valid @RequestBody EmployeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeService.creer(dto));
    }

    @Operation(summary = "Modifier")
    @ApiResponse(responseCode = "200", description = "Modifie")
    @ApiResponse(responseCode = "400", description = "Donnees invalides")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @PutMapping("/{id}")
    public ResponseEntity<EmployeDTO> update(
            @Parameter(description = "Identifiant") @PathVariable Long id,
            @Valid @RequestBody EmployeDTO dto) {
        return ResponseEntity.ok(employeService.update(id, dto));
    }

    @Operation(summary = "Affecter un employe a une agence")
    @ApiResponse(responseCode = "204", description = "Employe affecte")
    @ApiResponse(responseCode = "404", description = "Employe ou agence introuvable")
    @PutMapping("/{employeId}/agence/{agenceId}")
    public ResponseEntity<Void> affecterAAgence(@PathVariable Long employeId,
                                                @PathVariable Long agenceId) {
        employeService.affecterEmployeAAgence(employeId, agenceId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Desaffecter un employe de son agence")
    @ApiResponse(responseCode = "204", description = "Employe desaffecte")
    @ApiResponse(responseCode = "404", description = "Employe introuvable")
    @PutMapping("/{employeId}/desaffecter-agence")
    public ResponseEntity<Void> desaffecterDeAgence(@PathVariable Long employeId) {
        employeService.desaffecterEmployeDeAgence(employeId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Supprimer")
    @ApiResponse(responseCode = "204", description = "Supprime")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identifiant") @PathVariable Long id) {
        employeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
