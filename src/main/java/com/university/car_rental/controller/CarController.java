package com.university.car_rental.controller;

import com.university.car_rental.domain.CarDTO;
import com.university.car_rental.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
@Tag(name = "Cars", description = "APIs for managing cars")
public class CarController {

    private final CarService carService;

    @GetMapping("/available")
    @Operation(summary = "Get all available cars")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CarDTO>> getAllAvailableCars() {
        return ResponseEntity.ok(carService.findAllAvailableCars());
    }

    @PostMapping
    @Operation(summary = "Create a new car")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarDTO> createCar(@RequestBody CarDTO carDTO) {
        return new ResponseEntity<>(carService.createCar(carDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get car by ID")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarDTO> getCarById(@PathVariable UUID id) {
        return ResponseEntity.ok(carService.findCarById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update car")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CarDTO> updateCar(@PathVariable UUID id, @RequestBody CarDTO carDTO) {
        return ResponseEntity.ok(carService.updateCar(id, carDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete car")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCar(@PathVariable UUID id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
