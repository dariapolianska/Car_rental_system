package com.university.car_rental.repository;

import com.university.car_rental.entity.CarEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<CarEntity, UUID> {
    List<CarEntity> findByAvailableTrue();
    boolean existsByLicensePlate(String licensePlate);
}
