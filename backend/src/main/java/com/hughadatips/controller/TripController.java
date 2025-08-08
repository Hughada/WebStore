package com.hughadatips.controller;

import com.hughadatips.dto.TripDTO;
import com.hughadatips.entity.Trip;
import com.hughadatips.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public ResponseEntity<List<TripDTO>> getAllTrips() {
        List<TripDTO> trips = tripService.findAll();
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/available")
    public ResponseEntity<List<TripDTO>> getAvailableTrips() {
        List<TripDTO> trips = tripService.findAvailable();
        return ResponseEntity.ok(trips);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripDTO> getTripById(@PathVariable Long id) {
        TripDTO trip = tripService.findById(id);
        if (trip == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(trip);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TripDTO> createTrip(@Valid @RequestBody Trip trip) {
        Trip saved = tripService.createOrUpdate(trip);
        return ResponseEntity.ok(tripService.toDTO(saved));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TripDTO> updateTrip(@PathVariable Long id, @Valid @RequestBody Trip trip) {
        TripDTO existing = tripService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        trip.setId(id);
        Trip saved = tripService.createOrUpdate(trip);
        return ResponseEntity.ok(tripService.toDTO(saved));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/all")
    public ResponseEntity<List<TripDTO>> getAllForAdmin() {
        List<TripDTO> list = tripService.findAll();
        return ResponseEntity.ok(list);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        TripDTO existing = tripService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        tripService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public TripDTO create(
            @RequestPart("dto") TripDTO dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        return tripService.createFromDto(dto, images);
    }

    @PutMapping("/admin/{id}")
    public TripDTO update(
            @PathVariable Long id,
            @RequestPart("dto") TripDTO dto,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        return tripService.updateFromDto(id, dto, images);
    }

}
