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
    public ResponseEntity<PostDto> createPost(@RequestBody PostCreateDto postDto,
                                              HttpServletRequest request) {
        PostDto createdPost = postsService.createPost(postDto, 1L);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) {
        Long userId = UserContextHolder.getCurrentUserId();
        PostDto postDto = postsService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }
    @GetMapping("/users/{userId}/allPosts")
    public ResponseEntity<List<PostDto>> getAllPostsOfUser(@PathVariable Long userId) {
        List<PostDto> posts = postsService.getAllPostsOfUser(userId);
        return ResponseEntity.ok(posts);
    }
}
