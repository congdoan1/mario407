package edu.cs544.mario477.repository;

import edu.cs544.mario477.base.BaseRepository;
import edu.cs544.mario477.domain.Comment;
import edu.cs544.mario477.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentRepository extends BaseRepository<Comment, Long> {

    Page<Comment> findByPost(Post post, Pageable pageable);

}
