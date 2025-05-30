package com.university.car_rental.service.impl;

import com.university.car_rental.domain.RentalDTO;
import com.university.car_rental.domain.enums.RentalStatus;
import com.university.car_rental.entity.RentalEntity;
import com.university.car_rental.entity.CarEntity;
import com.university.car_rental.entity.UserEntity;
import com.university.car_rental.repository.RentalRepository;
import com.university.car_rental.repository.CarRepository;
import com.university.car_rental.repository.UserRepository;
import com.university.car_rental.service.RentalService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    @Override
    public List<RentalDTO> findRentalsByUser(UUID userId) {
        return rentalRepository.findByUserId(userId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RentalDTO> findRentalsByCar(UUID carId) {
        return rentalRepository.findByCarId(carId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RentalDTO createRental(RentalDTO rentalDTO) {
        CarEntity car = carRepository.findById(rentalDTO.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));
        UserEntity user = userRepository.findById(rentalDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        RentalEntity rental = RentalEntity.builder()
                .car(car)
                .user(user)
                .startDate(rentalDTO.getStartDate())
                .endDate(rentalDTO.getEndDate())
                .status(RentalStatus.ACTIVE)
                .build();

        return mapToDTO(rentalRepository.save(rental));
    }

    @Override
    public RentalDTO completeRental(UUID id) {
        RentalEntity rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rental not found"));

        rental = rental.toBuilder()
                .status(RentalStatus.COMPLETED)
                .build();

        rentalRepository.save(rental);

        CarEntity car = rental.getCar();
        car = car.toBuilder().available(true).build();
        carRepository.save(car);

        return mapToDTO(rental);
    }


    private RentalDTO mapToDTO(RentalEntity rental) {
        return RentalDTO.builder()
                .id(rental.getId())
                .carId(rental.getCar().getId())
                .userId(rental.getUser().getId())
                .startDate(rental.getStartDate())
                .endDate(rental.getEndDate())
                .status(rental.getStatus())
                .build();
    }
}
