package com.university.car_rental.service;

import com.university.car_rental.domain.RentalDTO;
import java.util.List;
import java.util.UUID;

public interface RentalService {
    List<RentalDTO> findRentalsByUser(UUID userId);
    List<RentalDTO> findRentalsByCar(UUID carId);
    RentalDTO createRental(RentalDTO rentalDTO);
    RentalDTO completeRental(UUID id);
}
