package com.boardcamp.api.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class RentalDTO {
    
    public RentalDTO(long customerId, long gameId, int daysRented) {
        this.customerId = customerId;
        this.gameId = gameId;
        this.daysRented = daysRented;
    }

    @NotNull
    private long customerId;

    @NotNull
    private long gameId;

    @NotNull
    @Min(value = 1)
    private int daysRented;
}
