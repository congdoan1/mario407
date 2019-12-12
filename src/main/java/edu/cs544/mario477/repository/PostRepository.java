package edu.cs544.mario477.repository;

import edu.cs544.mario477.base.BaseRepository;
import edu.cs544.mario477.domain.Post;

import edu.cs544.mario477.domain.Role;

import edu.cs544.mario477.domain.User;
import javafx.geometry.Pos;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends BaseRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE  p.text in (SELECT k.definition FROM Keyword  k WHERE k.enabled=true )")
    List<Post> findUnhealthyPostByKeyword();


}
