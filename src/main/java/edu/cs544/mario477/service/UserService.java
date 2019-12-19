package edu.cs544.mario477.service;

import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.RegistrationDTO;
import edu.cs544.mario477.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserDTO register(RegistrationDTO dto);

    User followUser(String username);

    UserDTO getUser(String username);

    Page<UserDTO> getListFollowingByUser(String username, Pageable pageable);

    Page<UserDTO> getListFollowerByUser(String username, Pageable pageable);

    User unfollowUser(String username);

    void updateUser(UserDTO dto);

    void updateAvatar(MultipartFile file);

    Page<UserDTO> getListSuggested(Pageable pageable);
}
