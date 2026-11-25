package tn.esprit.tpautoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.web.dto.AgenceDTO;
import tn.esprit.tpautoloc.service.IAgenceService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/agences")
@RequiredArgsConstructor
@Tag(name = "Agences", description = "Gestion des agences")
public class AgenceController {

    private final IAgenceService agenceService;

    @Operation(summary = "Lister les agences")
    @ApiResponse(responseCode = "200", description = "Liste des agences")
    @GetMapping
    public ResponseEntity<List<AgenceDTO>> findAll() {
        return ResponseEntity.ok(agenceService.findAll());
    }

    @Operation(summary = "Consulter une agence")
    @ApiResponse(responseCode = "200", description = "Agence trouvee")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @GetMapping("/{id}")
    public ResponseEntity<AgenceDTO> findById(
            @Parameter(description = "Identifiant") @PathVariable Long id) {
        return ResponseEntity.ok(agenceService.findById(id));
    }

    @Operation(summary = "Creer une agence")
    @ApiResponse(responseCode = "201", description = "Agence creee")
    @ApiResponse(responseCode = "400", description = "Donnees invalides")
    @PostMapping
    public ResponseEntity<AgenceDTO> creer(@Valid @RequestBody AgenceDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(agenceService.creer(dto));
    }

    @Operation(summary = "Modifier une agence")
    @ApiResponse(responseCode = "200", description = "Agence modifiee")
    @ApiResponse(responseCode = "400", description = "Donnees invalides")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @PutMapping("/{id}")
    public ResponseEntity<AgenceDTO> update(
            @Parameter(description = "Identifiant") @PathVariable Long id,
            @Valid @RequestBody AgenceDTO dto) {
        return ResponseEntity.ok(agenceService.update(id, dto));
    }

    @Operation(summary = "Supprimer une agence")
    @ApiResponse(responseCode = "204", description = "Agence supprimee")
    @ApiResponse(responseCode = "404", description = "Agence introuvable")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identifiant") @PathVariable Long id) {
        agenceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}