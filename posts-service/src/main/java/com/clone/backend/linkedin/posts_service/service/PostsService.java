package com.clone.backend.linkedin.posts_service.service;

import com.clone.backend.linkedin.posts_service.auth.UserContextHolder;
import com.clone.backend.linkedin.posts_service.client.ConnectionClient;
import com.clone.backend.linkedin.posts_service.entity.Post;
import com.clone.backend.linkedin.posts_service.exception.ResourceNotFoundException;
import com.clone.backend.linkedin.posts_service.model.PersonDto;
import com.clone.backend.linkedin.posts_service.model.PostCreateDto;
import com.clone.backend.linkedin.posts_service.model.PostDto;
import com.clone.backend.linkedin.posts_service.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService {

    private final PostsRepository postsRepository;
    private final ModelMapper modelMapper;
    private final ConnectionClient connectionClient;

    public PostDto createPost(PostCreateDto postDto, Long userId) {
        Post post = modelMapper.map(postDto, Post.class);
        post.setUserId(userId);
        Post savedPost = postsRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    public PostDto getPostById(Long postId) {
        log.debug("Retrieving post with ID: {}", postId);
        List<PersonDto> firstConnections = connectionClient.getFirstConnections();
        // TODO Send Notifications to all connections
        Post post = postsRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post not found with id: "+postId));
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostsOfUser(Long userId) {
        List<Post> posts = postsRepository.findByUserId(userId);
        return posts.stream().map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

}
