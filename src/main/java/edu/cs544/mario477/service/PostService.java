package edu.cs544.mario477.service;

import edu.cs544.mario477.domain.Comment;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.CommentDTO;
import edu.cs544.mario477.dto.PostDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface PostService {

    Page<PostDTO> getHomePosts(User currentUser, Pageable pageable);

    Page<PostDTO> getTimelineByUsername(String username, Pageable pageable);

    PostDTO createPost(MultipartFile[] files, String text);

    Page<PostDTO> searchPost(String q, Pageable pageable);

    void likePost(User currentUser, long postId);

    void unlikePost(User currentUser, long postId);

    Page<CommentDTO> getCommentByPost(long postId, Pageable pageable);

    PostDTO getPost(Long postId);
}
