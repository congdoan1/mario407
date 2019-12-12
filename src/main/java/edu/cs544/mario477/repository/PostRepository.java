package edu.cs544.mario477.repository;

import edu.cs544.mario477.base.BaseRepository;
import edu.cs544.mario477.domain.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends BaseRepository<Post, Long> {
}