package edu.cs544.mario477.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class PostDTO {

    private Long id;

    private String text;

    @JsonProperty("posted_date")
    private LocalDateTime postedDate;

    @JsonProperty("last_modified_date")
    private LocalDateTime lastModifiedDate;

    private boolean enabled;

    private UserDTO owner;
}
