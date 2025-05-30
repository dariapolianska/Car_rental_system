package com.university.car_rental.repository;

import com.university.car_rental.entity.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RentalRepository extends JpaRepository<RentalEntity, UUID> {
    List<RentalEntity> findByUserId(UUID userId);
    List<RentalEntity> findByCarId(UUID carId);
}
