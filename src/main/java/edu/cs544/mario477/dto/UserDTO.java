package edu.cs544.mario477.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserDTO {

    private Long id;

    private String username;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    private String phone;

    private LocalDate birthday;

    private String avatarUrl;

    private boolean enabled;

    private AddressDTO address;
}
