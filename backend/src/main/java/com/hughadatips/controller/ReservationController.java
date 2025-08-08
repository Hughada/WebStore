package com.hughadatips.controller;

import com.hughadatips.dto.ReservationDTO;
import com.hughadatips.entity.Reservation;
import com.hughadatips.service.ReservationService;
import com.hughadatips.service.UserService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;

    public ReservationController(ReservationService reservationService, UserService userService) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<ReservationDTO> createReservation(
            @AuthenticationPrincipal UserDetails currentUser,
            @Valid @RequestBody CreateReservationRequest request) {
        Long userId = extractUserId(currentUser.getUsername());
        return ResponseEntity.ok(
                reservationService.create(userId, request.getTripId(), request.getNbPlaces())
        );
    }

    @GetMapping("/me")
    public ResponseEntity<List<ReservationDTO>> getMyReservations(@AuthenticationPrincipal UserDetails currentUser) {
        Long userId = extractUserId(currentUser.getUsername());
        List<ReservationDTO> list = reservationService.findByUserId(userId);
        return ResponseEntity.ok(list);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(
            @PathVariable Long id,
            @RequestParam int nbPlaces,
            @AuthenticationPrincipal UserDetails currentUser) {

        Long userId = extractUserId(currentUser.getUsername());
        ReservationDTO dto = reservationService.updateReservation(id, userId, nbPlaces);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<ReservationDTO> cancelReservation(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails currentUser) {

        Long userId = extractUserId(currentUser.getUsername());
        ReservationDTO dto = reservationService.cancelReservation(id, userId);
        return ResponseEntity.ok(dto);
    }


    @PostMapping("/{id}/status")
    public ResponseEntity<ReservationDTO> changeStatus(@PathVariable Long id,
                                                       @RequestParam Reservation.Statut statut) {
        ReservationDTO dto = reservationService.changeStatus(id, statut);
        return ResponseEntity.ok(dto);
    }

    private Long extractUserId(String email) {
        return userService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"))
                .getId();
    }


    /* ADMIN – liste toutes les réservations */
    @GetMapping("/admin/all")
    public ResponseEntity<List<ReservationDTO>> getAllReservations(@AuthenticationPrincipal UserDetails currentUser) {
        // Vérif rapide que c’est bien un ADMIN
        if (!currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            throw new SecurityException("Accès refusé");
        }
        return ResponseEntity.ok(reservationService.findAll());
    }

    /* ADMIN – confirmer une réservation */
    @PostMapping("/admin/{id}/confirm")
    public ResponseEntity<ReservationDTO> confirmReservation(@PathVariable Long id) {
        ReservationDTO dto = reservationService.updateStatus(id, Reservation.Statut.CONFIRMEE);
        return ResponseEntity.ok(dto);
    }

    /* ADMIN – rejeter une réservation */
    @PostMapping("/admin/{id}/reject")
    public ResponseEntity<ReservationDTO> rejectReservation(@PathVariable Long id) {
        ReservationDTO dto = reservationService.updateStatus(id, Reservation.Statut.ANNULEE);
        return ResponseEntity.ok(dto);
    }

    @Data
    public static class CreateReservationRequest {
        private Long tripId;
        private int nbPlaces;
    }
}
