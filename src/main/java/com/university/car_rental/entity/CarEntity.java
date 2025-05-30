package com.university.car_rental.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Entity
@Table(name = "cars")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String brand;

    @Column(nullable = false, length = 100)
    private String model;

    @Column(name = "car_year", nullable = false)    private int year;

    @Column(nullable = false, unique = true, length = 20)
    private String licensePlate;

    @Column(nullable = false)
    private boolean available;
}
