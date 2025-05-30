package com.university.car_rental.config;

import com.university.car_rental.domain.enums.RentalStatus;
import com.university.car_rental.domain.enums.Role;
import com.university.car_rental.entity.CarEntity;
import com.university.car_rental.entity.RentalEntity;
import com.university.car_rental.entity.UserEntity;
import com.university.car_rental.repository.CarRepository;
import com.university.car_rental.repository.RentalRepository;
import com.university.car_rental.repository.UserRepository;
import com.university.car_rental.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TestDataInitializer {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final RentalRepository rentalRepository;

    public TestData initTestData() {
        rentalRepository.deleteAll();
        carRepository.deleteAll();
        userRepository.deleteAll();

        UserEntity adminUser = userRepository.save(UserEntity.builder()
                .email("admin@gmail.com")
                .password("securepassword")
                .username("Admin")
                .role(Role.ADMIN)
                .createdAt(LocalDateTime.now())
                .build());

        String adminToken = jwtProvider.createToken(adminUser.getEmail(), adminUser.getRole());

        UserEntity clientUser = userRepository.save(UserEntity.builder()
                .email("client@gmail.com")
                .password("securepassword")
                .username("ClientUser")
                .role(Role.USER)
                .createdAt(LocalDateTime.now())
                .build());

        String clientToken = jwtProvider.createToken(clientUser.getEmail(), clientUser.getRole());

        CarEntity vehicle = carRepository.save(CarEntity.builder()
                .model("Corolla")
                .brand("Toyota")
                .year(2020)
                .licensePlate("ABC-1234")
                .available(true)
                .build());

        RentalEntity rental = rentalRepository.save(RentalEntity.builder()
                .user(clientUser)
                .car(vehicle)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(3))
                .status(RentalStatus.ACTIVE)
                .build());


        return new TestData(adminUser.getId(), clientUser.getId(), vehicle.getId(), rental.getId(), adminToken, clientToken);
    }

    public record TestData(UUID adminId, UUID clientId, UUID vehicleId, UUID rentalId, String adminToken, String clientToken) {}
}
