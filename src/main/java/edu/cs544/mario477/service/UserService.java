package edu.cs544.mario477.service;

import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.RegistrationDTO;
import edu.cs544.mario477.dto.UserDTO;

public interface UserService {

    UserDTO register(RegistrationDTO dto);

    User followUser(long currentId, long id);

    User unfollowUser(long currentId, long id);
}
