package edu.cs544.mario477.service.impl;

import com.cloudinary.Cloudinary;
import edu.cs544.mario477.domain.Role;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.RegistrationDTO;
import edu.cs544.mario477.dto.UserDTO;
import edu.cs544.mario477.exception.ResourceNotFoundException;
import edu.cs544.mario477.repository.RoleRepository;
import edu.cs544.mario477.repository.UserRepository;
import edu.cs544.mario477.service.IAuthenticationFacade;
import edu.cs544.mario477.service.UserService;
import edu.cs544.mario477.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private final IAuthenticationFacade authenticationFacade;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           IAuthenticationFacade authenticationFacade) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationFacade = authenticationFacade;
    }

    @PreAuthorize("permitAll()")
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
    public User followUser(long id) {
        User currentUser = authenticationFacade.getCurrentUser();
        User userToFollow = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        int checkIndex = currentUser.getFollowings().indexOf(userToFollow);
        if (checkIndex < 0) {
            currentUser.getFollowings().add(userToFollow);
            userRepository.save(userToFollow);
        }
        return currentUser;
    }

    @Override
    public User unfollowUser(long id) {
        User currentUser = authenticationFacade.getCurrentUser();
        User userToFollow = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        int checkIndex = currentUser.getFollowings().indexOf(userToFollow);
        if (checkIndex > -1) {
            currentUser.getFollowings().remove(checkIndex);
            userRepository.save(userToFollow);
        }
        return currentUser;
    }

}
