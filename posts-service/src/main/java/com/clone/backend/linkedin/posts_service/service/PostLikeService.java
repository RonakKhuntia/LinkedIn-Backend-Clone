package com.clone.backend.linkedin.posts_service.service;

import com.clone.backend.linkedin.posts_service.entity.PostLike;
import com.clone.backend.linkedin.posts_service.exception.BadRequestException;
import com.clone.backend.linkedin.posts_service.exception.ResourceNotFoundException;
import com.clone.backend.linkedin.posts_service.repository.PostLikeRepository;
import com.clone.backend.linkedin.posts_service.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostsRepository postsRepository;
    public void likePost(Long postId, Long userId) {
        boolean exists = postsRepository.existsById(postId);
        if (!exists) {
            throw new ResourceNotFoundException("Post Not Found with id " + postId);
        }
        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if (alreadyLiked) {
            throw new BadRequestException("Cannot like the same post again!");
        }
        PostLike postLike = new PostLike();
        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLikeRepository.save(postLike);
    }

    @Transactional
    public void unlikePost(Long postId, Long userId) {
        boolean exists = postsRepository.existsById(postId);
        if (!exists) {
            throw new ResourceNotFoundException("Post Not Found with id " + postId);
        }
        boolean alreadyLiked = postLikeRepository.existsByUserIdAndPostId(userId, postId);
        if (!alreadyLiked) {
            throw new BadRequestException("Cannot unlike the post that was not liked previously");
        }
        postLikeRepository.deleteByUserIdAndPostId(userId, postId);
    }

}
