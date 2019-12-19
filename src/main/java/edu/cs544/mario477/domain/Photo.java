package edu.cs544.mario477.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "IMAGE")
public class Photo extends Media {

    public Photo(String id, String url, String fileFormat) {
        super(url, fileFormat);
    }
}