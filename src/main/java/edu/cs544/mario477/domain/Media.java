package edu.cs544.mario477.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "media")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "url")
    private String url;

    @Column(name = "file_format")
    private String fileFormat;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Media(String url, String fileFormat, String type) {
        this.url = url;
        this.fileFormat = fileFormat;
    }
}