package edu.cs544.mario477.controller;

import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import edu.cs544.mario477.dto.MailDTO;
import edu.cs544.mario477.dto.PostDTO;
import edu.cs544.mario477.dto.UserDTO;
import edu.cs544.mario477.service.AdminService;
import edu.cs544.mario477.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    private EmailUtil emailUtil;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/posts?isUnHealthyPost=true")
    public Response<List<PostDTO>> filterPost(@RequestParam(defaultValue = "0") Integer page) {
        List<PostDTO> posts = adminService.findUnhealthyPost(page);
        return ResponseBuilder.buildSuccess(posts);
    }

    @PutMapping("/posts/{id}?active=true")
    public Response activePost(@PathVariable Long id) {
        adminService.setPostStatus(id, true);
        return ResponseBuilder.buildSuccess();

    }

    @PutMapping("/posts/{id}?active=false")
    public Response deActivePost(@PathVariable Long id) {
        adminService.setPostStatus(id, false);
        return ResponseBuilder.buildSuccess();

    }

    @GetMapping("/users?isMalicious=true")
    public Response<List<UserDTO>> maliciousUser(@RequestParam(defaultValue = "0") Integer page) {
        List<UserDTO> users = adminService.findMaliciousUser(page);

        return ResponseBuilder.buildSuccess(users);
    }

    @PutMapping("/users/{id}?active=true")
    public Response activeUser(@PathVariable Long id) {
        adminService.setUserStatus(id, true);
        return ResponseBuilder.buildSuccess();

    }

    @PutMapping("/users/{id}?active=false")
    public Response deActiveUser(@PathVariable Long id) {
        adminService.setUserStatus(id, false);
        return ResponseBuilder.buildSuccess();

    }

    @PostMapping("/mail")
    public Response sendmail(){
        MailDTO dto = new MailDTO("duythong03@gmail.com","pduythong@gmail.com","subject","test");

        emailUtil.sendMail(dto);

        return ResponseBuilder.buildSuccess();
    }

}
