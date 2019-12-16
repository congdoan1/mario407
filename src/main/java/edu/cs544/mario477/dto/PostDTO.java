package edu.cs544.mario477.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.cs544.mario477.domain.Media;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PostDTO {

    private Long id;

    private String text;

    private LocalDateTime postedDate;

    private LocalDateTime lastModifiedDate;

    private boolean enabled;

    @JsonProperty("media")
    private Set<MediaDTO> mediaList;

    private UserDTO owner;

    private int numberOfLikes;

    private int numberOfComments;
}
