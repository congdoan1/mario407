package edu.cs544.mario477.service.impl;

import edu.cs544.mario477.common.Constants;
import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.dto.PostDTO;
import edu.cs544.mario477.dto.UserDTO;
import edu.cs544.mario477.repository.PostRepository;
import edu.cs544.mario477.repository.UserRepository;
import edu.cs544.mario477.service.AdminService;
import edu.cs544.mario477.util.Mapper;
import edu.cs544.mario477.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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
    public List<PostDTO> findUnhealthyPost(int page) {
        Sort sort = Sort.by("text").ascending();
        Pageable pageable = PageUtil.initPage(page, Constants.DEFAULT_SIZE, sort);
        List<Post> posts = postRepository.findUnhealthyPost(pageable);
        return posts.stream()
                .map(post -> Mapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }


    public List<PostDTO> findUnhealthyPost() {
        List<Post> posts = postRepository.findUnhealthyPost();
        return posts.stream()
                .map(post -> Mapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> findUnhealthyPostByUser(int userId) {
        return Mapper.mapList(postRepository.findUnhealthyPostByUser(userId), PostDTO.class);
    }

    @Override
    public List<UserDTO> findMaliciousUser(int page) {
        Sort sort = Sort.by("username").ascending();
        Pageable pageable = PageUtil.initPage(page, Constants.DEFAULT_SIZE, sort);

        return userRepository.findMaliciousUser(pageable).stream()
                .map(user -> Mapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }


    @Override
    public void setPostStatus(Long postId, boolean status) {
        postRepository.updatePostStatus(postId, status);

    }

    @Override
    public void setUserStatus(Long userId, boolean status) {
        userRepository.updateUserStatus(userId, status);
    }

}
