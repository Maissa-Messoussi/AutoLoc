package tn.esprit.tpautoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.domain.Vehicule;
import tn.esprit.tpautoloc.service.IVehiculeService;

import java.util.List;

@RestController
@RequestMapping("/vehicules")
@RequiredArgsConstructor
@Tag(name = "Vehicules", description = "Gestion des vehicules")
public class VehiculeController {

    private final IVehiculeService vehiculeService;

    @Operation(summary = "Lister les vehicules")
    @GetMapping
    public List<Vehicule> findAll() {
        return vehiculeService.findAll();
    }

    @Operation(summary = "Consulter un vehicule")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Vehicule trouve"),
            @ApiResponse(responseCode = "404", description = "Vehicule introuvable")
    })
    @GetMapping("/{id}")
    public Vehicule findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return vehiculeService.findById(id);
    }

    @Operation(summary = "Creer un vehicule")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Vehicule create(@RequestBody Vehicule vehicule) {
        return vehiculeService.create(vehicule);
    }

    @Operation(summary = "Modifier un vehicule")
    @PutMapping("/{id}")
    public Vehicule update(@Parameter(description = "Identifiant") @PathVariable Long id,
                           @RequestBody Vehicule vehicule) {
        return vehiculeService.update(id, vehicule);
    }

    @Operation(summary = "Supprimer un vehicule")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "Identifiant") @PathVariable Long id) {
        vehiculeService.deleteById(id);
    }
}
