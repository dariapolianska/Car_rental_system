package com.university.car_rental.domain;

import com.university.car_rental.domain.enums.RentalStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.UUID;

@Value
@Builder(toBuilder = true)
public class RentalDTO {
    UUID id;

    @NotNull(message = "Car ID must be provided")
    UUID carId;

    @NotNull(message = "User ID must be provided")
    UUID userId;

    @NotNull(message = "Start date must be provided")
    LocalDateTime startDate;

    @NotNull(message = "End date must be provided")
    LocalDateTime endDate;

    @NotNull(message = "Rental status must be provided")
    RentalStatus status;
}
