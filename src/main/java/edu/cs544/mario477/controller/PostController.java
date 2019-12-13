package edu.cs544.mario477.controller;

import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import edu.cs544.mario477.domain.Post;
import edu.cs544.mario477.domain.User;
import edu.cs544.mario477.dto.CommentDTO;
import edu.cs544.mario477.dto.PostDTO;
import edu.cs544.mario477.service.CommentService;
import edu.cs544.mario477.service.PostService;
import edu.cs544.mario477.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("posts")
public class PostController {

    private final PostService postService;

    private final CommentService commentService;

    @Autowired
    public PostController(PostService postService,
                          CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<PostDTO> create(@RequestParam("files") MultipartFile[] files,
                                    @RequestParam("text") String text) {

        PostDTO postDTO = postService.createPost(files, text);

        return ResponseBuilder.buildSuccess(postDTO);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<PostDTO> update(@RequestParam(value = "files", required = false) MultipartFile[] files,
                                    @RequestParam("text") String text) {

        PostDTO postDTO = postService.createPost(files, text);

        return ResponseBuilder.buildSuccess(postDTO);
    }

    @GetMapping
    public Response search(@RequestParam("q") String q,
                           @RequestParam("page") Integer page,
                           @RequestParam("size") Integer size) {

        Page<PostDTO> p = postService.searchPost(q, PageUtil.initPage(page, size));

        return ResponseBuilder.buildSuccess(p);
    }

    @PostMapping("/{id}/comments")
    public Response comment(@PathVariable("id") Long postId,
                            @RequestBody @Valid CommentDTO dto) {

        CommentDTO commentDTO = commentService.comment(postId, dto.getText());

        return ResponseBuilder.buildSuccess(commentDTO);
    }
  
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
