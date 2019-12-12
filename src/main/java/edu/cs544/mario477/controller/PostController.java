package edu.cs544.mario477.controller;

import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @GetMapping("/post")
    public Response loadPost(@RequestParam(defaultValue = "1") int page) {
        long currentId = 1;
        return ResponseBuilder.buildSuccess(postService.getPostByFollow(currentId, page));
    }

    @GetMapping("/timeline")
    public Response timeline(@RequestParam(defaultValue = "0") long id, @RequestParam(defaultValue = "1") int page) {
//        Fake current user
        if (id == 0) {
//          TODO assign current ID for null id
            id = 1;
        }
        return ResponseBuilder.buildSuccess(postService.getTimelineById(id, page));
    }
}
