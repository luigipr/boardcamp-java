package com.boardcamp.api.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.exceptions.BadRequestException;
import com.boardcamp.api.exceptions.NotFoundException;
import com.boardcamp.api.exceptions.UnprocessableEntityException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentalRepository;

@Service
public class RentalService {
    
    final RentalRepository rentalRepository;
    final CustomerRepository customerRepository;
    final GameRepository gameRepository;

    RentalService(RentalRepository rentalRepository, CustomerRepository customerRepository, GameRepository gameRepository) {
        this.rentalRepository = rentalRepository;
        this.customerRepository = customerRepository;
        this.gameRepository = gameRepository;
    }

    public List<RentalModel> findAll() {
        return rentalRepository.findAll();
    }

    public RentalModel save(RentalDTO dto) {

        int daysRented = dto.getDaysRented();
        if(daysRented <= 0) {
            throw new BadRequestException("You can't rent a game for none or less than one day!");
        }

        CustomerModel customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(
                () -> new NotFoundException("There is no customer with this id!"));

        GameModel game = gameRepository.findById(dto.getGameId()).orElseThrow(
                () -> new NotFoundException("There is no game with this id!"));

        int stock = game.getStockTotal();
        if (stock <= 0) {
            throw new UnprocessableEntityException("There is no more games to rent!"); 
        }

        LocalDate rentDate = LocalDate.now();

        int originalPrice = dto.getDaysRented() * game.getPricePerDay();

        int delayFee = 0;

        RentalModel rental = new RentalModel(rentDate, originalPrice, delayFee, daysRented, customer, game);
        return rentalRepository.save(rental);
    }

    public RentalModel update(Long id) {

        Optional<RentalModel> rental = rentalRepository.findById(id);
        if (!rental.isPresent()) {
            throw new NotFoundException("There is no rental with this id!");
        }

        if(rental.get().getReturnDate() != null) {
            throw new UnprocessableEntityException("This game was already returned!"); 
        }

        LocalDate rentDate = rental.get().getRentDate();
        int daysRented = rental.get().getDaysRented();
        LocalDate returnDate = LocalDate.now();
        long daysDelayed = ChronoUnit.DAYS.between(rentDate, returnDate);

        int delayFee = 0;
        if (daysDelayed > daysRented) {
            delayFee = (int) ((daysDelayed - daysRented) * rental.get().getGame().getPricePerDay());
        }

        rental.get().setReturnDate(returnDate);
        rental.get().setDelayFee(delayFee);
        

        return rentalRepository.save(rental.get());
    } 
}
