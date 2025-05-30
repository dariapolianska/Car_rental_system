package com.university.car_rental.controller;

import com.university.car_rental.domain.RentalDTO;
import com.university.car_rental.service.RentalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rentals")
@RequiredArgsConstructor
@Tag(name = "Rentals", description = "APIs for managing car rentals")
public class RentalController {

    private final RentalService rentalService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get rentals by user")
    public ResponseEntity<List<RentalDTO>> getRentalsByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(rentalService.findRentalsByUser(userId));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/car/{carId}")
    @Operation(summary = "Get rentals by car")
    public ResponseEntity<List<RentalDTO>> getRentalsByCar(@PathVariable UUID carId) {
        return ResponseEntity.ok(rentalService.findRentalsByCar(carId));
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Create a new rental")
    public ResponseEntity<RentalDTO> createRental(@RequestBody RentalDTO rentalDTO) {
        return new ResponseEntity<>(rentalService.createRental(rentalDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{id}/complete")
    @Operation(summary = "Complete a rental")
    public ResponseEntity<RentalDTO> completeRental(@PathVariable UUID id) {
        return ResponseEntity.ok(rentalService.completeRental(id));
    }
}
