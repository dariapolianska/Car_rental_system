package com.university.car_rental.service;

import com.university.car_rental.domain.CarDTO;
import java.util.List;
import java.util.UUID;

public interface CarService {
    List<CarDTO> findAllAvailableCars();
    CarDTO createCar(CarDTO carDTO);
    CarDTO findCarById(UUID id);
    CarDTO updateCar(UUID id, CarDTO carDTO);
    void deleteCar(UUID id);
}
