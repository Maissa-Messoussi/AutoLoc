package tn.esprit.tpautoloc.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "Consulter un paiement")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Paiement trouve"),
            @ApiResponse(responseCode = "404", description = "Paiement introuvable")
    })
    @GetMapping("/{id}")
    public Paiement findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return paiementService.findById(id);
    }
}