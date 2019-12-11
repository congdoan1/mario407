package edu.cs544.mario477.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "[user]")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "username", length = 20, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", length = 64)
    private String firstName;

    @Column(name = "last_name", length = 64)
    private String lastName;

    @Column(name = "email", length = 50)
    private String email;

    @Column(name = "phone", length = 10)
    private String phone;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "signup_date")
    private LocalDateTime signupDate;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "following",
            joinColumns = {@JoinColumn(name = "following_id")},
            inverseJoinColumns = {@JoinColumn(name = "follower_id")})
    private List<User> followers = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "following",
            joinColumns = {@JoinColumn(name = "follower_id")},
            inverseJoinColumns = {@JoinColumn(name = "following_id")})
    private List<User> followings = new ArrayList<>();

    public void addRole(Role role) {
        roles.add(role);
    }
}
