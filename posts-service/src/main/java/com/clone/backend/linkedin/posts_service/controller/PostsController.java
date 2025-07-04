package com.clone.backend.linkedin.posts_service.controller;

import com.clone.backend.linkedin.posts_service.auth.UserContextHolder;
import com.clone.backend.linkedin.posts_service.model.PostCreateDto;
import com.clone.backend.linkedin.posts_service.model.PostDto;
import com.clone.backend.linkedin.posts_service.service.PostsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateDto postDto) {
        PostDto createdPost = postsService.createPost(postDto);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        Long userId = UserContextHolder.getCurrentUserId();
        PostDto postDto = postsService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/user/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostsOfUser() {
        List<PostDto> posts = postsService.getAllPostsOfUser(UserContextHolder.getCurrentUserId());
        return ResponseEntity.ok(posts);
    }

}
