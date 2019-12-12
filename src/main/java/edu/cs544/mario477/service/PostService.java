package edu.cs544.mario477.service;

import edu.cs544.mario477.dto.PostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

    PostDTO createPost(MultipartFile[] files, String text);

    Page<PostDTO> searchPost(String q, Pageable pageable);
}
