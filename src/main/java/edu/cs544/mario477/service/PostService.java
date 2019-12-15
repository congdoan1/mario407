package edu.cs544.mario477.service;

import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.dto.PostDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostService {

    List<PostDTO> getPostByFollow(long id, int page);

    List<PostDTO> getTimelineById(long id, int page);

    PostDTO createPost(MultipartFile[] files, String text);

    Page<PostDTO> searchPost(String q, Pageable pageable);

    void likePost(long id, long postId);

    void unlikePost(long id, long postId);
}
