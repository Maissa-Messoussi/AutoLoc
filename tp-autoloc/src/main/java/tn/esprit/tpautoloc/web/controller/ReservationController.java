package tn.esprit.tpautoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.domain.Reservation;
import tn.esprit.tpautoloc.service.IReservationService;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservations", description = "Gestion des reservations")
public class ReservationController {

    private final IReservationService reservationService;

    @Operation(summary = "Lister les reservations")
    @GetMapping
    public List<Reservation> findAll() {
        return reservationService.findAll();
    }

    @Operation(summary = "Consulter une reservation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reservation trouvee"),
            @ApiResponse(responseCode = "404", description = "Reservation introuvable")
    })
    @GetMapping("/{id}")
    public Reservation findById(@Parameter(description = "Identifiant") @PathVariable Long id) {
        return reservationService.findById(id);
    }

    @Operation(summary = "Creer une reservation")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reservation create(@RequestBody Reservation reservation) {
        return reservationService.create(reservation);
    }

    @Operation(summary = "Modifier une reservation")
    @PutMapping("/{id}")
    public Reservation update(@Parameter(description = "Identifiant") @PathVariable Long id,
                              @RequestBody Reservation reservation) {
        return reservationService.update(id, reservation);
    }

    @Operation(summary = "Supprimer une reservation")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Parameter(description = "Identifiant") @PathVariable Long id) {
        reservationService.deleteById(id);
    }
}
