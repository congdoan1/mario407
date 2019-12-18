package edu.cs544.mario477.repository;

import edu.cs544.mario477.base.BaseRepository;
import edu.cs544.mario477.domain.Comment;
import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends BaseRepository<Post, Long> {
    Page<Post> findByOwnerIn(List<User> user, Pageable pageable);

    Page<Post> findByOwner(User user, Pageable pageable);

    @Query("select p.comments from Post p where p.id = :postId")
    Page<Comment> getCommentByPostId(@Param("postId") long postId, Pageable pageable);
}
