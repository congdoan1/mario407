package edu.cs544.mario477.controller;

import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.UserDTO;
import edu.cs544.mario477.service.UserService;
import edu.cs544.mario477.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/follow")
    public Response follow(@RequestParam(defaultValue = "0") long id) {
        long currentId = 1;
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
}
