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
@DiscriminatorValue(value = "VIDEO")
public class Video extends Media {

    private double duration;

    public Video(String id, String url, String fileFormat, double duration) {
        super(url, fileFormat);
        this.duration = duration;
    }
}