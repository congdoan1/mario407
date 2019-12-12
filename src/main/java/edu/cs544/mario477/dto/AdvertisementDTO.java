package edu.cs544.mario477.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
public class AdvertisementDTO {

    @NotEmpty
    @Length(min = 5,max = 118)
    private String title;

    @NotEmpty
    @Length(min = 5)
    private String description;

    private int age;

    private String city;

    private String state;

    private String country;

    private  boolean allUser;

    private boolean enabled;

}
