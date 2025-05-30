package com.university.car_rental.IT;

import com.university.car_rental.config.TestDataInitializer;
import com.university.car_rental.domain.RentalDTO;
import com.university.car_rental.domain.enums.RentalStatus;
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
public class RentalControllerIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private TestDataInitializer dataInitializer;

    private String clientToken;
    private UUID rentalId, userId, carId;

    @BeforeEach
    void setup() {
        TestDataInitializer.TestData testData = dataInitializer.initTestData();
        rentalId = testData.rentalId();
        carId = testData.vehicleId();
        userId = testData.clientId();
        clientToken = testData.clientToken();
    }

    @Test
    void shouldCreateRental() {
        RentalDTO newRental = RentalDTO.builder()
                .carId(carId)
                .userId(userId)
                .startDate(java.time.LocalDateTime.now())
                .endDate(java.time.LocalDateTime.now().plusDays(5))
                .status(RentalStatus.ACTIVE)
                .build();

        webTestClient.post()
                .uri("/api/v1/rentals")
                .header("Authorization", "Bearer " + clientToken)
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .bodyValue(newRental)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(RentalDTO.class)
                .consumeWith(response -> {
                    RentalDTO rental = response.getResponseBody();
                    assertNotNull(rental);
                    assertEquals(userId, rental.getUserId());
                });
    }

    @Test
    void shouldGetRentalsByUser() {
        webTestClient.get()
                .uri("/api/v1/rentals/user/{userId}", userId)
                .header("Authorization", "Bearer " + clientToken)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(RentalDTO.class)
                .consumeWith(response -> {
                    assertNotNull(response.getResponseBody());
                    assertEquals(1, response.getResponseBody().size());
                    assertEquals(carId, response.getResponseBody().get(0).getCarId());
                });
    }

    @Test
    void shouldCompleteRental() {
        webTestClient.put()
                .uri("/api/v1/rentals/{id}/complete", rentalId)
                .header("Authorization", "Bearer " + clientToken)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RentalDTO.class)
                .consumeWith(response -> {
                    RentalDTO rental = response.getResponseBody();
                    assertNotNull(rental);
                    assertEquals(RentalStatus.COMPLETED, rental.getStatus());
                });
    }
}
