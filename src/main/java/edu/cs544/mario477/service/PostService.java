package edu.cs544.mario477.service;

import edu.cs544.mario477.base.BaseRepository;
import edu.cs544.mario477.domain.Post;
import org.springframework.stereotype.Service;
import java.util.List;
import edu.cs544.mario477.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    PostDTO createPost(MultipartFile[] files, String text);

    Page<PostDTO> searchPost(String q, Pageable pageable);
  
    List<Post> getPostByFollow(long id, int page);

    List<Post> getTimelineById(long id, int page);
}
