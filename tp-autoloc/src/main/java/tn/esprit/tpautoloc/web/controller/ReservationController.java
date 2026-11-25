package tn.esprit.tpautoloc.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import tn.esprit.tpautoloc.service.IReservationService;
import tn.esprit.tpautoloc.web.dto.ReservationRequestDTO;
import tn.esprit.tpautoloc.web.dto.ReservationResponseDTO;
import tn.esprit.tpautoloc.web.validator.ReservationRequestValidator;

import java.util.List;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
@Tag(name = "Reservations", description = "Gestion des reservations")
public class ReservationController {

    private final IReservationService reservationService;
    private final ReservationRequestValidator reservationRequestValidator;

    @Operation(summary = "Lister les reservations")
    @ApiResponse(responseCode = "200", description = "Liste des reservations")
    @GetMapping
    public ResponseEntity<List<ReservationResponseDTO>> findAll() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @Operation(summary = "Consulter une reservation")
    @ApiResponse(responseCode = "200", description = "Reservation trouvee")
    @ApiResponse(responseCode = "404", description = "Reservation introuvable")
    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> findById(
            @Parameter(description = "Identifiant") @PathVariable Long id) {
        return ResponseEntity.ok(reservationService.findById(id));
    }

    @Operation(summary = "Creer une reservation")
    @ApiResponse(responseCode = "201", description = "Reservation creee")
    @ApiResponse(responseCode = "400", description = "Donnees invalides")
    @PostMapping
    public ResponseEntity<Object> creer(
            @Valid @RequestBody ReservationRequestDTO dto,
            BindingResult result) {

        reservationRequestValidator.validate(dto, result);

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(
                    result.getFieldErrors().stream()
                            .map(error -> error.getField() + " : " + error.getDefaultMessage())
                            .toList());
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationService.creer(dto));
    }

    @Operation(summary = "Modifier une reservation")
    @ApiResponse(responseCode = "200", description = "Reservation modifiee")
    @ApiResponse(responseCode = "400", description = "Donnees invalides")
    @ApiResponse(responseCode = "404", description = "Reservation introuvable")
    @PutMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> update(
            @Parameter(description = "Identifiant") @PathVariable Long id,
            @Valid @RequestBody ReservationRequestDTO dto) {
        return ResponseEntity.ok(reservationService.update(id, dto));
    }

    @Operation(summary = "Annuler une reservation")
    @ApiResponse(responseCode = "200", description = "Reservation annulee")
    @ApiResponse(responseCode = "404", description = "Reservation introuvable")
    @PatchMapping("/{id}/annuler")
    public ResponseEntity<ReservationResponseDTO> annuler(
            @Parameter(description = "Identifiant") @PathVariable Long id) {
        return ResponseEntity.ok(reservationService.annulerReservation(id));
    }

    @Operation(summary = "Supprimer une reservation")
    @ApiResponse(responseCode = "204", description = "Reservation supprimee")
    @ApiResponse(responseCode = "404", description = "Reservation introuvable")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Identifiant") @PathVariable Long id) {
        reservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}