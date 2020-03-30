package edu.cs544.mario477.controller;

import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import edu.cs544.mario477.dto.PostDTO;
import edu.cs544.mario477.dto.UserDTO;
import edu.cs544.mario477.service.AdminService;
import edu.cs544.mario477.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/posts")
    public Response filterPost(@RequestParam(defaultValue = "false") Boolean isHealthy,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "size", required = false) Integer size) {

        Sort sort = Sort.by("postedDate").descending();
        Page<PostDTO> posts = adminService.findUnhealthyPost(PageUtil.initPage(page, size, sort));
        return ResponseBuilder.buildSuccess(posts);
    }

    @PostMapping("/posts/{id}")
    public Response setPostStatus(@PathVariable Long id,
                                  @Param("active") Boolean active) {

        adminService.setPostStatus(id, active);
        return ResponseBuilder.buildSuccess();

    }

    @GetMapping("/users")
    public Response maliciousUser(@RequestParam("malicious") Boolean malicious,
                                  @RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "size", required = false) Integer size) {

        Page<UserDTO> users = adminService.findMaliciousUser(PageUtil.initPage(page, size));
        return ResponseBuilder.buildSuccess(users);

    }

    @PostMapping("/users/{id}")
    public Response setUserStatus(@PathVariable Long id
            , @Param("active") Boolean active) {

        adminService.setUserStatus(id, active);
        return ResponseBuilder.buildSuccess();

    }
}
