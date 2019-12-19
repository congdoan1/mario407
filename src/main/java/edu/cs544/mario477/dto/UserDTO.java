package edu.cs544.mario477.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
public class UserDTO {

    private Long id;

    private String username;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    private String phone;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

    private String avatarUrl;

    private boolean enabled;

    @Valid
    private AddressDTO address;

    private boolean isFollowing;

    private int numberOfPosts;

    private int numberOfFollowers;

    private int numberOfFollowings;
}
