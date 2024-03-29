package edu.cs544.mario477.repository;

import edu.cs544.mario477.base.BaseRepository;
import edu.cs544.mario477.domain.Address;
import edu.cs544.mario477.domain.User;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {

//    select distinct u.* from "user" u join post p on u.id = p.owner_id where (
//            select count(p.text) from post p  where p.text in (select k.definition from Keyword k where k.enabled=true)) >20


    @Query("select distinct u from User u join u.posts p where " +
            "( select count (p) from p where p.text in (select k.definition from Keyword k where k.enabled=true)) >=20")
    List<User> findMaliciousUser( Pageable pageable);

    List<User> findUserByAddressIsContaining(Address address);

    List<User> findUserByBirthdayBefore(LocalDate date);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.enabled =:status WHERE u.id=:id")
    void updateUserStatus(@Param("id") Long id, boolean status);

}
