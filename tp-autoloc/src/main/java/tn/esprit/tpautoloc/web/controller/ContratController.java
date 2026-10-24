
package tn.esprit.tpautoloc.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.domain.Contrat;
import tn.esprit.tpautoloc.service.IContratService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
@RestController
@RequestMapping("/contrats")
@RequiredArgsConstructor
@Tag(name = "Contrats", description = "Gestion des contrats de location")
public class ContratController {

    private final IContratService contratService;

    @Operation(summary = "Lister les contrats")
    @ApiResponse(responseCode = "200", description = "Liste des contrats")
    @GetMapping
    public List<Contrat> findAll() {
        return contratService.findAll();
    }

    @Operation(summary = "Consulter un contrat")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Contrat trouve"),
            @ApiResponse(responseCode = "404", description = "Contrat introuvable")
    })
    @GetMapping("/{id}")
    public Contrat findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return contratService.findById(id);
    }

    @Operation(summary = "Creer un contrat")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Contrat cree"),
            @ApiResponse(responseCode = "400", description = "Donnees invalides")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Contrat create(@RequestBody Contrat contrat) {
        return contratService.create(contrat);
    }
}