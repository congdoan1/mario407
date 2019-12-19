package edu.cs544.mario477.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @Max(12)
    private String phone;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birthday;

    private String avatarUrl;

    private boolean enabled;

    private AddressDTO address;

    private boolean isFollowing;

    private int numberOfPosts;

    private int numberOfFollowers;

    private int numberOfFollowings;
}
