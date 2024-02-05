package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerDTO {
    
    @NotBlank(message = "Title is mandatory")
    @Size(max = 100)
    private String name;

    @NotBlank
    @Size(max = 11)
    private String cpf;

}
