package edu.cs544.mario477.service.impl;

import edu.cs544.mario477.domain.Address;
import edu.cs544.mario477.domain.Media;
import edu.cs544.mario477.domain.Role;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.RegistrationDTO;
import edu.cs544.mario477.dto.UserDTO;
import edu.cs544.mario477.exception.ResourceNotFoundException;
import edu.cs544.mario477.repository.RoleRepository;
import edu.cs544.mario477.repository.UserRepository;
import edu.cs544.mario477.service.IAuthenticationFacade;
import edu.cs544.mario477.service.StorageService;
import edu.cs544.mario477.service.UserService;
import edu.cs544.mario477.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private final IAuthenticationFacade authenticationFacade;

    private final StorageService storageService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           IAuthenticationFacade authenticationFacade,
                           StorageService storageService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationFacade = authenticationFacade;
        this.storageService = storageService;
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

    @Override
    public UserDTO getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, "username", username));
        return Mapper.map(user, UserDTO.class);
    }

    @Override
    public Page<UserDTO> getListFollowingByUser(String username, Pageable pageable) {
        Page<User> followings = userRepository.getListFollowingByUser(username, pageable);
        return Mapper.mapPage(followings, UserDTO.class);
    }

    @Override
    public Page<UserDTO> getListFollowerByUser(String username, Pageable pageable) {
        return null;
    }

    @Override
    public void updateUser(UserDTO dto) {
        User user = authenticationFacade.getCurrentUser();
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        user.setBirthday(dto.getBirthday());
        user.addAddress(Mapper.map(dto.getAddress(), Address.class));
        userRepository.save(user);
    }

    @Override
    public void updateAvatar(MultipartFile file) {
        User user = authenticationFacade.getCurrentUser();
        user.setAvatarUrl(storageService.upload(file, user.getId()));
        userRepository.save(user);
    }
}
