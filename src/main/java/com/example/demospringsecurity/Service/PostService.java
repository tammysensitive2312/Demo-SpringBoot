package com.example.demospringsecurity.Service;

import com.example.demospringsecurity.ENUM.PostStatus;
import com.example.demospringsecurity.Entity.Post;
import com.example.demospringsecurity.Repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    public String create(Post post, String principalName) {
        post.setStatus(PostStatus.PENDING);
        post.setUserName(principalName);
        postRepository.save(post);
        return principalName + " Your post published successfully , Required ADMIN/MODERATOR Action !";
    }

    public String approvePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        post.setStatus(PostStatus.APPROVED);
        postRepository.save(post);
        return "Post Approved !!";
    }

    public String approveAll() {
        postRepository.findAll().forEach(post -> {
            if (post.getStatus().equals(PostStatus.PENDING)) {
                post.setStatus(PostStatus.APPROVED);
                postRepository.save(post);
            }
        });
        return "Approved all posts !";
    }

    public String removePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Post not found"));
        post.setStatus(PostStatus.REJECTED);
        postRepository.save(post);
        return "Post Rejected !!";
    }

    public String rejectAll() {
        postRepository.findAll().forEach(post -> {
            if (post.getStatus().equals(PostStatus.PENDING)) {
                post.setStatus(PostStatus.REJECTED);
                postRepository.save(post);
            }
        });
        return "Rejected all posts !";
    }

    public List<Post> viewAll() {
        return postRepository.findAll().stream()
                .filter(post -> post.getStatus().equals(PostStatus.APPROVED))
                .collect(Collectors.toList());
    }
}
