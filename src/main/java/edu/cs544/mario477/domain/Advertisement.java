package edu.cs544.mario477.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "advertisement")
public class Advertisement {

    @OneToMany
//    @JoinTable(name = "advertisement_user",
//            joinColumns = {@JoinColumn(name = "advertisement_id")},
//            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    List<User> users;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "title", length = 128, nullable = false)
    private String title;
    @Column(name = "description")
    private String description;
    @LastModifiedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public void addUser(User user) {
        users.add(user);
    }

    public void addUsers(List<User> userList) {
        users.addAll(userList);
    }

}
