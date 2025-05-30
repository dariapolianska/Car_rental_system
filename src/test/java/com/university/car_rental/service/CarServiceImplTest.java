package com.university.car_rental.service;

import com.university.car_rental.domain.CarDTO;
import com.university.car_rental.entity.CarEntity;
import com.university.car_rental.repository.CarRepository;
import com.university.car_rental.service.impl.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTest {

    @Mock
    private CarRepository carRepository;

    private CarServiceImpl carService;
    private UUID carId;
    private CarEntity carEntity;
    private CarDTO carDTO;

    @BeforeEach
    void setUp() {
        carService = new CarServiceImpl(carRepository);
        carId = UUID.randomUUID();

        carEntity = CarEntity.builder()
                .id(carId)
                .brand("Toyota")
                .model("Corolla")
                .year(2022)
                .licensePlate("ABC-123")
                .available(true)
                .build();

        carDTO = CarDTO.builder()
                .brand("Toyota")
                .model("Corolla")
                .year(2022)
                .licensePlate("ABC-123")
                .build();
    }

    @Test
    void testFindAllAvailableCars() {
        when(carRepository.findByAvailableTrue()).thenReturn(List.of(carEntity));

        List<CarDTO> result = carService.findAllAvailableCars();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Toyota", result.get(0).getBrand());
        verify(carRepository).findByAvailableTrue();
    }

    @Test
    void testCreateCar() {
        when(carRepository.save(any(CarEntity.class))).thenReturn(carEntity);

        CarDTO result = carService.createCar(carDTO);

        assertNotNull(result);
        assertEquals("Toyota", result.getBrand());
        verify(carRepository).save(any(CarEntity.class));
    }

    @Test
    void testFindCarById() {
        when(carRepository.findById(carId)).thenReturn(Optional.of(carEntity));

        CarDTO result = carService.findCarById(carId);

        assertNotNull(result);
        assertEquals(carId, result.getId());
        verify(carRepository).findById(carId);
    }

    @Test
    void testFindCarByIdNotFound() {
        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> carService.findCarById(carId));
    }

    @Test
    void testUpdateCar() {
        when(carRepository.findById(carId)).thenReturn(Optional.of(carEntity));
        when(carRepository.save(any(CarEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CarDTO updatedDTO = CarDTO.builder()
                .brand("Honda")
                .model("Civic")
                .year(2023)
                .licensePlate("XYZ-789")
                .build();

        CarDTO result = carService.updateCar(carId, updatedDTO);

        assertNotNull(result);
        assertEquals("Honda", result.getBrand());
        verify(carRepository).save(any(CarEntity.class));
    }

    @Test
    void testDeleteCar() {
        when(carRepository.existsById(carId)).thenReturn(true);

        carService.deleteCar(carId);

        verify(carRepository).deleteById(carId);
    }

    @Test
    void testDeleteCarNotFound() {
        when(carRepository.existsById(carId)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> carService.deleteCar(carId));
    }
}
