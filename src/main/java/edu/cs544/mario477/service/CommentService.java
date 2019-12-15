package edu.cs544.mario477.service;

import edu.cs544.mario477.dto.CommentDTO;

public interface CommentService {

    CommentDTO comment(Long postId, String text);
}
