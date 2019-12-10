package edu.cs544.mario477.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
public class RegistrationDTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    @JsonProperty("first_name")
    private String firstName;

    @NotBlank
    @JsonProperty("last_name")
    private String lastName;

    @Email(message = "Wrong email format")
    @NotBlank(message = "Email is required")
    private String email;

    private String phone;

    @Past
    private LocalDate birthday;
}
