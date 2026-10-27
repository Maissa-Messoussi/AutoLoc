package tn.esprit.tpautoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.domain.Employe;
import tn.esprit.tpautoloc.service.IEmployeService;

import java.util.List;

@RestController
@RequestMapping("/employes")
@RequiredArgsConstructor
@Tag(name = "Employes", description = "Gestion des employes")
public class EmployeController {

    private final IEmployeService employeService;

    @Operation(summary = "Lister les employes")
    @GetMapping
    public List<Employe> findAll() {
        return employeService.findAll();
    }

    @Operation(summary = "Consulter un employe")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Employe trouve"),
            @ApiResponse(responseCode = "404", description = "Employe introuvable")
    })
    @GetMapping("/{id}")
    public Employe findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return employeService.findById(id);
    }

    @Operation(summary = "Creer un employe")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employe create(@RequestBody Employe employe) {
        return employeService.create(employe);
    }

    @Operation(summary = "Modifier un employe")
    @PutMapping("/{id}")
    public Employe update(@Parameter(description = "Identifiant") @PathVariable Long id,
                          @RequestBody Employe employe) {
        return employeService.update(id, employe);
    }

    @Operation(summary = "Supprimer un employe")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "Identifiant") @PathVariable Long id) {
        employeService.deleteById(id);
    }
}
