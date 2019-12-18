package edu.cs544.mario477.service;

import edu.cs544.mario477.dto.PostDTO;
import edu.cs544.mario477.dto.UserDTO;

import java.util.List;

public interface AdminService {

    List<PostDTO> findUnhealthyPost(int page);

    List<PostDTO> findUnhealthyPost();

    List<PostDTO> findUnhealthyPostByUser(int UserId);

    List<UserDTO> findMaliciousUser(int page);

    void setPostStatus(Long postId, boolean status);

    void setUserStatus(Long userId, boolean status);


}
