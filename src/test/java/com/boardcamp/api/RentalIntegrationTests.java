package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentalRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RentalIntegrationTests {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
	private RentalRepository rentalRepository;

	@Autowired
	private CustomerRepository customerRepository;

    @Autowired
	private GameRepository gameRepository;

    @BeforeEach
    @AfterEach
    void cleanDB() {
        rentalRepository.deleteAll();
        customerRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    void givenWrongCustomerId_whenCreatingRental_thenThrowsError() {
        //given
        CustomerModel customer = new CustomerModel(null, "test", "11111111111");
        CustomerModel createdCustomer = customerRepository.save(customer);
        customerRepository.deleteById(createdCustomer.getId());
        
        GameModel game = new GameModel(null, "name", "image", 3, 3000);
        GameModel createdGame = gameRepository.save(game);

        RentalDTO rental = new RentalDTO(createdCustomer.getId(), createdGame.getId(), 3);
        HttpEntity<RentalDTO> body = new HttpEntity<>(rental);

        //when
        ResponseEntity<String> response = restTemplate.exchange("/rentals", HttpMethod.POST, body, String.class);

        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(0, rentalRepository.count());
    }

    @Test
    void givenWrongGameId_whenCreatingRental_thenThrowsError() {
        //given
        CustomerModel customer = new CustomerModel(null, "test", "11111111111");
        CustomerModel createdCustomer = customerRepository.save(customer);
        
        GameModel game = new GameModel(null, "name", "image", 3, 3000);
        GameModel createdGame = gameRepository.save(game);
        gameRepository.deleteById(createdGame.getId());

        RentalDTO rental = new RentalDTO(createdCustomer.getId(), createdGame.getId(), 3);
        HttpEntity<RentalDTO> body = new HttpEntity<>(rental);

        //when
        ResponseEntity<String> response = restTemplate.exchange("/rentals", HttpMethod.POST, body, String.class);

        //then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(0, rentalRepository.count());
    }

    @Test
    void givenCorrectIds_whenCreatingRental_thenThrowsError() {
        //given
        CustomerModel customer = new CustomerModel(null, "test", "11111111111");
        CustomerModel createdCustomer = customerRepository.save(customer);
        
        GameModel game = new GameModel(null, "name", "image", 3, 3000);
        GameModel createdGame = gameRepository.save(game);

        RentalDTO rental = new RentalDTO(createdCustomer.getId(), createdGame.getId(), 3);
        HttpEntity<RentalDTO> body = new HttpEntity<>(rental);

        //when
        ResponseEntity<String> response = restTemplate.exchange("/rentals", HttpMethod.POST, body, String.class);

        //then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, rentalRepository.count());
    }
}
