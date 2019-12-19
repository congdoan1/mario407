package edu.cs544.mario477.service.impl;

import edu.cs544.mario477.common.Constants;
import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.CommentDTO;
import edu.cs544.mario477.dto.PostDTO;
import edu.cs544.mario477.dto.UserDTO;
import edu.cs544.mario477.exception.ResourceNotFoundException;
import edu.cs544.mario477.repository.PostRepository;
import edu.cs544.mario477.repository.UserRepository;
import edu.cs544.mario477.service.AdminService;
import edu.cs544.mario477.util.Mapper;
import edu.cs544.mario477.util.PageUtil;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@PreAuthorize("hasRole('ADMIN')")
public class AdminServiceImpl implements AdminService {

    private PostRepository postRepository;

    private UserRepository userRepository;

    @Autowired
    public AdminServiceImpl(PostRepository postRepository,
                            UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PRI_READ','PRI_WRITE','PRI_EDIT')")
    public Page<PostDTO> findUnhealthyPost(Pageable pageable) {
        Page<Post> posts = postRepository.findByHealthyIsFalseAndReviewIsFalse(pageable);
        return Mapper.mapPage(posts, PostDTO.class);

    }

    @Override
    @PreAuthorize("hasAnyAuthority('PRI_READ','PRI_WRITE','PRI_EDIT')")
    public Page<UserDTO> findMaliciousUser(Pageable pageable) {
        Page<User> users = userRepository.findMaliciousUser(pageable);
        return Mapper.mapPage(users, UserDTO.class);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PRI_EDIT')")
    public void setPostStatus(Long postId, boolean status) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        if(status) post.setReview(true);
        post.setEnabled(status);
        postRepository.save(post);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PRI_EDIT')")
    public void setUserStatus(Long userId, boolean status) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        user.setEnabled(status);
        userRepository.save(user);
    }

}
