package com.university.car_rental.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class CarDTO {
    UUID id;

    @NotNull(message = "Brand must be provided")
    String brand;

    @NotNull(message = "Model must be provided")
    String model;

    @NotNull(message = "Year must be provided")
    int year;

    @NotNull(message = "License plate must be provided")
    String licensePlate;
}
