package tn.esprit.tpautoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.domain.Maintenance;
import tn.esprit.tpautoloc.service.IMaintenanceService;

import java.util.List;

@RestController
@RequestMapping("/maintenances")
@RequiredArgsConstructor
@Tag(name = "Maintenances", description = "Gestion des maintenances")
public class MaintenanceController {

    private final IMaintenanceService maintenanceService;

    @Operation(summary = "Lister les maintenances")
    @GetMapping
    public List<Maintenance> findAll() {
        return maintenanceService.findAll();
    }

    @Operation(summary = "Consulter une maintenance")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Maintenance trouvee"),
            @ApiResponse(responseCode = "404", description = "Maintenance introuvable")
    })
    @GetMapping("/{id}")
    public Maintenance findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return maintenanceService.findById(id);
    }

    @Operation(summary = "Creer une maintenance")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Maintenance create(@RequestBody Maintenance maintenance) {
        return maintenanceService.create(maintenance);
    }

    @Operation(summary = "Modifier une maintenance")
    @PutMapping("/{id}")
    public Maintenance update(@Parameter(description = "Identifiant") @PathVariable Long id,
                              @RequestBody Maintenance maintenance) {
        return maintenanceService.update(id, maintenance);
    }

    @Operation(summary = "Supprimer une maintenance")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "Identifiant") @PathVariable Long id) {
        maintenanceService.deleteById(id);
    }
}
