package edu.cs544.mario477.service.impl;

import edu.cs544.mario477.domain.Role;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.RegistrationDTO;
import edu.cs544.mario477.dto.UserDTO;
import edu.cs544.mario477.exception.ResourceNotFoundException;
import edu.cs544.mario477.repository.RoleRepository;
import edu.cs544.mario477.repository.UserRepository;
import edu.cs544.mario477.service.UserService;
import edu.cs544.mario477.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO register(RegistrationDTO dto) {
        User user = Mapper.map(dto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSignupDate(LocalDateTime.now());
        user.setEnabled(true);

        Role adminRole = roleRepository.findByName("ADMIN");
        user.addRole(adminRole);

        userRepository.save(user);
        return Mapper.map(user, UserDTO.class);
    }

    @Override
    public User followUser(long currentId, long id) {
        User userToFollow = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        User currentUser = userRepository.findById(currentId).orElseThrow(() -> new ResourceNotFoundException("User", "currentId", currentId));
        int checkIndex = currentUser.getFollowings().indexOf(userToFollow);
        if (checkIndex < 0) {
            currentUser.getFollowings().add(userToFollow);
            userRepository.save(userToFollow);
        }
        return currentUser;
    }

}
