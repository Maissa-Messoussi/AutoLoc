package tn.esprit.tpautoloc.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tn.esprit.tpautoloc.web.dto.PaiementDTO;
import tn.esprit.tpautoloc.service.IPaiementService;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
@Tag(name = "Paiements", description = "Consultation des paiements")
public class PaiementController {

    private final IPaiementService paiementService;

    @Operation(summary = "Lister les paiements")
    @ApiResponse(responseCode = "200", description = "Liste des paiements")
    @GetMapping
    public ResponseEntity<List<PaiementDTO>> findAll() {
        return ResponseEntity.ok(paiementService.findAll());
    }

    @Operation(summary = "Creer un paiement")
    @ApiResponse(responseCode = "201", description = "Paiement cree")
    @ApiResponse(responseCode = "400", description = "Donnees invalides")
    @PostMapping
    public ResponseEntity<PaiementDTO> creer(@Valid @RequestBody PaiementDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paiementService.creer(dto));
    }

    @Operation(summary = "Consulter un paiement")
    @ApiResponse(responseCode = "200", description = "Paiement trouve")
    @ApiResponse(responseCode = "404", description = "Paiement introuvable")
    @GetMapping("/{id}")
    public ResponseEntity<PaiementDTO> findById(
            @Parameter(description = "Identifiant") @PathVariable Long id) {
        return ResponseEntity.ok(paiementService.findById(id));
    }

    @Operation(summary = "Modifier un paiement")
    @ApiResponse(responseCode = "200", description = "Paiement modifie")
    @ApiResponse(responseCode = "400", description = "Donnees invalides")
    @ApiResponse(responseCode = "404", description = "Paiement introuvable")
    @PutMapping("/{id}")
    public ResponseEntity<PaiementDTO> update(
            @Parameter(description = "Identifiant") @PathVariable Long id,
            @Valid @RequestBody PaiementDTO dto) {
        return ResponseEntity.ok(paiementService.update(id, dto));
    }

    @Operation(summary = "Supprimer un paiement")
    @ApiResponse(responseCode = "204", description = "Paiement supprime")
    @ApiResponse(responseCode = "404", description = "Paiement introuvable")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identifiant") @PathVariable Long id) {
        paiementService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}