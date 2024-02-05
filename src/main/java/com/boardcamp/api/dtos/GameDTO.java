package com.boardcamp.api.dtos;

import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class GameDTO {
    @NotBlank(message = "Text cannot be empty")
    @Size(max = 280)
    private String name;

    @NotNull
    private String image;

    @NotNull
    @Min(value = 1)
    private int stockTotal;

    @NotNull
    @Min(value = 1)
    private int pricePerDay;
}
