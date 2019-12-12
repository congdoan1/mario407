package edu.cs544.mario477.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class KeywordDTO {
    @NotEmpty
    private String definition;
    private boolean enabled;
}
