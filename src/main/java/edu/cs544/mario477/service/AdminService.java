package edu.cs544.mario477.service;

import edu.cs544.mario477.dto.PostDTO;
import edu.cs544.mario477.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {

    Page<PostDTO> findUnhealthyPost(Pageable pageable);

    Page<UserDTO> findMaliciousUser(Pageable pageable);

    void setPostStatus(Long postId, boolean status);

    void setUserStatus(Long userId, boolean status);


}
