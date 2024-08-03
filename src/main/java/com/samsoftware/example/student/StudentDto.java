package com.samsoftware.example.student;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record StudentDto(
        @NotBlank(message = "Firstname must be provided")
        String firstname,
        @NotBlank(message = "Lastname must be provided")
        String lastname,
        @Email(message = "A valid email address is required")
        @NotEmpty(message = "Email must be provided")
        String email,
        Integer schoolId
) {
}
