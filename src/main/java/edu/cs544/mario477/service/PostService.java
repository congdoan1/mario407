package edu.cs544.mario477.service;

import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.PostDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostService {

    List<PostDTO> getPostByFollow(User currentUser, int page);

    List<PostDTO> getTimelineById(long currentUser, int page);

    PostDTO createPost(MultipartFile[] files, String text);

    Page<PostDTO> searchPost(String q, Pageable pageable);

    void likePost(User currentUser, long postId);

    void unlikePost(User currentUser, long postId);
}
