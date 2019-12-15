package edu.cs544.mario477.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @CreatedDate
    @Column(name = "posted_date")
    private LocalDateTime postedDate;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "is_healthy")
    private boolean isHealthy;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "post")
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private Set<Media> mediaList = new HashSet<>();

    @ManyToMany
    private List<User> likers = new ArrayList<>();

    public Post(String text, User owner) {
        this.text = text;
        this.owner = owner;
    }

    public void addMedia(Media media) {
        this.mediaList.add(media);
        media.setPost(this);
    }
}
