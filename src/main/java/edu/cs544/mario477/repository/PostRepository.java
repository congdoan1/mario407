package edu.cs544.mario477.repository;

import edu.cs544.mario477.base.BaseRepository;
import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends BaseRepository<Post, Long> {
    List<Post> findByOwnerIn(List<User> user, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE  p.text in (SELECT k.definition FROM Keyword  k WHERE k.enabled=true )")
    List<Post> findUnhealthyPost();

    @Query("SELECT p FROM Post p WHERE  p.text in (SELECT k.definition FROM Keyword  k WHERE k.enabled=true )")
    List<Post> findUnhealthyPost(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.owner.id = :userId and  p.text in (SELECT k.definition FROM Keyword  k WHERE k.enabled=true )")
    List<Post> findUnhealthyPostByUser(@Param("userId") int userId);

    List<Post> findByOwner(User currentUser, Pageable pageable);

    @Modifying
    @Query("UPDATE Post p SET p.enabled =:status WHERE p.id=:id")
    void updatePostStatus(@Param("id") Long id, boolean status);

    long countUnHealthyPost(Long ownerID);
}
