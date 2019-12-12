package edu.cs544.mario477.service;

import edu.cs544.mario477.base.BaseRepository;
import edu.cs544.mario477.domain.Post;
import javafx.geometry.Pos;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {
    List<Post> getPostByFollow(long id, int page);

    List<Post> getTimelineById(long id, int page);
}
