package edu.cs544.mario477.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private Long id;

    private String text;

    @JsonProperty("commented_date")
    private LocalDateTime postedDate;

    @JsonProperty("last_modified_date")
    private LocalDateTime lastModifiedDate;

    private UserDTO owner;
}
