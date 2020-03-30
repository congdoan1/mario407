package edu.cs544.mario477.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PostDTO {

    private Long id;
    @NotEmpty
    private String text;

    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime postedDate;

    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime lastModifiedDate;

    private boolean enabled;

    @JsonProperty("media")
    private Set<MediaDTO> mediaList;

    private UserDTO owner;

    private int numberOfLikes;

    private int numberOfComments;

    private boolean isLiked;
}
