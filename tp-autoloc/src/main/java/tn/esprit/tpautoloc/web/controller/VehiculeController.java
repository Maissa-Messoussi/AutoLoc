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
import tn.esprit.tpautoloc.service.IVehiculeService;
import tn.esprit.tpautoloc.web.dto.VehiculeDTO;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(name = "Vehicules", description = "Gestion des vehicules")
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @Operation(summary = "Lister les vehicules")
    @ApiResponse(responseCode = "200", description = "Liste des vehicules")
    @GetMapping
    public ResponseEntity<List<VehiculeDTO>> findAll() {
        return ResponseEntity.ok(vehiculeService.findAll());
    }

    @Operation(summary = "Consulter un vehicule")
    @ApiResponse(responseCode = "200", description = "Vehicule trouve")
    @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    @GetMapping("/{id}")
    public ResponseEntity<VehiculeDTO> findById(
            @Parameter(description = "Identifiant") @PathVariable Long id) {
        return ResponseEntity.ok(vehiculeService.findById(id));
    }

    @Operation(summary = "Creer un vehicule")
    @ApiResponse(responseCode = "201", description = "Vehicule cree")
    @ApiResponse(responseCode = "400", description = "Donnees invalides")
    @PostMapping
    public ResponseEntity<VehiculeDTO> creer(@Valid @RequestBody VehiculeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehiculeService.creer(dto));
    }

    @Operation(summary = "Modifier un vehicule")
    @ApiResponse(responseCode = "200", description = "Vehicule modifie")
    @ApiResponse(responseCode = "400", description = "Donnees invalides")
    @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    @PutMapping("/{id}")
    public ResponseEntity<VehiculeDTO> update(
            @Parameter(description = "Identifiant") @PathVariable Long id,
            @Valid @RequestBody VehiculeDTO dto) {
        return ResponseEntity.ok(vehiculeService.update(id, dto));
    }

    @Operation(summary = "Affecter un vehicule a une agence")
    @ApiResponse(responseCode = "204", description = "Vehicule affecte")
    @ApiResponse(responseCode = "404", description = "Vehicule ou agence introuvable")
    @PutMapping("/{vehiculeId}/agence/{agenceId}")
    public ResponseEntity<Void> affecterAAgence(@PathVariable Long vehiculeId,
                                                @PathVariable Long agenceId) {
        vehiculeService.affecterVehiculeAAgence(vehiculeId, agenceId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Desaffecter un vehicule de son agence")
    @ApiResponse(responseCode = "204", description = "Vehicule desaffecte")
    @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    @PutMapping("/{vehiculeId}/desaffecter-agence")
    public ResponseEntity<Void> desaffecterDeAgence(@PathVariable Long vehiculeId) {
        vehiculeService.desaffecterVehiculeDeAgence(vehiculeId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Affecter un equipement a un vehicule")
    @ApiResponse(responseCode = "204", description = "Equipement affecte")
    @ApiResponse(responseCode = "404", description = "Vehicule ou equipement introuvable")
    @PutMapping("/{vehiculeId}/equipements/{equipementId}")
    public ResponseEntity<Void> affecterEquipement(@PathVariable Long vehiculeId,
                                                   @PathVariable Long equipementId) {
        vehiculeService.affecterEquipementAVehicule(vehiculeId, equipementId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Desaffecter un equipement d'un vehicule")
    @ApiResponse(responseCode = "204", description = "Equipement desaffecte")
    @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    @PutMapping("/{vehiculeId}/desaffecter-equipement/{equipementId}")
    public ResponseEntity<Void> desaffecterEquipement(@PathVariable Long vehiculeId,
                                                      @PathVariable Long equipementId) {
        vehiculeService.desaffecterEquipementDeVehicule(vehiculeId, equipementId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Supprimer un vehicule")
    @ApiResponse(responseCode = "204", description = "Vehicule supprime")
    @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identifiant") @PathVariable Long id) {
        vehiculeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}