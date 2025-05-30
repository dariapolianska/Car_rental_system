package com.university.car_rental.repository;

import com.university.car_rental.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    boolean existsByEmail(String email);
    UserEntity findByEmail(String email);
}