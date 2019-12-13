package edu.cs544.mario477.repository;

import edu.cs544.mario477.base.BaseRepository;
import edu.cs544.mario477.domain.Address;
import edu.cs544.mario477.domain.User;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends BaseRepository<User, Long> {

//    select distinct u.* from "user" u join post p on u.id = p.owner_id where (
//            select count(p.text) from post p  where p.text in (select k.definition from Keyword k where k.enabled=true)) >20


    @Query("select distinct u from User u join u.posts p where " +
            "( select count (p) from p where p.text in (select k.definition from Keyword k where k.enabled=true)) >=20")
    List<User> findMaliciousUser();

    List<User> findUserByAddressIsContaining(Address address);

    List<User> findUserByBirthdayBefore(LocalDate date);
}
