package com.university.car_rental.service;

import com.university.car_rental.domain.RentalDTO;
import com.university.car_rental.domain.enums.RentalStatus;
import com.university.car_rental.entity.CarEntity;
import com.university.car_rental.entity.RentalEntity;
import com.university.car_rental.entity.UserEntity;
import com.university.car_rental.repository.CarRepository;
import com.university.car_rental.repository.RentalRepository;
import com.university.car_rental.repository.UserRepository;
import com.university.car_rental.service.impl.RentalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RentalServiceImplTest {

    @Mock
    private RentalRepository rentalRepository;
    @Mock
    private CarRepository carRepository;
    @Mock
    private UserRepository userRepository;

    private RentalServiceImpl rentalService;
    private UUID rentalId, carId, userId;
    private RentalEntity rentalEntity;
    private RentalDTO rentalDTO;
    private CarEntity carEntity;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        rentalService = new RentalServiceImpl(rentalRepository, carRepository, userRepository);
        rentalId = UUID.randomUUID();
        carId = UUID.randomUUID();
        userId = UUID.randomUUID();

        carEntity = CarEntity.builder()
                .id(carId)
                .brand("Toyota")
                .model("Corolla")
                .year(2022)
                .licensePlate("ABC-123")
                .available(true)
                .build();

        userEntity = UserEntity.builder()
                .id(userId)
                .email("test@example.com")
                .username("testuser")
                .build();

        rentalEntity = RentalEntity.builder()
                .id(rentalId)
                .car(carEntity)
                .user(userEntity)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(3))
                .status(RentalStatus.ACTIVE)
                .build();

        rentalDTO = RentalDTO.builder()
                .carId(carId)
                .userId(userId)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(3))
                .build();
    }

    @Test
    void testFindRentalsByUser() {
        when(rentalRepository.findByUserId(userId)).thenReturn(List.of(rentalEntity));

        List<RentalDTO> result = rentalService.findRentalsByUser(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(carId, result.get(0).getCarId());
        verify(rentalRepository).findByUserId(userId);
    }

    @Test
    void testFindRentalsByCar() {
        when(rentalRepository.findByCarId(carId)).thenReturn(List.of(rentalEntity));

        List<RentalDTO> result = rentalService.findRentalsByCar(carId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userId, result.get(0).getUserId());
        verify(rentalRepository).findByCarId(carId);
    }

    @Test
    void testCreateRental() {
        when(carRepository.findById(carId)).thenReturn(Optional.of(carEntity));
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));
        when(rentalRepository.save(any(RentalEntity.class))).thenReturn(rentalEntity);

        RentalDTO result = rentalService.createRental(rentalDTO);

        assertNotNull(result);
        assertEquals(carId, result.getCarId());
        verify(rentalRepository).save(any(RentalEntity.class));
    }

    @Test
    void testCompleteRental() {
        when(rentalRepository.findById(rentalId)).thenReturn(Optional.of(rentalEntity));
        when(carRepository.save(any(CarEntity.class))).thenReturn(carEntity);
        when(rentalRepository.save(any(RentalEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        RentalDTO result = rentalService.completeRental(rentalId);

        assertNotNull(result);
        assertEquals(RentalStatus.COMPLETED, result.getStatus());
        verify(rentalRepository).save(any(RentalEntity.class));
    }
}
