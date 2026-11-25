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
import tn.esprit.tpautoloc.service.IMaintenanceService;
import tn.esprit.tpautoloc.web.dto.MaintenanceDTO;

import java.util.List;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
@Tag(name = "Maintenances", description = "Gestion des maintenances")
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @Operation(summary = "Lister")
    @ApiResponse(responseCode = "200", description = "Liste")
    @GetMapping
    public ResponseEntity<List<MaintenanceDTO>> findAll() {
        return ResponseEntity.ok(maintenanceService.findAll());
    }

    @Operation(summary = "Consulter par identifiant")
    @ApiResponse(responseCode = "200", description = "Trouve")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceDTO> findById(
            @Parameter(description = "Identifiant") @PathVariable Long id) {
        return ResponseEntity.ok(maintenanceService.findById(id));
    }

    @Operation(summary = "Creer")
    @ApiResponse(responseCode = "201", description = "Cree")
    @ApiResponse(responseCode = "400", description = "Donnees invalides")
    @PostMapping
    public ResponseEntity<MaintenanceDTO> creer(@Valid @RequestBody MaintenanceDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(maintenanceService.creer(dto));
    }

    @Operation(summary = "Modifier")
    @ApiResponse(responseCode = "200", description = "Modifie")
    @ApiResponse(responseCode = "400", description = "Donnees invalides")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceDTO> update(
            @Parameter(description = "Identifiant") @PathVariable Long id,
            @Valid @RequestBody MaintenanceDTO dto) {
        return ResponseEntity.ok(maintenanceService.update(id, dto));
    }

    @Operation(summary = "Affecter une maintenance a un vehicule")
    @ApiResponse(responseCode = "204", description = "Maintenance affectee")
    @ApiResponse(responseCode = "404", description = "Maintenance ou vehicule introuvable")
    @PutMapping("/{maintenanceId}/vehicule/{vehiculeId}")
    public ResponseEntity<Void> affecterAVehicule(@PathVariable Long maintenanceId,
                                                  @PathVariable Long vehiculeId) {
        maintenanceService.affecterMaintenanceAVehicule(maintenanceId, vehiculeId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Desaffecter une maintenance de son vehicule")
    @ApiResponse(responseCode = "204", description = "Maintenance desaffectee")
    @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    @PutMapping("/{maintenanceId}/desaffecter-vehicule")
    public ResponseEntity<Void> desaffecterDeVehicule(@PathVariable Long maintenanceId) {
        maintenanceService.desaffecterMaintenanceDeVehicule(maintenanceId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Supprimer")
    @ApiResponse(responseCode = "204", description = "Supprime")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identifiant") @PathVariable Long id) {
        maintenanceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
