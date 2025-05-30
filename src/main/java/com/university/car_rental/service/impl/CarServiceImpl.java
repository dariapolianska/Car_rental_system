package com.university.car_rental.service.impl;

import com.university.car_rental.domain.CarDTO;
import com.university.car_rental.entity.CarEntity;
import com.university.car_rental.repository.CarRepository;
import com.university.car_rental.service.CarService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    public List<CarDTO> findAllAvailableCars() {
        return carRepository.findByAvailableTrue().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CarDTO createCar(CarDTO carDTO) {
        CarEntity car = CarEntity.builder()
                .brand(carDTO.getBrand())
                .model(carDTO.getModel())
                .year(carDTO.getYear())
                .licensePlate(carDTO.getLicensePlate())
                .available(true)
                .build();
        return mapToDTO(carRepository.save(car));
    }

    @Override
    public CarDTO findCarById(UUID id) {
        CarEntity car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));
        return mapToDTO(car);
    }

    @Override
    public CarDTO updateCar(UUID id, CarDTO carDTO) {
        CarEntity car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found with id: " + id));

        car = car.toBuilder()
                .brand(carDTO.getBrand())
                .model(carDTO.getModel())
                .year(carDTO.getYear())
                .licensePlate(carDTO.getLicensePlate())
                .available(true)
                .build();
        return mapToDTO(carRepository.save(car));
    }

    @Override
    public void deleteCar(UUID id) {
        if (!carRepository.existsById(id)) {
            throw new RuntimeException("Car not found with id: " + id);
        }
        carRepository.deleteById(id);
    }

    private CarDTO mapToDTO(CarEntity car) {
        return CarDTO.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .year(car.getYear())
                .licensePlate(car.getLicensePlate())
                .build();
    }
}
