package edu.cs544.mario477.service.impl;

import com.cloudinary.Cloudinary;
import edu.cs544.mario477.domain.Comment;
import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.CommentDTO;
import edu.cs544.mario477.dto.MailDTO;
import edu.cs544.mario477.dto.PostDTO;
import edu.cs544.mario477.exception.AppException;
import edu.cs544.mario477.exception.ResourceNotFoundException;
import edu.cs544.mario477.repository.CommentRepository;
import edu.cs544.mario477.notification.Notification;
import edu.cs544.mario477.repository.PostRepository;
import edu.cs544.mario477.repository.UserRepository;
import edu.cs544.mario477.service.IAuthenticationFacade;
import edu.cs544.mario477.service.PostService;
import edu.cs544.mario477.service.StorageService;
import edu.cs544.mario477.util.EmailUtil;
import edu.cs544.mario477.util.Mapper;
import edu.cs544.mario477.util.PageUtil;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final StorageService storageService;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    private final Cloudinary cloudinary;

    private final IAuthenticationFacade authenticationFacade;

    private final EmailUtil emailUtil;

    private final Notification notification;

    @Value("${cloudinary.folder}")
    private String folder;

    @Autowired
    public PostServiceImpl(PostRepository postRepository,
                           StorageService storageService,
                           UserRepository userRepository,
                           CommentRepository commentRepository,
                           Cloudinary cloudinary,
                           IAuthenticationFacade authenticationFacade,
                           EmailUtil emailUtil,
                           Notification notification) {
        this.postRepository = postRepository;
        this.storageService = storageService;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.cloudinary = cloudinary;
        this.authenticationFacade = authenticationFacade;
        this.emailUtil = emailUtil;
        this.notification = notification;
    }

    @Override
    public Page<PostDTO> getHomePosts(User currentUser, Pageable pageable) {
        List<User> users = new ArrayList<>(currentUser.getFollowings());
        users.add(currentUser);
        Page<Post> posts = postRepository.findByOwnerIn(users, pageable);
        return Mapper.mapPage(posts, PostDTO.class);
    }

    @Override
    public Page<PostDTO> getTimelineByUsername(String username, Pageable pageable) {
        User queryUser;
        if (!authenticationFacade.getCurrentUser().getUsername().equals(username)) {
            queryUser = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
            if (authenticationFacade.getCurrentUser().getFollowings().indexOf(queryUser) < 0) {
                System.out.println("do");
                return null;
            }
        } else {
            queryUser = authenticationFacade.getCurrentUser();
        }
        Page<Post> posts = postRepository.findByOwner(queryUser, pageable);
        return Mapper.mapPage(posts, PostDTO.class);
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

            //Send new post message to RabbitMQ
            notification.notifyNewPost(post);

            if (postRepository.checkHealthyPost(post.getText()) > 0) {
                notification.notifyUnHealthyPost(post);
            }

            //Check malicious user's unhealthy post;
            if (postRepository.countUnhealthyPost(post.getOwner().getId()) >= 20) {
                userRepository.updateUserStatus(post.getOwner().getId(), false);
                MailDTO mailDTO = new MailDTO();
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

    @Override
    public Page<CommentDTO> getCommentByPost(long postId, Pageable pageable) {
        Post currentPost = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
        Page<Comment> comments = commentRepository.findByPost(currentPost, pageable);
        return Mapper.mapPage(comments, CommentDTO.class);
    }

    @Override
    public PostDTO getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException(Post.class, "id", postId));
        return Mapper.map(post, PostDTO.class);
    }
}
