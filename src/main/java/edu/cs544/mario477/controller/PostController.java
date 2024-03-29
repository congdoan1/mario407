package edu.cs544.mario477.controller;

import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import edu.cs544.mario477.dto.CommentDTO;
import edu.cs544.mario477.dto.PostDTO;
import edu.cs544.mario477.service.IAuthenticationFacade;
import edu.cs544.mario477.service.PostService;
import edu.cs544.mario477.service.CommentService;
import edu.cs544.mario477.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("posts")
public class PostController {

    private final PostService postService;

    private final CommentService commentService;

    private final IAuthenticationFacade authenticationFacade;

    @Autowired
    public PostController(PostService postService,
                          CommentService commentService,
                          IAuthenticationFacade authenticationFacade) {
        this.postService = postService;
        this.commentService = commentService;
        this.authenticationFacade = authenticationFacade;
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
    public Response loadPost(@RequestParam(defaultValue = "0") int page) {
        List<PostDTO> posts = postService.getPostByFollow(authenticationFacade.getCurrentUser(), page);
        return ResponseBuilder.buildSuccess(posts);
    }

    @GetMapping("/timeline")
    public Response timeline(@RequestParam(defaultValue = "0") long id, @RequestParam(defaultValue = "1") int page) {
        return ResponseBuilder.buildSuccess(postService.getTimelineById(
               id == 0 ? authenticationFacade.getCurrentUser().getId() : id
                , page)
        );
    }

    @PostMapping("/like")
    public Response like(@RequestParam(required = true) long postId) {
        postService.likePost(authenticationFacade.getCurrentUser(), postId);
        return ResponseBuilder.buildSuccess();
    }

    @PostMapping("/unlike")
    public Response unlike(@RequestParam(required = true) long postId) {
        postService.unlikePost(authenticationFacade.getCurrentUser(), postId);
        return ResponseBuilder.buildSuccess();
    }
}
