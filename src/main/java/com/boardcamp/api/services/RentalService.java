package com.boardcamp.api.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.exceptions.BadRequestException;
import com.boardcamp.api.exceptions.NotFoundException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentalRepository;

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

    public Optional<RentalModel> save(RentalDTO dto) {

        int daysRented = dto.getDaysRented();
        if(daysRented <= 0) {
            throw new BadRequestException("You can't rent a game for none or less than one day!");
        }

        Optional<CustomerModel> customer = customerRepository.findById(dto.getCustomerId());
        if (!customer.isPresent()) {
            throw new NotFoundException("There is no customer with this id!");
        }
        Optional<GameModel> game = gameRepository.findById(dto.getGameId());
        if (!game.isPresent()) {
            throw new NotFoundException("There is no game with this id!");
        }

        int stock = game.get().getStockTotal();
        if (stock <= 0) {
            throw new RecipeTitleConflictException("This recipe already exists!"); 
        }

        LocalDate rentDate = LocalDate.now();

        int originalPrice = dto.getDaysRented() * game.get().getPricePerDay();

        int delayFee = 0;

        RentalDTO dto2 = new RentalDTO(rentDate, originalPrice, delayFee, daysRented);

        RentalModel rental = new RentalModel(dto2, customer.get(), game.get());
        return Optional.of(rentalRepository.save(rental));
    }

    public Optional<RentalModel> update(RentalDTO dto, Long id) {

        Optional<RentalModel> rental = rentalRepository.findById(id);
        if (!rental.isPresent()) {
            return Optional.empty();
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
        
        return Optional.of(rentalRepository.save(rental));
    } 
}
