package edu.cs544.mario477.service.impl;

import com.cloudinary.Cloudinary;
import edu.cs544.mario477.common.Constants;
import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.PostDTO;
import edu.cs544.mario477.exception.AppException;
import edu.cs544.mario477.exception.ResourceNotFoundException;
import edu.cs544.mario477.repository.PostRepository;
import edu.cs544.mario477.repository.UserRepository;
import edu.cs544.mario477.service.IAuthenticationFacade;
import edu.cs544.mario477.service.PostService;
import edu.cs544.mario477.service.StorageService;
import edu.cs544.mario477.util.Mapper;
import edu.cs544.mario477.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final StorageService storageService;

    private final UserRepository userRepository;

    private final Cloudinary cloudinary;

    private final IAuthenticationFacade authenticationFacade;

    @Value("${cloudinary.folder}")
    private String folder;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           StorageService storageService,
                           UserRepository userRepository,
                           Cloudinary cloudinary,
                           IAuthenticationFacade authenticationFacade) {
        this.postRepository = postRepository;
        this.storageService = storageService;
        this.userRepository = userRepository;
        this.cloudinary = cloudinary;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public List<PostDTO> getPostByFollow(long id, int page) {
        Sort sort = Sort.by("postedDate").descending();
        Pageable pageable = PageUtil.initPage(page, Constants.DEFAULT_SIZE, sort);
        User currentUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return postRepository
                .findByOwnerIn(currentUser.getFollowings(), pageable)
                .stream()
                .map(post -> Mapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getTimelineById(long id, int page) {
        Sort sort = Sort.by("postedDate").descending();
        Pageable pageable = PageUtil.initPage(page, Constants.DEFAULT_SIZE, sort);
        User currentUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return postRepository.findByOwner(currentUser, pageable).stream()
                .map(post -> Mapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO createPost(MultipartFile[] files, String text) {
        try {
            Post post = new Post(text, authenticationFacade.getCurrentUser());
            post.setEnabled(true);
            post.setPostedDate(LocalDateTime.now());
            post.setLastModifiedDate(LocalDateTime.now());
            postRepository.saveAndFlush(post);

            for (int i = 0; i < files.length; i++) {
                post.addMedia(storageService.upload(files[i], post.getId(), i));
            }
            postRepository.save(post);
            return Mapper.map(post, PostDTO.class);
        } catch (IOException e) {
            throw new AppException(e.getLocalizedMessage());
        }
    }

    @Override
    public Page<PostDTO> searchPost(String q, Pageable pageable) {
        return null;
    }
}
