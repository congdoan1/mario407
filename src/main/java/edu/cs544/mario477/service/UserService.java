package edu.cs544.mario477.service;

import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.RegistrationDTO;
import edu.cs544.mario477.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    UserDTO register(RegistrationDTO dto);

    User followUser(long id);

    UserDTO getUser(String username);

    User unfollowUser(long id);

    void updateUser(UserDTO dto);

    void updateAvatar(MultipartFile file);
}
