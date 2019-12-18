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
    Page<Post> findByOwnerIn(List<User> user, Pageable pageable);
//SELECT count (p.id) FROM Post p WHERE p.id=1 and  p.text like '%' || (SELECT k.definition FROM Keyword  k WHERE k.enabled=true) ||'%'
    @Query(value = "SELECT p.* FROM Post p WHERE  p.text like '%' || (SELECT k.definition FROM Keyword  k WHERE k.enabled=true )||",
    nativeQuery = true)
    List<Post> findUnhealthyPost();

    @Query("SELECT p FROM Post p WHERE  p.text in (SELECT k.definition FROM Keyword  k WHERE k.enabled=true )")
    List<Post> findUnhealthyPost(Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.owner.id = :userId and  p.text in (SELECT k.definition FROM Keyword  k WHERE k.enabled=true )")
    List<Post> findUnhealthyPostByUser(@Param("userId") int userId);

    @Modifying
    @Query("UPDATE Post p SET p.enabled =:status WHERE p.id=:id")
    void updatePostStatus(@Param("id") Long id, boolean status);

    @Query("Select count(p.id) from Post p where p.isHealthy=true and p.owner.id=:ownerId")
    long countUnhealthyPost(@Param("ownerId") Long ownerId);

    Page<Post> findByOwner(User user, Pageable pageable);

    @Query("select p.comments from Post p where p.id = :postId")
    Page<Comment> getCommentByPostId(@Param("postId") long postId, Pageable pageable);


    //
    //select count( k.definition )from Keyword k where k.enabled = true and 'asdfasgsad bad key llkasjdflkjsak' like '%' || definition || '%'
    @Query(value = "select count(k.definition) from Keyword k where k.enabled = true and :text like '%' || definition || '%' ",nativeQuery = true)
    int checkHealthyPost(@Param("text") String text);
}
