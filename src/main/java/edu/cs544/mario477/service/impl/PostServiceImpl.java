package edu.cs544.mario477.service.impl;

import com.cloudinary.Cloudinary;
import edu.cs544.mario477.common.Constants;
import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.MailDTO;
import edu.cs544.mario477.dto.PostDTO;
import edu.cs544.mario477.exception.AppException;
import edu.cs544.mario477.exception.ResourceNotFoundException;
import edu.cs544.mario477.repository.PostRepository;
import edu.cs544.mario477.repository.UserRepository;
import edu.cs544.mario477.service.IAuthenticationFacade;
import edu.cs544.mario477.service.PostService;
import edu.cs544.mario477.service.StorageService;
import edu.cs544.mario477.util.EmailUtil;
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

    private final EmailUtil emailUtil;

    @Value("${cloudinary.folder}")
    private String folder;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           StorageService storageService,
                           UserRepository userRepository,
                           Cloudinary cloudinary,
                           IAuthenticationFacade authenticationFacade,
                           EmailUtil emailUtil) {
        this.postRepository = postRepository;
        this.storageService = storageService;
        this.userRepository = userRepository;
        this.cloudinary = cloudinary;
        this.authenticationFacade = authenticationFacade;
        this.emailUtil = emailUtil;
    }

    @Override
    public List<PostDTO> getPostByFollow(User currentUser, int page) {
        Sort sort = Sort.by("postedDate").descending();
        Pageable pageable = PageUtil.initPage(page, Constants.DEFAULT_SIZE, sort);
        return postRepository
                .findByOwnerIn(currentUser.getFollowings(), pageable)
                .stream()
                .map(post -> Mapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDTO> getTimelineById(long id, int page) {
        User currentUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        Sort sort = Sort.by("postedDate").descending();
        Pageable pageable = PageUtil.initPage(page, Constants.DEFAULT_SIZE, sort);
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

            //Check malicious user's unhealthy post;
            if (postRepository.countUnHealthyPost(post.getOwner().getId()) >= 20) {
                userRepository.updateUserStatus(post.getOwner().getId(), false);
                MailDTO  mailDTO = new MailDTO();
                mailDTO.setFrom("");
                mailDTO.setSubject("Lock account");
                mailDTO.setText("Your account was locked by unhealthy posts. Contact admin for more information");
                emailUtil.sendMail(mailDTO);

            }

            return Mapper.map(post, PostDTO.class);
        } catch (IOException e) {
            throw new AppException(e.getLocalizedMessage());
        }
    }

    @Override
    public Page<PostDTO> searchPost(String q, Pageable pageable) {
        return null;
    }

    @Override
    public void likePost(User currentUser, long postId) {
        Post currentPost = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        if (currentPost.getLikers().indexOf(currentUser) < 0) {
            currentPost.getLikers().add(currentUser);
            postRepository.save(currentPost);
        }
    }

    @Override
    public void unlikePost(User currentUser, long postId) {
        Post currentPost = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        if (currentPost.getLikers().indexOf(currentUser) > -1) {
            currentPost.getLikers().remove(currentUser);
            postRepository.save(currentPost);
        }
    }
}
