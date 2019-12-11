package edu.cs544.mario477.service;

import edu.cs544.mario477.dto.RegistrationDTO;
import edu.cs544.mario477.dto.UserDTO;

public interface UserService {

    UserDTO register(RegistrationDTO dto);
}
