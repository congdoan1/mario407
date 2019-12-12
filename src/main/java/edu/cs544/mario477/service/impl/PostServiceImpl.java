package edu.cs544.mario477.service.impl;

import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.dto.PostDTO;
import edu.cs544.mario477.repository.PostRepository;
import edu.cs544.mario477.repository.UserRepository;
import edu.cs544.mario477.service.PostService;
import edu.cs544.mario477.service.StorageService;
import edu.cs544.mario477.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final StorageService storageService;

    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           StorageService storageService,
                           UserRepository userRepository) {
        this.postRepository = postRepository;
        this.storageService = storageService;
        this.userRepository = userRepository;
    }

    @Override
    public PostDTO createPost(MultipartFile[] files, String text) {
        Post post = new Post(text, userRepository.getOne(1L));
        post.setEnabled(true);
        post.setPostedDate(LocalDateTime.now());
        post.setLastModifiedDate(LocalDateTime.now());
        postRepository.saveAndFlush(post);

        for (int i = 0; i < files.length; i++) {
            post.addMedia(storageService.upload(files[i], post.getId(), i));
        }
        postRepository.save(post);
        return Mapper.map(post, PostDTO.class);
    }

    @Override
    public Page<PostDTO> searchPost(String q, Pageable pageable) {
        return null;
    }
}
