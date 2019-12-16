package edu.cs544.mario477.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private Long id;

    private String text;

    private LocalDateTime postedDate;

    private LocalDateTime lastModifiedDate;

    private UserDTO owner;
}
