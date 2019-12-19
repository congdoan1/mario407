package edu.cs544.mario477.service.impl;

import edu.cs544.mario477.domain.Address;
import edu.cs544.mario477.domain.Media;
import edu.cs544.mario477.domain.Role;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.RegistrationDTO;
import edu.cs544.mario477.dto.UserDTO;
import edu.cs544.mario477.exception.AppException;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final IAuthenticationFacade authenticationFacade;
    private final StorageService storageService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

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

        Role userRole = roleRepository.findByName("USER");
        user.addRole(userRole);

        userRepository.save(user);
        return Mapper.map(user, UserDTO.class);
    }

    @Override
    public User followUser(String username) {
        User currentUser = authenticationFacade.getCurrentUser();
        if (!authenticationFacade.getCurrentUser().getUsername().equals(username)) {
            User userToFollow = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "id", username));
            int checkIndex = currentUser.getFollowings().indexOf(userToFollow);
            if (checkIndex < 0) {
                currentUser.getFollowings().add(userToFollow);
                userRepository.save(userToFollow);
            }
        }
        return currentUser;
    }

    @Override
    public User unfollowUser(String username) {
        User currentUser = authenticationFacade.getCurrentUser();
        User userToFollow = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        int checkIndex = currentUser.getFollowings().indexOf(userToFollow);
        if (checkIndex > -1) {
            currentUser.getFollowings().remove(checkIndex);
            userRepository.save(userToFollow);
        }
        return currentUser;
    }

    @Override
    public UserDTO getUser(String username) {
        User currentUser = authenticationFacade.getCurrentUser();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, "username", username));
        UserDTO dto = Mapper.map(user, UserDTO.class);
        dto.setNumberOfPosts(user.getPosts().size());
        dto.setNumberOfFollowers(user.getFollowers().size());
        dto.setNumberOfFollowings(user.getFollowings().size());
        if (!currentUser.getUsername().equals(username)) {
            dto.setFollowing(user.getFollowers().contains(currentUser));
        }
        return dto;
    }

    @Override
    public Page<UserDTO> getListFollowingByUser(String username, Pageable pageable) {
        Page<User> followings = userRepository.getListFollowingByUser(username, pageable);
        return Mapper.mapPage(followings, UserDTO.class);
    }

    @Override
    public Page<UserDTO> getListFollowerByUser(String username, Pageable pageable) {
        Page<User> followers = userRepository.getListFollowerByUser(username, pageable);
        return Mapper.mapPage(followers, UserDTO.class);
    }

    @Override
    public void updateUser(UserDTO dto) {
        if (dto.getUsername().equals(authenticationFacade.getCurrentUser().getUsername())) {
            User user = authenticationFacade.getCurrentUser();
            user.setEmail(dto.getEmail());
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setPhone(dto.getPhone());
            user.setBirthday(dto.getBirthday());
            user.addAddress(Mapper.map(dto.getAddress(), Address.class));
            userRepository.save(user);
        } else {
            throw new AppException("You do not have permission to update this user information");
        }

    }

    @Override
    public void updateAvatar(MultipartFile file) {
        User user = authenticationFacade.getCurrentUser();
        user.setAvatarUrl(storageService.upload(file, user.getId()));
        userRepository.save(user);
    }

    @Override
    public Page<UserDTO> getListSuggested(Pageable pageable) {
        List<Long> excludeList = authenticationFacade.getCurrentUser().getFollowings().stream().map(user -> user.getId()).collect(Collectors.toList());
        excludeList.add(authenticationFacade.getCurrentUser().getId());
        Page<User> users = userRepository.findByIdNotIn(excludeList, pageable);
        return Mapper.mapPage(users, UserDTO.class);
    }

    public void claimUser(Long id, Boolean status) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setClaim(status);
        userRepository.save(user);
    }

    @Override
    @PreAuthorize("hasAnyAuthority('PRI_READ','PRI_WRITE','PRI_EDIT')")
    public Page<UserDTO> getListClaimUser(Pageable pageable) {
        Page<User> users = userRepository.getByClaimIsTrue(pageable);
        return Mapper.mapPage(users, UserDTO.class);
    }
}
