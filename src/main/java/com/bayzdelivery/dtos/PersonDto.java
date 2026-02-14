package com.bayzdelivery.dtos;

import com.bayzdelivery.utilites.PERSON_TYPE;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PersonDto {

    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @NotNull(message = "Email is required")
    private String email;
    @NotNull(message = "Person type is required")
    private PERSON_TYPE type;
    private String registrationNumber;
}
