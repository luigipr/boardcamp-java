package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.exceptions.NotFoundException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentalRepository;
import com.boardcamp.api.services.RentalService;

@SpringBootTest
class RentalUnitTests {
    
    @InjectMocks
	private RentalService rentalService;

	@Mock
	private RentalRepository rentalRepository;

	@Mock
	private CustomerRepository customerRepository;

    @Mock
	private GameRepository gameRepository;


    @Test
    void givenWrongCustomerId_whenCreatingRental_thenThrowsError() {
        // given
        RentalDTO rentalDto = new RentalDTO(1L, 1L, 1);
    
        doReturn(Optional.empty()).when(customerRepository).findById(any());
        // when
        NotFoundException exception = assertThrows(NotFoundException.class, () -> rentalService.save(rentalDto));

        // then
        assertEquals("There is no customer with this id!", exception.getMessage());
        verify(rentalRepository, times(0)).save(any());
        verify(customerRepository, times(1)).findById(any());
    }

    @Test
    void givenWrongGameId_whenCreatingRental_thenThrowsError() {
        // given
        RentalDTO rentalDto = new RentalDTO(1L, 1L, 1);
        CustomerModel customer = new CustomerModel();
        doReturn(Optional.of(customer)).when(customerRepository).findById(any());
        doReturn(Optional.empty()).when(gameRepository).findById(any());
        // when
        NotFoundException exception = assertThrows(NotFoundException.class, () -> rentalService.save(rentalDto));

        // then
        assertEquals("There is no game with this id!", exception.getMessage());
        verify(rentalRepository, times(0)).save(any());
        verify(gameRepository, times(1)).findById(any());
        verify(customerRepository, times(1)).findById(any());
    }

    @Test
    void givenCorrectRental_whenCreatingRental_thenCreatesRental() {
        // given
        RentalDTO rentalDto = new RentalDTO(1L, 1L, 1);
        CustomerModel customer = new CustomerModel();
        GameModel game = new GameModel(1L, "2", "3",4,5);
        RentalModel newRental = new RentalModel();
        doReturn(Optional.of(customer)).when(customerRepository).findById(any());
        doReturn(Optional.of(game)).when(gameRepository).findById(any());
        doReturn(newRental).when(rentalRepository).save(any());
        
        // when
        RentalModel result = rentalService.save(rentalDto);

        // then
        assertNotNull(result);
        verify(rentalRepository, times(1)).save(any());
        verify(gameRepository, times(1)).findById(any());
        verify(customerRepository, times(1)).findById(any());
    }
}