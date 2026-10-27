package tn.esprit.tpautoloc.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import tn.esprit.tpautoloc.domain.Paiement;
import tn.esprit.tpautoloc.service.IPaiementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/paiements")
@RequiredArgsConstructor
@Tag(name = "Paiements", description = "Consultation des paiements")
public class PaiementController {

    private final IPaiementService paiementService;

    @Operation(summary = "Lister les paiements")
    @ApiResponse(responseCode = "200", description = "Liste des paiements")
    @GetMapping
    public List<Paiement> findAll() {
        return paiementService.findAll();
    }

    @Operation(summary = "Creer un paiement")
    @ApiResponse(responseCode = "201", description = "Paiement cree")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Paiement create(@RequestBody Paiement paiement) {
        return paiementService.create(paiement);
    }

    @Operation(summary = "Consulter un paiement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paiement trouve"),
            @ApiResponse(responseCode = "404", description = "Paiement introuvable")
    })
    @GetMapping("/{id}")
    public Paiement findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return paiementService.findById(id);
    }

    @Operation(summary = "Modifier un paiement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paiement modifie"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides"),
            @ApiResponse(responseCode = "404", description = "Paiement introuvable")
    })
    @PutMapping("/{id}")
    public Paiement update(@Parameter(description = "Identifiant") @PathVariable Long id,
                           @RequestBody Paiement paiement) {
        return paiementService.update(id, paiement);
    }

    @Operation(summary = "Supprimer un paiement")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Paiement supprime"),
            @ApiResponse(responseCode = "404", description = "Paiement introuvable")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "Identifiant") @PathVariable Long id) {
        paiementService.deleteById(id);
    }
}