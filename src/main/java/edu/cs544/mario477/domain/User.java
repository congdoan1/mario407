package edu.cs544.mario477.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "username", length = 20, nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", length = 64)
    private String firstName;

    @Column(name = "last_name", length = 64)
    private String lastName;

    @Column(name = "email", length = 50, unique = true)
    private String email;

    @Column(name = "phone", length = 12, unique = true)
    private String phone;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "signup_date")
    private LocalDateTime signupDate;

    @Column(name = "enabled")
    private boolean enabled;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    @LazyCollection(LazyCollectionOption.EXTRA)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "owner")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "following",
            joinColumns = {@JoinColumn(name = "following_id")},
            inverseJoinColumns = {@JoinColumn(name = "follower_id")})
    @LazyCollection(LazyCollectionOption.EXTRA)
    private List<User> followers = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "following",
            joinColumns = {@JoinColumn(name = "follower_id")},
            inverseJoinColumns = {@JoinColumn(name = "following_id")})
    @LazyCollection(LazyCollectionOption.EXTRA)
    private List<User> followings = new ArrayList<>();

    public void addRole(Role role) {
        roles.add(role);
    }

    public void addAddress(Address address) {
        if (address != null) {
            if (this.address == null) {
                this.address = address;
            } else {
                this.address.setStreet(address.getStreet());
                this.address.setCity(address.getCity());
                this.address.setState(address.getState());
                this.address.setZipcode(address.getZipcode());
                this.address.setCountry(address.getCountry());
            }
        }
    }
}
