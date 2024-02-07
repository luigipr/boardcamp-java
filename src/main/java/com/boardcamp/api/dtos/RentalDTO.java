package com.boardcamp.api.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RentalDTO {
    
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
