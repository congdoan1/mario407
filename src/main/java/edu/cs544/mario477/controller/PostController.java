package edu.cs544.mario477.controller;

import edu.cs544.mario477.common.Response;
import edu.cs544.mario477.common.ResponseBuilder;
import edu.cs544.mario477.dto.CommentDTO;
import edu.cs544.mario477.dto.PostDTO;
import edu.cs544.mario477.service.AdminService;
import edu.cs544.mario477.service.IAuthenticationFacade;
import edu.cs544.mario477.service.PostService;
import edu.cs544.mario477.service.CommentService;
import edu.cs544.mario477.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    private final CommentService commentService;

    private final IAuthenticationFacade authenticationFacade;

    private final AdminService adminService;

    @Autowired
    public PostController(PostService postService,
                          CommentService commentService,
                          IAuthenticationFacade authenticationFacade,
                          AdminService adminService) {
        this.postService = postService;
        this.commentService = commentService;
        this.authenticationFacade = authenticationFacade;
        this.adminService = adminService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<PostDTO> create(@RequestParam("files") MultipartFile[] files,
                                    @RequestParam("text") String text) {

        PostDTO postDTO = postService.createPost(files, text, false);

        return ResponseBuilder.buildSuccess(postDTO);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Response<PostDTO> update(@RequestParam(value = "files", required = false) MultipartFile[] files,
                                    @RequestParam("type") String type,
                                    @RequestParam("text") String text,
                                    @RequestParam("notify") Boolean notify) {

        PostDTO postDTO = postService.createPost(files, text, notify);

        return ResponseBuilder.buildSuccess(postDTO);
    }

    @GetMapping
    public Response search(@RequestParam(value = "q", required = false) String q,
                           @RequestParam(value = "isHealthy", required = false) Boolean isHealthy,
                           @RequestParam(value = "page", required = false) Integer page,
                           @RequestParam(value = "size", required = false) Integer size) {

        Page<PostDTO> p = postService.searchPost(q, PageUtil.initPage(page, size));

        return ResponseBuilder.buildSuccess(p);
    }

    @PostMapping("/{id}/comments")
    public Response comment(@PathVariable("id") Long postId,
                            @RequestBody @Valid CommentDTO dto) {
        CommentDTO commentDTO = commentService.comment(postId, dto.getText());
        return ResponseBuilder.buildSuccess(commentDTO);
    }

    @GetMapping("/home")
    public Response loadPost(@RequestParam(value = "page", required = false) Integer page,
                             @RequestParam(value = "size", required = false) Integer size) {
        Sort sort = Sort.by("postedDate").descending();
        Page<PostDTO> posts = postService.getHomePosts(authenticationFacade.getCurrentUser(), PageUtil.initPage(page, size, sort));
        return ResponseBuilder.buildSuccess(posts);
    }

    @PostMapping("/{postId}/like")
    public Response like(@PathVariable long postId) {
        postService.likePost(authenticationFacade.getCurrentUser(), postId);
        return ResponseBuilder.buildSuccess();
    }

    @PostMapping("/{postId}/unlike")
    public Response unlike(@PathVariable long postId) {
        postService.unlikePost(authenticationFacade.getCurrentUser(), postId);
        return ResponseBuilder.buildSuccess();
    }

    @GetMapping("/{postId}/comments")
    public Response getComments(@PathVariable long postId,
                                @RequestParam(value = "page", required = false) Integer page,
                                @RequestParam(value = "size", required = false) Integer size) {
        Page<CommentDTO> comments = postService.getCommentByPost(postId, PageUtil.initPage(page, size));
        return ResponseBuilder.buildSuccess(comments);
    }

    @GetMapping("/{postId}")
    public Response getPost(@PathVariable("postId") Long postId) {
        PostDTO postDTO = postService.getPost(postId);
        return ResponseBuilder.buildSuccess(postDTO);
    }

    @GetMapping("/filter")
    public Response filterPost(@RequestParam(defaultValue = "false") Boolean isHealthy,
                               @RequestParam(value = "page", required = false) Integer page,
                               @RequestParam(value = "size", required = false) Integer size) {

        Sort sort = Sort.by("postedDate").descending();
        Page<PostDTO> posts = adminService.findUnhealthyPost(PageUtil.initPage(page, size, sort));
        return ResponseBuilder.buildSuccess(posts);

    }

    @PostMapping("/id}/active")
    public Response setPostActive(@PathVariable Long id) {

        adminService.setPostStatus(id, true);
        return ResponseBuilder.buildSuccess();

    }

    @PostMapping("/id}/deActive")
    public Response setPostDeActive(@PathVariable Long id) {

        adminService.setPostStatus(id, false);
        return ResponseBuilder.buildSuccess();

    }
}
