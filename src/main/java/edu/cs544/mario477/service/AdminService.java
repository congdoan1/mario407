package edu.cs544.mario477.service;

import edu.cs544.mario477.dto.PostDTO;
import edu.cs544.mario477.dto.UserDTO;

import java.util.List;

public interface AdminService {

    List<PostDTO> filterPostByKeyword();

    boolean setPostStatus(Long postId, boolean status);

    boolean setUserStatus(Long userId, boolean status);


}
