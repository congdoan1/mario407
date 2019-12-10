package edu.cs544.mario477.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "street", length = 128, nullable = false)
    private String street;

    @Column(name = "city", length = 50, nullable = false)
    private String city;

    @Column(name = "state", length = 20, nullable = false)
    private String state;

    @Column(name = "zipcode", length = 5, nullable = false)
    private String zipcode;

    @Column(name = "country", length = 128, nullable = false)
    private String country;

    @OneToOne(mappedBy = "address")
    private User user;
}
