package edu.cs544.mario477.repository;

import edu.cs544.mario477.base.BaseRepository;
import edu.cs544.mario477.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    @Query("select u.followings from User u where u.username = :username")
    Page<User> getListFollowingByUser(@Param("username") String username, Pageable pageable);

    @Query("select u.followers from User u where u.username = :username")
    Page<User> getListFollowerByUser(@Param("username") String username, Pageable pageable);
}
