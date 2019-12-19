package edu.cs544.mario477.controller;

import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.UserDTO;
import edu.cs544.mario477.service.AdminService;
import edu.cs544.mario477.service.IAuthenticationFacade;
import edu.cs544.mario477.service.PostService;
import edu.cs544.mario477.service.UserService;
import edu.cs544.mario477.util.Mapper;
import edu.cs544.mario477.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final IAuthenticationFacade authenticationFacade;
    private UserService userService;
    private PostService postService;
    private AdminService adminService;

    @Autowired
    public UserController(UserService userService,
                          PostService postService,
                          IAuthenticationFacade authenticationFacade,
                          AdminService adminService) {
        this.userService = userService;
        this.postService = postService;
        this.authenticationFacade = authenticationFacade;
        this.adminService = adminService;
    }

    @PostMapping("/{username}/follow")
    public Response follow(@PathVariable("username") String username) {
        User user = userService.followUser(username);
        List<UserDTO> userDTOs = user.getFollowings().stream().map(user1 -> Mapper.map(user1, UserDTO.class)).collect(Collectors.toList());
        return ResponseBuilder.buildSuccess(userDTOs);
    }

    @PostMapping("/{username}/unfollow")
    public Response unfollow(@PathVariable("username") String username) {
        User user = userService.unfollowUser(username);
        List<UserDTO> userDTOs = user.getFollowings().stream().map(user1 -> Mapper.map(user1, UserDTO.class)).collect(Collectors.toList());
        return ResponseBuilder.buildSuccess(userDTOs);
    }

    @GetMapping("/{username}/followings")
    public Response follow(@PathVariable("username") String username,
                           @RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "size", required = false) Integer size) {
        Page<UserDTO> userDTOs = userService.getListFollowingByUser(username, PageUtil.initPage(page, size));
        return ResponseBuilder.buildSuccess(userDTOs);
    }

    @GetMapping("/{username}/followers")
    public Response follower(@PathVariable("username") String username,
                             @RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "size", required = false) Integer size) {
        Page<UserDTO> userDTOs = userService.getListFollowerByUser(username, PageUtil.initPage(page, size));
        return ResponseBuilder.buildSuccess(userDTOs);
    }

    @GetMapping("/{username}")
    public Response<UserDTO> getUser(@PathVariable("username") String username) {
        UserDTO userDTO = userService.getUser(username);
        return ResponseBuilder.buildSuccess(userDTO);
    }


    @GetMapping("/{username}/timeline")
    public Response timeline(@PathVariable("username") String username,
                             @RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "size", required = false) Integer size) {
        Sort sort = Sort.by("postedDate").descending();
        return ResponseBuilder.buildSuccess(postService.getTimelineByUsername(username, PageUtil.initPage(page, size, sort)));
    }

    @PutMapping("/{username}")
    public Response<Void> updateUser(@PathVariable String username,
                                     @RequestBody @Valid UserDTO userDTO) {
        userService.updateUser(userDTO);
        return ResponseBuilder.buildSuccess();
    }

    @PutMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<Void> updateAvatar(@RequestParam(value = "avatar", required = false) MultipartFile avatarFile) {
        userService.updateAvatar(avatarFile);
        return ResponseBuilder.buildSuccess();
    }

    @GetMapping("/users")
    public Response maliciousUser(@RequestParam("malicious") Boolean malicious,
                                  @RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "size", required = false) Integer size) {

        Page<UserDTO> users = adminService.findMaliciousUser(PageUtil.initPage(page, size));
        return ResponseBuilder.buildSuccess(users);

    }

    @PostMapping("/{id}/active")
    public Response setUserActive(@PathVariable Long id) {

        adminService.setUserStatus(id, true);
        return ResponseBuilder.buildSuccess();

    }

    @PostMapping("/{id}/deActive")
    public Response setUserDeActive(@PathVariable Long id) {

        adminService.setUserStatus(id, false);
        return ResponseBuilder.buildSuccess();

    }
}
