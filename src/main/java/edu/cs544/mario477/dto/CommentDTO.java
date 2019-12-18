package edu.cs544.mario477.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDTO {

    private Long id;

    private String text;

    private LocalDateTime commentedDate;

    private LocalDateTime lastModifiedDate;

    private UserDTO owner;
}
