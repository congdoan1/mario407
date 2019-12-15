package edu.cs544.mario477.controller;

import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.UserDTO;
import edu.cs544.mario477.exception.ResourceNotFoundException;
import edu.cs544.mario477.repository.UserRepository;
import edu.cs544.mario477.service.UserService;
import edu.cs544.mario477.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/follow")
    public Response follow(@RequestParam(defaultValue = "0") long id) {
        long currentId = 2;
        User user = userService.followUser(currentId, id);
        List<UserDTO> userDTOs = user.getFollowings().stream().map(user1 -> Mapper.map(user1, UserDTO.class)).collect(Collectors.toList());
        return ResponseBuilder.buildSuccess(userDTOs);
    }

    @PostMapping("/unfollow")
    public Response unfollow(@RequestParam(defaultValue = "0") long id) {
        long currentId = 1;
        User user = userService.unfollowUser(currentId, id);
        List<UserDTO> userDTOs = user.getFollowings().stream().map(user1 -> Mapper.map(user1, UserDTO.class)).collect(Collectors.toList());
        return ResponseBuilder.buildSuccess(userDTOs);
    }

    @GetMapping("/follow")
    public Response follow() {
        long currentId = 1;
        User user = userRepository.findById(currentId).orElseThrow(() -> new ResourceNotFoundException("User", "currentUser", currentId));
        List<UserDTO> userDTOs = user.getFollowings().stream().map(user1 -> Mapper.map(user1, UserDTO.class)).collect(Collectors.toList());
        return ResponseBuilder.buildSuccess(userDTOs);
    }

    @GetMapping("/follower")
    public Response follower() {
        long currentId = 1;
        User user = userRepository.findById(currentId).orElseThrow(() -> new ResourceNotFoundException("User", "currentUser", currentId));
        List<UserDTO> userDTOs = user.getFollowers().stream().map(user1 -> Mapper.map(user1, UserDTO.class)).collect(Collectors.toList());
        return ResponseBuilder.buildSuccess(userDTOs);
    }

    @GetMapping("/{username}")
    public Response getUser(@PathVariable("username") String username) {
        UserDTO userDTO = userService.getUser(username);
        return ResponseBuilder.buildSuccess(userDTO);
    }
}
