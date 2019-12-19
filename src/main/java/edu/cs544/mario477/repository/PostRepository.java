package edu.cs544.mario477.repository;

import edu.cs544.mario477.base.BaseRepository;
import edu.cs544.mario477.domain.Comment;
import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends BaseRepository<Post, Long> {
    Page<Post> findByOwnerInAndAndEnabledIsTrue(List<User> user, Pageable pageable);

    Page<Post> findByHealthyIsFalseAndReviewIsFalse(Pageable pageable);

    @Modifying
    @Query("UPDATE Post p SET p.enabled =:status WHERE p.id=:id")
    void updatePostStatus(@Param("id") Long id, @Param("status") boolean status);

    @Query("Select count(p.id) from Post p where p.healthy=true and p.owner.id=:ownerId")
    long countUnhealthyPost(@Param("ownerId") Long ownerId);

    Page<Post> findByOwnerAndEnabledIsTrue(User user, Pageable pageable);

    @Query("select p.comments from Post p where p.id = :postId")
    Page<Comment> getCommentByPostId(@Param("postId") long postId, Pageable pageable);

    //select count( k.definition )from Keyword k where k.enabled = true and 'asdfasgsad bad key llkasjdflkjsak' like '%' || definition || '%'
    @Query(value = "select count(k.definition) from Keyword k where k.enabled = true and :text like '%' || definition || '%' ", nativeQuery = true)
    int checkHealthyPost(@Param("text") String text);

    Page<Post> findByTextContainingAndOwnerIn(String q, List<User> users, Pageable pageable);
}
