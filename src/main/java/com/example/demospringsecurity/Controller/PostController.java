package com.example.demospringsecurity.Controller;

import com.example.demospringsecurity.Entity.Post;
import com.example.demospringsecurity.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    @Autowired
    PostService postService;
    @GetMapping("create")
    public String createPost(@RequestBody Post post, Principal principal) {
        String message = postService.create(post, principal.getName());
        return message;
    }

    @GetMapping("/approvePost/{postId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String approvePost(@PathVariable Long postId) {
        return postService.approvePost(postId);
    }

    @GetMapping("/approveAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String approveAll() {
        return postService.approveAll();
    }

    @GetMapping("/removePost/{postId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String removePost(@PathVariable Long postId) {
        return postService.removePost(postId);
    }

    @GetMapping("/rejectAll")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String rejectAll() {
        return postService.rejectAll();
    }

    @GetMapping("/viewAll")
    public List<Post> viewAll() {
        return postService.viewAll();
    }
}
