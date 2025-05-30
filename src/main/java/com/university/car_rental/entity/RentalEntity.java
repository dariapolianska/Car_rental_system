package com.university.car_rental.entity;

import com.university.car_rental.domain.enums.RentalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "rentals")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class RentalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private CarEntity car;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private RentalStatus status;
}
