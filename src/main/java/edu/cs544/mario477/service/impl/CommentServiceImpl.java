package edu.cs544.mario477.service.impl;

import edu.cs544.mario477.domain.Comment;
import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.dto.CommentDTO;
import edu.cs544.mario477.exception.ResourceNotFoundException;
import edu.cs544.mario477.repository.CommentRepository;
import edu.cs544.mario477.repository.PostRepository;
import edu.cs544.mario477.repository.UserRepository;
import edu.cs544.mario477.service.CommentService;
import edu.cs544.mario477.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public CommentDTO comment(Long postId, String text) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        Comment comment = new Comment(text, post, userRepository.getOne(1L));
        comment.setCommentedDate(LocalDateTime.now());
        comment.setLastModifiedDate(LocalDateTime.now());
        commentRepository.save(comment);

        return Mapper.map(comment, CommentDTO.class);
    }
}
