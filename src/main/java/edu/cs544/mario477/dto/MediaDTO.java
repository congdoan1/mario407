package edu.cs544.mario477.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MediaDTO {

    private Long id;

    private String url;

    @JsonProperty("file_format")
    private String fileFormat;
}