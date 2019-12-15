package edu.cs544.mario477.controller;

import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.UserDTO;
import edu.cs544.mario477.exception.ResourceNotFoundException;
import edu.cs544.mario477.repository.UserRepository;
import edu.cs544.mario477.service.CommentService;
import edu.cs544.mario477.service.IAuthenticationFacade;
import edu.cs544.mario477.service.PostService;
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

    private UserService userService;

    private UserRepository userRepository;

    private final IAuthenticationFacade authenticationFacade;

    @Autowired
    public UserController(UserService userService,
                          UserRepository userRepository,
                          IAuthenticationFacade authenticationFacade) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.authenticationFacade = authenticationFacade;
    }

    @PostMapping("/follow")
    public Response follow(@RequestParam(defaultValue = "0") long id) {
        User user = userService.followUser(id);
        List<UserDTO> userDTOs = user.getFollowings().stream().map(user1 -> Mapper.map(user1, UserDTO.class)).collect(Collectors.toList());
        return ResponseBuilder.buildSuccess(userDTOs);
    }

    @PostMapping("/unfollow")
    public Response unfollow(@RequestParam(defaultValue = "0") long id) {
        User user = userService.unfollowUser(id);
        List<UserDTO> userDTOs = user.getFollowings().stream().map(user1 -> Mapper.map(user1, UserDTO.class)).collect(Collectors.toList());
        return ResponseBuilder.buildSuccess(userDTOs);
    }

    @GetMapping("/follow")
    public Response follow() {
        List<UserDTO> userDTOs = authenticationFacade.getCurrentUser().getFollowings().stream().map(user1 -> Mapper.map(user1, UserDTO.class)).collect(Collectors.toList());
        return ResponseBuilder.buildSuccess(userDTOs);
    }

    @GetMapping("/follower")
    public Response follower() {
        List<UserDTO> userDTOs = authenticationFacade.getCurrentUser().getFollowers().stream().map(user1 -> Mapper.map(user1, UserDTO.class)).collect(Collectors.toList());
        return ResponseBuilder.buildSuccess(userDTOs);
    }

    @GetMapping("/{username}")
    public Response getUser(@PathVariable("username") String username) {
        UserDTO userDTO = userService.getUser(username);
        return ResponseBuilder.buildSuccess(userDTO);
    }
}
