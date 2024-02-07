package com.boardcamp.api.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RentalDTO {
    
    public RentalDTO(LocalDate rentDate, int originalPrice, int delayFee, int daysRented) {
        this.rentDate = rentDate;
        this.delayFee = delayFee;
        this.returnDate = null;
        this.originalPrice = originalPrice;
        this.daysRented = daysRented;
    }

    @NotNull
    private long customerId;

    @NotNull
    private long gameId;

    @NotNull
    private LocalDate rentDate;

    @NotNull
    @Min(value = 1)
    private int daysRented;

    private LocalDate returnDate;

    @NotNull
    private int originalPrice;

    private int delayFee;
}
