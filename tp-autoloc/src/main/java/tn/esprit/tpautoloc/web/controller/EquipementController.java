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
import tn.esprit.tpautoloc.service.IEquipementService;
import tn.esprit.tpautoloc.web.dto.EquipementDTO;

import java.util.List;

@RestController
@RequestMapping("/equipements")
@RequiredArgsConstructor
@Tag(name = "Equipements", description = "Gestion des equipements")
public class EquipementController {

    private final IEquipementService equipementService;

    @Operation(summary = "Lister")
    @ApiResponse(responseCode = "200", description = "Liste")
    @GetMapping
    public ResponseEntity<List<EquipementDTO>> findAll() {
        return ResponseEntity.ok(equipementService.findAll());
    }

    @Operation(summary = "Consulter par identifiant")
    @ApiResponse(responseCode = "200", description = "Trouve")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @GetMapping("/{id}")
    public ResponseEntity<EquipementDTO> findById(
            @Parameter(description = "Identifiant") @PathVariable Long id) {
        return ResponseEntity.ok(equipementService.findById(id));
    }

    @Operation(summary = "Creer")
    @ApiResponse(responseCode = "201", description = "Cree")
    @ApiResponse(responseCode = "400", description = "Donnees invalides")
    @PostMapping
    public ResponseEntity<EquipementDTO> creer(@Valid @RequestBody EquipementDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(equipementService.creer(dto));
    }

    @Operation(summary = "Modifier")
    @ApiResponse(responseCode = "200", description = "Modifie")
    @ApiResponse(responseCode = "400", description = "Donnees invalides")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @PutMapping("/{id}")
    public ResponseEntity<EquipementDTO> update(
            @Parameter(description = "Identifiant") @PathVariable Long id,
            @Valid @RequestBody EquipementDTO dto) {
        return ResponseEntity.ok(equipementService.update(id, dto));
    }

    @Operation(summary = "Supprimer")
    @ApiResponse(responseCode = "204", description = "Supprime")
    @ApiResponse(responseCode = "404", description = "Introuvable")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identifiant") @PathVariable Long id) {
        equipementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
