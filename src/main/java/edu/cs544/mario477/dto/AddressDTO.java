package edu.cs544.mario477.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddressDTO {

    private Long id;

    @NotBlank
    private String street;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String zipcode;

    @NotBlank
    private String country;
}
