package com.university.car_rental.IT;

import com.university.car_rental.config.TestDataInitializer;
import com.university.car_rental.domain.CarDTO;
import com.university.car_rental.repository.RentalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TestDataInitializer dataInitializer;

    @Autowired
    private RentalRepository rentalRepository;

    private String adminToken;
    private UUID carId;

    @BeforeEach
    void setup() {
        TestDataInitializer.TestData testData = dataInitializer.initTestData();
        carId = testData.vehicleId();
        adminToken = testData.adminToken();
    }

    @Test
    void shouldGetAvailableCars() {
        webTestClient.get()
                .uri("/api/v1/cars/available")
                .header("Authorization", "Bearer " + adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CarDTO.class)
                .consumeWith(response -> {
                    assertNotNull(response.getResponseBody());
                    assertEquals(1, response.getResponseBody().size());
                    assertEquals("Toyota", response.getResponseBody().get(0).getBrand());
                });
    }

    @Test
    void shouldCreateCar() {
        CarDTO newCar = CarDTO.builder()
                .brand("Honda")
                .model("Civic")
                .year(2023)
                .licensePlate("XYZ-789")
                .build();

        webTestClient.post()
                .uri("/api/v1/cars")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .bodyValue(newCar)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(CarDTO.class)
                .consumeWith(response -> {
                    CarDTO car = response.getResponseBody();
                    assertNotNull(car);
                    assertEquals("Honda", car.getBrand());
                });
    }

    @Test
    void shouldGetCarById() {
        webTestClient.get()
                .uri("/api/v1/cars/{id}", carId)
                .header("Authorization", "Bearer " + adminToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(CarDTO.class)
                .consumeWith(response -> {
                    CarDTO car = response.getResponseBody();
                    assertNotNull(car);
                    assertEquals("Toyota", car.getBrand());
                });
    }

    @Test
    void shouldDeleteCar() {
        rentalRepository.deleteAll();

        webTestClient.delete()
                .uri("/api/v1/cars/{id}", carId)
                .header("Authorization", "Bearer " + adminToken)
                .exchange()
                .expectStatus().isNoContent();
    }
}
